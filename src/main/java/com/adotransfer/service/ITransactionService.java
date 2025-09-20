package com.adotransfer.service;

import com.adotransfer.dto.TransferRequest;
import com.adotransfer.dto.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ITransactionService {
    
    TransactionResponse transferMoney(Long senderId, TransferRequest request);
    
    Page<TransactionResponse> getUserTransactions(Long userId, Pageable pageable);
    
    Page<TransactionResponse> getSentTransactions(Long userId, Pageable pageable);
    
    Page<TransactionResponse> getReceivedTransactions(Long userId, Pageable pageable);
    
    TransactionResponse getTransactionById(String transactionId);
    
    List<TransactionResponse> getTransactionsByDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    
    BigDecimal getTotalSentAmount(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    
    BigDecimal getTotalReceivedAmount(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    
    TransactionResponse cancelTransaction(Long userId, String transactionId);
}
