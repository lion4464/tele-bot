package com.example.telegrambot.revoke_access_token;


import java.util.UUID;


public interface RevokeAccessTokenService {
    void create(String accessToken, UUID userId);
    boolean isRevoked(String token);
}
