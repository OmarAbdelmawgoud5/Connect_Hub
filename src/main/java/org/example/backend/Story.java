package org.example.backend;

import java.time.LocalDateTime;

public class Story extends Content{
    public Story(String contentId, String authorId, LocalDateTime timeStamp,MediaDetails content) {
        super(contentId, authorId, timeStamp,content);
    }
}
