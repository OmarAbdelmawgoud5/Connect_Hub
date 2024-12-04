package org.example.backend;

import java.time.LocalDateTime;

public class Post extends Content {
    public Post(String contentId, String authorId, LocalDateTime timeStamp,MediaDetails content) {
        super(contentId, authorId, timeStamp,content);
    }
}
