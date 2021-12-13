package server.task;

import server.IDMap;

import java.io.*;
import java.net.Socket;

class MethodTask extends Thread{
    protected final Socket socket;
    protected final IDMap idMap;
    protected final DataInputStream input;
    protected final DataOutputStream output;
    protected final String saveDir;
    public MethodTask(Socket socket, IDMap idMap, DataInputStream input, DataOutputStream output,
                      String saveDir){
        this.socket = socket;
        this.idMap = idMap;
        this.input = input;
        this.output = output;
        this.saveDir = saveDir;
    }

    @Override
    public void run(){
        try {
            this.input.close();
            this.output.close();
            this.socket.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}