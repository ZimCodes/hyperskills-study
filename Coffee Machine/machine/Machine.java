package machine;

import machine.beverage.Coffee;

class Machine {
    public int water;
    public int milk;
    public int beans;
    public int cups;
    public int money;

    public Machine() {
        this.water = 400;
        this.milk = 540;
        this.beans = 120;
        this.cups = 9;
        this.money = 550;
    }

    public void buy(Coffee coffee) {
        if (this.water - coffee.getWater() < 0) {
            System.out.println("Sorry, not enough water!\n");
            return;
        }
        if (this.milk - coffee.getMilk() < 0) {
            System.out.println("Sorry, not enough milk!\n");
            return;
        }
        if (this.beans - coffee.getBeans() < 0) {
            System.out.println("Sorry, not enough coffee beans!\n");
            return;
        }
        if (this.cups < 1) {
            System.out.println("Sorry, not enough coffee beans!\n");
            return;
        }
        System.out.println("I have enough resources, making you a coffee!\n");
        this.cups--;
        this.water -= coffee.getWater();
        this.milk -= coffee.getMilk();
        this.beans -= coffee.getBeans();
        this.money += coffee.getPrice();
    }

    public void take() {
        System.out.printf("%nI gave you $%d%n%n", this.money);
        this.money = 0;
    }

    @Override
    public String toString() {
        return String.format("The coffee machine has:%n" +
                "%d ml of water%n" +
                "%d ml of milk%n" +
                "%d g of coffee beans%n" +
                "%d disposable cups%n" +
                "$%d of money%n", this.water, this.milk, this.beans, this.cups, this.money);
    }
}