package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        LocalDate c = LocalDate.now();
          var x=new User("yousef","yousefe;hezawy75",c,"D:\\Me\\vv.jpg", "D:\\Me\\vv.jpg","2611210y","online","153");
        new Profile(x);
        var y=new User("Omar","yousefe;hezawy75",c,"D:\\Me\\vv.jpg", "D:\\Me\\vv.jpg","2611210y","online","153");
        var z=new User("eyad","yousefe;hezawy75",c,"D:\\Me\\vv.jpg", "D:\\Me\\vv.jpg","2611210y","online","153");

        Map<User, Integer> map = new HashMap<>();

        map.put(x, 30);
        map.put(y, 6);

        Map<User,Map> m=new HashMap<>();
        m.put(z,map);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File("output.json"), m);

    }
}