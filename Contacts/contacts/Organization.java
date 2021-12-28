package contacts;

class Organization extends Record {
    protected final static long serialVersionUID = 1L;
    public String address;

    public Organization(String name, String phoneNumber, String address) {
        super(name, phoneNumber);
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
        this.setLastModifiedDate();
    }

    @Override
    public String toString() {
        return String.format("Organization name: %s%n" +
                        "Address: %s%n" +
                        "Number: %s%n" +
                        "Time created: %s%n" +
                        "Time last edit: %s%n", this.getName(), this.getAddress(), this.getPhoneNumber(),
                this.getCreatedDate(), this.getLastModifiedDate());
    }
}