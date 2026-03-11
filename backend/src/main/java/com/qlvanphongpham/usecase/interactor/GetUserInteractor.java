package com.qlvanphongpham.usecase.interactor;

import com.qlvanphongpham.usecase.boundary.input.GetUserInputBoundary;
import com.qlvanphongpham.usecase.port.UserRepository;
import com.qlvanphongpham.usecase.request.GetUserRequest;
import com.qlvanphongpham.usecase.response.UserResponse;
import com.qlvanphongpham.usecase.response.UserListResponse;
import com.qlvanphongpham.domain.User;
import java.util.List;
import java.util.stream.Collectors;

public class GetUserInteractor implements GetUserInputBoundary {
    private UserRepository userRepository;

    public GetUserInteractor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse getUserById(GetUserRequest request) {
        User user = userRepository.findById(request.getId());
        if (user != null) {
            return UserResponse.fromUser(user);
        }
        return null;
    }

    @Override
    public UserListResponse getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = users.stream()
                .map(UserResponse::fromUser)
                .collect(Collectors.toList());
        
        return new UserListResponse(userResponses);
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return UserResponse.fromUser(user);
        }
        return null;
    }
}