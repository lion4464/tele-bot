package com.example.telegrambot.intergation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestTemplateMultiValue {
    private RestTemplate restTemplate;
    private HttpEntity<?> request;

}
