package server;

import java.io.IOException;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.util.*;

public class IDMap implements Serializable {
    private final static long serialVersionUID = 1L;
    public volatile Map<String, String> map;

    public IDMap() {
        map = new HashMap<>();
    }

    public void put(String id, String fileName) {
        map.put(id, fileName);
    }

    public String get(String id) {
        return map.get(id);
    }

    public void deleteByName(String fileName) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equals(fileName)) {
                map.remove(entry.getKey());
                break;
            }
        }
    }

    private void readObject(ObjectInputStream in) {
        try {
            in.defaultReadObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}