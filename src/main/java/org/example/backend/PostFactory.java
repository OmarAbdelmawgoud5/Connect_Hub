package org.example.backend;

import java.time.LocalDateTime;

public class PostFactory extends ContentFactory{
    @Override
    public Post createContent(String contentId, String authorId, LocalDateTime timeStamp, MediaDetails content){
        return  new Post(contentId, authorId, timeStamp, content);
    }

    public Post createContent( String authorId, LocalDateTime timeStamp, MediaDetails content){
        return  new Post(authorId, timeStamp, content);
    }
}
