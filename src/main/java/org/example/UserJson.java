package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.User;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
public class UserJson {
    ObjectMapper c;
    ArrayList<User> h;
    UserJson()
    {
        LoadJson();
    }
    File x=new File("D:\\College\\Term 5\\Programming 2\\lab9\\ConnectHub\\src\\main\\resources\\Users.Json");
    ArrayList<User> LoadJson() {

        c=new ObjectMapper();c.registerModule(new JavaTimeModule());
        try {
            h= c.readValue(x,  new TypeReference<ArrayList<User>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return h;
    }
    void SaveJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        for (User u : h) {
            writer.writeValue(new File("D:/dataTwo.json"), u);
        }
    }
    User LoadUser(String id) {
        for(User u:h) {
            if(u.id.equals(id)) {
                return u;
            }
        }
        return null;
    }
}
