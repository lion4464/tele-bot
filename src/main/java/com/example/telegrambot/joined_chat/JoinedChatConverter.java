package com.example.telegrambot.joined_chat;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JoinedChatConverter {
    JoinedChatDto convertFromEntity(JoinedChatEntity entity);
    List<JoinedChatDto> convertFromEntities(List<JoinedChatEntity> listEntity);
}
