package com.adotransfer.dto;

import com.adotransfer.model.User;
import com.adotransfer.model.UserStatus;

import java.time.LocalDateTime;

public class UserResponse {
    
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private UserStatus status;
    private Boolean isVerified;
    private Boolean kycVerified;
    private LocalDateTime createdAt;
    private String fullName;
    
    // Constructeurs
    public UserResponse() {}
    
    public UserResponse(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.status = user.getStatus();
        this.isVerified = user.getIsVerified();
        this.kycVerified = user.getKycVerified();
        this.createdAt = user.getCreatedAt();
        this.fullName = user.getFullName();
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public UserStatus getStatus() {
        return status;
    }
    
    public void setStatus(UserStatus status) {
        this.status = status;
    }
    
    public Boolean getIsVerified() {
        return isVerified;
    }
    
    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }
    
    public Boolean getKycVerified() {
        return kycVerified;
    }
    
    public void setKycVerified(Boolean kycVerified) {
        this.kycVerified = kycVerified;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
