package com.qlvanphongpham.usecase.request;

public class GetUserRequest {
    private Long id;

    // Constructors
    public GetUserRequest() {}

    public GetUserRequest(Long id) {
        this.id = id;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @Override
    public String toString() {
        return "GetUserRequest{" +
                "id=" + id +
                '}';
    }
}