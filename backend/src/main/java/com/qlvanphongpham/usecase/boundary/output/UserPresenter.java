package com.qlvanphongpham.usecase.boundary.output;

import com.qlvanphongpham.usecase.response.LoginResponse;
import com.qlvanphongpham.usecase.response.UserResponse;
import com.qlvanphongpham.usecase.response.UserListResponse;
import java.util.List;
import java.util.ArrayList;

public class UserPresenter implements ManageUserOutputBoundary {
    private UserResponse lastUser;
    private LoginResponse loginResponse;
    private List<UserResponse> userList = new ArrayList<>();
    private String lastError;
    private String lastSuccess;

    @Override
    public void presentLoginResult(LoginResponse response) {
        this.loginResponse = response;
    }

    @Override
    public void presentUserResult(UserResponse response) {
        this.lastUser = response;
    }

    @Override
    public void presentError(String message) {
        this.lastError = message;
    }

    // THÊM CÁC METHODS BỊ THIẾU
    public void presentUser(UserResponse user) {
        this.lastUser = user;
    }

    public void presentSuccess(String message) {
        this.lastSuccess = message;
    }

    public void presentUserList(List<UserResponse> users) {
        this.userList = new ArrayList<>(users);
    }

    // GETTER METHODS CHO TEST
    public UserResponse getLastUser() { return lastUser; }
    public LoginResponse getLoginResponse() { return loginResponse; }
    public List<UserResponse> getUserList() { return new ArrayList<>(userList); }
    public String getLastError() { return lastError; }
    public String getLastSuccess() { return lastSuccess; }

    public void clear() {
        this.lastUser = null;
        this.loginResponse = null;
        this.userList.clear();
        this.lastError = null;
        this.lastSuccess = null;
    }
}