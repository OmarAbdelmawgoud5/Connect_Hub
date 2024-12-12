package org.example.backend;

import java.util.ArrayList;

public class Group {
    ArrayList<String> contentId;
    ArrayList<String> membersId;
    String name;
    String photoPath;
    public String id="123";

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

    String description;
    public Group(String name, String photoPath, String description)
    {
        membersId=new ArrayList<>();
        contentId=new ArrayList<>();
            this.name=name;
            this.photoPath=photoPath;
            this.description=description;
    }
    public void addMember(String MemberId)
    {
        membersId.add(MemberId);
    }
    public void addContent(String ContentId)
    {
        contentId.add(ContentId);
    }
}
