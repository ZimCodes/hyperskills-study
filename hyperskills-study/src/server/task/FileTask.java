package server.task;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import util.Args;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.locks.Lock;

abstract class FileTask extends Task {
    protected final String saveFilePath;
    protected final Lock wLock;

    public FileTask(Socket socket, Gson gson, DataInputStream in, DataOutputStream out, Map<String,
            JsonElement> dbMap, Args argParse, String saveFilePath, Lock wLock) {
        super(socket, gson, in, out, dbMap, argParse);
        this.saveFilePath = saveFilePath;
        this.wLock = wLock;
    }
}