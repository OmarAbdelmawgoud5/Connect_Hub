package org.example.backend;

import java.time.LocalDateTime;

public abstract class ContentFactory {
    public abstract Content createContent(String contentId, String authorId, LocalDateTime timeStamp, MediaDetails content);
}
