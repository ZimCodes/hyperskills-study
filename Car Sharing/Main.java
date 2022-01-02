package carsharing;


import carsharing.dao.CarDao;
import carsharing.dao.CompanyDao;
import carsharing.dao.CustomerDao;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        MyDatabase db = new MyDatabase(args);
        CompanyDao companyDao = new CompanyDao(db);
        CarDao carDao = new CarDao(db);
        CustomerDao customerDao = new CustomerDao(db);
        Menu menu = new Menu(companyDao, carDao, customerDao, scan);
        menu.start();
    }
}