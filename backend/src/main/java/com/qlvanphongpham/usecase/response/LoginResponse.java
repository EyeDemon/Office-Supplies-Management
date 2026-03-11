package com.qlvanphongpham.usecase.response;

import com.qlvanphongpham.domain.User;

public class LoginResponse {
    private boolean success;
    private String message;
    private User user;

    // Constructors
    public LoginResponse() {}

    public LoginResponse(boolean success, String message, User user) {
        this.success = success;
        this.message = message;
        this.user = user;
    }

    // Getters and Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", user=" + (user != null ? user.getUsername() : "null") +
                '}';
    }
}