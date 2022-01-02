package carsharing.dao;

import carsharing.MyDatabase;
import carsharing.record.Customer;
import carsharing.record.Car;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao extends RefDao<Customer, Car> {

    public CustomerDao(MyDatabase db) {
        super(db, "CUSTOMER", "CAR");
    }

    @Override
    public List<Customer> getAll() {
        List<Customer> list = new ArrayList<>();
        try {
            ResultSet resultSet = this.db.getStmt().executeQuery("SELECT * FROM " + tableName);
            while (resultSet.next()) {
                Customer customer = new Customer(resultSet.getInt("ID"), resultSet.getString(
                        "NAME"),
                        resultSet.getInt("RENTED_CAR_ID"));
                list.add(customer);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateRentedCar(int customerID, int carID) {
        try {
            String statement = carID == 0 ?
                    String.format("UPDATE %s SET RENTED_CAR_ID = NULL WHERE ID = %d",
                            this.tableName, customerID):
                    String.format("UPDATE %s SET " +
                            "RENTED_CAR_ID = %s WHERE ID = %d",
                    this.tableName, carID, customerID) ;
            this.db.getStmt().executeUpdate(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Customer get(int id) {
        try {
            ResultSet resultSet = this.db.getStmt().executeQuery(String.format("SELECT * FROM %s " +
                            "WHERE " +
                            "ID = %d",
                    this.tableName, id));
            if (resultSet.next()){
                return new Customer(resultSet.getInt("ID"), resultSet.getString("NAME"),
                        resultSet.getInt("RENTED_CAR_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert(Customer item) {
        try {
            this.db.getStmt().executeUpdate(String.format("INSERT INTO %s(NAME) " +
                            "VALUES" +
                            "('%s')",
                    this.tableName, item.getName()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Car> getRefs(int refID) {
        List<Car> list = new ArrayList<>();
        try {
            ResultSet resultSet = this.db.getStmt().executeQuery(String.format("SELECT * FROM %s " +
                    "WHERE ID = %d", this.refTableName, refID));
            while (resultSet.next()) {
                Car car = new Car(resultSet.getInt("ID"), resultSet.getString("NAME"),
                        resultSet.getInt("COMPANY_ID"));
                list.add(car);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}