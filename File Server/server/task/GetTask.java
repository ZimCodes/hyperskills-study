package server.task;

import server.IDMap;
import util.FileManager;

import java.io.*;
import java.net.Socket;

public class GetTask extends RetrieveTask {

    public GetTask(Socket socket, IDMap idMap, DataInputStream input, DataOutputStream output,
                   String saveDir,
                   String[] splitMSG) {
        super(socket, idMap, input, output, saveDir, splitMSG);
    }

    @Override
    protected void sendResponse(File file, String fileName) throws IOException{
        this.output.writeUTF("200");
        FileManager.sendFile(this.output, file.getPath());
    }
}