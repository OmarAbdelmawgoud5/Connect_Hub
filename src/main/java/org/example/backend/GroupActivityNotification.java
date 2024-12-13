package org.example.backend;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

public class GroupActivityNotification extends NotificationAbstract {

    private static final String TYPE = "group activity";

    public GroupActivityNotification(String notificationId, String recipientId, String senderId,
            LocalDateTime timeStamp, LocalDateTime expireTime) throws IOException {
        super(notificationId, recipientId, senderId, TYPE, timeStamp, expireTime, generateMessage(senderId));
    }

    private static String generateMessage(String senderId) throws IOException {
        GettingGroupByGroupId gettingGroup = new GettingGroupByGroupId();
        Group group = gettingGroup.getGroup(senderId);

        if (group != null) {
            return "You have added been added to the group : " + group.getName();
        } else {
            return "You have added been added to the group";
        }
    }

    @Override
    public String getMessage() {
        return message;
    }
}
