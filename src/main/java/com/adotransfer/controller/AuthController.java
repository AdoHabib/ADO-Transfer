package com.adotransfer.controller;

import com.adotransfer.dto.ApiResponse;
import com.adotransfer.dto.LoginRequest;
import com.adotransfer.dto.RegisterRequest;
import com.adotransfer.dto.UserResponse;
import com.adotransfer.security.JwtUtil;
import com.adotransfer.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentification", description = "API d'authentification et d'inscription")
public class AuthController {

    @Autowired
    private IUserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    @Operation(summary = "Inscription d'un nouvel utilisateur")
    public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody RegisterRequest request) {
        try {
            UserResponse user = userService.registerUser(request);
            return ResponseEntity.ok(ApiResponse.success("Inscription réussie", user));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Erreur lors de l'inscription: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Connexion d'un utilisateur")
    public ResponseEntity<ApiResponse<Map<String, Object>>> login(@Valid @RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getPhoneNumber(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserResponse user = userService.getUserByPhoneNumber(request.getPhoneNumber());
            String token = jwtUtil.generateToken((org.springframework.security.core.userdetails.UserDetails) authentication.getPrincipal());

            Map<String, Object> response = new HashMap<>();
            response.put("user", user);
            response.put("token", token);
            response.put("tokenType", "Bearer");

            return ResponseEntity.ok(ApiResponse.success("Connexion réussie", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Identifiants incorrects"));
        }
    }

    @PostMapping("/verify-phone")
    @Operation(summary = "Vérification du numéro de téléphone")
    public ResponseEntity<ApiResponse<String>> verifyPhone(@RequestParam String phoneNumber) {
        try {
            // Ici, vous pourriez envoyer un SMS avec un code OTP
            // Pour l'instant, on simule juste la vérification
            return ResponseEntity.ok(ApiResponse.success("Code de vérification envoyé"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Erreur lors de l'envoi du code"));
        }
    }

    @PostMapping("/verify-otp")
    @Operation(summary = "Vérification du code OTP")
    public ResponseEntity<ApiResponse<String>> verifyOtp(@RequestParam String phoneNumber, 
                                                       @RequestParam String otp) {
        try {
            // Ici, vous vérifieriez le code OTP
            // Pour l'instant, on simule juste la vérification
            userService.verifyUser(userService.findUserByPhoneNumber(phoneNumber).getId());
            return ResponseEntity.ok(ApiResponse.success("Numéro de téléphone vérifié"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Code OTP incorrect"));
        }
    }
}
