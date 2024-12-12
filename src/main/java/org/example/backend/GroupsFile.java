package org.example.backend;

import java.io.File;

public class GroupsFile {
    private static GroupsFile instance = null;
    private File DatabaseFile;
    private GroupsFile() {
        DatabaseFile = new File(DatabaseFiles.GROUPS_DB);
    }
    public static GroupsFile getInstance() {
        if (instance == null) {
            instance = new GroupsFile();
        }
        return instance;
    }
    public File getDatabaseFile() {
        return DatabaseFile;
    }
}
