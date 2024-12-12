package org.example.backend;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

public class FriendsManagerWriter {

    public static void friendsWriter(User coreUser, User secondUser, UserAction action) throws IOException {
        Map<User, Integer> user1Map = new FriendsJson(coreUser.getUserId()).getDb();
        Map<User, Integer> user2Map = new FriendsJson(secondUser.getUserId()).getDb();

        System.out.println("Debug 2" + coreUser.getUserId() + secondUser.getUserId());

        FriendRequestNotification friendRequestNotification;
        switch (action) {
            case SendRequest:
                user1Map.put(secondUser, FriendsStatus.Sent.ordinal());
                user2Map.put(coreUser, FriendsStatus.Received.ordinal());

                friendRequestNotification = new FriendRequestNotification(
                        "notify-" + System.currentTimeMillis(),
                        secondUser.getUserId(),
                        coreUser.getUserId(),
                        LocalDateTime.now(),
                        LocalDateTime.now().plusDays(1)
                );
                NotificationDatabaseSaver.saveNotification(secondUser.getUserId(), friendRequestNotification);
                break;

            case AcceptRequest:
                user1Map.put(getRightUser(user1Map, secondUser), FriendsStatus.Friend.ordinal());
                user2Map.put(getRightUser(user2Map, coreUser), FriendsStatus.Friend.ordinal());
                break;

            case BlockFriend:

                user1Map.put(getRightUser(user1Map, secondUser), FriendsStatus.Blocking.ordinal());
                user2Map.put(getRightUser(user2Map, coreUser), FriendsStatus.Blocked.ordinal());
                break;

            case UnBlockFriend:
                user1Map.remove(getRightUser(user1Map, secondUser));
                user2Map.remove(getRightUser(user2Map, coreUser));
                break;

            case UnFriend:
                user1Map.remove(getRightUser(user1Map, secondUser));
                user2Map.remove(getRightUser(user2Map, coreUser));
                break;

            default:
                System.out.println("Unknown action.");
        }
        System.out.println("print");
        for (Map.Entry<User, Integer> entry : user1Map.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        FriendsJson friendsDatabase = new FriendsJson(coreUser.getUserId());
        friendsDatabase.editfirend(user1Map);
        FriendsJson friendsDatabase2 = new FriendsJson(secondUser.getUserId());
        friendsDatabase2.editfirend(user2Map);

    }

    private static User getRightUser(Map<User, Integer> map, User removedUser) {
        for (Map.Entry<User, Integer> entry : map.entrySet()) {
            if (entry.getKey().getUserId().equals(removedUser.getUserId())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
