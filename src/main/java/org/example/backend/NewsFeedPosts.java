package org.example.backend;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
public class NewsFeedPosts {
    private String userId;
    private FriendsJson friendsJson;
    private Map<User, Integer> fr;
    private ArrayList<User> friends;
    public NewsFeedPosts(String userid) throws IOException {
        this.userId = userid;
        friendsJson = new FriendsJson(userid);
        fr=friendsJson.db;
    }
    public ArrayList<User> getFriends(){
        friends=new ArrayList<>();
        for (var entry : fr.entrySet()) {
            Integer value = entry.getValue();
            if (value==4)
                friends.add(entry.getKey());
        }
        Collections.shuffle(friends);
        return friends;
    }
}
