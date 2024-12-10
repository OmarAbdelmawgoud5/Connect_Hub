package org.example.backend;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class postsDisplay {
    ArrayList<Content> c=new ArrayList<>() ;
    Map<String ,User> mp=new HashMap<String ,User>() ;

    public Map<String, User> getMp() {
        return mp;
    }
    public postsDisplay (ArrayList<User> friends) throws IOException {
        for(int i=0;i<friends.size();i++) {
            mp.put(friends.get(i).getUserId(), friends.get(i));
            c.addAll(ContentDatabaseLoader.loadContent(friends.get(i).getUserId(), "post"));
            Collections.shuffle(c);
        }
    }
    public ArrayList<Content> getContent()
    {
        return c;
    }

}
