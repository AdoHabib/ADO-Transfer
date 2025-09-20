package com.adotransfer.controller;

import com.adotransfer.dto.ApiResponse;
import com.adotransfer.dto.TransferRequest;
import com.adotransfer.dto.TransactionResponse;
import com.adotransfer.service.ITransactionService;
import com.adotransfer.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/transactions")
@Tag(name = "Transactions", description = "API de gestion des transactions")
@SecurityRequirement(name = "bearerAuth")
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    @Autowired
    private IUserService userService;

    @PostMapping("/transfer")
    @Operation(summary = "Effectuer un transfert d'argent")
    public ResponseEntity<ApiResponse<TransactionResponse>> transferMoney(Authentication authentication,
                                                                        @Valid @RequestBody TransferRequest request) {
        try {
            String phoneNumber = authentication.getName();
            Long userId = userService.findUserByPhoneNumber(phoneNumber).getId();
            TransactionResponse transaction = transactionService.transferMoney(userId, request);
            return ResponseEntity.ok(ApiResponse.success("Transfert effectué avec succès", transaction));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Erreur lors du transfert: " + e.getMessage()));
        }
    }

    @GetMapping
    @Operation(summary = "Obtenir l'historique des transactions de l'utilisateur")
    public ResponseEntity<ApiResponse<Page<TransactionResponse>>> getUserTransactions(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            String phoneNumber = authentication.getName();
            Long userId = userService.findUserByPhoneNumber(phoneNumber).getId();
            Pageable pageable = PageRequest.of(page, size);
            Page<TransactionResponse> transactions = transactionService.getUserTransactions(userId, pageable);
            return ResponseEntity.ok(ApiResponse.success(transactions));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Erreur lors de la récupération des transactions: " + e.getMessage()));
        }
    }

    @GetMapping("/sent")
    @Operation(summary = "Obtenir les transactions envoyées par l'utilisateur")
    public ResponseEntity<ApiResponse<Page<TransactionResponse>>> getSentTransactions(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            String phoneNumber = authentication.getName();
            Long userId = userService.findUserByPhoneNumber(phoneNumber).getId();
            Pageable pageable = PageRequest.of(page, size);
            Page<TransactionResponse> transactions = transactionService.getSentTransactions(userId, pageable);
            return ResponseEntity.ok(ApiResponse.success(transactions));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Erreur lors de la récupération des transactions envoyées: " + e.getMessage()));
        }
    }

    @GetMapping("/received")
    @Operation(summary = "Obtenir les transactions reçues par l'utilisateur")
    public ResponseEntity<ApiResponse<Page<TransactionResponse>>> getReceivedTransactions(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            String phoneNumber = authentication.getName();
            Long userId = userService.findUserByPhoneNumber(phoneNumber).getId();
            Pageable pageable = PageRequest.of(page, size);
            Page<TransactionResponse> transactions = transactionService.getReceivedTransactions(userId, pageable);
            return ResponseEntity.ok(ApiResponse.success(transactions));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Erreur lors de la récupération des transactions reçues: " + e.getMessage()));
        }
    }

    @GetMapping("/{transactionId}")
    @Operation(summary = "Obtenir une transaction par son ID")
    public ResponseEntity<ApiResponse<TransactionResponse>> getTransactionById(@PathVariable String transactionId) {
        try {
            TransactionResponse transaction = transactionService.getTransactionById(transactionId);
            return ResponseEntity.ok(ApiResponse.success(transaction));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Transaction non trouvée: " + e.getMessage()));
        }
    }

    @GetMapping("/by-date-range")
    @Operation(summary = "Obtenir les transactions dans une plage de dates")
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> getTransactionsByDateRange(
            Authentication authentication,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            String phoneNumber = authentication.getName();
            Long userId = userService.findUserByPhoneNumber(phoneNumber).getId();
            List<TransactionResponse> transactions = transactionService.getTransactionsByDateRange(userId, startDate, endDate);
            return ResponseEntity.ok(ApiResponse.success(transactions));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Erreur lors de la récupération des transactions: " + e.getMessage()));
        }
    }

    @GetMapping("/statistics")
    @Operation(summary = "Obtenir les statistiques des transactions")
    public ResponseEntity<ApiResponse<Object>> getTransactionStatistics(
            Authentication authentication,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            String phoneNumber = authentication.getName();
            Long userId = userService.findUserByPhoneNumber(phoneNumber).getId();
            
            BigDecimal totalSent = transactionService.getTotalSentAmount(userId, startDate, endDate);
            BigDecimal totalReceived = transactionService.getTotalReceivedAmount(userId, startDate, endDate);
            
            var statistics = new Object() {
                public final BigDecimal totalSentAmount = totalSent;
                public final BigDecimal totalReceivedAmount = totalReceived;
                public final BigDecimal netAmount = totalReceived.subtract(totalSent);
            };
            
            return ResponseEntity.ok(ApiResponse.success(statistics));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Erreur lors de la récupération des statistiques: " + e.getMessage()));
        }
    }

    @PostMapping("/{transactionId}/cancel")
    @Operation(summary = "Annuler une transaction")
    public ResponseEntity<ApiResponse<TransactionResponse>> cancelTransaction(
            Authentication authentication,
            @PathVariable String transactionId) {
        try {
            String phoneNumber = authentication.getName();
            Long userId = userService.findUserByPhoneNumber(phoneNumber).getId();
            TransactionResponse transaction = transactionService.cancelTransaction(userId, transactionId);
            return ResponseEntity.ok(ApiResponse.success("Transaction annulée avec succès", transaction));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Erreur lors de l'annulation: " + e.getMessage()));
        }
    }
}
