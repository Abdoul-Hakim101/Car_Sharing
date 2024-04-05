package carsharing;

import java.util.List;
import java.util.Scanner;

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


    private static void printCompanies(CompanyDao company) {
        List<Company> companies = company.get();
        if (companies.isEmpty()) {
            System.out.println("\nThe company list is empty!");
        } else {
            System.out.println("\nCompany list:");
            companies.forEach(elm -> System.out.println(elm.ID() + ". " + elm.NAME()));
        }
    }

    private static void addCompany(CompanyDao companyDao, Scanner scanner) {
        System.out.println("\nEnter the company name:");
        String name = scanner.nextLine();
        companyDao.add(name);
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
                    printCompanies(company);
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