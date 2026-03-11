package com.qlvanphongpham.usecase.request;

public class DeleteUserRequest {
    private Long id;

    // Constructors
    public DeleteUserRequest() {}

    public DeleteUserRequest(Long id) {
        this.id = id;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @Override
    public String toString() {
        return "DeleteUserRequest{" +
                "id=" + id +
                '}';
    }
}