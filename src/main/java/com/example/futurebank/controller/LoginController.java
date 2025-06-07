package com.example.futurebank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("pageTitle", "FNB Botswana - Secure Login");
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam(required = false) boolean rememberMe,
            Model model) {

        return "redirect:/dashboard"; // Redirect to dashboard on success
    }

    @GetMapping("/register")
    public String showRegistrationPage() {
        return "register";
    }
}