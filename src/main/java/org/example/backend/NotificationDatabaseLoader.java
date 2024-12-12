package org.example.backend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NotificationDatabaseLoader {

    private static final ObjectMapper mapper = new ObjectMapper();

    synchronized public static ArrayList<NotificationAbstract> loadNotifications(String recipientUserId) throws IOException {
        ArrayList<NotificationAbstract> extractedNotifications = new ArrayList<>();

        File notificationsFile = new File(DatabaseFiles.NOTIFICATIONS_DB);

        Map<String, List<Map<String, Object>>> notificationData;
        try {
            notificationData = mapper.readValue(notificationsFile, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new IOException("Failed to load notifications file", e);
        }

        List<Map<String, Object>> userNotifications = notificationData.get(recipientUserId);
        if (userNotifications == null || userNotifications.isEmpty()) {
            return extractedNotifications;
        }

        for (Map<String, Object> singleNotification : userNotifications) {
            String notificationId = (String) singleNotification.get("notificationId");
            String senderId = (String) singleNotification.get("senderId");
            String notificationMessage = (String) singleNotification.get("message");
            String notificationType = (String) singleNotification.get("type");

            if (notificationId == null || notificationMessage == null || notificationType == null || senderId == null) {
                continue;
            }

            LocalDateTime timeStamp = parseDateTime((String) singleNotification.get("timeStamp"));
            LocalDateTime expireTime = parseDateTime((String) singleNotification.get("expireTime"));

            boolean isRead = (boolean) singleNotification.get("isRead");

            NotificationAbstract notification = null;

            switch (notificationType) {
                case "friend request":
                    notification = new FriendRequestNotification(notificationId, recipientUserId, senderId, timeStamp, expireTime);
                    break;
                case "group activity":
                    notification = new GroupActivityNotification(notificationId, recipientUserId, senderId, timeStamp, expireTime);
                    break;
                case "new post":
                    notification = new NewPostNotification(notificationId, recipientUserId, senderId, timeStamp, expireTime);
                    break;
                default:
                    continue;
            }

            if (notification != null && !isRead) {
                extractedNotifications.add(notification);
            }
        }

        return extractedNotifications;
    }

    private static LocalDateTime parseDateTime(String dateTimeString) {
        try {
            return LocalDateTime.parse(dateTimeString);
        } catch (Exception e) {
            return null;
        }
    }

    private synchronized static void validateField(Object field, String fieldName) {
        if (field == null || field.toString().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " is missing or empty.");
        }
    }
}
