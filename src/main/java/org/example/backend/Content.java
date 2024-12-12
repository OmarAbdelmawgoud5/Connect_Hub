package org.example.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class Content {
    private String contentId;
    private String authorId;
    private LocalDateTime timeStamp;
    private MediaDetails content;
    Content(String authorId, LocalDateTime timeStamp, MediaDetails content) {
        this.contentId = generateUniqueId();
        this.authorId = authorId;
        this.timeStamp = timeStamp;
        this.content = content;
    }
    Content(String contentId,String authorId, LocalDateTime timeStamp, MediaDetails content) {
        this.contentId = contentId;
        this.authorId = authorId;
        this.timeStamp = timeStamp;
        this.content = content;
    }

    public ObjectNode toJsonNode() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode contentNode = mapper.createObjectNode();
        contentNode.put("contentId", this.contentId);
        contentNode.put("authorId", this.authorId);
        contentNode.put("timeStamp", this.timeStamp.toString());
        contentNode.put("content", this.content.toJsonNode()); // Assuming MediaDetails has a toJsonNode method
        return contentNode;
    }


    public String generateUniqueId()
    {
        return UUID.randomUUID().toString();
    }
    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
    public MediaDetails getContent() {
        return content;
    }
    public void setContent(MediaDetails content) {
        this.content = content;
    }
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("contentId", contentId);
        map.put("timeStamp", timeStamp.toString());
        map.put("content",content.toMap());
        return map;
    }
}
