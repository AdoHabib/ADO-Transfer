package com.adotransfer.service;

import com.adotransfer.model.Account;
import com.adotransfer.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Profile("no-db")
public class MockAccountService {

    public Account getAccountByUserId(Long userId) {
        // Mock account
        Account mockAccount = new Account();
        mockAccount.setId(1L);
        mockAccount.setAccountNumber("MOCK123456");
        mockAccount.setBalance(BigDecimal.valueOf(1000.00));
        return mockAccount;
    }

    public Account getAccountByAccountNumber(String accountNumber) {
        // Mock account
        Account mockAccount = new Account();
        mockAccount.setId(1L);
        mockAccount.setAccountNumber(accountNumber);
        mockAccount.setBalance(BigDecimal.valueOf(1000.00));
        return mockAccount;
    }

    public BigDecimal getBalance(Long userId) {
        return BigDecimal.valueOf(1000.00);
    }

    public void deposit(Long userId, BigDecimal amount) {
        // Mock deposit - do nothing
    }

    public void withdraw(Long userId, BigDecimal amount) {
        // Mock withdrawal - do nothing
    }
}
