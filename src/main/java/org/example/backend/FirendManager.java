package org.example.backend;

import java.util.ArrayList;
import java.util.Map;

public class FirendManager {
    ArrayList<User> friends=new ArrayList<>();
    ArrayList<User> blocked=new ArrayList<>();
    ArrayList<User> pending=new ArrayList<>();
    ArrayList<User> Requested=new ArrayList<>();

    public ArrayList<User> getFriends() {
        return friends;
    }

    public ArrayList<User> getBlocked() {
        return blocked;
    }

    public ArrayList<User> getPending() {
        return pending;
    }

    public ArrayList<User> getRequested() {
        return Requested;
    }

    public FirendManager(Map<User, Integer> map)
    {
        for (Map.Entry<User,Integer> entry : map.entrySet())
        {
                switch (entry.getValue())
                {
                    case 4:
                        friends.add(entry.getKey());
                        break;
                    case 1:
                        blocked.add(entry.getKey());
                        break;
                    case 2:
                        pending.add(entry.getKey());
                        break;
                    case 3:
                        Requested.add(entry.getKey());
                        break;
                }



        }
    }
}
