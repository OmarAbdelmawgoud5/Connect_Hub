package org.example.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

public class GroupDBReader {
    private static GroupDBReader instance = null;
    private JsonNode rootNode;
    private GroupDBReader() {
        reload();
    }
    public void reload(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            rootNode=mapper.readTree(GroupsFile.getInstance().getDatabaseFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static GroupDBReader getInstance() {

        return new GroupDBReader();
    }

    public ArrayList<Group> readGroups() {
        ArrayList<Group> groups = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        Iterator<String> iterator = rootNode.fieldNames();
        while (iterator.hasNext()) {
            String groupId = iterator.next();
            JsonNode groupNode = rootNode.get(groupId);
            try {
                Group group = mapper.treeToValue(groupNode,Group.class);
                groups.add(group);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

      return groups;
    }
    public Group readGroups(String groupId) {
        ObjectMapper mapper = new ObjectMapper();
        Group group;
        try {
             group = mapper.readValue(rootNode.get(groupId).toString(),Group.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return group;
    }



}
