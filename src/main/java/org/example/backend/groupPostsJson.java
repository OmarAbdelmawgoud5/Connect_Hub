package org.example.backend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class groupPostsJson {
    static JsonNode rootNode;
    static public ArrayList<Content> loadPosts(String contentid) throws IOException {
        File f=FileGenerator.getFile(DatabaseFiles.GROUPPOSTS_DB);
        ObjectMapper objectMapper = new ObjectMapper();
        rootNode = objectMapper.readTree(f);
        JsonNode x=rootNode.get(contentid);
        ArrayList<Content> posts=new ArrayList<>();
        if(x!=null) {
            ArrayList<String> db = objectMapper.readValue(x.toString(), new TypeReference<>() {
            });
            for (String s : db) {
                Content c = ContentDatabaseLoaderByID.loadPost(s);
                posts.add(c);
            }

            return posts;
        }
        return null;
    }
    public static synchronized  void editContent(Content c,String groupId) throws IOException {
        File f=FileGenerator.getFile(DatabaseFiles.GROUPPOSTS_DB);
        ObjectMapper objectMapper = new ObjectMapper();
        rootNode = objectMapper.readTree(f);
        ObjectNode objectNode = (ObjectNode) rootNode;
        objectNode.remove(c.getContentId());
        objectNode.put(c.getContentId(), c.toJsonNode());
        SaveJson();
    }

    static public synchronized  void SaveJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(FileGenerator.getFile(DatabaseFiles.GROUPPOSTS_DB),rootNode);
    }
}
