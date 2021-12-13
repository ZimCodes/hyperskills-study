package util;

import java.io.*;

public class FileManager {
    public static void writeFile(DataInputStream input, String outFilePath) {
        try (FileOutputStream fileOutput = new FileOutputStream(outFilePath);
        BufferedOutputStream bufOutput = new BufferedOutputStream(fileOutput)) {
            long totalSize = input.readLong();
            long currentSize = 0;
            int byteCount = 0;
            while (currentSize < totalSize) {
                byteCount = input.read();
                bufOutput.write(byteCount);
                currentSize += 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void sendFile(DataOutputStream out, String inFilePath){
        File file = new File(inFilePath);
        try(FileInputStream fileInput = new FileInputStream(inFilePath);
            BufferedInputStream bufInput = new BufferedInputStream(fileInput)){
            int byteCount = 0;
            out.writeLong(file.length());
            while ((byteCount = bufInput.read()) > -1) {
                out.write(byteCount);
            }
            out.write(byteCount);
        }catch (IOException | ClassCastException e){
            e.printStackTrace();
        }
    }
}