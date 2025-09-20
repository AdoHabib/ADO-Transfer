package com.adotransfer.service;

import com.adotransfer.dto.RegisterRequest;
import com.adotransfer.dto.UserResponse;
import com.adotransfer.model.User;

public interface IUserService {
    
    UserResponse registerUser(RegisterRequest request);
    
    UserResponse getUserByPhoneNumber(String phoneNumber);
    
    UserResponse getUserById(Long id);
    
    UserResponse updateUser(Long id, RegisterRequest request);
    
    void setUserPin(Long userId, String pin);
    
    boolean verifyPin(Long userId, String pin);
    
    void verifyUser(Long userId);
    
    void verifyKyc(Long userId);
    
    User findUserByPhoneNumber(String phoneNumber);
    
    User findUserById(Long id);
}
