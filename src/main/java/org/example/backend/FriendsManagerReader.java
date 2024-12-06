package org.example.backend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FriendsManagerReader {
    public static ArrayList<User> getFriends(String id,FriendsStatus status) throws IOException {

        FriendsJson friendsJson=new FriendsJson(id);
        ArrayList<User> friends=new ArrayList<>();
        Map<User,Integer>fullMap;
        fullMap=friendsJson.getDb();
        for(Map.Entry<User,Integer> entry:fullMap.entrySet()){
            if(entry.getValue()==status.ordinal()){
                friends.add(entry.getKey());
            }
        }
        return friends;
    }
}
