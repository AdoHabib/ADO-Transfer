package com.adotransfer.service;

import com.adotransfer.dto.TransferRequest;
import com.adotransfer.dto.TransactionResponse;
import com.adotransfer.model.Account;
import com.adotransfer.model.Transaction;
import com.adotransfer.model.TransactionStatus;
import com.adotransfer.model.TransactionType;
import com.adotransfer.model.User;
import com.adotransfer.repository.AccountRepository;
import com.adotransfer.repository.TransactionRepository;
import com.adotransfer.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Profile("!no-db")
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EncryptionUtil encryptionUtil;

    @Value("${app.transfer-fee-percentage:0.5}")
    private double transferFeePercentage;

    @Value("${app.min-transfer-amount:1.0}")
    private BigDecimal minTransferAmount;

    @Value("${app.max-transfer-amount:10000.0}")
    private BigDecimal maxTransferAmount;

    public TransactionResponse transferMoney(Long senderId, TransferRequest request) {
        // Vérifier le PIN
        if (!userService.verifyPin(senderId, request.getPin())) {
            throw new RuntimeException("PIN incorrect");
        }

        // Récupérer l'expéditeur
        User sender = userService.findUserById(senderId);
        Account senderAccount = accountRepository.findByUser(sender)
                .orElseThrow(() -> new RuntimeException("Compte expéditeur non trouvé"));

        // Récupérer le destinataire
        User receiver = userService.findUserByPhoneNumber(request.getRecipientPhoneNumber());
        Account receiverAccount = accountRepository.findByUser(receiver)
                .orElseThrow(() -> new RuntimeException("Compte destinataire non trouvé"));

        // Vérifications
        if (sender.getId().equals(receiver.getId())) {
            throw new RuntimeException("Impossible de transférer vers son propre compte");
        }

        if (request.getAmount().compareTo(minTransferAmount) < 0) {
            throw new RuntimeException("Le montant minimum est de " + minTransferAmount + " EUR");
        }

        if (request.getAmount().compareTo(maxTransferAmount) > 0) {
            throw new RuntimeException("Le montant maximum est de " + maxTransferAmount + " EUR");
        }

        // Calculer les frais
        BigDecimal fee = request.getAmount().multiply(BigDecimal.valueOf(transferFeePercentage / 100));
        BigDecimal totalAmount = request.getAmount().add(fee);

        // Vérifier le solde
        if (!senderAccount.hasSufficientBalance(totalAmount)) {
            throw new RuntimeException("Solde insuffisant");
        }

        // Créer la transaction
        String transactionId = encryptionUtil.generateTransactionId();
        Transaction transaction = new Transaction(
                transactionId,
                sender,
                receiver,
                request.getAmount(),
                TransactionType.TRANSFER,
                request.getDescription()
        );
        transaction.setFee(fee);
        transaction.setStatus(TransactionStatus.PROCESSING);

        // Sauvegarder la transaction
        transaction = transactionRepository.save(transaction);

        try {
            // Effectuer le transfert
            senderAccount.subtractBalance(totalAmount);
            receiverAccount.addBalance(request.getAmount());

            accountRepository.save(senderAccount);
            accountRepository.save(receiverAccount);

            // Mettre à jour le statut de la transaction
            transaction.setStatus(TransactionStatus.COMPLETED);
            transaction.setCompletedAt(LocalDateTime.now());
            transaction = transactionRepository.save(transaction);

            return new TransactionResponse(transaction);

        } catch (Exception e) {
            // En cas d'erreur, marquer la transaction comme échouée
            transaction.setStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new RuntimeException("Erreur lors du transfert: " + e.getMessage());
        }
    }

    public Page<TransactionResponse> getUserTransactions(Long userId, Pageable pageable) {
        User user = userService.findUserById(userId);
        Page<Transaction> transactions = transactionRepository.findByUserOrderByCreatedAtDesc(user, pageable);
        return transactions.map(TransactionResponse::new);
    }

    public Page<TransactionResponse> getSentTransactions(Long userId, Pageable pageable) {
        User user = userService.findUserById(userId);
        Page<Transaction> transactions = transactionRepository.findBySenderOrderByCreatedAtDesc(user, pageable);
        return transactions.map(TransactionResponse::new);
    }

    public Page<TransactionResponse> getReceivedTransactions(Long userId, Pageable pageable) {
        User user = userService.findUserById(userId);
        Page<Transaction> transactions = transactionRepository.findByReceiverOrderByCreatedAtDesc(user, pageable);
        return transactions.map(TransactionResponse::new);
    }

    public TransactionResponse getTransactionById(String transactionId) {
        Transaction transaction = transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction non trouvée"));
        return new TransactionResponse(transaction);
    }

    public List<TransactionResponse> getTransactionsByDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        User user = userService.findUserById(userId);
        List<Transaction> transactions = transactionRepository.findByUserOrderByCreatedAtDesc(user, Pageable.unpaged())
                .getContent()
                .stream()
                .filter(t -> t.getCreatedAt().isAfter(startDate) && t.getCreatedAt().isBefore(endDate))
                .collect(Collectors.toList());
        return transactions.stream().map(TransactionResponse::new).collect(Collectors.toList());
    }

    public BigDecimal getTotalSentAmount(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        User user = userService.findUserById(userId);
        Double total = transactionRepository.getTotalSentAmountByUserAndDateRange(user, startDate, endDate);
        return total != null ? BigDecimal.valueOf(total) : BigDecimal.ZERO;
    }

    public BigDecimal getTotalReceivedAmount(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        User user = userService.findUserById(userId);
        Double total = transactionRepository.getTotalReceivedAmountByUserAndDateRange(user, startDate, endDate);
        return total != null ? BigDecimal.valueOf(total) : BigDecimal.ZERO;
    }

    public TransactionResponse cancelTransaction(Long userId, String transactionId) {
        User user = userService.findUserById(userId);
        Transaction transaction = transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction non trouvée"));

        if (!transaction.getSender().getId().equals(user.getId())) {
            throw new RuntimeException("Vous ne pouvez annuler que vos propres transactions");
        }

        if (transaction.getStatus() != TransactionStatus.PENDING) {
            throw new RuntimeException("Seules les transactions en attente peuvent être annulées");
        }

        transaction.setStatus(TransactionStatus.CANCELLED);
        transaction = transactionRepository.save(transaction);

        return new TransactionResponse(transaction);
    }
}
