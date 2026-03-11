package com.qlvanphongpham.adapter.security;

import com.qlvanphongpham.usecase.port.PasswordHasher;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasherImpl implements PasswordHasher {
    
    @Override
    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    @Override
    public boolean checkPassword(String plainPassword, String hashedPassword) {
        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (Exception e) {
            System.out.println("❌ Password check error: " + e.getMessage());
            return false;
        }
    }
}