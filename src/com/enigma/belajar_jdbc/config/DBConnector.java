package com.enigma.belajar_jdbc.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    public static Connection connect() {
        try {
            String url = "jdbc:postgresql://localhost:5432/wmb_db";
            String password = System.getenv("DB_PASSWORD");
            String username = System.getenv("DB_USERNAME");
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("error while connection: " + e);
        }
    }
}
