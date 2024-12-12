package org.example.backend;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.HashMap;
import java.util.Map;

public class MediaDetails {
    private String text;
    private String image;


    public ObjectNode toJsonNode() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode mediaNode = mapper.createObjectNode();
        mediaNode.put("text", this.text);
        mediaNode.put("image", this.image);
        return mediaNode;
    }
    public MediaDetails() {};
    public MediaDetails(String text, String image) {
        this.text = text;
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public Map<String,String> toMap() {
        Map<String,String> map = new HashMap<String,String>();
        map.put("text", text);
        map.put("image", image);
        return map;
    }
}

/*
package org.example.backend;

import java.util.HashMap;
import java.util.Map;

public class MediaDetails {
    private String text;
    private String image;

    public MediaDetails(String text, String image) {
        this.text = text;
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public Map<String,String> toMap() {
        Map<String,String> map = new HashMap<String,String>();
        map.put("text", text);
        map.put("image", image);
        return map;
    }
}*/