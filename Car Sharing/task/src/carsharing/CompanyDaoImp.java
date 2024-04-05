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
    public List<Company> get() {
        List<Company> companies = new ArrayList<>();
        try {
            String SELECT_ALL = "SELECT * FROM COMPANY ORDER BY ID";
            preparedStatement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null){
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
    public void add(String CompanyName) {
        String INSERT_DATA = "INSERT INTO COMPANY (NAME) VALUES (?)";
        try {
            preparedStatement = connection.prepareStatement(INSERT_DATA);
            preparedStatement.setString(1, CompanyName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void connectDB(String databaseFileName) {
        String databaseFilePath = ".\\src\\carsharing\\db\\" + databaseFileName;
        String createTableQuery = "CREATE TABLE IF NOT EXISTS COMPANY ("
                + "ID INT PRIMARY KEY AUTO_INCREMENT, "
                + "NAME VARCHAR(255) UNIQUE NOT NULL"
                + ")";

        try {
            Class.forName("org.h2.Driver");
            String url = "jdbc:h2:".concat(databaseFilePath);
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(true);
            preparedStatement = connection.prepareStatement(createTableQuery);

            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("ClassNotFoundException | SQLException");
        }

    }
}