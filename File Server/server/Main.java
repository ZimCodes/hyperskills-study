package server;

import java.net.*;
import java.io.*;
import java.util.concurrent.*;

import util.*;
import server.task.*;

public class Main {
    private static final int PORT = 4010;
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final String SAVE_DIR = "./data/";
    private static final String MAP_FILE = "ids.txt";

    public static void main(String[] args) {
        IDMap idMap = initIDMap();
        ExecutorService executor = Executors.newFixedThreadPool(4);
        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(IP_ADDRESS))) {
            Talker.talk("Server started!");
            while (true) {
                Talker.talk("Waiting for requests...");
                Socket socket = server.accept();
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                DataInputStream input = new DataInputStream(socket.getInputStream());
                String reqMsg = input.readUTF();
                String[] splitMSG = reqMsg.split(" ", 3);
                String method = splitMSG[0];
                if ("exit".equals(method)) {
                    break;
                }
                handleTask(socket, executor, idMap, output, input, splitMSG);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            SerializeUtil.serialize(idMap, SAVE_DIR + MAP_FILE);
            Talker.talk("Server Terminated!");
        }
    }

    private static void handleTask(Socket socket, ExecutorService executor, IDMap idMap,
                                   DataOutputStream output,
                                   DataInputStream input,
                                   String[] splitMSG) {
        Talker.talk("Handling tasks...");
        String method = splitMSG[0];
        if ("PUT".equals(method)) {
            executor.submit(new PutTask(socket, idMap, input, output, SAVE_DIR));
        } else if ("GET".equals(method)) {
            executor.submit(new GetTask(socket, idMap, input, output, SAVE_DIR, splitMSG));
        } else if ("DELETE".equals(method)) {
            executor.submit(new DeleteTask(socket, idMap, input, output, SAVE_DIR, splitMSG));
        }
    }

    static IDMap initIDMap() {
        IDMap idMap = (IDMap) SerializeUtil.deserialize(SAVE_DIR + MAP_FILE);
        if (idMap == null) {
            return new IDMap();
        }
        return idMap;
    }

}