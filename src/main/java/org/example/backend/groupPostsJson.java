package org.example.backend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class groupPostsJson {
    static public ArrayList<Content> loadPosts(String groupId) throws IOException {
        File f=FileGenerator.getFile(DatabaseFiles.GROUPS_DB);
        JsonNode rootNode;
        ObjectMapper objectMapper = new ObjectMapper();
        rootNode = objectMapper.readTree(f);
        JsonNode x=rootNode.get(groupId);
        ArrayList<Content> posts=new ArrayList<>();
        ArrayList<String> db = objectMapper.readValue(x.toString(), new TypeReference<>() {});
        for (String s : db) {
            Content c = ContentDatabaseLoaderByID.loadPost(s);
            posts.add(c);
        }
        return posts;
    }
//
//    public static void main(String[] args) throws IOException {
//        ArrayList<Content>c=groupPostsJson.loadPosts("123");
//        for (Content c1 : c) {
//            System.out.println(c1.getContent().getText());
//        }
//    }
}
