package com.example.telegrambot.joined_chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinedChatDto {
    private UUID id;
    private String title;
    private String chatId;
    private Map<String,Object> myChatMember;
    private String type;
    private JoinedChatStatus status;
    private UUID createdBy;
    private Long createdDate;
    private Long modifiedDate;
    private UUID modifiedBy;


}
