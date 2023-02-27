package com.example.telegrambot.bot;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class Message {
    private Chat chat;
    private Chat from;
    private Long date;

    public Map<String,Object>  getChatMin() {
        Map<String,Object> minDto = new HashMap<>();
        minDto.put("id",chat.getId());
        minDto.put("title",chat.getTitle());
        minDto.put("type",chat.getType());
        minDto.put("username",from.getUserName());
        return minDto;
    }
}
