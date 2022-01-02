package carsharing.util;

public class Args {
    private static Args instance;
    private String dbName;

    private Args() {
    }

    public static Args getInstance() {
        if (instance == null) {
            instance = new Args();
        }
        return instance;
    }

    public String getDbName() {
        return this.dbName;
    }

    public void parse(String[] args) {
        int index = 0;
        while (index < args.length) {
            String arg = args[index];
            if ("-databaseFileName".equals(arg)) {
                index++;
                this.dbName = args[index];
            }
            index++;
        }
        setDefaults();
    }

    private void setDefaults() {
        if (this.dbName == null) {
            this.dbName = "myData";
        }
    }
}