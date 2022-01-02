package carsharing.dao;

import carsharing.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import carsharing.record.Car;
import carsharing.record.Company;

public class CompanyDao extends RefDao<Company, Car> {

    public CompanyDao(MyDatabase db) {
        super(db, "COMPANY", "CAR");
    }

    @Override
    public List<Company> getAll() {
        List<Company> list = new ArrayList<>();
        try {
            ResultSet resultSet = this.db.getStmt().executeQuery("SELECT * FROM " + tableName);
            while (resultSet.next()) {
                Company company = new Company(resultSet.getInt("ID"), resultSet.getString("NAME"));
                list.add(company);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Company get(int id) {
        try {
            ResultSet resultSet = this.db.getStmt().executeQuery(String.format("SELECT * FROM %s " +
                            "WHERE " +
                            "ID = %d",
                    this.tableName, id));
            if (resultSet.next()){
                return new Company(resultSet.getInt("ID"), resultSet.getString("NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert(Company item) {
        try {
            this.db.getStmt().executeUpdate(String.format("INSERT INTO %s(NAME) VALUES('%s')",
                    tableName, item.getName()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Car> getRefs(int carRefID) {
        List<Car> list = new ArrayList<>();
        try {
            ResultSet resultSet = this.db.getStmt().executeQuery(String.format("SELECT * FROM %s " +
                    "WHERE COMPANY_ID = %d", this.refTableName, carRefID));
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

    public List<Car> getRefsOrdered(int carRefID) {
        List<Car> list = new ArrayList<>();
        try {
            ResultSet resultSet = this.db.getStmt().executeQuery(String.format("SELECT * FROM %s " +
                    "WHERE COMPANY_ID = %d ORDER BY ID", this.refTableName, carRefID));
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