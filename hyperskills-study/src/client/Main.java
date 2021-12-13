package client;

import com.beust.jcommander.JCommander;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import util.Args;

import java.io.*;
import java.net.*;

public class Main {
    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 5000;

    public static void main(String[] args) {
        System.out.println("Client started!");
        Args argParse = new Args();
        Gson gson = new Gson();
        JCommander.newBuilder().addObject(argParse).build().parse(args);
        try (Socket socket = new Socket(InetAddress.getByName(ADDRESS), PORT);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            String req = argParse.input != null ? gson.toJson(argParse.input) : gson.toJson(argParse);
            output.writeUTF(req);
            System.out.println("Sent: " + req);
            String res = input.readUTF();
            JsonElement resJSON = gson.fromJson(res,JsonElement.class);
            System.out.println("Received: " + resJSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
