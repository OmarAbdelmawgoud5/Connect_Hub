package org.example.backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import static org.example.backend.Encryption.verifyPassword;

public class GettingUserByPass {

    private UserJson db = UserJson.getdb();

    public User getUser(String password) {
        try {
            for (JsonNode userNode : db.rootNode) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                User user = objectMapper.treeToValue(userNode, User.class);

                if (verifyPassword(password, user.getPassword())) {

                    return user;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
