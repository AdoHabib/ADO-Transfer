package com.adotransfer.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    @GetMapping
    public Map<String, Object> health() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "UP");
        status.put("service", "ADO Transfer");
        status.put("profile", activeProfile);
        status.put("timestamp", System.currentTimeMillis());
        return status;
    }

    @GetMapping("/simple")
    public String simpleHealth() {
        return "OK";
    }

    @GetMapping("/status")
    public Map<String, Object> status() {
        Map<String, Object> status = new HashMap<>();
        status.put("application", "ADO Transfer");
        status.put("status", "RUNNING");
        status.put("profile", activeProfile);
        status.put("version", "1.0.0");
        return status;
    }
}
