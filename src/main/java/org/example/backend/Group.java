package org.example.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Group {
    ArrayList<String> contentId;
    ArrayList<String> membersId;
    String name;
    String photoPath;

    String groupId;


    public ArrayList<String> getContentId() {
        return contentId;
    }

    public ArrayList<String> getMembersId() {
        return membersId;
    }

    public String getName() {
        return name;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public String getDescription() {
        return description;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    String description;
    public Group(String name, String photoPath, String description)
    {
        membersId=new ArrayList<>();
        contentId=new ArrayList<>();
            this.name=name;
            this.photoPath=photoPath;
            this.description=description;
            this.groupId= UUID.randomUUID().toString();
    }
    public Group(String name, String photoPath, String description,String groupId){
        membersId=new ArrayList<>();
        contentId=new ArrayList<>();
        this.name=name;
        this.photoPath=photoPath;
        this.description=description;
        this.groupId=groupId;
    }
    public Group() {
        membersId = new ArrayList<>();
        contentId = new ArrayList<>();
    }
    public void addMember(String MemberId)
    {
        membersId.add(MemberId);
    }
    public void addContent(String ContentId)
    {
        contentId.add(ContentId);
    }
    public Map<String,Object> toMap(){
        Map<String,Object> map=new HashMap<>();
        map.put("name",name);
        map.put("photoPath",photoPath);
        map.put("description",description);
        map.put("membersId",membersId);
        map.put("contentId",contentId);
        return map;
    }
}
