package org.example.backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;

public class UserSignUp {

    private UserJson db = UserJson.getdb();

    public UserSignUp() throws IOException {
    }

    public boolean signUp(User newUser) {
        try {
            for (JsonNode userNode : db.rootNode) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                User user = objectMapper.treeToValue(userNode, User.class);

                if (user.getEmail().equals(newUser.getEmail())) {
                    return false;
                }
            }

        newUser.setPassword(newUser.getPassword());
            System.out.println("Debug");
            db.editUser(newUser);
            return true;
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
