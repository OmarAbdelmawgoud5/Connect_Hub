package org.example.backend;

import java.time.LocalDateTime;
import java.util.Map;

public interface NotificationInterface {

    String getNotificationId();

    String getRecipientId();

    String getSenderId();

    LocalDateTime getTimeStamp();

    LocalDateTime getExpireTime();

    String getMessage();

    boolean isRead();

    void markAsRead();

    Map<String, Object> toMap();
}
