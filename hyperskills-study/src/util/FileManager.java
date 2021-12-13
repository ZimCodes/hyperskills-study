package util;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.Map;

public class FileManager {
    public static void writeFile(Gson gson, Object obj, String filePath, Lock wLock) {
        try {
            File file = new File(filePath);
            wLock.lock();
            writeFile(gson, obj, file);
        } finally {
            wLock.unlock();
        }

    }

    public static void writeFile(Gson gson, Object obj, File file){
        try{
            if (!file.exists()){
                file.createNewFile();
            }
            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(obj, writer);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map readFile(Gson gson, String filePath, Lock rLock) {
        Map readMap = null;
        File file = new File(filePath);
        if (!file.exists()){
            return null;
        }
        try(FileReader reader = new FileReader(filePath)) {
            rLock.lock();
            readMap =  gson.fromJson(reader,Map.class);
            if (readMap == null) {
                readMap = new HashMap<>();
            }
        }catch (IOException e){
            readMap = new HashMap<>();
        } finally {
            rLock.unlock();
        }
        return readMap;
    }

}