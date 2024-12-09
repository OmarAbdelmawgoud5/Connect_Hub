package org.example.backend;
import com.fasterxml.jackson.databind.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class UserJson {


    JsonNode rootNode;

    public UserJson() throws IOException {
        LoadUser();
    }
    public synchronized  void editUser(User user) throws IOException {
        ObjectNode objectNode = (ObjectNode) rootNode;
        objectNode.remove(user.getUserId());
        objectNode.put(user.getUserId(), user.toJsonNode());
        SaveJson();
    }

    public synchronized Map<String, User> getmap() throws IOException {
        Map<String, User> mp = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            JsonNode userNode = entry.getValue();
            User user = objectMapper.readValue(userNode.toString(), User.class);
            mp.put(entry.getKey(), user);
        }
        return mp;
    }
    public synchronized  void SaveJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(FileGenerator.getFile(DatabaseFiles.USERS_DB),rootNode);
        System.out.println(rootNode.toString());
    }
    public synchronized  void LoadUser() throws IOException {
        Map<User,Integer> mp=new HashMap<>();
        ArrayList<Map<User,Integer>> temp=new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        rootNode = objectMapper.readTree(FileGenerator.getFile(DatabaseFiles.USERS_DB));
    }
    public synchronized  User LoadUser(String id) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        System.out.println(rootNode.get(id).toString());
        User user = objectMapper.readValue(rootNode.get(id).toString(), User.class);
        return  user;
    }

}
