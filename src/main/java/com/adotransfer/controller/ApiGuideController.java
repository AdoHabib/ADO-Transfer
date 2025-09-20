package com.adotransfer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiGuideController {

    @GetMapping("/guide")
    public Map<String, Object> apiGuide() {
        Map<String, Object> guide = new HashMap<>();
        guide.put("title", "ADO Transfer API - Guida Completa");
        guide.put("version", "1.0.0");
        guide.put("status", "UP");
        
        Map<String, Object> auth = new HashMap<>();
        auth.put("register", Map.of(
            "method", "POST",
            "url", "/api/auth/register",
            "description", "Registra un nuovo utente",
            "body", Map.of(
                "fullName", "string",
                "phoneNumber", "string",
                "email", "string",
                "password", "string"
            )
        ));
        auth.put("login", Map.of(
            "method", "POST",
            "url", "/api/auth/login",
            "description", "Login utente",
            "body", Map.of(
                "phoneNumber", "string",
                "password", "string"
            )
        ));
        
        Map<String, Object> transactions = new HashMap<>();
        transactions.put("transfer", Map.of(
            "method", "POST",
            "url", "/api/transactions/transfer",
            "description", "Trasferimento denaro",
            "headers", "Authorization: Bearer {token}",
            "body", Map.of(
                "recipientPhoneNumber", "string",
                "amount", "number",
                "pin", "string"
            )
        ));
        
        guide.put("authentication", auth);
        guide.put("transactions", transactions);
        
        guide.put("instructions", "Usa un tool come Postman o curl per testare questi endpoint");
        guide.put("note", "Modalità mock attiva - tutti i dati sono simulati");
        
        return guide;
    }
}
