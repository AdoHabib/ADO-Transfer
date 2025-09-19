package com.adotransfer.data.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private Long id;
    
    @SerializedName("firstName")
    private String firstName;
    
    @SerializedName("lastName")
    private String lastName;
    
    @SerializedName("phoneNumber")
    private String phoneNumber;
    
    @SerializedName("email")
    private String email;
    
    @SerializedName("status")
    private String status;
    
    @SerializedName("isVerified")
    private Boolean isVerified;
    
    @SerializedName("kycVerified")
    private Boolean kycVerified;
    
    @SerializedName("createdAt")
    private String createdAt;
    
    @SerializedName("fullName")
    private String fullName;

    // Constructeurs
    public User() {}

    public User(Long id, String firstName, String lastName, String phoneNumber, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
