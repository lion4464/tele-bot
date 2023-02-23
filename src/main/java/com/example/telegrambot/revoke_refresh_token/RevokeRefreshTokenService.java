package com.example.telegrambot.revoke_refresh_token;


import java.util.UUID;

public interface RevokeRefreshTokenService{
    void create(String refreshToken, UUID userId);
    boolean isRevoked(String token);
}
