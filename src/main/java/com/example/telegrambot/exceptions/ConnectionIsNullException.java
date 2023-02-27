package com.example.telegrambot.exceptions;

import org.springframework.http.HttpStatus;

public class ConnectionIsNullException extends ApiException {
    public ConnectionIsNullException(String msg) {
    super(msg, HttpStatus.NO_CONTENT);
    }
}
