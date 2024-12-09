package org.example.backend;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Post extends Content {
    public Post(String authorId, LocalDateTime timeStamp,MediaDetails content) {
        super(authorId, timeStamp,content);
    }
    public Post(String contentId, String authorId, LocalDateTime timeStamp,MediaDetails content) {
        super(contentId, authorId, timeStamp,content);
    }
    // Default constructor for Jackson
    @JsonCreator
    public Post(
            @JsonProperty("contentId") String contentId,
            @JsonProperty("authorId") String authorId,
            @JsonProperty("timeStamp") String timeStamp,  // Receive as String to convert to LocalDateTime
            @JsonProperty("content") MediaDetails content) {
        super(contentId, authorId, LocalDateTime.parse(timeStamp), content);
    }
}
