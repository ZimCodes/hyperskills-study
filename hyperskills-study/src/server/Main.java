package server;

import java.net.*;
import java.io.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import util.*;
import com.google.gson.Gson;
import server.response.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.Lock;

import server.task.*;

public class Main {
    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 5000;
    private static final String dbPath = "[SERVER_DATA_DIR_PATH]\\db.json";

    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))) {
            System.out.println("Server started!");
            ExecutorService executor = Executors.newFixedThreadPool(4);
            Gson gson = new Gson();
            final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
            final Lock rLock = readWriteLock.readLock();
            final Lock wLock = readWriteLock.writeLock();
            String method = "";
            while (!"exit".equals(method)) {
                Socket socket = server.accept();
                Map<String, JsonElement> dbMap = FileManager.readFile(gson, dbPath, rLock);
                if (dbMap == null) {
                    dbMap = new HashMap<>();
                }
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                String reqJSON = input.readUTF();
                JsonElement jsonElement = JsonParser.parseString(reqJSON);
                JsonObject jsonObj = jsonElement.getAsJsonObject();
                Args argParse = new Args();
                for (Map.Entry<String, JsonElement> req : jsonObj.entrySet()) {
                    String key = req.getKey();
                    switch (key) {
                        case "type":
                            argParse.type = req.getValue().getAsString();
                            break;
                        case "key":
                            argParse.keyJSON = req.getValue();
                            break;
                        case "value":
                            argParse.valueJSON = req.getValue();
                            break;
                        default:
                            break;
                    }
                }
                try {
                    method = argParse.type;
                    if ("exit".equals(method)) {
                        Response res = new Response();
                        String resJSON = gson.toJson(res);
                        output.writeUTF(resJSON);
                        break;
                    } else {
                        if ("set".equalsIgnoreCase(method)) {
                            executor.submit(new SetTask(socket, gson, input, output, dbMap,
                                    argParse, dbPath, wLock));
                        } else if ("get".equalsIgnoreCase(method)) {
                            executor.submit(new GetTask(socket, gson, input, output, dbMap, argParse));
                        } else if ("delete".equalsIgnoreCase(method)) {
                            executor.submit(new DeleteTask(socket, gson, input, output, dbMap,
                                    argParse, dbPath, wLock));
                        } else {
                            Response res = new ErrorResponse("No such key");
                            String resJSON = gson.toJson(res);
                            output.writeUTF(resJSON);
                            input.close();
                            output.close();
                            socket.close();
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    Response res = new ErrorResponse("No such key");
                    String resJSON = gson.toJson(res);
                    output.writeUTF(resJSON);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
