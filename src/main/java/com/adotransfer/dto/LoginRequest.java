package com.adotransfer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class LoginRequest {
    
    @NotBlank(message = "Le numéro de téléphone est requis")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Format de numéro de téléphone invalide")
    private String phoneNumber;
    
    @NotBlank(message = "Le mot de passe est requis")
    private String password;
    
    // Constructeurs
    public LoginRequest() {}
    
    public LoginRequest(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
    
    // Getters et Setters
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}
