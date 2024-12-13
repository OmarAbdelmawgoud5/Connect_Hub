package org.example.backend;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public abstract class NotificationAbstract implements NotificationInterface {

    protected String notificationId;
    protected String recipientId;
    protected String senderId;
    protected String type;
    protected LocalDateTime timeStamp;
    protected LocalDateTime expireTime;
    protected boolean isRead;
    protected String message;

    public NotificationAbstract(String notificationId, String recipientId, String senderId, String type,
            LocalDateTime timeStamp, LocalDateTime expireTime, String message) {
        this.notificationId = notificationId;
        this.recipientId = recipientId;
        this.senderId = senderId;
        this.type = type;
        this.timeStamp = timeStamp;
        this.expireTime = expireTime;
        this.message = message;
        this.isRead = false;
    }

    @Override
    public String getNotificationId() {
        return notificationId;
    }

    @Override
    public String getRecipientId() {
        return recipientId;
    }

    @Override
    public String getSenderId() {
        return senderId;
    }

    @Override
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    @Override
    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public boolean isRead() {
        return isRead;
    }

    @Override
    public void markAsRead() {
        this.isRead = true;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("notificationId", notificationId);
        map.put("recipientId", recipientId);
        map.put("senderId", senderId);
        map.put("type", type);
        map.put("timeStamp", timeStamp.toString());
        map.put("expireTime", expireTime.toString());
        map.put("isRead", isRead);
        map.put("message", message);
        return map;
    }
}
