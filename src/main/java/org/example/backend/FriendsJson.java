package org.example.backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.frontend.FriendsListPanel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FriendsJson {
    String id;
    Map<User,Integer> db;
    JsonNode rootNode;
    public FriendsJson(String id) throws IOException {
        this.id = id;
        db=LoadFirendsJson();
    }

    public void editfirend(Map<User,Integer> t) throws IOException {
        ObjectNode objectNode = (ObjectNode) rootNode;
        objectNode.remove(id);


        ObjectNode friendData = objectNode.putObject(id); // Create a nested object for this ID
        for (Map.Entry<User, Integer> entry : t.entrySet()) {
            friendData.put(entry.getKey().getUserId(), entry.getValue());
        }


        SaveJson();
    }
    void SaveJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(DatabaseFiles.FRIENDS_DB),rootNode);
        System.out.println(rootNode.toString());
    }
    public Map<User, Integer> getDb() {
        return db;
    }
    Map<User, Integer>  LoadFirendsJson() throws IOException {
        Map<User,Integer> mp=new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        // Load the JSON as a tree
        rootNode = objectMapper.readTree(new File(DatabaseFiles.FRIENDS_DB));
        var f=UserJson.getdb();
        JsonNode desiredFieldNode = rootNode.get(id);
        if(desiredFieldNode!=null) {
            System.out.println(id);
            Iterator<Map.Entry<String, JsonNode>> fields = desiredFieldNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                User h = f.LoadUser(entry.getKey());
                mp.put(h, entry.getValue().asInt());
            }
        }
        return mp;
    }


}
