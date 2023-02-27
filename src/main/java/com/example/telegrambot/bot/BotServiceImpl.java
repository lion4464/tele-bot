package com.example.telegrambot.bot;

import com.example.telegrambot.exceptions.ConflictException;
import com.example.telegrambot.exceptions.ConnectionIsNullException;
import com.example.telegrambot.intergation.RequestTemplateMultiValue;
import com.example.telegrambot.intergation.RestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BotServiceImpl implements BotService {
    private final RestService restService;
    private static final Logger LOGGER = LoggerFactory.getLogger(BotServiceImpl.class);

    @Value("${senderbot.base_url}")
    private String baseUrl;

    @Value("${senderbot.token}")
    private String token;

    @Value("${senderbot.support}")
    private String supportChatId;


    @Override
    public List<ResultDto> getAllConnectionsBot(String userName) throws ConflictException {
        try{
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> request = generateRequest();
            ResponseDto response = restTemplate.postForObject(baseUrl +"/bot"+token+ "/getUpdates",request,ResponseDto.class);
            if (Objects.nonNull(response)){
                 Set<ResultDto> resultDtos = response.getResult().stream()
                         .filter(result -> result.getMyChatMember() != null && result.getMyChatMember().getChat().getId() < 0 && result.getMyChatMember().getFrom().getUserName().equals(userName)).collect(Collectors.toSet());
                 Map<String , ResultDto> resultMap =  new HashMap<>();
                 for (ResultDto r : resultDtos) resultMap.put(String.valueOf(r.getMyChatMember().getChatMin().get("id")),r);
                return new ArrayList<>(resultMap.values());

            }
            else
                throw new ConnectionIsNullException("there_is_not_connection_to_group");
        }catch (Exception e) {
            LOGGER.error("can_not_connect_group_list. Error: " + e.getMessage());
            throw new ConflictException("can_not_connect_group_list because: "+e.getMessage());
        }
    }

    @Override
    public Boolean send(String text, String chatId) throws ConflictException {
    try{
        RequestTemplateMultiValue requestTemplate = restService.sendMessageToGroup(text,chatId);
        LOGGER.info("Request is created: "+requestTemplate);
        ResponseEntity<ResultDto> response = requestTemplate.getRestTemplate().exchange(baseUrl +"bot"+token+ "/sendMessage",  HttpMethod.POST, requestTemplate.getRequest(), new ParameterizedTypeReference<>() {});
        return Objects.nonNull(response.getBody());
    }catch (Exception e) {
        LOGGER.error("Cant sent bot message. Error: " + e.getMessage());
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> request = generateRequest();
            LOGGER.info("Request is created: " + request);
            String urlPath = baseUrl + "bot" + token + "/sendMessage?chat_id="+supportChatId+"&&text="+e.getMessage();
            ResultDto response = restTemplate.getForObject(urlPath,ResultDto.class);
            assert response != null;
            if (response.getResponse())
                throw new ConflictException("ask_advice_from_support");
        }catch (Exception e1){
            throw new ConflictException(e1.getMessage());
        }
    }
    return false;
    }

    @Override
    public Boolean send(String caption, File document, String chatId) throws ConflictException {
        try{
            RequestTemplateMultiValue requestTemplate = restService.sendFileWithCaptionToGroup(caption,document,chatId);
            ResponseEntity<HashMap<String,String>> response = requestTemplate.getRestTemplate().exchange(baseUrl +"bot"+token+ "/sendDocument", HttpMethod.POST, requestTemplate.getRequest(), new ParameterizedTypeReference<>() {});
            return Objects.requireNonNull(response.getBody()).get("status").equals("OK");
        }catch (Exception e) {
            LOGGER.error("Cant sent bot message. Error: " + e.getMessage());
            RequestTemplateMultiValue requestTemplate = restService.sendErorrMessageToGroup(e.getMessage(),caption,supportChatId);
            ResponseEntity<HashMap<String,String>> response = requestTemplate.getRestTemplate().exchange(baseUrl +"bot"+token+ "/sendMessage",  HttpMethod.POST, requestTemplate.getRequest(), new ParameterizedTypeReference<>() {});
            if (Objects.requireNonNull(response.getBody()).get("status").equals("OK"))
                throw new ConflictException("ask_advice_from_support. because: "+e.getMessage());
        }
        return false;
    }
    private HttpEntity<String> generateRequest(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject resultJsonObject = new JSONObject();
        return new HttpEntity<String>(resultJsonObject.toString(), headers);
    }

}
