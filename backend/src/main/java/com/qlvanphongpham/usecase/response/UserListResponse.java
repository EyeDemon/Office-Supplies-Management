package com.qlvanphongpham.usecase.response;

import java.util.List;
import java.util.ArrayList;

public class UserListResponse {
    private List<UserResponse> users;

    // Constructors
    public UserListResponse() {
        this.users = new ArrayList<>();
    }

    public UserListResponse(List<UserResponse> users) {
        this.users = users != null ? users : new ArrayList<>();
    }

    // Getters and Setters
    public List<UserResponse> getUsers() { return users; }
    public void setUsers(List<UserResponse> users) { this.users = users; }

    public void addUser(UserResponse user) {
        if (this.users == null) {
            this.users = new ArrayList<>();
        }
        this.users.add(user);
    }

    public int getCount() {
        return users != null ? users.size() : 0;
    }

    @Override
    public String toString() {
        return "UserListResponse{" +
                "users=" + users +
                ", count=" + getCount() +
                '}';
    }
}