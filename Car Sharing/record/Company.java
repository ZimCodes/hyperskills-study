package carsharing.record;


public class Company extends Record {

    public Company(String name) {
        this(0, name);
    }

    public Company(int id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}