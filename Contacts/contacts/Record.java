package contacts;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.Serializable;

class Record implements Serializable {
    protected final static long serialVersionUID = 1L;
    private final static Pattern PHONE_PATTERN;
    public String name;
    public LocalDateTime createdDate;
    public LocalDateTime lastModifiedDate;
    public String phoneNumber;

    static {
        PHONE_PATTERN = Pattern.compile("\\+?([\\da-zA-Z]+|(\\([\\da-zA-Z]+\\)" +
                "|[\\da-zA-Z]+[ \\-]\\([\\da-zA-Z]{2,}\\)))([ \\-][\\da-zA-Z]{2,})*");
    }

    public Record(String name, String phoneNumber) {
        this.name = name;
        this.setPhoneNumber(phoneNumber);
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    protected void setLastModifiedDate() {
        this.lastModifiedDate = LocalDateTime.now();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
        this.setLastModifiedDate();
    }

    public String getPhoneNumber() {
        if (!this.hasNumber()) {
            return "[no number]";
        }
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (!isValidPhoneNumber(phoneNumber)) {
            View.wrongPhone();
            this.phoneNumber = "";
        } else {
            this.phoneNumber = phoneNumber;
        }
        this.setLastModifiedDate();
    }

    private boolean isValidPhoneNumber(String str) {
        Matcher phoneMat = PHONE_PATTERN.matcher(str);
        return phoneMat.matches();
    }

    public boolean hasNumber() {
        return !this.phoneNumber.isEmpty();
    }

    public boolean isPerson() {
        return this instanceof Person;
    }
}