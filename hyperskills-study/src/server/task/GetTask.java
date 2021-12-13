package server.task;

import com.google.gson.*;
import server.response.ErrorResponse;
import server.response.Response;
import server.response.ValResponse;
import util.Args;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class GetTask extends Task {

    public GetTask(Socket socket, Gson gson, DataInputStream in, DataOutputStream out, Map<String,
            JsonElement> dbMap, Args argParse) {
        super(socket, gson, in, out, dbMap, argParse);
    }
    private JsonElement getMap() {
        if (this.argParse.keyJSON.isJsonPrimitive()) {
            String key = this.argParse.keyJSON.getAsString();
            return this.gson.toJsonTree(this.dbMap.get(key));
        } else {
            JsonArray initArr = this.argParse.keyJSON.getAsJsonArray();
            int index = 0;
            if (initArr.size() == 1){
                String key = initArr.get(index).getAsString();
                return this.gson.toJsonTree(this.dbMap.get(key));
            }else{
                JsonElement tree = this.gson.toJsonTree(this.dbMap);
                JsonObject treeObj = tree.getAsJsonObject();
                return this.targetGet(initArr, index, treeObj);
            }
        }
    }

    private JsonElement targetGet(JsonArray arr, int index, JsonObject tree){
        if (arr.size() == 0) {
            return null;
        }
        if (index == arr.size() - 1) {
            String key = arr.get(index).getAsString();
            return tree.get(key);
        }
        String key = arr.get(index).getAsString();
        if (tree.has(key)) {
            JsonObject newObj = tree.getAsJsonObject(key);
            return this.targetGet(arr, ++index, newObj);
        }
        return null;
    }

    @Override
    public void run() {
        try {
            JsonElement val = this.getMap();
            boolean isDataValid = val != null;
            Response res = isDataValid ? new ValResponse(val)
                    : new ErrorResponse("No such key");
            String resJSON = this.gson.toJson(res);
            this.out.writeUTF(resJSON);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            super.run();
        }
    }
}