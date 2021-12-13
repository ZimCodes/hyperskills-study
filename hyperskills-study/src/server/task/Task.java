package server.task;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import util.Args;

class Task implements Runnable {
    protected final Socket socket;
    protected final Gson gson;
    protected final DataInputStream in;
    protected final DataOutputStream out;
    protected final Map<String, JsonElement> dbMap;
    protected final Args argParse;


    public Task(Socket socket, Gson gson, DataInputStream in, DataOutputStream out, Map<String,
            JsonElement> dbMap,
                Args argParse) {
        this.socket = socket;
        this.gson = gson;
        this.out = out;
        this.in = in;
        this.argParse = argParse;
        this.dbMap = dbMap;
    }



    @Override
    public void run() {
        try {
            this.in.close();
            this.out.close();
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}