package org.example.backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

public class GettingGroupByGroupId {

    private final GroupDBReader db;

    public GettingGroupByGroupId() {
        this.db = GroupDBReader.getInstance();
    }

    public Group getGroup(String groupId) {
        try {
            Group group = db.readGroups(groupId);
            if (group != null && group.getGroupId().equals(groupId)) {
                return group;
            }
        } catch (Exception e) {
            System.err.println("Error fetching group with ID: " + groupId);
            e.printStackTrace();
        }
        return null; 
    }
}
