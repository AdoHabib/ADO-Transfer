package com.adotransfer.repository;

import com.adotransfer.model.Account;
import com.adotransfer.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile("!no-db")
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    Optional<Account> findByUser(User user);
    
    Optional<Account> findByAccountNumber(String accountNumber);
    
    boolean existsByAccountNumber(String accountNumber);
    
    @Query("SELECT a FROM Account a WHERE a.user = :user AND a.status = 'ACTIVE'")
    Optional<Account> findActiveAccountByUser(@Param("user") User user);
    
    @Query("SELECT a FROM Account a WHERE a.accountNumber = :accountNumber AND a.status = 'ACTIVE'")
    Optional<Account> findActiveAccountByAccountNumber(@Param("accountNumber") String accountNumber);
}
