package contacts;

import java.time.LocalDate;

class Person extends Record {
    public String surName;
    public LocalDate birthDate;
    public String gender;


    public Person(String firstName, String phoneNumber, String surName, String gender,
                  String birthDate) {
        super(firstName, phoneNumber);
        this.surName = surName;
        this.setGender(gender);
        this.setBirthDate(birthDate);
    }

    public String getBirthDate() {
        if (this.birthDate == null) {
            return "[no data]";
        }
        return this.birthDate.toString();
    }

    public void setBirthDate(String date) {
        if (date.isEmpty()) {
            this.birthDate = null;
        } else {
            this.birthDate = LocalDate.parse(date);
            this.setLastModifiedDate();
        }
    }


    public String getGender() {
        if (this.gender == null || this.gender.isEmpty()) {
            return "[no data]";
        }
        return this.gender;
    }

    public void setGender(String gender) {
        if (!"M".equals(gender) && !"F".equals(gender)) {
            return;
        }
        this.gender = gender;
        this.setLastModifiedDate();
    }

    public String getSurName() {
        return this.surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
        this.setLastModifiedDate();
    }

    @Override
    public String toString() {
        return String.format("Name: %s%n" +
                        "Surname: %s%n" +
                        "Birth date: %s%n" +
                        "Gender: %s%n" +
                        "Number: %s%n" +
                        "Time created: %s%n" +
                        "Time last edit: %s%n", this.getName(), this.getSurName(), this.getBirthDate(),
                this.getGender(), this.getPhoneNumber(), this.getCreatedDate(), this.getLastModifiedDate());
    }
}