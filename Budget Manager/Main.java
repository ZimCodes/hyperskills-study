package budget;

import budget.sort.AllSort;
import budget.sort.CategorySort;
import budget.sort.Sort;
import budget.sort.TypeSort;

import java.util.*;
import java.io.*;

public class Main {
    private static final String FILE_PATH = "./purchases.txt";

    public static void main(String[] args) {
        actionMenu();
    }

    static void actionMenu() {
        Scanner scan = new Scanner(System.in);
        Map<String, List<String>> itemsMap = new HashMap<>();
        double wallet = 0.0;
        while (true) {
            displayMenu();
            int choice = scan.nextInt();
            System.out.println();
            if (choice == 0) {
                System.out.println("Bye!");
                break;
            } else if (choice == 1) {
                wallet += incomeAction(scan);
            } else if (choice == 2) {
                wallet -= purchaseAction(scan, itemsMap);
                if (wallet < 0) {
                    wallet = 0;
                }
            } else if (choice == 3) {
                showListAction(scan, itemsMap);
            } else if (choice == 4) {
                balanceAction(wallet);
            } else if (choice == 5) {
                saveAction(itemsMap, wallet);
            } else if (choice == 6) {
                wallet = loadAction(itemsMap, wallet);
            } else if (choice == 7) {
                analyzeAction(itemsMap,scan);
            }
        }
        scan.close();
    }

    static void analyzeAction(Map<String, List<String>> itemMap, Scanner scan) {
        boolean canContinue = true;
        while (canContinue) {
            canContinue = chooseSort(scan, itemMap);
        }
    }

    static double loadAction(Map<String, List<String>> itemMap, double currentBalance) {
        File file = new File(FILE_PATH);
        double balance = currentBalance;
        if (!file.exists()) {
            return balance;
        }
        try (Scanner scan = new Scanner(file)) {
            balance = scan.nextDouble();
            String key = "";
            while (scan.hasNext()) {
                String phrase = scan.nextLine();
                if (phrase.isEmpty()) {
                    continue;
                }
                if (phrase.endsWith(":")) {
                    key = phrase.substring(0, phrase.length() - 1);
                    itemMap.put(key, new ArrayList<>());
                } else {
                    itemMap.get(key).add(phrase);
                }
            }
            System.out.println("Purchases were loaded!\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return balance;
    }

    static void saveAction(Map<String, List<String>> items, double balance) {
        File file = new File(FILE_PATH);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println(balance);
            for (Map.Entry<String, List<String>> itemSet : items.entrySet()) {
                writer.println(itemSet.getKey() + ":");
                List<String> itemValues = itemSet.getValue();
                for (String item : itemValues) {
                    writer.println(item);
                }
            }
            System.out.println("Purchases were saved!\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static double purchaseAction(Scanner scan, Map<String, List<String>> items) {
        double totalPrice = 0;
        while (true) {
            StringBuilder sb = new StringBuilder();
            String category = choosePurchaseCategory(scan);
            if (category == null) {
                break;
            }
            System.out.println("Enter purchase name:");
            sb.append(scan.nextLine()).append(" $");
            System.out.println("Enter its price:");
            double price = scan.nextDouble();
            sb.append(String.format("%.2f", price));
            totalPrice += price;
            System.out.println("Purchase was added!\n");
            if (!items.containsKey(category)) {
                List<String> listItems = new ArrayList<>();
                listItems.add(sb.toString());
                items.put(category, listItems);
            } else {
                items.get(category).add(sb.toString());
            }
        }
        return totalPrice;
    }

    static void showListAction(Scanner scan, Map<String, List<String>> items) {
        if (items.size() == 0) {
            System.out.println("The purchase list is empty!\n");
        } else {
            while (true) {
                double total = 0.0;
                String category = chooseAllCategory(scan);
                if (category == null) {
                    System.out.println();
                    break;
                } else if (!"All".equals(category)) {
                    System.out.println("\n" + category + ":");
                    List<String> itemList = items.get(category);
                    if (itemList == null) {
                        System.out.println("The purchase list is empty!\n");
                        continue;
                    }
                    for (String item : itemList) {
                        System.out.println(item);
                        total += Compute.computeTotal(item);
                    }
                } else {
                    System.out.println("\n" + category + ":");
                    Collection<List<String>> values = items.values();
                    for (List<String> listItems : values) {
                        for (String item : listItems) {
                            System.out.println(item);
                            total += Compute.computeTotal(item);
                        }
                    }
                }
                System.out.printf("Total sum: $%.2f%n%n", total);
            }
        }
    }

    static void balanceAction(double wallet) {
        System.out.printf("Balance: $%.2f%n%n", wallet);
    }

    static double incomeAction(Scanner scan) {
        System.out.println("Enter income:");
        double income = scan.nextDouble();
        System.out.println("Income was added!\n");
        return income;
    }

    static void displayMenu() {
        System.out.printf("Choose your action:%n" +
                "1) Add income%n" +
                "2) Add purchase%n" +
                "3) Show list of purchases%n" +
                "4) Balance%n" +
                "5) Save%n" +
                "6) Load%n" +
                "7) Analyze%n" +
                "0) Exit%n");
    }

    static void displaySortMenu() {
        System.out.printf("How do you want to sort?%n" +
                "1) Sort all purchases%n" +
                "2) Sort by type%n" +
                "3) Sort certain type%n" +
                "4) Back%n");
    }

    static boolean chooseSort(Scanner scan, Map<String, List<String>> map) {
        displaySortMenu();
        String choice = scan.nextLine();
        Sort sorter = null;
        while (choice.isEmpty()) {
            choice = scan.nextLine();
        }
        System.out.println();
        switch (choice) {
            case "1":
                sorter = new AllSort();
                sorter.sort(map);
                return true;
            case "2":
                sorter = new CategorySort();
                sorter.sort(map);
                return true;
            case "3":
                String category = chooseSortCategory(scan);
                sorter = new TypeSort(category);
                sorter.sort(map);
                return true;
            case "4":
                return false;
            default:
                return true;
        }
    }

    static void displaySortChoices() {
        System.out.printf("Choose the type of purchase%n" +
                "1) Food%n" +
                "2) Clothes%n" +
                "3) Entertainment%n" +
                "4) Other%n");
    }

    static String chooseSortCategory(Scanner scan) {
        displaySortChoices();
        String category = scan.nextLine();
        while (category.isEmpty()) {
            category = scan.nextLine();
        }
        System.out.println();
        switch (category) {
            case "1":
                return "Food";
            case "2":
                return "Clothes";
            case "3":
                return "Entertainment";
            default:
                return "Other";
        }
    }

    static void displayPurchaseCategoryMenu() {
        System.out.printf("Choose the type of purchase%n" +
                "1) Food%n" +
                "2) Clothes%n" +
                "3) Entertainment%n" +
                "4) Other%n" +
                "5) Back%n");
    }

    static String choosePurchaseCategory(Scanner scan) {
        displayPurchaseCategoryMenu();
        String category = scan.nextLine();
        while (category.isEmpty()) {
            category = scan.nextLine();
        }
        System.out.println();
        switch (category) {
            case "1":
                return "Food";
            case "2":
                return "Clothes";
            case "3":
                return "Entertainment";
            case "4":
                return "Other";
            default:
                return null;
        }
    }

    static void displayAllCategoryMenu() {
        System.out.printf("Choose the type of purchases%n" +
                "1) Food%n" +
                "2) Clothes%n" +
                "3) Entertainment%n" +
                "4) Other%n" +
                "5) All%n" +
                "6) Back%n");
    }

    static String chooseAllCategory(Scanner scan) {
        displayAllCategoryMenu();
        String category = scan.nextLine();
        while (category.isEmpty()) {
            category = scan.nextLine();
        }
        switch (category) {
            case "1":
                return "Food";
            case "2":
                return "Clothes";
            case "3":
                return "Entertainment";
            case "4":
                return "Other";
            case "5":
                return "All";
            default:
                return null;
        }
    }
}
