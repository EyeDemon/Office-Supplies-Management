package com.qlvanphongpham.usecase.boundary.input;

import com.qlvanphongpham.usecase.request.*;
import com.qlvanphongpham.usecase.response.*;

public interface ManageUserInputBoundary {
    LoginResponse login(LoginRequest request);
    UserResponse registerUser(RegisterUserRequest request);
    UserResponse updateUser(UpdateUserRequest request);
    boolean deleteUser(DeleteUserRequest request);
}