package com.adotransfer.controller;

import com.adotransfer.dto.ApiResponse;
import com.adotransfer.model.Account;
import com.adotransfer.service.AccountService;
import com.adotransfer.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Comptes", description = "API de gestion des comptes")
@SecurityRequirement(name = "bearerAuth")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @GetMapping("/balance")
    @Operation(summary = "Obtenir le solde du compte de l'utilisateur connecté")
    public ResponseEntity<ApiResponse<BigDecimal>> getBalance(Authentication authentication) {
        try {
            String phoneNumber = authentication.getName();
            Long userId = userService.findUserByPhoneNumber(phoneNumber).getId();
            BigDecimal balance = accountService.getBalance(userId);
            return ResponseEntity.ok(ApiResponse.success(balance));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Erreur lors de la récupération du solde: " + e.getMessage()));
        }
    }

    @GetMapping("/details")
    @Operation(summary = "Obtenir les détails du compte de l'utilisateur connecté")
    public ResponseEntity<ApiResponse<Account>> getAccountDetails(Authentication authentication) {
        try {
            String phoneNumber = authentication.getName();
            Long userId = userService.findUserByPhoneNumber(phoneNumber).getId();
            Account account = accountService.getAccountByUserId(userId);
            return ResponseEntity.ok(ApiResponse.success(account));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Erreur lors de la récupération des détails du compte: " + e.getMessage()));
        }
    }

    @PostMapping("/deposit")
    @Operation(summary = "Effectuer un dépôt sur le compte")
    public ResponseEntity<ApiResponse<String>> deposit(Authentication authentication,
                                                     @RequestParam BigDecimal amount) {
        try {
            String phoneNumber = authentication.getName();
            Long userId = userService.findUserByPhoneNumber(phoneNumber).getId();
            accountService.deposit(userId, amount);
            return ResponseEntity.ok(ApiResponse.success("Dépôt effectué avec succès"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Erreur lors du dépôt: " + e.getMessage()));
        }
    }

    @PostMapping("/withdraw")
    @Operation(summary = "Effectuer un retrait du compte")
    public ResponseEntity<ApiResponse<String>> withdraw(Authentication authentication,
                                                      @RequestParam BigDecimal amount) {
        try {
            String phoneNumber = authentication.getName();
            Long userId = userService.findUserByPhoneNumber(phoneNumber).getId();
            accountService.withdraw(userId, amount);
            return ResponseEntity.ok(ApiResponse.success("Retrait effectué avec succès"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Erreur lors du retrait: " + e.getMessage()));
        }
    }

    @PostMapping("/freeze")
    @Operation(summary = "Geler le compte")
    public ResponseEntity<ApiResponse<String>> freezeAccount(Authentication authentication) {
        try {
            String phoneNumber = authentication.getName();
            Long userId = userService.findUserByPhoneNumber(phoneNumber).getId();
            accountService.freezeAccount(userId);
            return ResponseEntity.ok(ApiResponse.success("Compte gelé avec succès"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Erreur lors du gel du compte: " + e.getMessage()));
        }
    }

    @PostMapping("/unfreeze")
    @Operation(summary = "Dégeler le compte")
    public ResponseEntity<ApiResponse<String>> unfreezeAccount(Authentication authentication) {
        try {
            String phoneNumber = authentication.getName();
            Long userId = userService.findUserByPhoneNumber(phoneNumber).getId();
            accountService.unfreezeAccount(userId);
            return ResponseEntity.ok(ApiResponse.success("Compte dégelé avec succès"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Erreur lors du dégel du compte: " + e.getMessage()));
        }
    }
}
