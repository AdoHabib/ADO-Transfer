package com.adotransfer.service;

import com.adotransfer.dto.TransferRequest;
import com.adotransfer.dto.TransactionResponse;
import com.adotransfer.model.Transaction;
import com.adotransfer.model.TransactionStatus;
import com.adotransfer.model.TransactionType;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Profile("no-db")
public class MockTransactionService implements ITransactionService {

    public TransactionResponse transferMoney(Long senderId, TransferRequest request) {
        // Mock transaction response
        Transaction mockTransaction = new Transaction();
        mockTransaction.setId(1L);
        mockTransaction.setTransactionId("MOCK_TXN_123");
        mockTransaction.setAmount(request.getAmount());
        mockTransaction.setType(TransactionType.TRANSFER);
        mockTransaction.setDescription(request.getDescription());
        mockTransaction.setStatus(TransactionStatus.COMPLETED);
        mockTransaction.setCreatedAt(LocalDateTime.now());
        mockTransaction.setCompletedAt(LocalDateTime.now());
        
        return new TransactionResponse(mockTransaction);
    }

    public Page<TransactionResponse> getUserTransactions(Long userId, Pageable pageable) {
        // Mock empty page response
        List<TransactionResponse> emptyList = new ArrayList<>();
        return new PageImpl<>(emptyList, pageable, 0);
    }

    public Page<TransactionResponse> getSentTransactions(Long userId, Pageable pageable) {
        // Mock empty page response
        List<TransactionResponse> emptyList = new ArrayList<>();
        return new PageImpl<>(emptyList, pageable, 0);
    }

    public Page<TransactionResponse> getReceivedTransactions(Long userId, Pageable pageable) {
        // Mock empty page response
        List<TransactionResponse> emptyList = new ArrayList<>();
        return new PageImpl<>(emptyList, pageable, 0);
    }

    public TransactionResponse getTransactionById(String transactionId) {
        // Mock transaction response
        Transaction mockTransaction = new Transaction();
        mockTransaction.setId(1L);
        mockTransaction.setTransactionId(transactionId);
        mockTransaction.setAmount(new BigDecimal("100.00"));
        mockTransaction.setType(TransactionType.TRANSFER);
        mockTransaction.setDescription("Mock transaction");
        mockTransaction.setStatus(TransactionStatus.COMPLETED);
        mockTransaction.setCreatedAt(LocalDateTime.now());
        mockTransaction.setCompletedAt(LocalDateTime.now());
        
        return new TransactionResponse(mockTransaction);
    }

    public List<TransactionResponse> getTransactionsByDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        // Mock empty list response
        return new ArrayList<>();
    }

    public BigDecimal getTotalSentAmount(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        // Mock response
        return BigDecimal.ZERO;
    }

    public BigDecimal getTotalReceivedAmount(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        // Mock response
        return BigDecimal.ZERO;
    }

    public TransactionResponse cancelTransaction(Long userId, String transactionId) {
        // Mock transaction response
        Transaction mockTransaction = new Transaction();
        mockTransaction.setId(1L);
        mockTransaction.setTransactionId(transactionId);
        mockTransaction.setAmount(new BigDecimal("100.00"));
        mockTransaction.setType(TransactionType.TRANSFER);
        mockTransaction.setDescription("Mock cancelled transaction");
        mockTransaction.setStatus(TransactionStatus.CANCELLED);
        mockTransaction.setCreatedAt(LocalDateTime.now());
        
        return new TransactionResponse(mockTransaction);
    }
}
