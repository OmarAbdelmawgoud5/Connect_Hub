package org.example.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ContentDatabaseSaver {
    synchronized  public static void saveContent(Content content) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File postsFile = FileGenerator.getFile(DatabaseFiles.POSTS_DB);
        File storiesFile = FileGenerator.getFile(DatabaseFiles.STORIES_DB);
        File requiredFile ;
        if(content instanceof Post) {
            requiredFile = postsFile;
        }
        else if(content instanceof Story) {
            requiredFile = storiesFile;
        }
        else{
            throw new IllegalArgumentException("this type of content is not supported");
        }

        Map<String, ArrayList<Map<String,Object>>> contentData;
        Map<String,Object>newContentData=content.toMap();
        contentData= mapper.readValue(requiredFile, new TypeReference<>() {});

        if(contentData.containsKey(content.getAuthorId())) {
         ArrayList < Map<String,Object> >userContent= contentData.get(content.getAuthorId());
         userContent.add(newContentData);
        }

        else {
            ArrayList<Map<String,Object>> newArrayList=new ArrayList<>();
            newArrayList.add(newContentData);
            contentData.put(content.getAuthorId(),newArrayList);
        }

        mapper.writerWithDefaultPrettyPrinter().writeValue(requiredFile , contentData);
    }
}
