package com.example.telegrambot.bot;


import com.example.telegrambot.exceptions.ConflictException;

import java.io.File;
import java.util.List;
import java.util.Set;

public interface BotService {
    List<ResultDto> getAllConnectionsBot(String userName) throws ConflictException;

    Boolean send(String text, String chatId) throws ConflictException;

    Boolean send(String caption, File document, String chatId) throws ConflictException;


}
