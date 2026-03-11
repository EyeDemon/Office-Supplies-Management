package com.qlvanphongpham.usecase.boundary.input;

import com.qlvanphongpham.usecase.request.GetUserRequest;
import com.qlvanphongpham.usecase.response.UserResponse;
import com.qlvanphongpham.usecase.response.UserListResponse;

public interface GetUserInputBoundary {
    UserResponse getUserById(GetUserRequest request);
    UserListResponse getAllUsers();
    UserResponse getUserByUsername(String username);
}