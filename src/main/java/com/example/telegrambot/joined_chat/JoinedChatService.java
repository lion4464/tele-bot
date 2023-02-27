package com.example.telegrambot.joined_chat;

import com.example.telegrambot.exceptions.ConflictException;

import java.util.List;
import java.util.UUID;

public interface JoinedChatService {
    List<JoinedChatEntity> syncBotChat(String userName,UUID userId) throws ConflictException;

    List<JoinedChatEntity> findAllByCreatedByAndStatusAndDeletedFalse(UUID userId, JoinedChatStatus active);
}
