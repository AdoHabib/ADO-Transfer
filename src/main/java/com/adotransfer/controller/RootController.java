package com.adotransfer.controller;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RootController {

    private final Environment environment;

    public RootController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("/")
    public Map<String, Object> root() {
        Map<String, Object> info = new HashMap<>();
        info.put("application", "ADO Transfer");
        info.put("version", "1.0.0");
        info.put("profile", getActiveProfile());
        info.put("status", "UP");
        info.put("endpoints", Map.of(
            "health", "/api/health/simple",
            "swagger", "/api/swagger-ui.html",
            "api-docs", "/api/v3/api-docs"
        ));
        return info;
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
