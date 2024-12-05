package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.frontend.Profile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import  org.example.backend.*;
public class Main {
    public static void main(String[] args) throws IOException {
       /* FlatLightLaf.setup();
        UIManager.put( "Button.arc", 15 );

        new FriendManagementFrame();*/
        LocalDate c = LocalDate.now();
        var x=new User("yousef","yousefe;hezawy75",c,"D:\\Me\\vv.jpg", "D:\\Me\\vv.jpg","krrp","2611210y","online","123");
        new Profile(x);

        /*
        var y=new org.example.backend.User("Omar","yousefe;hezawy75",c,"D:\\Me\\vv.jpg", "D:\\Me\\vv.jpg","2611210y","online","153");
        var z=new org.example.backend.User("eyad","yousefe;hezawy75",c,"D:\\Me\\vv.jpg", "D:\\Me\\vv.jpg","2611210y","online","153");

        Map<User, Integer> map = new HashMap<>();

        map.put(x, 30);
        map.put(y, 6);

        Map<User,Map> m=new HashMap<>();
        m.put(z,map);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File("output.json"), m);*/
    }
}