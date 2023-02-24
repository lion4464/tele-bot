package com.example.telegrambot.intergation;


import java.io.File;

public interface RestService {

    RequestTemplateMultiValue sendMessageToGroup(String text,String chatId);
    RequestTemplateMultiValue sendFileWithCaptionToGroup(String caption, File document, String chatId);
}
