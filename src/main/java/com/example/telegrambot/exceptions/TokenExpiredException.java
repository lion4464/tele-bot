package com.example.telegrambot.exceptions;

public class TokenExpiredException  extends RuntimeException {
    public TokenExpiredException() {
        super();
    }

    public TokenExpiredException(String msg) {
        super(msg);
    }
}
