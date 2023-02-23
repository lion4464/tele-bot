package com.example.telegrambot.configuration;

import io.jsonwebtoken.Claims;

public interface TokenUtil {
    String getUsernameFromToken(String token);
    String generateAccessToken(UserDetailsImpl userDetails);
    String generateRefreshToken(UserDetailsImpl userDetails);
    Boolean validateToken(String token);
    Claims getClaims(String token);
    void revokeTokens(String accessToken, String refreshToken);
}
