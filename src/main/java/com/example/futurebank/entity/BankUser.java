package com.example.futurebank.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BankUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String accountNumber;
    private String password;
    private boolean enabled;

    //Constructors
    public BankUser() {
    }

    public BankUser(Long id, String username, String accountNumber, String password, boolean enabled) {
        this.id = id;
        this.username = username;
        this.accountNumber = accountNumber;
        this.password = password;
        this.enabled = enabled;
    }
}