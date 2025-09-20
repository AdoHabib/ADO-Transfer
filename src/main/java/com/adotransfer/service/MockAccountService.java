package com.adotransfer.service;

import com.adotransfer.model.Account;
import com.adotransfer.model.AccountStatus;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Profile("no-db")
public class MockAccountService implements IAccountService {

    public Account getAccountByUserId(Long userId) {
        // Mock account
        Account mockAccount = new Account();
        mockAccount.setId(1L);
        mockAccount.setAccountNumber("MOCK123456");
        mockAccount.setBalance(BigDecimal.valueOf(1000.00));
        mockAccount.setStatus(AccountStatus.ACTIVE);
        return mockAccount;
    }

    public Account getAccountByAccountNumber(String accountNumber) {
        // Mock account
        Account mockAccount = new Account();
        mockAccount.setId(1L);
        mockAccount.setAccountNumber(accountNumber);
        mockAccount.setBalance(BigDecimal.valueOf(1000.00));
        mockAccount.setStatus(AccountStatus.ACTIVE);
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

    public void freezeAccount(Long userId) {
        // Mock freeze - do nothing
    }

    public void unfreezeAccount(Long userId) {
        // Mock unfreeze - do nothing
    }

    public void suspendAccount(Long userId) {
        // Mock suspend - do nothing
    }

    public void activateAccount(Long userId) {
        // Mock activate - do nothing
    }
}



