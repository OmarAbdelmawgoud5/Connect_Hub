package org.example.backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;

public class UserLogin {

    private UserJson db = new UserJson();
    private User user=null;

    public UserLogin() throws IOException {
    }

    public User getUser() {
        return user;
    }

    public boolean login(String email, String password) {
        try {
            for (JsonNode userNode : db.rootNode) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                user = objectMapper.treeToValue(userNode, User.class);
                if (user.getEmail().equals(email) && Encryption.verifyPassword(password, user.getPassword())) {
                    user.setStatus("Online");
                    db.editUser(user);
                    return true; 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
