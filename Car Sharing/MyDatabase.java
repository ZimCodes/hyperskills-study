package carsharing;

import carsharing.util.Args;

import java.sql.*;


public class MyDatabase {
    private final static String DB_DRIVER = "jdbc:h2:";
    private final static String DB_PATH = "./src/carsharing/db/";
    private Connection conn;
    private Statement stmt;

    public MyDatabase(String[] args) {
        Args.getInstance().parse(args);
        this.initDB();
    }

    private void registerDriver() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initDB() {
        registerDriver();
        try {
            this.conn =
                    DriverManager.getConnection(DB_DRIVER + DB_PATH + Args.getInstance().getDbName());
            this.stmt = this.conn.createStatement();
            this.createDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createDB() throws SQLException {
        this.stmt.execute("CREATE TABLE IF NOT EXISTS COMPANY(" +
                "ID INT PRIMARY KEY AUTO_INCREMENT," +
                "NAME VARCHAR(100) UNIQUE NOT NULL" +
                ");");
        this.stmt.execute("CREATE TABLE IF NOT EXISTS CAR(" +
                "ID INT PRIMARY KEY AUTO_INCREMENT," +
                "NAME VARCHAR(100) UNIQUE NOT NULL," +
                "COMPANY_ID INT NOT NULL," +
                "CONSTRAINT fk_company FOREIGN KEY(COMPANY_ID) REFERENCES COMPANY(ID)" +
                ");");
        this.stmt.execute("CREATE TABLE IF NOT EXISTS CUSTOMER(" +
                "ID INT PRIMARY KEY AUTO_INCREMENT," +
                "NAME VARCHAR(100) UNIQUE NOT NULL," +
                "RENTED_CAR_ID INT," +
                "CONSTRAINT fk_car FOREIGN KEY(RENTED_CAR_ID) REFERENCES CAR(ID)" +
                "ON UPDATE SET NULL);");
    }

    public Statement getStmt() {
        return this.stmt;
    }

    public void close() {
        try {
            this.conn.close();
            this.stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}