package com.example.futurebank.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "contact_messages")
public class ContactMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String subject;
    private String message;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    private boolean processed = false;

    // Constructors
    public ContactMessage() {
        this.submittedAt = LocalDateTime.now();
    }

    public ContactMessage(Long id, String firstName, String lastName, String email, String phone, String subject, String message, LocalDateTime submittedAt, boolean processed) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.subject = subject;
        this.message = message;
        this.submittedAt = submittedAt;
        this.processed = processed;
    }
}
