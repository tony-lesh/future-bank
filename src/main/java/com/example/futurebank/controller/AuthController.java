package com.example.futurebank.controller;

import com.example.futurebank.dto.AuthRequestDto;
import com.example.futurebank.dto.AuthResponseDto;
import com.example.futurebank.service.AuthService;
import com.example.futurebank.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, JwtService jwtService,
                          AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> authenticateAndGetToken(
            @RequestBody AuthRequestDto authRequest,
            HttpServletRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            // Configure session
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(1800); // 30 minutes timeout

            // Generate tokens
            String accessToken = jwtService.generateToken(authService.loadUserByUsername(authRequest.getUsername()));
            String refreshToken = jwtService.generateRefreshToken(authService.loadUserByUsername(authRequest.getUsername()));

            return ResponseEntity.ok(
                    new AuthResponseDto(accessToken, refreshToken, "Login successful")
            );
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDto> refreshToken(
            @RequestParam("refreshToken") String refreshToken) {

        String username = jwtService.extractUsername(refreshToken);
        UserDetails userDetails = authService.loadUserByUsername(username);

        if (jwtService.isTokenValid(refreshToken, userDetails)) {
            String newAccessToken = jwtService.generateToken(userDetails);
            return ResponseEntity.ok(
                    new AuthResponseDto(newAccessToken, refreshToken, "Token refreshed successfully")
            );
        }
        return ResponseEntity.badRequest().body(
                new AuthResponseDto(null, null, "Invalid refresh token")
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logged out successfully");
    }
}