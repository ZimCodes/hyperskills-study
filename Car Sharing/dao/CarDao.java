package carsharing.dao;

import carsharing.MyDatabase;
import carsharing.record.Car;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDao extends Dao<Car> {

    public CarDao(MyDatabase db) {
        super(db, "CAR");
    }

    @Override
    public List<Car> getAll() {
        List<Car> list = new ArrayList<>();
        try {
            ResultSet resultSet = this.db.getStmt().executeQuery("SELECT * FROM " + tableName);
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

    @Override
    public Car get(int id) {
        try {
            ResultSet resultSet = this.db.getStmt().executeQuery(String.format("SELECT * FROM %s " +
                            "WHERE " +
                            "ID = %d",
                    this.tableName, id));
            if(resultSet.next()){
                return new Car(resultSet.getInt("ID"), resultSet.getString("NAME"),resultSet.getInt(
                        "COMPANY_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert(Car item) {
        try {
            this.db.getStmt().executeUpdate(String.format("INSERT INTO %s(NAME,COMPANY_ID) VALUES" +
                            "('%s',%d)",
                    this.tableName, item.getName(), item.getCompanyID()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}