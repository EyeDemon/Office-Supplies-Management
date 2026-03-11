package com.qlvanphongpham.usecase.interactor;

import com.qlvanphongpham.usecase.boundary.input.ManageUserInputBoundary;
import com.qlvanphongpham.usecase.boundary.output.ManageUserOutputBoundary;
import com.qlvanphongpham.usecase.port.UserRepository;
import com.qlvanphongpham.usecase.port.PasswordHasher;
import com.qlvanphongpham.usecase.request.*;
import com.qlvanphongpham.usecase.response.*;
import com.qlvanphongpham.domain.User;
import com.qlvanphongpham.domain.valueobjects.Email;
import com.qlvanphongpham.domain.valueobjects.Password;
import com.qlvanphongpham.domain.valueobjects.PhoneNumber;

public class ManageUserInteractor implements ManageUserInputBoundary {
    private UserRepository userRepository;
    private PasswordHasher passwordHasher;
    private ManageUserOutputBoundary outputBoundary;

    public ManageUserInteractor(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    public ManageUserInteractor(UserRepository userRepository, PasswordHasher passwordHasher, 
                               ManageUserOutputBoundary outputBoundary) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        System.out.println("🔐 Processing login for: " + request.getUsername());
        
        User user = userRepository.findByUsername(request.getUsername());
        
        if (user == null) {
            System.out.println("❌ User not found: " + request.getUsername());
            return new LoginResponse(false, "Tên đăng nhập hoặc mật khẩu không đúng", null);
        }

        System.out.println("🔐 Checking password...");
        boolean passwordValid = passwordHasher.checkPassword(request.getPassword(), user.getPassword().getValue());
        System.out.println("🔐 Password check result: " + passwordValid);

        if (passwordValid) {
            System.out.println("✅ Login successful for: " + request.getUsername());
            return new LoginResponse(true, "Đăng nhập thành công", user);
        } else {
            System.out.println("❌ Invalid password for: " + request.getUsername());
            return new LoginResponse(false, "Tên đăng nhập hoặc mật khẩu không đúng", null);
        }
    }

    @Override
public UserResponse registerUser(RegisterUserRequest request) {
    if (userRepository.existsByUsername(request.getUsername())) {
        // FIX: Gọi outputBoundary khi username đã tồn tại
        if (outputBoundary != null) {
            outputBoundary.presentError("Username already exists");
        }
        return null;
    }

    if (userRepository.existsByEmail(request.getEmail())) {
        // FIX: Gọi outputBoundary khi email đã tồn tại
        if (outputBoundary != null) {
            outputBoundary.presentError("Email already exists");
        }
        return null;
    }

    User user = new User();
    user.setUsername(request.getUsername());
    user.setEmail(new Email(request.getEmail()));
    user.setPassword(new Password(passwordHasher.hashPassword(request.getPassword())));
    user.setFullName(request.getFullName());
    
    if (request.getPhoneNumber() != null && !request.getPhoneNumber().isEmpty()) {
        user.setPhoneNumber(new PhoneNumber(request.getPhoneNumber()));
    }
    
    user.setRole(User.Role.USER);

    User savedUser = userRepository.save(user);
    if (savedUser != null) {
        UserResponse response = UserResponse.fromUser(savedUser);
        // FIX: Gọi outputBoundary khi thành công
        if (outputBoundary != null) {
            outputBoundary.presentUserResult(response);
        }
        return response;
    }
    
    return null;
}

    @Override
    public UserResponse updateUser(UpdateUserRequest request) {
        User existingUser = userRepository.findById(request.getId());
        if (existingUser == null) {
            return null;
        }

        existingUser.setEmail(new Email(request.getEmail()));
        existingUser.setFullName(request.getFullName());
        
        if (request.getPhoneNumber() != null && !request.getPhoneNumber().isEmpty()) {
            existingUser.setPhoneNumber(new PhoneNumber(request.getPhoneNumber()));
        } else {
            existingUser.setPhoneNumber(null);
        }
        
        existingUser.setRole(User.Role.valueOf(request.getRole()));

        // Update password if provided
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            existingUser.setPassword(new Password(passwordHasher.hashPassword(request.getPassword())));
        }

        User updatedUser = userRepository.save(existingUser);
        if (updatedUser != null) {
            return UserResponse.fromUser(updatedUser);
        }
        
        return null;
    }

    @Override
    public boolean deleteUser(DeleteUserRequest request) {
        return userRepository.delete(request.getId());
    }

    // THÊM CÁC METHODS NÀY VÀO CUỐI CLASS ManageUserInteractor - FIX: Đặt đúng vị trí
    public void loginUser(LoginRequest request) {
        LoginResponse response = login(request);
        if (outputBoundary != null) {
            outputBoundary.presentLoginResult(response);
        }
    }

    public void getUserById(Long id) {
        User user = userRepository.findById(id);
        if (user != null && outputBoundary != null) {
            UserResponse response = UserResponse.fromUser(user);
            outputBoundary.presentUser(response);
        } else if (outputBoundary != null) {
            outputBoundary.presentError("User not found");
        }
    }

    public void changePassword(Long userId, String currentPassword, String newPassword) {
        User user = userRepository.findById(userId);
        if (user == null) {
            if (outputBoundary != null) {
                outputBoundary.presentError("User not found");
            }
            return;
        }

        boolean currentPasswordValid = passwordHasher.checkPassword(currentPassword, user.getPassword().getValue());
        if (!currentPasswordValid) {
            if (outputBoundary != null) {
                outputBoundary.presentError("Current password is incorrect");
            }
            return;
        }

        String newHashedPassword = passwordHasher.hashPassword(newPassword);
        user.setPassword(new Password(newHashedPassword));
        User updatedUser = userRepository.save(user);

        if (updatedUser != null && outputBoundary != null) {
            outputBoundary.presentSuccess("Password changed successfully");
        }
    }
}