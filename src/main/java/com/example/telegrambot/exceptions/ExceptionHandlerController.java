package com.example.telegrambot.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.FileNotFoundException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;

@ControllerAdvice
public class ExceptionHandlerController{
    private static final Logger logger = LogManager.getLogger();

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

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> apiException(ApiException e, ServletWebRequest webRequest) {
        logger.error(e.getMessage(), e);
        HttpStatus httpBody = e.getHttpBody();
//        if you want to another struct please write here
        HashMap<String, Object> result = new HashMap<>();
        result.put("message",e.getMessage());
        result.put("httpBody",e.getHttpBody());
        result.put("tameStamp",ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(result,httpBody);
    }



}
