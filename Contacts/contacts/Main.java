package contacts;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        View.textln("open " + Filer.FILE_NAME);
        View.line();
        PhoneBook book = new PhoneBook();
        boolean shouldClose = false;
        while (!shouldClose) {
            View.actionMenu();
            String choice = scan.nextLine();
            switch (choice) {
                case "count":
                    View.recordAmount(book.size());
                    break;
                case "search":
                    View.text("Enter search query:");
                    String query = scan.nextLine();
                    List<Record> foundList = book.searchRecords(query);
                    View.recordFound(foundList.size());
                    View.recordList(foundList);
                    View.line();
                    int inputNum = -2;
                    while (true) {
                        View.actionSearchMenu();
                        query = scan.nextLine();
                        if ("back".equals(query)) {
                            break;
                        } else if ("again".equals(query)) {
                            View.text("Enter search query:");
                            query = scan.nextLine();
                            foundList = book.searchRecords(query);
                            View.recordList(foundList);
                        } else {
                            inputNum = Integer.parseInt(query);
                            if (inputNum <= foundList.size()){
                                View.textln(foundList.get(inputNum - 1).toString());
                            }else{
                                inputNum = -1;
                            }
                            break;
                        }
                    }
                    if (inputNum > -1) {
                        Record recordToEdit = foundList.get(inputNum - 1);
                        int recordChoice = book.getRecordIndex(recordToEdit);
                        while (true) {
                            View.actionRecordMenu();
                            query = scan.nextLine();
                            if ("edit".equals(query)) {
                                if (recordToEdit instanceof Organization) {
                                    View.selectA("field (name, address, number)");
                                } else {
                                    View.selectA("field (name, surname, birth, gender, number)");
                                }
                                String fieldChoice = scan.nextLine();
                                String input;
                                if (recordToEdit instanceof Organization) {
                                    Organization org = null;
                                    if ("name".equals(fieldChoice)) {
                                        View.text("Enter name:");
                                        input = scan.nextLine();
                                        book.editName(recordChoice, input);
                                    } else if ("number".equals(fieldChoice)) {
                                        View.text("Enter number:");
                                        input = scan.nextLine();
                                        book.editPhoneNumber(recordChoice, input);
                                    } else if ("address".equals(fieldChoice)) {
                                        View.text("Enter address:");
                                        input = scan.nextLine();
                                        org = (Organization) book.getRecord(recordChoice);
                                        org.setAddress(input);
                                    }
                                    if (org != null) {
                                        book.insertRecord(recordChoice, org);
                                    }
                                } else {
                                    Person person = null;
                                    if ("number".equals(fieldChoice)) {
                                        View.text("Enter number:");
                                        input = scan.nextLine();
                                        book.editPhoneNumber(recordChoice, input);
                                    } else if ("name".equals(fieldChoice)) {
                                        View.text("Enter name:");
                                        input = scan.nextLine();
                                        book.editName(recordChoice, input);
                                    } else if ("surname".equals(fieldChoice)) {
                                        View.text("Enter surname:");
                                        input = scan.nextLine();
                                        person = (Person) book.getRecord(recordChoice);
                                        person.setSurName(input);
                                    } else if ("birth".equals(fieldChoice)) {
                                        View.text("Enter birth:");
                                        input = scan.nextLine();
                                        person = (Person) book.getRecord(recordChoice);
                                        person.setBirthDate(input);
                                    } else if ("gender".equals(fieldChoice)) {
                                        View.text("Enter gender (M, F):");
                                        input = scan.nextLine();
                                        if (!"M".equals(input) && !"F".equals(input)) {
                                            View.textln("Bad gender!");
                                        }
                                        person = (Person) book.getRecord(recordChoice);
                                        person.setGender(input);
                                    }

                                    if (person != null) {
                                        book.insertRecord(recordChoice, person);
                                    }
                                }
                                Filer.save(book);
                                View.recordSaved();
                                View.text(book.getRecord(recordChoice).toString());
                                View.line();
                            } else if ("delete".equals(query)) {
                                book.removeRecord(recordChoice);
                                Filer.save(book);
                                View.recordSaved();
                            } else {
                                View.line();
                                break;
                            }
                        }
                    }
                    break;
                case "add":
                    Record record = null;
                    View.enterTheType();
                    String type = scan.nextLine();
                    if ("person".equals(type)) {
                        View.enterThe("name");
                        String name = scan.nextLine();
                        View.enterThe("surname");
                        String surname = scan.nextLine();
                        View.enterThe("birth date");
                        String birthDate = scan.nextLine();
                        if (birthDate.isEmpty()) {
                            View.textln("Bad birth date!");
                        }
                        View.enterThe("gender (M, F)");
                        String gender = scan.nextLine();
                        if (!"M".equals(gender) && !"F".equals(gender)) {
                            View.textln("Bad gender!");
                        }
                        View.enterThe("number");
                        String number = scan.nextLine();
                        record = new Person(name, number, surname, gender, birthDate);
                    } else if ("organization".equals(type)) {
                        View.enterThe("organization name");
                        String name = scan.nextLine();
                        View.enterThe("address");
                        String address = scan.nextLine();
                        View.enterThe("number");
                        String number = scan.nextLine();
                        record = new Organization(name, number, address);
                    }
                    book.addRecord(record);
                    Filer.save(book);
                    View.recordSaved();
                    View.line();
                    break;
                case "list":
                    View.recordList(book.getRecords());
                    View.line();
                    View.actionListMenu();
                    String listChoice = scan.nextLine();
                    if(!"back".equals(listChoice)){
                        int listChoiceNum = Integer.parseInt(listChoice);
                        Record recordEdit = book.getRecord(listChoiceNum);
                        View.text(recordEdit.toString());
                        View.line();
                        while (true) {
                            View.actionRecordMenu();
                            query = scan.nextLine();
                            if ("edit".equals(query)) {
                                if (recordEdit instanceof Organization) {
                                    View.selectA("field (name, address, number)");
                                } else {
                                    View.selectA("field (name, surname, birth, gender, number)");
                                }
                                String fieldChoice = scan.nextLine();
                                String input;
                                if (recordEdit instanceof Organization) {
                                    Organization org = null;
                                    if ("name".equals(fieldChoice)) {
                                        View.text("Enter name:");
                                        input = scan.nextLine();
                                        book.editName(listChoiceNum, input);
                                    } else if ("number".equals(fieldChoice)) {
                                        View.text("Enter number:");
                                        input = scan.nextLine();
                                        book.editPhoneNumber(listChoiceNum, input);
                                    } else if ("address".equals(fieldChoice)) {
                                        View.text("Enter address:");
                                        input = scan.nextLine();
                                        org = (Organization) book.getRecord(listChoiceNum);
                                        org.setAddress(input);
                                    }
                                    if (org != null) {
                                        book.insertRecord(listChoiceNum, org);
                                    }
                                } else {
                                    Person person = null;
                                    if ("number".equals(fieldChoice)) {
                                        View.text("Enter number:");
                                        input = scan.nextLine();
                                        book.editPhoneNumber(listChoiceNum, input);
                                    } else if ("name".equals(fieldChoice)) {
                                        View.text("Enter name:");
                                        input = scan.nextLine();
                                        book.editName(listChoiceNum, input);
                                    } else if ("surname".equals(fieldChoice)) {
                                        View.text("Enter surname:");
                                        input = scan.nextLine();
                                        person = (Person) book.getRecord(listChoiceNum);
                                        person.setSurName(input);
                                    } else if ("birth".equals(fieldChoice)) {
                                        View.text("Enter birth:");
                                        input = scan.nextLine();
                                        person = (Person) book.getRecord(listChoiceNum);
                                        person.setBirthDate(input);
                                    } else if ("gender".equals(fieldChoice)) {
                                        View.text("Enter gender (M, F):");
                                        input = scan.nextLine();
                                        if (!"M".equals(input) && !"F".equals(input)) {
                                            View.textln("Bad gender!");
                                        }
                                        person = (Person) book.getRecord(listChoiceNum);
                                        person.setGender(input);
                                    }

                                    if (person != null) {
                                        book.insertRecord(listChoiceNum, person);
                                    }
                                }
                                Filer.save(book);
                                View.recordSaved();
                                View.text(book.getRecord(listChoiceNum).toString());
                                View.line();
                            } else if ("delete".equals(query)) {
                                book.removeRecord(listChoiceNum);
                                Filer.save(book);
                                View.recordSaved();
                            } else {
                                View.line();
                                break;
                            }
                        }
                    }
                    break;
                case "exit":
                    shouldClose = true;
                    break;
            }
        }
    }
}
