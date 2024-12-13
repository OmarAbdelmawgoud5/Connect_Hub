package org.example.backend;
import java.io.IOException;
import java.util.*;
public class Search {
    private static Search instance;
    private Map<String, ArrayList<String>> map;
    private Collection<User> users;
    private Search() throws IOException {
        map = new HashMap<>();
        users = new ArrayList<>();
        setData();
        setMap();
    }
    public static synchronized Search getInstance() throws IOException {
        if (instance == null) {
            instance = new Search();
        }
        return instance;
    }
    private void setData() throws IOException {
        users =new  UserJson().getmap().values();
    }
    private void setMap() {
        for (User user : users) {
            String username = user.getUserName();
            String id = user.getUserId();
            map.putIfAbsent(username, new ArrayList<>());
            map.get(username).add(id);
        }
    }
    public ArrayList<User> getUsers(String username) throws IOException {
        ArrayList<String> list = map.get(username);
        ArrayList<User> users = new ArrayList<>();
        UserJson userJson = new UserJson();
        if (list == null) {
            return new ArrayList<>();
        }
        for (String s : list) {
            users.add(userJson.LoadUser(s));
        }
        return users;
    }
}
