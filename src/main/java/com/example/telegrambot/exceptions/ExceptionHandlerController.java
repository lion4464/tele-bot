package com.example.telegrambot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ExceptionHandlerController{
    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<Object> exception(BadCredentialsException e){
        HttpStatus httpBody=HttpStatus.UNAUTHORIZED;
        ApiException result=new ApiException(
                e.getMessage(),
                httpBody,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(result,httpBody);
    }

}
