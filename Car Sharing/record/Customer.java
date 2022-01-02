package carsharing.record;

public class Customer extends Record {
    private int carID;

    public Customer(int id, String name, int carID) {
        super(id, name);
        this.carID = carID;
    }

    public int getCarID() {
        return this.carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }
}