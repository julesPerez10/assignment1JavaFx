package com.example.assignmentjava;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseUtil {
    private static final String URL = "jdbc:mysql://localhost:3307/football_stats";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection connect() {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Successfully connected to the database.");
            return connection;
        } catch (Exception e) {
            System.out.println("Error connecting to the database.");
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet fetchData(String query) {
        try {
            Connection connection = connect();
            if (connection != null) {
                Statement statement = connection.createStatement();
                System.out.println("Executing query: " + query);
                return statement.executeQuery(query);
            } else {
                System.out.println("Could not establish a connection to execute the query.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error executing the query.");
            e.printStackTrace();
            return null;
        }
    }
}
