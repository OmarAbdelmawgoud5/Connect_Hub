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
}
