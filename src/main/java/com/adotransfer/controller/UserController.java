package com.adotransfer.controller;

import com.adotransfer.dto.ApiResponse;
import com.adotransfer.dto.RegisterRequest;
import com.adotransfer.dto.UserResponse;
import com.adotransfer.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "Utilisateurs", description = "API de gestion des utilisateurs")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    @Operation(summary = "Obtenir le profil de l'utilisateur connecté")
    public ResponseEntity<ApiResponse<UserResponse>> getProfile(Authentication authentication) {
        try {
            String phoneNumber = authentication.getName();
            UserResponse user = userService.getUserByPhoneNumber(phoneNumber);
            return ResponseEntity.ok(ApiResponse.success(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Erreur lors de la récupération du profil: " + e.getMessage()));
        }
    }

    @PutMapping("/profile")
    @Operation(summary = "Mettre à jour le profil de l'utilisateur connecté")
    public ResponseEntity<ApiResponse<UserResponse>> updateProfile(Authentication authentication,
                                                                 @Valid @RequestBody RegisterRequest request) {
        try {
            String phoneNumber = authentication.getName();
            Long userId = userService.findUserByPhoneNumber(phoneNumber).getId();
            UserResponse user = userService.updateUser(userId, request);
            return ResponseEntity.ok(ApiResponse.success("Profil mis à jour avec succès", user));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Erreur lors de la mise à jour du profil: " + e.getMessage()));
        }
    }

    @PostMapping("/set-pin")
    @Operation(summary = "Définir le PIN de l'utilisateur")
    public ResponseEntity<ApiResponse<String>> setPin(Authentication authentication,
                                                    @RequestParam String pin) {
        try {
            String phoneNumber = authentication.getName();
            Long userId = userService.findUserByPhoneNumber(phoneNumber).getId();
            userService.setUserPin(userId, pin);
            return ResponseEntity.ok(ApiResponse.success("PIN défini avec succès"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Erreur lors de la définition du PIN: " + e.getMessage()));
        }
    }

    @PostMapping("/verify-pin")
    @Operation(summary = "Vérifier le PIN de l'utilisateur")
    public ResponseEntity<ApiResponse<String>> verifyPin(Authentication authentication,
                                                       @RequestParam String pin) {
        try {
            String phoneNumber = authentication.getName();
            Long userId = userService.findUserByPhoneNumber(phoneNumber).getId();
            boolean isValid = userService.verifyPin(userId, pin);
            
            if (isValid) {
                return ResponseEntity.ok(ApiResponse.success("PIN correct"));
            } else {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("PIN incorrect"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Erreur lors de la vérification du PIN: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un utilisateur par ID")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        try {
            UserResponse user = userService.getUserById(id);
            return ResponseEntity.ok(ApiResponse.success(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Utilisateur non trouvé: " + e.getMessage()));
        }
    }
}
