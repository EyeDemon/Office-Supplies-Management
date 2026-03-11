package com.qlvanphongpham.adapter.presenter;

import com.qlvanphongpham.usecase.response.UserResponse;
import com.qlvanphongpham.usecase.boundary.output.UserPresenter;
import com.qlvanphongpham.usecase.response.LoginResponse;
import com.qlvanphongpham.domain.User;
import com.qlvanphongpham.domain.valueobjects.Email;
import com.qlvanphongpham.domain.valueobjects.Password;
import com.qlvanphongpham.domain.valueobjects.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserPresenterTest {
    
    private UserPresenter userPresenter;
    
    @BeforeEach
    void setUp() {
        userPresenter = new UserPresenter();
    }
    
    @Test
    void testPresentUser() {
        // Given
        UserResponse user = UserResponse.builder()
            .id(1L)
            .username("testuser")
            .email("test@email.com")
            .phoneNumber("0123456789")
            .fullName("Test User")
            .role("USER")
            .createdAt(LocalDateTime.now())
            .build();
        
        // When
        userPresenter.presentUser(user);
        
        // Then
        assertEquals(user, userPresenter.getLastUser());
    }
    
    @Test
void testPresentLoginResult() {
    // Given - FIX: Dùng số điện thoại hợp lệ
    User user = new User();
    user.setId(1L);
    user.setUsername("testuser");
    user.setEmail(new Email("test@email.com"));
    user.setPassword(new Password("hashed_password"));
    user.setFullName("Test User");
    user.setPhoneNumber(new PhoneNumber("0912345678")); // FIX: Số hợp lệ
    user.setRole(User.Role.USER);
        
    LoginResponse loginResponse = new LoginResponse();
    loginResponse.setSuccess(true);
    loginResponse.setMessage("Login successful");
    loginResponse.setUser(user);
    
    // When
    userPresenter.presentLoginResult(loginResponse);
    
    // Then
    assertEquals(loginResponse, userPresenter.getLoginResponse());
}
    
    @Test
    void testPresentUserList() {
        // Given
        UserResponse user1 = UserResponse.builder()
            .id(1L)
            .username("user1")
            .email("user1@email.com")
            .phoneNumber("0123456789")
            .fullName("User One")
            .role("USER")
            .createdAt(LocalDateTime.now())
            .build();
            
        UserResponse user2 = UserResponse.builder()
            .id(2L)
            .username("user2")
            .email("user2@email.com")
            .phoneNumber("0987654321")
            .fullName("User Two")
            .role("USER")
            .createdAt(LocalDateTime.now())
            .build();
            
        List<UserResponse> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        
        // When
        userPresenter.presentUserList(users);
        
        // Then
        assertEquals(2, userPresenter.getUserList().size());
        assertTrue(userPresenter.getUserList().contains(user1));
        assertTrue(userPresenter.getUserList().contains(user2));
    }
    
    @Test
    void testPresentError() {
        // Given
        String errorMessage = "An error occurred";
        
        // When
        userPresenter.presentError(errorMessage);
        
        // Then
        assertEquals(errorMessage, userPresenter.getLastError());
    }
    
    @Test
    void testPresentSuccess() {
        // Given
        String successMessage = "Operation successful";
        
        // When
        userPresenter.presentSuccess(successMessage);
        
        // Then
        assertEquals(successMessage, userPresenter.getLastSuccess());
    }
    
    @Test
    void testClear() {
        // Given
        UserResponse userResponse = UserResponse.builder()
            .id(1L)
            .username("testuser")
            .email("test@email.com")
            .phoneNumber("0123456789")
            .fullName("Test User")
            .role("USER")
            .createdAt(LocalDateTime.now())
            .build();
            
        // FIX: Tạo User object cho LoginResponse
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail(new Email("test@email.com"));
        user.setPassword(new Password("hashed_password"));
        user.setFullName("Test User");
        user.setPhoneNumber(new PhoneNumber("0123456789"));
        user.setRole(User.Role.USER);
            
        List<UserResponse> users = new ArrayList<>();
        users.add(userResponse);
        
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setSuccess(true);
        loginResponse.setMessage("Success");
        loginResponse.setUser(user); // FIX: Set User object, không phải UserResponse
        
        userPresenter.presentUser(userResponse);
        userPresenter.presentLoginResult(loginResponse);
        userPresenter.presentUserList(users);
        userPresenter.presentError("Error");
        userPresenter.presentSuccess("Success");
        
        // Verify data is present before clear
        assertNotNull(userPresenter.getLastUser());
        assertNotNull(userPresenter.getLoginResponse());
        assertFalse(userPresenter.getUserList().isEmpty());
        assertNotNull(userPresenter.getLastError());
        assertNotNull(userPresenter.getLastSuccess());
        
        // When
        userPresenter.clear();
        
        // Then
        assertNull(userPresenter.getLastUser());
        assertNull(userPresenter.getLoginResponse());
        assertTrue(userPresenter.getUserList().isEmpty());
        assertNull(userPresenter.getLastError());
        assertNull(userPresenter.getLastSuccess());
    }
    
    @Test
    void testGetUserList_ReturnsCopy() {
        // Given
        UserResponse user1 = UserResponse.builder()
            .id(1L)
            .username("user1")
            .email("user1@email.com")
            .phoneNumber("0123456789")
            .fullName("User One")
            .role("USER")
            .createdAt(LocalDateTime.now())
            .build();
            
        List<UserResponse> users = new ArrayList<>();
        users.add(user1);
        
        userPresenter.presentUserList(users);
        
        // When - modify the returned list
        List<UserResponse> returnedList = userPresenter.getUserList();
        returnedList.clear();
        
        // Then - original list in presenter should not be affected
        assertEquals(1, userPresenter.getUserList().size());
    }
}