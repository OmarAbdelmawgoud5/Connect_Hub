package org.example.backend;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileGenerator {

    static Map<String,File> map=new HashMap<String,File>();

    static File getFile(String path)
    {
        File f;
        if(map.containsKey(path))
        {
            return map.get(path);
        }
        else
        {
            f=new File(path);
            map.put(path,f);
        }
        return f;
    }
}
