package contacts;

import java.util.List;

class View {
    public static void actionMenu() {
        System.out.print("[menu] Enter action (add, list, search, count, exit):");
    }

    public static void actionListMenu() {
        System.out.print("[list] Enter action ([number], back):");
    }

    public static void actionSearchMenu() {
        System.out.print("[search] Enter action ([number], back, again):");
    }

    public static void actionRecordMenu() {
        System.out.print("[record] Enter action (edit, delete, menu):");
    }

    public static void recordAmount(int size) {
        System.out.printf("The Phone Book has %s records.%n", size);
    }

    public static void recordFound(int size) {
        System.out.printf("Found %d results:%n", size);
    }

    public static void recordList(List<Record> recordList) {
        int index = 0;
        for (Record record : recordList) {
            index++;
            if (record.isPerson()) {
                Person person = (Person) record;
                System.out.printf("%d. %s %s%n", index, record.getName(), person.getSurName());
            } else {
                System.out.printf("%d. %s%n", index, record.getName());
            }
        }
    }

    public static void selectA(String str) {
        System.out.printf("Select a %s:", str);
    }

    public static void enterThe(String str) {
        System.out.printf("Enter the %s:", str);
    }

    public static void enterTheType() {
        enterThe("type (person, organization)");
    }

    public static void recordAdded() {
        System.out.println("The record added.");
    }

    public static void recordSaved() {
        System.out.println("Saved");
    }

    public static void line() {
        System.out.println();
    }

    public static void text(String str) {
        System.out.print(str);
    }

    public static void textln(String str) {
        System.out.println(str);
    }

    public static void wrongPhone() {
        System.out.println("Wrong number format!");
    }
}