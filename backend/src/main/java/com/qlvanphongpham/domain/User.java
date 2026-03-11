package com.qlvanphongpham.domain;

import com.qlvanphongpham.domain.valueobjects.Email;
import com.qlvanphongpham.domain.valueobjects.Password;
import com.qlvanphongpham.domain.valueobjects.PhoneNumber;
import java.time.LocalDateTime;

public class User {
    private Long id;
    private String username;
    private Email email;
    private Password password;
    private String fullName;
    private PhoneNumber phoneNumber;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum Role {
        ADMIN, USER
    }

    // Constructors
    public User() {}

    public User(String username, Email email, Password password, String fullName, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Email getEmail() { return email; }
    public void setEmail(Email email) { this.email = email; }

    public Password getPassword() { return password; }
    public void setPassword(Password password) { this.password = password; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public PhoneNumber getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(PhoneNumber phoneNumber) { this.phoneNumber = phoneNumber; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // THÊM VÀO CLASS User - FIX: Đặt đúng vị trí trong class
    public void setPhone(String phone) {
        if (phone != null && !phone.trim().isEmpty()) {
            this.phoneNumber = new PhoneNumber(phone);
        } else {
            this.phoneNumber = null;
        }
    }

    public void setActive(boolean active) {
        // Nếu không có field active, có thể bỏ qua hoặc thêm field
        // Hiện tại không có field active trong class User
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email=" + email +
                ", fullName='" + fullName + '\'' +
                ", role=" + role +
                ", createdAt=" + createdAt +
                '}';
    }
}