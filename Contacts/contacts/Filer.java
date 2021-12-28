package contacts;

import java.io.*;

class Filer {
    public final static String FILE_NAME = "phonebook.db";
    private final static String FILE_PATH = "./" + FILE_NAME;

    public static void save(PhoneBook book) {
        try (FileOutputStream outputStream = new FileOutputStream(createFile());
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream)) {
            objectOutputStream.writeObject(book);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object load() {
        try (FileInputStream fileInputStream = new FileInputStream(createFile());
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
             ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream)) {
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new PhoneBook();
    }

    private static File createFile() {
        File file = new File(FILE_PATH);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}