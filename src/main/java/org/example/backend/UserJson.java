package org.example.backend;
import com.fasterxml.jackson.databind.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class UserJson {

    static UserJson db;

    static {
        try {
            db = new UserJson();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    JsonNode rootNode;
    File x=new File("src/main/resources/Users.Json");
    static public UserJson getdb()
    {
        return db;
    }
    private UserJson() throws IOException {
        LoadUser();
    }
    public void editUser(User user) throws IOException {
        ObjectNode objectNode = (ObjectNode) rootNode;
        objectNode.remove(user.getUserId());
        objectNode.put(user.getUserId(), user.toJsonNode());
        System.out.println(user.toString());
        SaveJson();
    }
    void SaveJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("src/main/resources/Users.Json"),rootNode);
        System.out.println(rootNode.toString());
    }
    void LoadUser() throws IOException {
        Map<User,Integer> mp=new HashMap<>();
        ArrayList<Map<User,Integer>> temp=new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        rootNode = objectMapper.readTree(new File("src/main/resources/Users.Json"));
    }
    User LoadUser(String id) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        System.out.println(rootNode.get(id).toString());
        User user = objectMapper.readValue(rootNode.get(id).toString(), User.class);
        return  user;
    }
}