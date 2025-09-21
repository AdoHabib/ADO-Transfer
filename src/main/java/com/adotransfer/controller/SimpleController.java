package com.adotransfer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SimpleController {

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "Test funziona! L'app è online.";
    }
}
