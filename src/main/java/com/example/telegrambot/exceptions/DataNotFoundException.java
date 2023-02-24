package com.example.telegrambot.exceptions;

import org.springframework.http.HttpStatus;

public class DataNotFoundException extends ApiException {
    public DataNotFoundException() {
        super();
    }

    public DataNotFoundException(String msg) {
        super(msg, HttpStatus.NOT_FOUND);
    }

}
