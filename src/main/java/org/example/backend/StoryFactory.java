package org.example.backend;

import java.time.LocalDateTime;

public class StoryFactory extends ContentFactory {

        @Override
        public Story createContent(String contentId, String authorId, LocalDateTime timeStamp, MediaDetails content){
            return  new Story(contentId, authorId, timeStamp, content);
        }

    public Story createContent(String authorId, LocalDateTime timeStamp, MediaDetails content){
        return  new Story(authorId, timeStamp, content);
    }

    }

