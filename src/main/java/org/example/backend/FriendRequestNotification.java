package org.example.backend;

import java.io.IOException;
import java.time.LocalDateTime;

public class FriendRequestNotification extends NotificationAbstract {

    private static final String TYPE = "friend request";

    public FriendRequestNotification(String notificationId, String recipientId, String senderId,
            LocalDateTime timeStamp, LocalDateTime expireTime) throws IOException {
        super(notificationId, recipientId, senderId, TYPE, timeStamp, expireTime, generateMessage(senderId));
    }

    private static String generateMessage(String senderId) throws IOException {
        GettingUserByUserId gettingUser = new GettingUserByUserId();
        User user = gettingUser.getUser(senderId);

        if (user != null) {
            return "You have a new friend request from " + user.getUserName();
        } else {
            return "You have a new friend request.";
        }
    }

    @Override
    public String getMessage() {
        return message;
    }
}
