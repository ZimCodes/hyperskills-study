package contacts;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class PhoneBook implements Serializable {
    private final static long serialVersionUID = 1L;
    private final List<Record> records;

    public PhoneBook() {
        this.records = new ArrayList<>();
    }

    public void addRecord(Record record) {
        this.records.add(record);
    }

    public List<Record> getRecords() {
        return this.records;
    }

    public int getRecordIndex(Record record) {
        return this.records.indexOf(record) + 1;
    }

    public List<Record> searchRecords(String name) {
        List<Record> matches = new ArrayList<>();
        for (Record record : this.records) {
            String lowRecordName = null;
            if (record instanceof Person) {
                Person p = (Person) record;
                lowRecordName = String.format("%s %s %s %s %s %s %s", p.getName().toLowerCase(),
                        p.getSurName().toLowerCase(), p.getPhoneNumber().toLowerCase(),
                        p.getLastModifiedDate().toString().toLowerCase(),
                        p.getCreatedDate().toString().toLowerCase(), p.getGender().toLowerCase(),
                        p.getBirthDate().toLowerCase());
            } else {
                Organization o = (Organization) record;
                lowRecordName = String.format("%s %s %s %s %s", o.getName().toLowerCase(),
                        o.getPhoneNumber().toLowerCase(), o.getAddress().toLowerCase(),
                        o.getCreatedDate().toString().toLowerCase(),
                        o.getLastModifiedDate().toString().toLowerCase());
            }

            if (lowRecordName.contains(name.toLowerCase())) {
                matches.add(record);
            }
        }
        return matches;
    }

    public Record getRecord(int pos) {
        return this.records.get(pos - 1);
    }

    public void insertRecord(int pos, Record record) {
        this.records.set(pos - 1, record);
    }

    public void editPhoneNumber(int pos, String number) {
        this.records.get(pos - 1).setPhoneNumber(number);
    }


    public void editName(int pos, String name) {
        this.records.get(pos - 1).setName(name);
    }

    public void removeRecord(int pos) {
        this.records.remove(pos - 1);
    }

    public int size() {
        return this.records.size();
    }
}