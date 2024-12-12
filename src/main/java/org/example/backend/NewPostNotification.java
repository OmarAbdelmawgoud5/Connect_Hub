package org.example.backend;

import java.io.IOException;
import java.time.LocalDateTime;

public class NewPostNotification extends NotificationAbstract {

    private static final String TYPE = "new post";

    public NewPostNotification(String notificationId, String recipientId, String senderId,
            LocalDateTime timeStamp, LocalDateTime expireTime) throws IOException {
        super(notificationId, recipientId, senderId, TYPE, timeStamp, expireTime, generateMessage(senderId));
    }

    private static String generateMessage(String senderId) throws IOException {
        GettingUserByUserId gettingUser = new GettingUserByUserId();
        User user = gettingUser.getUser(senderId);

        if (user != null) {
            return " " + user.getUserName();
        } else {
            return "";
        }
    }

    @Override
    public String getMessage() {
        return message;
    }
}
