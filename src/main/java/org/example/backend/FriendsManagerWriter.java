package org.example.backend;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FriendsManagerWriter {

    public static void friendsWriter(User coreUser, User secondUser,UserAction action) throws IOException {
       Map<User,Integer>user1Map = new FriendsJson(coreUser.getUserId()).getDb();
       Map<User,Integer>user2Map = new FriendsJson(secondUser.getUserId()).getDb();


        switch (action) {
            case SendRequest:
                user1Map.put(secondUser,FriendsStatus.Sent.ordinal());
                user2Map.put(coreUser,FriendsStatus.Received.ordinal());
                break;

            case AcceptRequest:
                user1Map.put(secondUser,FriendsStatus.Friend.ordinal());
                user2Map.put(coreUser,FriendsStatus.Friend.ordinal());
                break;

            case BlockFriend:
                user1Map.put(secondUser,FriendsStatus.Blocking.ordinal());
                user2Map.put(coreUser,FriendsStatus.Blocked.ordinal());
                break;

            case UnBlockFriend:
                user1Map.remove(secondUser);
                user2Map.remove(coreUser);

            case UnFriend:
                user1Map.remove(secondUser);
                user2Map.remove(coreUser);
                break;

            default:
                System.out.println("Unknown action.");
        }
        System.out.println("print");
        for(Map.Entry<User,Integer> entry:user1Map.entrySet()){
            System.out.println(entry.getKey()+" "+entry.getValue());
        }
        FriendsJson friendsDatabase=new FriendsJson(coreUser.getUserId());
        friendsDatabase.editfirend(user1Map);
        FriendsJson friendsDatabase2=new FriendsJson(secondUser.getUserId());
        friendsDatabase2.editfirend(user2Map);

    }
}
