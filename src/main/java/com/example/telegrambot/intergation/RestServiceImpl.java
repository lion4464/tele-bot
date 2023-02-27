package com.example.telegrambot.intergation;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class RestServiceImpl implements RestService {

    @Override
    public RequestTemplateMultiValue sendMessageToGroup(String text,String chatId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept", "*/*");
        Map<String, String> map= new HashMap<>();
        map.put("chat_id", chatId);
        map.put("text", text);
        HttpEntity<?> request = new HttpEntity<>(map, headers);
        RestTemplate rest = new RestTemplate();
        return new RequestTemplateMultiValue(rest,request);
    }

    @Override
    public RequestTemplateMultiValue sendFileWithCaptionToGroup(String caption, File document, String chatId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("Accept", "*/*");
        MultiValueMap<String, Object> map= new LinkedMultiValueMap<String, Object>();
        map.add("chat_id", chatId);
        map.add("document", document);
        map.add("caption", caption);
        HttpEntity<?> request = new HttpEntity<>(map, headers);
        RestTemplate rest = new RestTemplate();
        return new RequestTemplateMultiValue(rest,request);

    }

    @Override
    public RequestTemplateMultiValue sendErorrMessageToGroup(String erorr, String text, String chatId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept", "*/*");
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("chat_id", chatId);
        map.add("text", text);
        HttpEntity<?> request = new HttpEntity<>(map, headers);
        RestTemplate rest = new RestTemplate();
        return new RequestTemplateMultiValue(rest,request);
    }

    @Override
    public RequestTemplateMultiValue getUpdate() {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept", "*/*");
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        HttpEntity<?> request = new HttpEntity<>(map, headers);
        return new RequestTemplateMultiValue(rest,request);
    }


}
