package com.example.telegrambot.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthResponce {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long accessTokenExpiresIn;
    private Long refreshTokenExpiresIn;
}
