package com.qlvanphongpham.adapter.persistence;

import java.sql.*;
import java.util.Properties;
import java.io.InputStream;

public class DatabaseConnection {
    private String url;
    private String username;
    private String password;

    public DatabaseConnection() {
        loadConfiguration();
    }

    private void loadConfiguration() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            if (input != null) {
                prop.load(input);
                this.url = prop.getProperty("db.url", "jdbc:mysql://localhost:3306/qlvanphongpham");
                this.username = prop.getProperty("db.username", "root");
                this.password = prop.getProperty("db.password", "0918102005PHIVAN");
            } else {
                // Default values if properties file not found
                this.url = "jdbc:mysql://localhost:3306/qlvanphongpham";
                this.username = "root";
                this.password = "0918102005PHIVAN";
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Fallback to default values
            this.url = "jdbc:mysql://localhost:3306/qlvanphongpham";
            this.username = "root";
            this.password = "0918102005PHIVAN";
        }
    }

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found", e);
        }
    }

    public void testConnection() {
        try (Connection conn = getConnection()) {
            System.out.println("✅ Database connection successful!");
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed: " + e.getMessage());
        }
    }
}