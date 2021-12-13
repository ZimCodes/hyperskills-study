package server.task;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import server.response.Response;
import util.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.locks.Lock;

public class SetTask extends FileTask {

    public SetTask(Socket socket, Gson gson, DataInputStream in, DataOutputStream out, Map<String,
            JsonElement> dbMap, Args argParse, String saveFilePath, Lock wLock) {
        super(socket, gson, in, out, dbMap, argParse, saveFilePath, wLock);
    }

    private void setMap() {
        if (this.argParse.keyJSON.isJsonPrimitive()) {
            this.dbMap.put(this.argParse.keyJSON.getAsString(), this.argParse.valueJSON);
            FileManager.writeFile(this.gson, this.dbMap, this.saveFilePath, this.wLock);
        } else {
            JsonArray initArr = this.argParse.keyJSON.getAsJsonArray();
            int index = 0;
            if (initArr.size() == 1) {
                String key = initArr.get(index).getAsString();
                this.dbMap.put(key, this.argParse.valueJSON);
                FileManager.writeFile(this.gson, this.dbMap, this.saveFilePath, this.wLock);
            } else {
                JsonElement tree = this.gson.toJsonTree(this.dbMap);
                JsonObject treeObj = tree.getAsJsonObject();
                this.recurseSet(initArr, index, treeObj);
                FileManager.writeFile(this.gson, tree, this.saveFilePath, this.wLock);
            }
        }
    }

    private void recurseSet(JsonArray arr, int index, JsonObject tree) {
        if (arr.size() == 0) {
            return;
        }
        if (index == arr.size() - 1) {
            String key = arr.get(index).getAsString();
            tree.add(key, this.argParse.valueJSON);
            return;
        }
        String key = arr.get(index).getAsString();
        JsonObject newObj = null;
        if (!tree.has(key)) {
            newObj = new JsonObject();
            tree.add(key, newObj);
        } else {
            newObj = tree.getAsJsonObject(key);
        }
        this.recurseSet(arr, ++index, newObj);
    }

    @Override
    public void run() {
        try {
            this.setMap();
            Response res = new Response();
            String resJSON = this.gson.toJson(res);
            this.out.writeUTF(resJSON);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            super.run();
        }
    }
}