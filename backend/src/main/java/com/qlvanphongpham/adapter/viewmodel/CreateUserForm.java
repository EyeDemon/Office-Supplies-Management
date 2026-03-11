package com.qlvanphongpham.adapter.viewmodel;

public class CreateUserForm {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;
    private String phoneNumber;
    private String role = "USER";

    // Constructors
    public CreateUserForm() {}

    public CreateUserForm(String username, String email, String password, String fullName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    // Validation methods
    public boolean isPasswordMatch() {
        return password != null && password.equals(confirmPassword);
    }

    public boolean isValid() {
        return username != null && !username.trim().isEmpty() &&
               email != null && !email.trim().isEmpty() &&
               password != null && !password.trim().isEmpty() &&
               fullName != null && !fullName.trim().isEmpty() &&
               isPasswordMatch();
    }

    @Override
    public String toString() {
        return "CreateUserForm{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}