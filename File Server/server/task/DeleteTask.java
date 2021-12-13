package server.task;

import server.IDMap;

import java.io.*;
import java.net.Socket;

public class DeleteTask extends RetrieveTask{

    public DeleteTask(Socket socket, IDMap idMap, DataInputStream input, DataOutputStream output,
                      String saveDir, String[] splitMSG) {
        super(socket, idMap, input, output, saveDir, splitMSG);
    }

    @Override
    protected void sendResponse(File file, String fileName) throws IOException {
        this.idMap.deleteByName(fileName);
        this.output.writeUTF(file.delete() ? "200" : "404");
    }
}