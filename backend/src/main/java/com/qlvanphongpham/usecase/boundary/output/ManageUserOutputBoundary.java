package com.qlvanphongpham.usecase.boundary.output;

import com.qlvanphongpham.usecase.response.LoginResponse;
import com.qlvanphongpham.usecase.response.UserResponse;

public interface ManageUserOutputBoundary {
    void presentLoginResult(LoginResponse response);
    void presentUserResult(UserResponse response);
    void presentError(String message);
    
    // THÊM METHODS MỚI
    void presentUser(UserResponse user);
    void presentSuccess(String message);
}