package com.qlvanphongpham.filter.main.mapper;

import com.qlvanphongpham.domain.User;
import com.qlvanphongpham.usecase.request.RegisterUserRequest;
import com.qlvanphongpham.usecase.request.UpdateUserRequest;
import com.qlvanphongpham.domain.valueobjects.Email;
import com.qlvanphongpham.domain.valueobjects.Password;
import com.qlvanphongpham.domain.valueobjects.PhoneNumber;

public class DTOMapper {

    public static User toUser(RegisterUserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(new Email(request.getEmail()));
        user.setPassword(new Password(request.getPassword())); // Will be hashed later
        user.setFullName(request.getFullName());
        
        if (request.getPhoneNumber() != null && !request.getPhoneNumber().isEmpty()) {
            user.setPhoneNumber(new PhoneNumber(request.getPhoneNumber()));
        }
        
        user.setRole(User.Role.valueOf(request.getRole()));
        
        return user;
    }

    public static void updateUserFromRequest(User user, UpdateUserRequest request) {
        if (request.getEmail() != null) {
            user.setEmail(new Email(request.getEmail()));
        }
        
        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }
        
        if (request.getPhoneNumber() != null) {
            if (request.getPhoneNumber().isEmpty()) {
                user.setPhoneNumber(null);
            } else {
                user.setPhoneNumber(new PhoneNumber(request.getPhoneNumber()));
            }
        }
        
        if (request.getRole() != null) {
            user.setRole(User.Role.valueOf(request.getRole()));
        }
        
        // Password is handled separately in the use case
    }
}