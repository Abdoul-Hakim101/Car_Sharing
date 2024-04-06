package carsharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoImp implements CompanyDao {
    private final String databaseFileName;
    Connection connection;
    private PreparedStatement preparedStatement;


    public CompanyDaoImp(String[] args) {
        databaseFileName = args[1];
        connectDB(databaseFileName);
    }

    public CompanyDaoImp() {
        databaseFileName = "carsharing";
        connectDB(databaseFileName);
    }

    @Override
    public List<Company> getCompanies() {
        List<Company> companies = new ArrayList<>();
        try {
            String SELECT_ALL = "SELECT * FROM COMPANY ORDER BY ID";
            preparedStatement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                    int ID = resultSet.getInt("ID");
                    String NAME = resultSet.getString("NAME");
                    companies.add(new Company(ID, NAME));
                }
            }
            return companies;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addCompany(String CompanyName) {
        String INSERT_DATA = "INSERT INTO COMPANY (NAME) VALUES (?)";
        try {
            preparedStatement = connection.prepareStatement(INSERT_DATA);
            preparedStatement.setString(1, CompanyName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getCars(int id) {
        List<String> cars = new ArrayList<>();
        String SELECT = "SELECT * FROM CAR WHERE COMPANY_ID = %d".formatted(id);
        try {
            preparedStatement = connection.prepareStatement(SELECT);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                    String name = resultSet.getString("NAME");
                    cars.add(name);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cars;
    }

    @Override
    public void addCAr(String CAR_NAME, int COMPANY_ID) {
        String INSERT_DATA = "INSERT INTO CAR (NAME, COMPANY_ID) VALUES (?, ?)";
        try {
            preparedStatement = connection.prepareStatement(INSERT_DATA);
            preparedStatement.setString(1,CAR_NAME);
            preparedStatement.setInt(2,COMPANY_ID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void connectDB(String databaseFileName) {

        String databaseFilePath = "C:\\Users\\abdihakin\\IdeaProjects\\Car Sharing\\Car Sharing\\task\\src\\carsharing\\db\\" + databaseFileName;
        String createCompanyTable = "CREATE TABLE IF NOT EXISTS COMPANY ("
                + "ID INT PRIMARY KEY AUTO_INCREMENT, "
                + "NAME VARCHAR(255) UNIQUE NOT NULL"
                + ")";
        String createCarTable = "CREATE TABLE IF NOT EXISTS CAR ("
                + "ID INT PRIMARY KEY AUTO_INCREMENT, "
                + "NAME VARCHAR(255) UNIQUE NOT NULL, "
                + "COMPANY_ID INT NOT NULL, "
                + "FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID)"
                + ")";
        try {
            Class.forName("org.h2.Driver");
            String url = "jdbc:h2:".concat(databaseFilePath);
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(true);
            preparedStatement = connection.prepareStatement(createCompanyTable);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(createCarTable);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("ClassNotFoundException | SQLException");
        }

    }
}