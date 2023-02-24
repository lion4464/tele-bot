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
public class RequestTemplate {
    private RestTemplate restTemplate;
    private HttpEntity<String> request;
}
