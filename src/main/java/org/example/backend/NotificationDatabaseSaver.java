package org.example.backend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NotificationDatabaseSaver {

    private static final File notificationsFile = new File(DatabaseFiles.NOTIFICATIONS_DB);
    private static final ObjectMapper mapper = new ObjectMapper();

    public synchronized static void saveNotification(String userId, NotificationAbstract notification) throws IOException {
        Map<String, List<Map<String, Object>>> notificationData = loadData();

        List<Map<String, Object>> userNotifications = notificationData.computeIfAbsent(userId, k -> new ArrayList<>());

        userNotifications.add(notification.toMap());

        saveData(notificationData);
    }

    public synchronized static void updateNotificationStatus(String notificationId, String userId, boolean isRead) throws IOException {
        Map<String, List<Map<String, Object>>> notificationData = loadData();
        List<Map<String, Object>> userNotifications = notificationData.get(userId);

        if (userNotifications != null) {
            for (Map<String, Object> notification : userNotifications) {
                if (notification.get("notificationId").equals(notificationId)) {
                    notification.put("isRead", isRead);  
                    break;
                }
            }
            saveData(notificationData);
        }
    }

    private synchronized static Map<String, List<Map<String, Object>>> loadData() throws IOException {
        if (!notificationsFile.exists()) {
            notificationsFile.createNewFile();
            return new java.util.HashMap<>();
        }
        try {
            return mapper.readValue(notificationsFile, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new IOException("Error loading data from notifications file.", e);
        }
    }

    private synchronized static void saveData(Map<String, List<Map<String, Object>>> data) throws IOException {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(notificationsFile, data);
        } catch (IOException e) {
            throw new IOException("Error saving data to notifications file.", e);
        }
    }
}
