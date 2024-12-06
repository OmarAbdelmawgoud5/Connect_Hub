package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.formdev.flatlaf.FlatLightLaf;
import org.example.frontend.LoginPage;
import org.example.frontend.NewsFeedFrame;
import org.example.frontend.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import  org.example.backend.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws IOException {
       FlatLightLaf.setup();
        UIManager.put( "Button.arc", 15 );
        new LoginPage();
/*
        LocalDate c = LocalDate.now();
        var x=new User("yousef","yousefe;hezawy75",c,"src/main/resources/user.png", "src/main/resources/user.png","krrp","2611210y","online","123");
        //new Profile(x);
        new NewsFeedFrame(new NewsFeedPosts(x.getUserId()),new NewsFeedStory(x.getUserId()),x);
        /*
        var y=new org.example.backend.User("Omar","yousefe;hezawy75",c,"D:\\Me\\vv.jpg", "D:\\Me\\vv.jpg","2611210y","online","153");
        var z=new org.example.backend.User("eyad","yousefe;hezawy75",c,"D:\\Me\\vv.jpg", "D:\\Me\\vv.jpg","2611210y","online","153");
    D:\College\Term 5\Programming 2\lab9\MergeVersion\Connect_Hub\src\main\resources\friendsdb.json
        Map<User, Integer> map = new HashMap<>();

        map.put(x, 30);
        map.put(y, 6);

        Map<User,Map> m=new HashMap<>();
        m.put(z,map);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File("output.json"), m);*/
    }
}