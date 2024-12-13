package org.example.backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.ArrayList;

public class ContentDatabaseLoaderByID {
    public static Content loadPost(String id) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        JsonNode rootNode= objectMapper.readTree(FileGenerator.getFile(DatabaseFiles.GROUPPOSTS_DB));
        if(rootNode==null) return null;
        Content c = objectMapper.readValue(rootNode.get(id).toString(), Post.class);
        return c;
    }
}
