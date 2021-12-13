package util;
import java.util.Scanner;

public class Talker {
    public static void talk(String msg) {
        System.out.println(msg);
    }
    public static String ask(Scanner scanner, String question){
        talk(question);
        return scanner.nextLine();
    }

    public static void requestSent() {
        talk("The request was sent!");
    }

    public static void talkResponse(String msg) {
        talk("The response says " + msg);
    }
    public static String idOrName(Scanner scan,String method) {
        return ask(scan, "Do you want to " + method + " the file by name or by id (1 - name, 2 - " +
                "id):");
    }
    public static void fileNotFound() {
        talkResponse("The response says that this file is not found!");
    }
}