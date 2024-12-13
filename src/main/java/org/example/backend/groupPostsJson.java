package org.example.backend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class groupPostsJson {
    static JsonNode rootNode;

    public static synchronized  void editContent(Content c) throws IOException {
        File f=FileGenerator.getFile(DatabaseFiles.GROUPPOSTS_DB);
        ObjectMapper objectMapper = new ObjectMapper();
        rootNode = objectMapper.readTree(f);
        System.out.println(rootNode.toString());
        ObjectNode objectNode = (ObjectNode) rootNode;
        objectNode.remove(c.getContentId());
        objectNode.put(c.getContentId(), c.toJsonNode());
        SaveJson();
    }
    public static synchronized  void delete(Content c) throws IOException {
        File f=FileGenerator.getFile(DatabaseFiles.GROUPPOSTS_DB);
        ObjectMapper objectMapper = new ObjectMapper();
        rootNode = objectMapper.readTree(f);
        System.out.println(rootNode.toString());
        ObjectNode objectNode = (ObjectNode) rootNode;
        objectNode.remove(c.getContentId());
        SaveJson();
    }

    static public synchronized  void SaveJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(FileGenerator.getFile(DatabaseFiles.GROUPPOSTS_DB),rootNode);
    }
    public static void main(String[] args) throws IOException {
        MediaDetails md = new MediaDetails();
        md.setImage("lol");
        md.setText("pop");
        Content c=new Post("123", LocalDateTime.now(),md);
        editContent(c);
    }
}
