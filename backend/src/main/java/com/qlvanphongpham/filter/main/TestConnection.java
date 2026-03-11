package com.qlvanphongpham.filter.main;

import com.qlvanphongpham.adapter.persistence.DatabaseConnection; // SỬA IMPORT

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("🧪 Testing Database Connection...");
        
        DatabaseConnection dbConnection = new DatabaseConnection(); // SỬA TÊN CLASS
        dbConnection.testConnection();
        
        System.out.println("✅ Test completed!");
    }
}