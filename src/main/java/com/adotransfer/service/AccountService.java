package com.adotransfer.service;

import com.adotransfer.model.Account;
import com.adotransfer.model.User;
import com.adotransfer.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserService userService;

    public Account getAccountByUserId(Long userId) {
        User user = userService.findUserById(userId);
        return accountRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Compte non trouvé"));
    }

    public Account getAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Compte non trouvé"));
    }

    public BigDecimal getBalance(Long userId) {
        Account account = getAccountByUserId(userId);
        return account.getBalance();
    }

    public void deposit(Long userId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Le montant doit être positif");
        }

        Account account = getAccountByUserId(userId);
        account.addBalance(amount);
        accountRepository.save(account);
    }

    public void withdraw(Long userId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Le montant doit être positif");
        }

        Account account = getAccountByUserId(userId);
        if (!account.hasSufficientBalance(amount)) {
            throw new RuntimeException("Solde insuffisant");
        }

        account.subtractBalance(amount);
        accountRepository.save(account);
    }

    public void freezeAccount(Long userId) {
        Account account = getAccountByUserId(userId);
        account.setStatus(com.adotransfer.model.AccountStatus.FROZEN);
        accountRepository.save(account);
    }

    public void unfreezeAccount(Long userId) {
        Account account = getAccountByUserId(userId);
        account.setStatus(com.adotransfer.model.AccountStatus.ACTIVE);
        accountRepository.save(account);
    }

    public void suspendAccount(Long userId) {
        Account account = getAccountByUserId(userId);
        account.setStatus(com.adotransfer.model.AccountStatus.SUSPENDED);
        accountRepository.save(account);
    }

    public void activateAccount(Long userId) {
        Account account = getAccountByUserId(userId);
        account.setStatus(com.adotransfer.model.AccountStatus.ACTIVE);
        accountRepository.save(account);
    }
}
