package repository.impl;

import java.sql.*;

public class DBConnection {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String SCHEMA = "electriccastle";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static DBConnection dbConnection = new DBConnection();

    private DBConnection() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection createConnection() {
        try {
            return DriverManager.getConnection(URL + SCHEMA + "?useSSL=false", USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static Connection getConnection() {
        return dbConnection.createConnection();
    }

    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void close(Statement statement) {
        try {
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void close(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
