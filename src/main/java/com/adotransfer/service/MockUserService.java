package com.adotransfer.service;

import com.adotransfer.dto.RegisterRequest;
import com.adotransfer.dto.UserResponse;
import com.adotransfer.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("no-db")
public class MockUserService {

    public UserResponse registerUser(RegisterRequest request) {
        // Mock response for testing
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setFirstName(request.getFirstName());
        mockUser.setLastName(request.getLastName());
        mockUser.setPhoneNumber(request.getPhoneNumber());
        mockUser.setEmail(request.getEmail());
        
        return new UserResponse(mockUser);
    }

    public UserResponse getUserByPhoneNumber(String phoneNumber) {
        // Mock response
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setPhoneNumber(phoneNumber);
        return new UserResponse(mockUser);
    }

    public UserResponse getUserById(Long id) {
        // Mock response
        User mockUser = new User();
        mockUser.setId(id);
        return new UserResponse(mockUser);
    }

    public User findUserByPhoneNumber(String phoneNumber) {
        // Mock user
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setPhoneNumber(phoneNumber);
        return mockUser;
    }

    public User findUserById(Long id) {
        // Mock user
        User mockUser = new User();
        mockUser.setId(id);
        return mockUser;
    }

    public boolean verifyPin(Long userId, String pin) {
        // Mock verification - always return true for testing
        return true;
    }
}

