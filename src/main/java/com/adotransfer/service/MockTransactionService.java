package com.adotransfer.service;

import com.adotransfer.dto.TransferRequest;
import com.adotransfer.dto.TransactionResponse;
import com.adotransfer.model.Transaction;
import com.adotransfer.model.TransactionStatus;
import com.adotransfer.model.TransactionType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Profile("no-db")
public class MockTransactionService {

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
}
