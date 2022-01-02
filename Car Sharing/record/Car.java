package carsharing.record;

public class Car extends Record {
    private int companyID;

    public Car(int id, String name, int companyID) {
        super(id, name);
        this.companyID = companyID;
    }

    public int getCompanyID() {
        return this.companyID;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

}