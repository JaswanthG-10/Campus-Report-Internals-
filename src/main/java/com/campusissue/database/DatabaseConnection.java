
package com.campusissue.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/campus_issue_system";

    private static final String USER = "root";

    // Replace with your MySQL root password
    private static final String PASSWORD = "pass123$";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Test the database connection
    public static void main(String[] args) {

        try (Connection connection = getConnection()) {

            if (connection != null && !connection.isClosed()) {
                System.out.println(
                    "MySQL database connected successfully!"
                );
            }

        } catch (SQLException e) {
            System.out.println("MySQL connection failed!");
            System.out.println("Error: " + e.getMessage());
        }
    }
}
