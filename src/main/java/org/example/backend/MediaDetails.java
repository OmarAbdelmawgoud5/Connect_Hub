package org.example.backend;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class MediaDetails {
    private String text;
    private String image;


    @JsonCreator
    public MediaDetails(
            @JsonProperty("image") String image,
            @JsonProperty("text") String text) {
        this.image = image;
        this.text = text;
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
