package org.example.backend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class GroupDBWriter {

    public static void addGroup(Group group) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Map<String, Object>> map = new HashMap<>();
        try {
            map = mapper.readValue(GroupsFile.getInstance().getDatabaseFile(), new TypeReference<>() {
            });
            map.put(group.getGroupId(), group.toMap());

            mapper.writerWithDefaultPrettyPrinter().writeValue(GroupsFile.getInstance().getDatabaseFile(), map);
        } catch (IOException e) {
            throw new RuntimeException("Error while adding a group: ", e);
        }
    }

    public static void addMemberToGroup(String groupId, String memberId, String senderId) throws IOException {
        Group group = GroupDBReader.getInstance().readGroups(groupId);

        if (group == null) {
            throw new IllegalArgumentException("Group not found with ID: " + groupId);
        }

        if (!group.getMembersId().contains(memberId)) {
            group.addMember(memberId);
            addGroup(group);

            NotificationAbstract notification = new GroupActivityNotification(
                    "notif-" + System.currentTimeMillis(),
                    memberId,
                    groupId,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusDays(1)
            );

            NotificationDatabaseSaver.saveNotification(memberId, notification);
        }
    }

    public static void addContentToGroup(String groupId, String contentId, String senderId) throws IOException {
        Group group = GroupDBReader.getInstance().readGroups(groupId);

        if (group == null) {
            throw new IllegalArgumentException("Group not found with ID: " + groupId);
        }

        group.addContent(contentId);
        addGroup(group);

        for (String memberId : group.getMembersId()) {
            if (!memberId.equals(senderId)) {
                NotificationAbstract notification = new GroupActivityNotification(
                        "notif-" + System.currentTimeMillis(),
                        memberId,
                        groupId,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusDays(1)
                );

                NotificationDatabaseSaver.saveNotification(memberId, notification);
            }
        }
    }
}
