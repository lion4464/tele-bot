package com.example.telegrambot.bot;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto {
    private static final String UPDATE_ID = "update_id";
    private static final String MY_CHAT_MEMBER = "my_chat_member";
    private static final String IS_RESPONSE_GET = "ok";

    @JsonProperty(UPDATE_ID)
    private String updateId;

    @JsonProperty(IS_RESPONSE_GET)
    private Boolean response;

    @JsonProperty(MY_CHAT_MEMBER)
    private Message myChatMember;

}
