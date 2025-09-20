package com.adotransfer.controller;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    private final Environment environment;

    public HealthController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping
    public Map<String, Object> health() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "UP");
        status.put("service", "ADO Transfer");
        status.put("profile", getActiveProfile());
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
        status.put("profile", getActiveProfile());
        status.put("version", "1.0.0");
        return status;
    }

    private String getActiveProfile() {
        try {
            String[] activeProfiles = environment.getActiveProfiles();
            return activeProfiles.length > 0 ? activeProfiles[0] : "default";
        } catch (Exception e) {
            return "unknown";
        }
    }
}
