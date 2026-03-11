package com.qlvanphongpham.usecase.port;

public interface PasswordHasher {
    String hashPassword(String plainPassword);
    boolean checkPassword(String plainPassword, String hashedPassword);
    
    // THÊM METHODS CHO TEST (nếu cần)
    default String hash(String password) {
        return hashPassword(password);
    }
    
    default boolean check(String password, String hashedPassword) {
        return checkPassword(password, hashedPassword);
    }
}