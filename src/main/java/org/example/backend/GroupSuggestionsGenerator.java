package org.example.backend;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.example.backend.FriendsManagerReader.getFriends;

public class GroupSuggestionsGenerator {
    public static ArrayList<String>generate(User user){
        ArrayList<User>friends=new ArrayList<>();
        ArrayList<String>suggestions=new ArrayList<>();
        ArrayList<String>other=new ArrayList<>(user.getGroups().keySet());
        Map<User,Integer> allPeople;
        try {
            allPeople=new FriendsJson(user.getUserId()).getDb();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(Map.Entry<User,Integer> entry:allPeople.entrySet()){
            if(entry.getValue()==4){
                friends.add(entry.getKey());
            }

        }


        for(int i=0;i<friends.size();i++)
        {
            ArrayList<String>friendGroups=new ArrayList<>(friends.get(i).getGroups().keySet());

            for(int j=0;j<friendGroups.size();j++)
            {

                if(other.contains(friendGroups.get(j))){
                    continue;
                }
                else{
                    suggestions.add(friendGroups.get(j));
                    other.add(friendGroups.get(j));
                }
            }
        }
        if(suggestions.isEmpty())
        {
            ArrayList<Group>allGroups=GroupDBReader.getInstance().readGroups();
            for(Group u:allGroups)
            {
                if(!other.contains(u.getGroupId())){
                    suggestions.add(u.getGroupId());
                    other.add(u.getGroupId());

                }
                if(suggestions.size()==15)return suggestions;
            }

        }
        return suggestions;
    }




}
