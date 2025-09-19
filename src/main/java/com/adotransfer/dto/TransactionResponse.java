package com.adotransfer.dto;

import com.adotransfer.model.Transaction;
import com.adotransfer.model.TransactionStatus;
import com.adotransfer.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse {
    
    private Long id;
    private String transactionId;
    private String senderPhoneNumber;
    private String receiverPhoneNumber;
    private String senderName;
    private String receiverName;
    private BigDecimal amount;
    private BigDecimal fee;
    private String description;
    private TransactionType type;
    private TransactionStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private String reference;
    
    // Constructeurs
    public TransactionResponse() {}
    
    public TransactionResponse(Transaction transaction) {
        this.id = transaction.getId();
        this.transactionId = transaction.getTransactionId();
        this.senderPhoneNumber = transaction.getSender().getPhoneNumber();
        this.receiverPhoneNumber = transaction.getReceiver().getPhoneNumber();
        this.senderName = transaction.getSender().getFullName();
        this.receiverName = transaction.getReceiver().getFullName();
        this.amount = transaction.getAmount();
        this.fee = transaction.getFee();
        this.description = transaction.getDescription();
        this.type = transaction.getType();
        this.status = transaction.getStatus();
        this.createdAt = transaction.getCreatedAt();
        this.completedAt = transaction.getCompletedAt();
        this.reference = transaction.getReference();
    }
    
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
    
    public TransactionType getType() {
        return type;
    }
    
    public void setType(TransactionType type) {
        this.type = type;
    }
    
    public TransactionStatus getStatus() {
        return status;
    }
    
    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
    
    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
    
    public String getReference() {
        return reference;
    }
    
    public void setReference(String reference) {
        this.reference = reference;
    }
}
