package com.adotransfer.controller;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final Environment environment;

    public HomeController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Benvenuto in ADO Transfer!");
        model.addAttribute("application", "ADO Transfer");
        model.addAttribute("version", "1.0.0");
        model.addAttribute("profile", getActiveProfile());
        model.addAttribute("status", "UP");
        return "index";
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
