package server.task;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import server.response.ErrorResponse;
import server.response.Response;
import util.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.locks.Lock;

public class DeleteTask extends FileTask {

    public DeleteTask(Socket socket, Gson gson, DataInputStream in, DataOutputStream out, Map<String,
            JsonElement> dbMap, Args argParse, String saveFilePath, Lock wLock) {
        super(socket, gson, in, out, dbMap, argParse, saveFilePath, wLock);
    }

    private boolean deleteMap() {
        if (this.argParse.keyJSON.isJsonPrimitive()) {
            JsonElement removedEl = this.dbMap.remove(this.argParse.keyJSON.getAsString());
            FileManager.writeFile(this.gson, this.dbMap, this.saveFilePath, this.wLock);
            return removedEl != null;
        } else {
            JsonArray initArr = this.argParse.keyJSON.getAsJsonArray();
            int index = 0;
            if (initArr.size() == 1) {
                String key = initArr.get(index).getAsString();
                JsonElement removedEl = this.dbMap.remove(key);
                FileManager.writeFile(this.gson, this.dbMap, this.saveFilePath, this.wLock);
                return removedEl != null;
            } else {
                JsonElement tree = this.gson.toJsonTree(this.dbMap);
                JsonObject treeObj = tree.getAsJsonObject();
                boolean hasDeleted = this.targetDelete(initArr, index, treeObj) != null;
                FileManager.writeFile(this.gson, tree, this.saveFilePath, this.wLock);
                return hasDeleted;
            }
        }
    }

    private JsonElement targetDelete(JsonArray arr, int index, JsonObject tree) {
        if (arr.size() == 0) {
            return null;
        }
        if (index == arr.size() - 1) {
            String key = arr.get(index).getAsString();
            return tree.remove(key);
        }
        String key = arr.get(index).getAsString();
        if (tree.has(key)) {
            JsonObject newObj = tree.getAsJsonObject(key);
            return this.targetDelete(arr, ++index, newObj);
        }
        return null;
    }

    @Override
    public void run() {
        try {
            boolean isDataValid = this.deleteMap();
            Response res = isDataValid ? new Response() :
                    new ErrorResponse("No such key");
            String resJSON = gson.toJson(res);
            this.out.writeUTF(resJSON);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            super.run();
        }
    }
}