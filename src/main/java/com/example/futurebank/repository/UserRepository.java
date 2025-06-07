package com.example.futurebank.repository;



import com.example.futurebank.entity.BankUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<BankUser, Long> {

    // Find user by username or account number (for login)
    Optional<BankUser> findByUsernameOrAccountNumber(String username, String accountNumber);

    // Check if username exists (for registration)
    boolean existsByUsername(String username);

    // Check if account number exists (for registration)
    boolean existsByAccountNumber(String accountNumber);

    // Find by username only
    Optional<BankUser> findByUsername(String username);

    // Find by account number only
    Optional<BankUser> findByAccountNumber(String accountNumber);
}
