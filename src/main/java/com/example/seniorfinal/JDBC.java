package com.example.seniorfinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC
{
    private static final String url = "jdbc:mysql://localhost:3306/marketplace";
    private static final String user = "root";
    private static String password = "1234";

    public static Connection startConnection() {
        try {
            System.out.println("working");
            return DriverManager.getConnection(url, user,password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
