package org.example.backend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ContentDatabaseLoader {

    public static ArrayList<Content> loadContent(String userId,String contentType) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Content> extractedContent = new ArrayList<>();

        File postsFile = new File(DatabaseFiles.POSTS_DB);
        File storiesFile = new File(DatabaseFiles.STORIES_DB);
        File requiredFile ;
        if(contentType.equals("post")){
            requiredFile = postsFile;
        }
        else if(contentType.equals("story")){
            requiredFile = storiesFile;
        }
        else{
            throw new IOException(contentType);
        }
        Map<String, List<Map<String, Object>>> contentData;
        try {


            contentData =
                    mapper.readValue(new File("D:\\College\\Term 5\\Programming 2\\lab9\\MergeVersion\\Connect_Hub\\src\\main\\resources\\posts.json"), new TypeReference<>() {
                    });

        }
        catch (RuntimeException e) {

            return null;
        }

        System.out.println("Omar");
        List<Map<String, Object>> userContent = contentData.get(userId);


        if (userContent == null) {
            return extractedContent;
        }


        for (Map<String, Object> singleUserContent : userContent) {
            String contentId = (String) singleUserContent.get("contentId");
            LocalDateTime timeStamp = LocalDateTime.parse((String) singleUserContent.get("timeStamp"));
            Map<String, String> mediaDetails = mapper.convertValue(singleUserContent.get("content"), new TypeReference<>() {});

            validateField(contentId, "contentId");
            validateField(timeStamp, "timeStamp");
            validateField(mediaDetails.get("text"), "text");

            MediaDetails media = new MediaDetails(mediaDetails.get("text"), mediaDetails.get("image"));
            Content content=ContentFactoryRegistry.getInstance().getContentFactory(contentType).createContent(contentId,userId,timeStamp,media);
            extractedContent.add(new Post(contentId, userId, timeStamp, media));
        }

        return extractedContent;
    }

    private static void validateField(Object field, String fieldName) {
        if (field == null || field.toString().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " is missing or empty.");
        }
    }
}
