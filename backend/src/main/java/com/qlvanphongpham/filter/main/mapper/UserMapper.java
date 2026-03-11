package com.qlvanphongpham.filter.main.mapper;

import com.qlvanphongpham.domain.User;
import com.qlvanphongpham.usecase.response.UserResponse;
import com.qlvanphongpham.adapter.viewmodel.UserViewModel;

public class UserMapper {

    public static UserResponse toUserResponse(User user) {
        if (user == null) {
            return null;
        }

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail().getValue());
        response.setFullName(user.getFullName());
        
        if (user.getPhoneNumber() != null) {
            response.setPhoneNumber(user.getPhoneNumber().getValue());
        }
        
        response.setRole(user.getRole().name());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        
        return response;
    }

    public static UserViewModel toUserViewModel(User user) {
        if (user == null) {
            return null;
        }

        UserViewModel viewModel = new UserViewModel();
        viewModel.setId(user.getId());
        viewModel.setUsername(user.getUsername());
        viewModel.setEmail(user.getEmail().getValue());
        viewModel.setFullName(user.getFullName());
        
        if (user.getPhoneNumber() != null) {
            viewModel.setPhoneNumber(user.getPhoneNumber().getValue());
        }
        
        viewModel.setRole(user.getRole().name());
        viewModel.setCreatedAt(user.getCreatedAt());
        
        return viewModel;
    }
}