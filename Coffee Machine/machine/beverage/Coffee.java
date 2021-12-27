package machine.beverage;

public class Coffee {
    protected int price;
    protected int water;
    protected int beans;
    protected int milk;

    public int getPrice(){
        return this.price;
    }

    public int getBeans() {
        return beans;
    }

    public int getMilk() {
        return milk;
    }

    public int getWater() {
        return water;
    }
}