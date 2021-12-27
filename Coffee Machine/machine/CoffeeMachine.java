package machine;

import machine.beverage.Cappuccino;
import machine.beverage.Coffee;
import machine.beverage.Espresso;
import machine.beverage.Latte;

import java.util.Scanner;

public class CoffeeMachine {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Machine machine = new Machine();
        boolean shouldExit = false;
        while (!shouldExit) {
            actionMenu();
            String choice = scan.nextLine();
            switch (choice) {
                case "take":
                    machine.take();
                    break;
                case "fill":
                    System.out.println("Write how many ml of water you want to add:");
                    int input = Integer.parseInt(scan.nextLine());
                    machine.water += input;
                    System.out.println("\nWrite how many ml of milk you want to add:");
                    input = Integer.parseInt(scan.nextLine());
                    machine.milk += input;
                    System.out.println("Write how many grams of coffee beans you want to add:");
                    input = Integer.parseInt(scan.nextLine());
                    machine.beans += input;
                    System.out.println("Write how many disposable cups of coffee you want to add:");
                    input = Integer.parseInt(scan.nextLine());
                    machine.cups += input;
                    System.out.println();
                    break;
                case "buy":
                    System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - " +
                            "cappuccino, back - to main menu:");
                    String coffeeNum = scan.nextLine();
                    Coffee coffee = null;
                    if ("1".equals(coffeeNum)) {
                        coffee = new Espresso();
                    } else if ("2".equals(coffeeNum)) {
                        coffee = new Latte();
                    } else if ("3".equals(coffeeNum)) {
                        coffee = new Cappuccino();
                    } else if ("back".equals(coffeeNum)) {
                        continue;
                    }
                    machine.buy(coffee);
                    break;
                case "remaining":
                    System.out.printf("%n%s%n", machine);
                    break;
                case "exit":
                    shouldExit = true;
                    break;
            }
        }
    }

    static void actionMenu() {
        System.out.println("Write action (buy, fill, take, remaining, exit):");
    }

    static int milkAmount(int cups) {
        return cups * 50;
    }

    static int waterAmount(int cups) {
        return cups * 200;
    }

    static int beanAmount(int cups) {
        return cups * 15;
    }
}
