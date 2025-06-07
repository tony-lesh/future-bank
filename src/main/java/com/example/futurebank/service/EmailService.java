package com.example.futurebank.service;


import com.example.futurebank.entity.ContactMessage;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);  // Set sender email from properties
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
        } catch (MailException ex) {
            // Log or handle the exception appropriately
            System.err.println("Error sending email: " + ex.getMessage());
            throw new RuntimeException("Failed to send email", ex);
        }
    }
    public void sendContactConfirmation(String to, String name) {
        String subject = "Thank you for contacting us";
        String text = "Dear " + name + ",\n\n" +
                "Thank you for reaching out to us. We have received your message and our team will get back to you shortly.\n\n" +
                "Best regards,\n" +
                "Customer Support Team";

        sendSimpleMessage(to, subject, text);
    }

    public void notifyAdminAboutNewMessage(String adminEmail, ContactMessage contactMessage) {
        String subject = "New Contact Form Submission: " + contactMessage.getSubject();
        String text = "You have a new contact form submission:\n\n" +
                "Name: " + contactMessage.getFirstName() + " " + contactMessage.getLastName() + "\n" +
                "Email: " + contactMessage.getEmail() + "\n" +
                "Phone: " + contactMessage.getPhone() + "\n" +
                "Subject: " + contactMessage.getSubject() + "\n" +
                "Message: " + contactMessage.getMessage() + "\n\n" +
                "Submitted at: " + contactMessage.getSubmittedAt();

        sendSimpleMessage(adminEmail, subject, text);
    }
}
