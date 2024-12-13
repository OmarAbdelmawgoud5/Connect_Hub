package org.example.backend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GroupDBWriter {
    public static void addGroup(Group group) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Map<String,Object>> map = new HashMap<>();
        try {
            map=mapper.readValue(GroupsFile.getInstance().getDatabaseFile(), new TypeReference<>() {});
            map.put(group.getGroupId(),group.toMap());
            mapper.writerWithDefaultPrettyPrinter().writeValue(GroupsFile.getInstance().getDatabaseFile(),map);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteGroup(Group group) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Map<String,Object>> map = new HashMap<>();
        try {
            map=mapper.readValue(GroupsFile.getInstance().getDatabaseFile(), new TypeReference<>() {});
            map.remove(group.getGroupId());
            mapper.writerWithDefaultPrettyPrinter().writeValue(GroupsFile.getInstance().getDatabaseFile(),map);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
