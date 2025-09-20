package com.adotransfer.repository;

import com.adotransfer.model.Transaction;
import com.adotransfer.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("!no-db")
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    Optional<Transaction> findByTransactionId(String transactionId);
    
    List<Transaction> findBySender(User sender);
    
    List<Transaction> findByReceiver(User receiver);
    
    Page<Transaction> findBySenderOrderByCreatedAtDesc(User sender, Pageable pageable);
    
    Page<Transaction> findByReceiverOrderByCreatedAtDesc(User receiver, Pageable pageable);
    
    @Query("SELECT t FROM Transaction t WHERE (t.sender = :user OR t.receiver = :user) ORDER BY t.createdAt DESC")
    Page<Transaction> findByUserOrderByCreatedAtDesc(@Param("user") User user, Pageable pageable);
    
    @Query("SELECT t FROM Transaction t WHERE t.sender = :user AND t.createdAt BETWEEN :startDate AND :endDate ORDER BY t.createdAt DESC")
    List<Transaction> findSentTransactionsByUserAndDateRange(@Param("user") User user, 
                                                           @Param("startDate") LocalDateTime startDate, 
                                                           @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT t FROM Transaction t WHERE t.receiver = :user AND t.createdAt BETWEEN :startDate AND :endDate ORDER BY t.createdAt DESC")
    List<Transaction> findReceivedTransactionsByUserAndDateRange(@Param("user") User user, 
                                                               @Param("startDate") LocalDateTime startDate, 
                                                               @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.sender = :user AND t.status = 'COMPLETED' AND t.createdAt BETWEEN :startDate AND :endDate")
    Double getTotalSentAmountByUserAndDateRange(@Param("user") User user, 
                                              @Param("startDate") LocalDateTime startDate, 
                                              @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.receiver = :user AND t.status = 'COMPLETED' AND t.createdAt BETWEEN :startDate AND :endDate")
    Double getTotalReceivedAmountByUserAndDateRange(@Param("user") User user, 
                                                  @Param("startDate") LocalDateTime startDate, 
                                                  @Param("endDate") LocalDateTime endDate);
}
