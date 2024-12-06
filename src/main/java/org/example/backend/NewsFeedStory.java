package org.example.backend;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
public class NewsFeedStory {
    private String userId;
    private FriendsJson friendsJson;
    private Map<User, Integer> fr;
    private ArrayList<User> friends;
    private ArrayList<Content> stories;
    public NewsFeedStory(String userId) throws IOException {
        this.userId = userId;
        friendsJson = new FriendsJson(userId);
        fr=friendsJson.db;
    }
    public ArrayList<User> getFriends(){
        friends=new ArrayList<>();
        for (var entry : fr.entrySet()) {
            Integer value = entry.getValue();
            if (value==4)
                friends.add(entry.getKey());
        }
        return friends;
    }
    public ArrayList<Content> getStoriesForFriend(User friend) throws IOException {
        stories= new ArrayList<>();
        stories.addAll(ContentDatabaseLoader.loadContent(friend.getUserId(), "story"));
        return stories;
    }
}
