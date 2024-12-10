package org.example.backend;

import java.util.HashMap;
import java.util.Map;

public class ContentFactoryRegistry {
    private static final Map<String, ContentFactory> factories = new HashMap<>();
    private static ContentFactoryRegistry instance;
    private ContentFactoryRegistry() {
        factories.put("post",new PostFactory());
        factories.put("story",new StoryFactory());
    }

    public static synchronized ContentFactoryRegistry getInstance()
    {
        if (instance == null)
            instance = new ContentFactoryRegistry();
        return instance;
    }

    public ContentFactory getContentFactory(String type) {
        return factories.get(type);
    }
}
