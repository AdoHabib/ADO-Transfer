package com.adotransfer.data.model;

import com.google.gson.annotations.SerializedName;
import java.math.BigDecimal;

public class Transaction {
    @SerializedName("id")
    private Long id;
    
    @SerializedName("transactionId")
    private String transactionId;
    
    @SerializedName("senderPhoneNumber")
    private String senderPhoneNumber;
    
    @SerializedName("receiverPhoneNumber")
    private String receiverPhoneNumber;
    
    @SerializedName("senderName")
    private String senderName;
    
    @SerializedName("receiverName")
    private String receiverName;
    
    @SerializedName("amount")
    private BigDecimal amount;
    
    @SerializedName("fee")
    private BigDecimal fee;
    
    @SerializedName("description")
    private String description;
    
    @SerializedName("type")
    private String type;
    
    @SerializedName("status")
    private String status;
    
    @SerializedName("createdAt")
    private String createdAt;
    
    @SerializedName("completedAt")
    private String completedAt;
    
    @SerializedName("reference")
    private String reference;

    // Constructeurs
    public Transaction() {}

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getSenderPhoneNumber() {
        return senderPhoneNumber;
    }

    public void setSenderPhoneNumber(String senderPhoneNumber) {
        this.senderPhoneNumber = senderPhoneNumber;
    }

    public String getReceiverPhoneNumber() {
        return receiverPhoneNumber;
    }

    public void setReceiverPhoneNumber(String receiverPhoneNumber) {
        this.receiverPhoneNumber = receiverPhoneNumber;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
