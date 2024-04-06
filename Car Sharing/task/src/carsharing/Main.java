package carsharing;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CompanyDao companyDao;

        if (args.length >= 1) {
            companyDao = new CompanyDaoImp(args);
        } else {
            companyDao = new CompanyDaoImp();
        }

        System.out.println("1. Log in as a manager\n" +
                "0. Exit");
        displayMenu(companyDao, scanner);

    }
    
    private static void printCompanies(CompanyDao company, Scanner scanner) {
        List<Company> companies = company.getCompanies();
        if (companies.isEmpty()) {
            System.out.println("\nThe company list is empty!");
        } else {

            while (true) {
                System.out.println("\nChoose the company: ");
                companies.forEach(elm -> System.out.println(elm.ID() + ". " + elm.NAME()));
                System.out.println("0. Back");
                int userAction = scanner.nextInt();
                scanner.nextLine();
                if (userAction == 0) {
                    break;
                } else {
                    boolean notFound = companies.stream().filter(company1 -> company1.ID() == userAction).findAny().isEmpty();

                    if (notFound) {
                        System.out.println("Wrong Company Id");
                    } else {
                        String name = companies.stream().filter(company1 -> company1.ID() == userAction).toList().get(0).NAME();
                        int id = companies.stream().filter(company1 -> company1.ID() == userAction).toList().get(0).ID();
                        System.out.printf("\n'%s' company%n", name);
                        executeUserAction(scanner, company, id);
                        break;
                    }
                }
            }

        }
    }

    private static void executeUserAction(Scanner scanner, CompanyDao company, int id) {
        boolean isRunning = true;
        System.out.println("1. Car list\n2. Create a car\n0. Back");
        do {
            int userAction = scanner.nextInt();
            scanner.nextLine();
            switch (userAction) {
                case 1:
                    printCars(company, id);
                    break;
                case 2:
                    addCar(company, scanner, id);
                    break;
                case 0:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
            if (isRunning) {
                System.out.println("\n1. Car list\n2. Create a car\n0. Back");
            }
        } while (isRunning);
    }

    private static void printCars(CompanyDao company, int id) {
        List<String> cars = company.getCars(id);
        if (cars.isEmpty()) {
            System.out.println("\nThe car list is empty!\n");
        } else {
            System.out.println("\nCar list:");
            AtomicInteger i = new AtomicInteger(1);
            cars.forEach(car -> System.out.println(i.getAndIncrement() + ". " + car));
        }
    }

    private static void addCar(CompanyDao companyDao, Scanner scanner, int id) {
        System.out.println("\nEnter the car name:");
        String name = scanner.nextLine();
        companyDao.addCAr(name, id);
        System.out.println("The car was added!");
    }

    private static void addCompany(CompanyDao companyDao, Scanner scanner) {
        System.out.println("\nEnter the company name:");
        String name = scanner.nextLine();
        companyDao.addCompany(name);
        System.out.println("The company was created!");
    }

    private static void executeUserAction(CompanyDao company, Scanner scanner) {
        boolean isRunning = true;
        do {

            System.out.println("""

                    1. Company list
                    2. Create a company
                    0. Back""");
            int userAction = scanner.nextInt();
            scanner.nextLine();
            switch (userAction) {
                case 1:
                    printCompanies(company, scanner);
                    break;
                case 2:
                    addCompany(company, scanner);
                    break;
                case 0:
                    isRunning = false;
            }
        } while (isRunning);
    }

    private static void displayMenu(CompanyDao company, Scanner scanner) {
        boolean isRunning = true;
        do {
            int userAction = scanner.nextInt();
            scanner.nextLine();

            switch (userAction) {
                case 1:
                    executeUserAction(company, scanner);
                    break;
                case 0:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Wrong input!");
            }
            if (isRunning) {
                System.out.println("""

                        1. Log in as a manager
                        0. Exit""");
            }

        } while (isRunning);

    }
}