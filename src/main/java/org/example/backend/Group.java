package org.example.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Group {
   private ArrayList<String> contentId;
   private ArrayList<String> membersId;
   private  String name;
   private  String photoPath;
   private String groupId;


    public ArrayList<String> getContentId() {
        return contentId;
    }

    public void addContentId(String Id) {
        membersId.add(Id);
    }

    public ArrayList<String> getMembersId() {
        return membersId;
    }

    public void addMembersId(String Id) {
        membersId.add(Id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void removeMembersId(String Id) {
        membersId.remove(Id);
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
    public void removeContent(String ContentId)
    {
        contentId.remove(ContentId);
    }
    public Map<String,Object> toMap(){
        Map<String,Object> map=new HashMap<>();
        map.put("name",name);
        map.put("photoPath",photoPath);
        map.put("description",description);
        map.put("membersId",membersId);
        map.put("contentId",contentId);
        map.put("groupId",groupId);
        return map;
    }
}
