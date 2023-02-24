package com.example.telegrambot.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
public class ApiException extends Exception{
    private String message;
    private HttpStatus httpBody;
    private ZonedDateTime timeStamp;

    public ApiException(String message, HttpStatus httpBody) {
        super(message);
        this.message=message;
        this.httpBody=httpBody;
    }

    public ApiException(String message,HttpStatus httpBody, ZonedDateTime timeStamp) {
        super(message);
        this.message = message;
        this.httpBody = httpBody;
        this.timeStamp = timeStamp;
    }

}
