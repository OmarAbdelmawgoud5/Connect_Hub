package org.example.backend;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.example.backend.FriendsManagerReader.getFriends;

public class FriendsSuggestionsGenerator {
    public static ArrayList<User>generate(User user){
        ArrayList<User>friends=new ArrayList<>();
        ArrayList<User>other=new ArrayList<>();
        ArrayList<User>suggestions=new ArrayList<>();
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
          else {
              other.add(entry.getKey());
          }

        }


        if(friends.isEmpty())
        {

            ArrayList<User>allUsers=getAllUsers();
            for(User u:allUsers)
            {

                if(!contains(other,u)&&!u.getUserId().equals(user.getUserId())){
                    System.out.println(u.getUserId());
                    suggestions.add(u);
                    other.add(u);

                }
                if(suggestions.size()==15)return suggestions;
            }
            return suggestions;
        }

        for(int i=0;i<friends.size();i++)
        {
            ArrayList<User>friends2=new ArrayList<>();
            try {
                friends2 = FriendsManagerReader.getFriends(friends.get(i).getUserId(), FriendsStatus.Friend);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for(int j=0;j<friends2.size();j++)
            {
                if(contains(friends,friends2.get(j))||contains(other,friends2.get(j))||friends2.get(j).getUserId().equals(user.getUserId())){
                    continue;
                }
                else{
                    suggestions.add(friends2.get(j));
                    other.add(friends2.get(j));
                }

            }

        }





        return suggestions;
    }

    private static boolean contains(ArrayList<User>people,User user){
        for(User u:people){
            if(u.getUserId().equals(user.getUserId())){
                return true;
            }


        }
        return false;
    }
    private static ArrayList<User>getAllUsers(){
        try {
            return new ArrayList<>(UserJson.getdb().getmap().values())  ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
