package com.example.telegrambot.exceptions;

import org.springframework.http.HttpStatus;

public class ConflictException extends ApiException{
    public ConflictException(String s) {
        super(s, HttpStatus.CONFLICT);
    }
}
