package client;

import java.net.*;
import java.io.*;
import java.util.*;

import util.*;

class Main {
    private static final int PORT = 4010;
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final String SAVE_DIR = "[CLIENT_DATA_DIR_PATH]";

    public static void main(String[] args) {
        int retryCount = 0;
        while(retryCount < 3) {
            try (Socket socket = new Socket(IP_ADDRESS, PORT);
                 DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                 DataInputStream input = new DataInputStream(socket.getInputStream());
                 Scanner scan = new Scanner(System.in)) {
                Talker.talk("Client started!");
                String action = Talker.ask(scan, "Enter action (1 - get a file, 2 - save a file, 3 - " +
                        "delete a file," +
                        " exit - terminate server):");
                if ("exit".equals(action)) {
                    output.writeUTF("exit");
                    Talker.requestSent();
                } else {
                    if ("2".equals(action)) {
                        String clientFile = Talker.ask(scan, "What file needs to be saved: ");
                        String serverFile = Talker.ask(scan, "What is the the new name of the file on the server: ");
                        output.writeUTF("PUT");
                        output.writeUTF(serverFile);
                        FileManager.sendFile(output, SAVE_DIR + clientFile);
                        Talker.requestSent();
                        String resMSG = input.readUTF();
                        String[] splitMSG = resMSG.split(" ");
                        if ("403".equals(splitMSG[0])) {
                            Talker.talkResponse("file cannot be saved!");
                        } else {
                            Talker.talk("Response says that file is saved! ID = " + splitMSG[1]);
                        }
                    } else {
                        String getChoice = Talker.idOrName(scan, "get");
                        boolean isId = "2".equals(getChoice);
                        String nameMethod = !isId ? "BY_NAME" : "BY_ID";
                        String idOrName = Talker.ask(scan, "Enter " + (isId ? "id" : "name") + ":");
                        if ("3".equals(action)) {
                            output.writeUTF("DELETE " + nameMethod + " " + idOrName);
                            Talker.requestSent();
                            String resMSG = input.readUTF();
                            if ("404".equals(resMSG)) {
                                Talker.fileNotFound();
                            } else {
                                Talker.talkResponse("file was successfully deleted!");
                            }
                        } else {
                            output.writeUTF("GET " + nameMethod + " " + idOrName);
                            Talker.requestSent();
                            String resMSG = input.readUTF();
                            if ("404".equals(resMSG)) {
                                Talker.fileNotFound();
                            } else {
                                String fileName = Talker.ask(scan, "New name of file to save locally:");
                                FileManager.writeFile(input, SAVE_DIR + fileName);
                                Talker.talk("File saved on the hard drive!");
                            }
                        }
                    }

                }
                break;
            } catch (IOException e) {
                retryCount += 1;
                try{
                    Thread.sleep(2000);
                } catch(InterruptedException v){
                    e.printStackTrace();
                }

            }
        }
    }

}