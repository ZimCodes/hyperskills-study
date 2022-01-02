package carsharing;

import carsharing.dao.CustomerDao;
import carsharing.record.Customer;
import carsharing.record.IRecord;
import carsharing.record.Car;
import carsharing.dao.CarDao;
import carsharing.record.Company;
import carsharing.dao.CompanyDao;
import carsharing.util.View;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class Menu {
    private final Scanner scan;
    private final CompanyDao companyDao;
    private final CarDao carDao;
    private final CustomerDao customerDao;

    public Menu(CompanyDao companyDao, CarDao carDao, CustomerDao customerDao, Scanner scan) {
        this.companyDao = companyDao;
        this.carDao = carDao;
        this.scan = scan;
        this.customerDao = customerDao;
    }

    public void start() {
        boolean shouldExit = false;
        while (!shouldExit) {
            View.menuOptions(new String[]{"Log in as a manager",
                    "Log in as a customer",
                    "Create a customer",
                    "Exit"});
            String choice = this.scan.nextLine();
            View.line();
            if ("1".equals(choice)) {
                this.companyOptions();
            } else if ("2".equals(choice)) {
                List<Customer> customerList = this.customerDao.getAll();
                if (!customerList.isEmpty()) {
                    this.customerChoice();
                } else {
                    View.println("The customer list is empty!");
                    View.line();
                }
            } else if ("3".equals(choice)) {
                View.println("Enter the customer name:");
                String customerName = this.scan.nextLine();
                Customer customer = new Customer(0, customerName, 0);
                this.customerDao.insert(customer);
                View.println("The customer was added!");
                View.line();
            } else if ("0".equals(choice)) {
                shouldExit = true;
            }
        }
        this.close();
    }

    private void customerChoice() {
        boolean canGoBack = false;
        while (!canGoBack) {
            View.println("Customer list:");
            List<Customer> customerList = this.customerDao.getAll();
            String[] customerNames = toArray(customerList);
            View.menuOptions(customerNames, "Back");
            String choice = this.scan.nextLine();
            View.line();
            if ("0".equals(choice)) {
                canGoBack = true;
            } else {
                if (customerList.isEmpty()) {
                    View.println("The customer list is empty!");
                    View.line();
                } else {
                    int choiceNum = Integer.parseInt(choice);
                    Customer customer = customerList.get(choiceNum - 1);
                    this.customerMenu(customer);
                    canGoBack = true;
                }
            }
        }
    }

    private void customerMenu(Customer customer) {
        boolean canGoBack = false;
        while (!canGoBack) {
            View.menuOptions(new String[]{"Rent a car", "Return a rented car", "My rented car",
                    "Back"});
            String choice = this.scan.nextLine();
            View.line();
            if ("1".equals(choice)) {
                List<Company> companyList = this.companyDao.getAll();
                if (customer.getCarID() != 0) {
                    View.println("You've already rented a car!");
                    View.line();
                } else if (companyList.isEmpty()) {
                    View.println("The company list is empty!");
                    View.line();
                } else {
                    String[] companyNames = toArray(companyList);
                    View.println("Choose a company:");
                    View.menuOptions(companyNames, "Back");
                    String companyChoice = this.scan.nextLine();
                    View.line();
                    if (!"0".equals(companyChoice)) {
                        int companyChoiceNum = Integer.parseInt(companyChoice);
                        Company company = companyList.get(companyChoiceNum - 1);
                        List<Car> cars = this.companyDao.getRefsOrdered(company.getId());
                        List<Integer> customerCarIDs = toListID(this.customerDao.getAll());
                        cars = cars.stream()
                                .filter(car ->  !customerCarIDs.contains(car.getId()))
                                .collect(Collectors.toList());
                        if (cars.isEmpty()) {
                            View.println(String.format("No available cars in the '%s' company",
                                    company.getName()));
                            View.line();
                        } else {
                            View.println("Choose a car:");
                            String[] carNames = toArray(cars);
                            View.menuOptions(carNames, "Back");
                            String carChoice = this.scan.nextLine();
                            View.line();
                            if (!"0".equals(carChoice)) {
                                int carChoiceNum = Integer.parseInt(carChoice);
                                Car car = cars.get(carChoiceNum - 1);
                                this.customerDao.updateRentedCar(customer.getId(), car.getId());
                                customer.setCarID(car.getId());
                                View.println(String.format("You rented '%s'", car.getName()));
                                View.line();
                            }
                        }
                    }
                }
            } else if ("2".equals(choice)) {
                if (customer.getCarID() == 0) {
                    View.println("You didn't rent a car!");
                } else {
                    this.customerDao.updateRentedCar(customer.getId(), 0);
                    View.println("You've returned a rented car!");
                    customer.setCarID(0);
                }
                View.line();
            } else if ("3".equals(choice)) {
                if (customer.getCarID() == 0) {
                    View.println("You didn't rent a car!");
                } else {
                    View.println("Your rented car:");
                    List<Car> cars = this.customerDao.getRefs(customer.getCarID());
                    Car car = cars.get(0);
                    View.println(car.getName());
                    View.println("Company:");
                    Company company = this.companyDao.get(car.getCompanyID());
                    View.println(company.getName());
                }
                View.line();
            } else if ("0".equals(choice)) {
                canGoBack = true;
            }
        }
    }

    private void companyOptions() {
        boolean canGoBack = false;
        while (!canGoBack) {
            View.menuOptions(new String[]{"Company list", "Create a company", "Back"});
            String choice = this.scan.nextLine();
            View.line();
            if ("1".equals(choice)) {
                List<Company> companies = this.companyDao.getAll();
                if (companies == null || companies.isEmpty()) {
                    View.println("The company list is empty!");
                    View.line();
                } else {
                    this.companyChoice(companies);
                }
            } else if ("2".equals(choice)) {
                View.println("Enter the company name:");
                String companyName = this.scan.nextLine();
                Company company = new Company(companyName);
                this.companyDao.insert(company);
                View.println("The company was created!");
                View.line();
            } else {
                canGoBack = true;
            }
        }
    }

    private void companyChoice(List<Company> companyList) {
        boolean canGoBack = false;
        while (!canGoBack) {
            View.println("Choose the company:");
            String[] companyNames = toArray(companyList);
            View.menuOptions(companyNames, "Back");
            String choice = this.scan.nextLine();
            View.line();
            if (!"0".equals(choice)) {
                int choiceNum = Integer.parseInt(choice);
                Company company = companyList.get(choiceNum - 1);
                this.carMenu(company);
            }
            canGoBack = true;
        }
    }

    private void carMenu(Company company) {
        boolean canGoBack = false;
        while (!canGoBack) {
            View.println(String.format("'%s' company", company.getName()));
            View.menuOptions(new String[]{"Car list", "Create a car", "Back"});
            String choice = this.scan.nextLine();
            View.line();
            if ("1".equals(choice)) {
                List<Car> cars = this.companyDao.getRefs(company.getId());
                if (cars == null || cars.isEmpty()) {
                    View.println("The car list is empty!");
                } else {
                    this.carList(company.getId());
                }
                View.line();
            } else if ("2".equals(choice)) {
                View.println("Enter the car name:");
                String carName = this.scan.nextLine();
                Car car = new Car(0, carName, company.getId());
                this.carDao.insert(car);
                View.println("The car was added!");
                View.line();
            } else if ("0".equals(choice)) {
                canGoBack = true;
            }
        }
    }

    private void carList(int refId) {
        View.println("Car list:");
        List<Car> carList = this.companyDao.getRefs(refId);
        String[] carNames = toArray(carList);
        View.menuOptions(carNames);
    }

    private String[] toArray(List<? extends IRecord> list) {
        String[] names = new String[list.size()];
        int i = 0;
        for (IRecord company : list) {
            names[i] = company.getName();
            i++;
        }
        return names;
    }

    private List<Integer> toListID(List<Customer> list) {
        return list.stream().map(Customer::getCarID).distinct().collect(Collectors.toList());
    }

    private void close() {
        this.companyDao.close();
        this.carDao.close();
        this.scan.close();
    }
}