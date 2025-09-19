package com.adotransfer.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class TransferRequest {
    
    @NotBlank(message = "Le numéro de téléphone du destinataire est requis")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Format de numéro de téléphone invalide")
    private String recipientPhoneNumber;
    
    @NotNull(message = "Le montant est requis")
    @DecimalMin(value = "0.01", message = "Le montant doit être supérieur à 0")
    private BigDecimal amount;
    
    @Size(max = 500, message = "La description ne peut pas dépasser 500 caractères")
    private String description;
    
    @NotBlank(message = "Le PIN est requis")
    @Pattern(regexp = "^\\d{6}$", message = "Le PIN doit contenir exactement 6 chiffres")
    private String pin;
    
    // Constructeurs
    public TransferRequest() {}
    
    public TransferRequest(String recipientPhoneNumber, BigDecimal amount, String description, String pin) {
        this.recipientPhoneNumber = recipientPhoneNumber;
        this.amount = amount;
        this.description = description;
        this.pin = pin;
    }
    
    // Getters et Setters
    public String getRecipientPhoneNumber() {
        return recipientPhoneNumber;
    }
    
    public void setRecipientPhoneNumber(String recipientPhoneNumber) {
        this.recipientPhoneNumber = recipientPhoneNumber;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getPin() {
        return pin;
    }
    
    public void setPin(String pin) {
        this.pin = pin;
    }
}
