package org.example.backend;
import java.util.*;
public class SearchGroups {
    private static SearchGroups instance;
    private ArrayList<Group> groups;
    private SearchGroups() {
        groups = new ArrayList<>();
        handleGroups();
    }
    public static SearchGroups getInstance() {
        if (instance == null) {
            instance = new SearchGroups();
        }
        return instance;
    }
    private void handleGroups() {
        groups=GroupDBReader.getInstance().readGroups();
        groups.sort(Comparator.comparing((Group g) -> g.getName(), String::compareToIgnoreCase));
    }
    private int lowerBound(String name) {
        int low = 0, high = groups.size();
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (groups.get(mid).getName().compareToIgnoreCase(name) < 0) {
                low = mid + 1;
            }
            else {
                high = mid;
            }
        }
        return low;
    }
    private int upperBound(String name) {
        int low = 0, high = groups.size();
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (groups.get(mid).getName().compareToIgnoreCase(name) <= 0) {
                low = mid + 1;
            }
            else {
                high = mid;
            }
        }
        return low;
    }
    public ArrayList<Group> getGroups(String name) {
        ArrayList<Group> result = new ArrayList<>();
        int lower = lowerBound(name);
        int upper = upperBound(name);
        for (int i = lower; i < upper; i++) {
            result.add(groups.get(i));
        }
        return result;
    }
}
