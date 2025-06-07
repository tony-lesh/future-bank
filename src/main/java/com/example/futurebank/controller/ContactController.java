package com.example.futurebank.controller;

import com.example.futurebank.entity.ContactMessage;
import com.example.futurebank.repository.ContactMessageRepository;
import com.example.futurebank.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {

    @Autowired
    private ContactMessageRepository contactMessageRepository;

    @Autowired
    private EmailService emailService;

    @Value("${admin.email}")
    private String adminEmail;

    @GetMapping("/contact")
    public String showContactForm(Model model) {
        model.addAttribute("contactMessage", new ContactMessage());
        return "contact";
    }

    @PostMapping("/contact")
    public String submitContactForm(@ModelAttribute ContactMessage contactMessage, Model model) {
        try {
            // Save to database
            ContactMessage savedMessage = contactMessageRepository.save(contactMessage);

            // Send confirmation email to user
            emailService.sendContactConfirmation(
                    savedMessage.getEmail(),
                    savedMessage.getFirstName() + " " + savedMessage.getLastName()
            );

            // Notify admin
            emailService.notifyAdminAboutNewMessage(adminEmail, savedMessage);

            model.addAttribute("message", "Thank you for your message! We've sent a confirmation to your email.");
        } catch (Exception e) {
            model.addAttribute("error", "There was an error processing your request. Please try again later.");
            e.printStackTrace();
        }

        return "contact";
    }
}