package com.adotransfer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "transaction_id", unique = true, nullable = false, length = 50)
    private String transactionId;
    
    @NotNull(message = "L'expéditeur est requis")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;
    
    @NotNull(message = "Le destinataire est requis")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;
    
    @NotNull(message = "Le montant est requis")
    @DecimalMin(value = "0.01", message = "Le montant doit être supérieur à 0")
    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;
    
    @Column(name = "fee", precision = 19, scale = 2)
    private BigDecimal fee = BigDecimal.ZERO;
    
    @Column(name = "description", length = 500)
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TransactionType type;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransactionStatus status = TransactionStatus.PENDING;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "completed_at")
    private LocalDateTime completedAt;
    
    @Column(name = "reference", length = 100)
    private String reference;
    
    // Constructeurs
    public Transaction() {}
    
    public Transaction(String transactionId, User sender, User receiver, BigDecimal amount, 
                     TransactionType type, String description) {
        this.transactionId = transactionId;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.type = type;
        this.description = description;
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
    
    public User getSender() {
        return sender;
    }
    
    public void setSender(User sender) {
        this.sender = sender;
    }
    
    public User getReceiver() {
        return receiver;
    }
    
    public void setReceiver(User receiver) {
        this.receiver = receiver;
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
    
    // Méthodes utilitaires
    public BigDecimal getTotalAmount() {
        return amount.add(fee);
    }
    
    public boolean isCompleted() {
        return status == TransactionStatus.COMPLETED;
    }
    
    public boolean isPending() {
        return status == TransactionStatus.PENDING;
    }
    
    public boolean isFailed() {
        return status == TransactionStatus.FAILED;
    }
}
