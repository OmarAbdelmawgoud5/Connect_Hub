package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class FriendsJson {
    User myuser;

    public FriendsJson(User myuser) {
        this.myuser = myuser;
    }

    void SaveJson()
    {
        var temp=new UserJson();
        var x=temp.LoadUser(myuser.getId());

    }

}
