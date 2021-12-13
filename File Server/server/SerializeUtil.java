package server;
import java.io.*;

class SerializeUtil {

    public static void serialize(Object obj, String fileName){
        try(FileOutputStream writer = new FileOutputStream(fileName);
            BufferedOutputStream buffer = new BufferedOutputStream(writer);
            ObjectOutputStream oos = new ObjectOutputStream(buffer)){
            oos.writeObject(obj);
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    public static Object deserialize(String fileName){
        try(FileInputStream reader = new FileInputStream(fileName);
            BufferedInputStream buffer = new BufferedInputStream(reader);
            ObjectInputStream oos = new ObjectInputStream(buffer)){
            return oos.readObject();
        }catch(FileNotFoundException e){
            File file = new File(fileName);
            try {
                file.createNewFile();
            }catch(IOException o) {
                o.printStackTrace();
            }

        }catch(EOFException e){
            System.out.println("ID file is empty!");
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
}