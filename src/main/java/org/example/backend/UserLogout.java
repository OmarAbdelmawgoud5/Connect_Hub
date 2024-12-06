package org.example.backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;

public class UserLogout {

    private UserJson db = UserJson.getdb();

    public UserLogout() throws IOException {
    }

    public boolean logout(User x) {
        try {
            x.setStatus("Offline");
            db.editUser(x);
            return true;
            }
         catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
