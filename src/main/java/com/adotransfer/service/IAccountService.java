package com.adotransfer.service;

import com.adotransfer.model.Account;

import java.math.BigDecimal;

public interface IAccountService {
    
    Account getAccountByUserId(Long userId);
    
    Account getAccountByAccountNumber(String accountNumber);
    
    BigDecimal getBalance(Long userId);
    
    void deposit(Long userId, BigDecimal amount);
    
    void withdraw(Long userId, BigDecimal amount);
    
    void freezeAccount(Long userId);
    
    void unfreezeAccount(Long userId);
    
    void suspendAccount(Long userId);
    
    void activateAccount(Long userId);
}
