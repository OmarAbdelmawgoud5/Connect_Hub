package org.example.backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;

public class GettingUserByUserId {

 private UserJson db = UserJson.getdb();

    public User getUser(String userId) {
        try {
            for (JsonNode userNode : db.rootNode) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                User user = objectMapper.treeToValue(userNode, User.class);

                if (user.getUserId().equals(userId)) {

                    return user;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
