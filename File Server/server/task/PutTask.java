package server.task;

import server.IDMap;
import util.FileManager;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class PutTask extends MethodTask{
    private final Random rand;

    public PutTask(Socket socket, IDMap idMap, DataInputStream input, DataOutputStream output,
                   String saveDir) {
        super(socket, idMap, input, output, saveDir);
        this.rand = new Random();
    }

    @Override
    public void run() {
        try {
            String serverFile = this.input.readUTF();
            serverFile = serverFile.isEmpty() ? "unknown" + this.rand.nextInt() + ".txt" :
                    serverFile;
            File file = new File(this.saveDir + serverFile);
            if (file.exists()) {
                this.output.writeUTF("403");
            } else {
                FileManager.writeFile(this.input, file.getPath());
                int hash = Objects.hash(serverFile);
                String id = String.format("%d", Math.abs(hash));
                this.idMap.put(id, serverFile);
                this.output.writeUTF("200 " + id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            super.run();
        }
    }
}