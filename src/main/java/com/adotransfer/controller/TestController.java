package com.adotransfer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/hello")
    public Map<String, Object> hello() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hello from ADO Transfer API!");
        response.put("status", "success");
        response.put("timestamp", System.currentTimeMillis());
        response.put("instructions", "L'API è funzionante. Usa questi endpoint per testare:");
        response.put("endpoints", Map.of(
            "register", "POST /api/auth/register",
            "login", "POST /api/auth/login",
            "transfer", "POST /api/transactions/transfer",
            "balance", "GET /api/accounts/balance"
        ));
        return response;
    }

    @GetMapping("/status")
    public Map<String, Object> status() {
        Map<String, Object> response = new HashMap<>();
        response.put("application", "ADO Transfer");
        response.put("version", "1.0.0");
        response.put("status", "UP");
        response.put("profile", "no-db");
        response.put("message", "Applicazione funzionante!");
        return response;
    }
}
