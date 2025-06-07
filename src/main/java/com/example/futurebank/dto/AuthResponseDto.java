package com.example.futurebank.dto;

public class AuthResponseDto {
    private String accessToken;
    private String refreshToken;
    private String message;

    // Constructor
    public AuthResponseDto(String accessToken, String refreshToken, String message) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.message = message;
    }

    // Getters
    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getMessage() {
        return message;
    }
}