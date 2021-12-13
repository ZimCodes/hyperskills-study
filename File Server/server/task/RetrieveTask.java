package server.task;

import server.IDMap;

import java.io.*;
import java.net.Socket;

abstract class RetrieveTask extends MethodTask {
    protected final String[] splitMSG;

    public RetrieveTask(Socket socket, IDMap idMap, DataInputStream input, DataOutputStream output,
                        String saveDir, String[] splitMSG) {
        super(socket, idMap, input, output, saveDir);
        this.splitMSG = splitMSG;
    }
    abstract protected void sendResponse(File file, String fileName) throws IOException;

    @Override
    public void run(){
        try {
            boolean isByID = "BY_ID".equals(this.splitMSG[1]);
            String fileName = isByID ? idMap.get(this.splitMSG[2]) : this.splitMSG[2];
            File file = new File(this.saveDir + fileName);
            if (file.exists()) {
                this.sendResponse(file,fileName);
            } else {
                output.writeUTF("404");
            }
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            super.run();
        }
    }
}