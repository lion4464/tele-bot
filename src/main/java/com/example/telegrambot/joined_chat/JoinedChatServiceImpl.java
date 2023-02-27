package com.example.telegrambot.joined_chat;

import com.example.telegrambot.bot.BotService;
import com.example.telegrambot.bot.ResponseDto;
import com.example.telegrambot.bot.ResultDto;
import com.example.telegrambot.exceptions.ConflictException;
import com.example.telegrambot.user.UserEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.glassfish.grizzly.http.util.MimeType.contains;

@Service
public class JoinedChatServiceImpl implements JoinedChatService {

    private final JoinedChatRepository joinedChatRepository;
    private final BotService  botService;


    public JoinedChatServiceImpl(JoinedChatRepository joinedChatRepository, BotService botService) {
        this.joinedChatRepository = joinedChatRepository;
        this.botService = botService;
    }

    @Override
    public List<JoinedChatEntity> syncBotChat(String userName,UUID userId) throws ConflictException {
        List<ResultDto> allResponseFromApi = botService.getAllConnectionsBot(userName);
       Map<String,JoinedChatEntity> allModel = joinedChatRepository.findAllByCreatedBy(userId).stream().collect(Collectors.toMap(JoinedChatEntity::getChatId,Function.identity()));
       Map<String,JoinedChatEntity> allModelFromApi = allResponseFromApi.stream().map(res->convertToJoinedChatEntity(res)).collect(Collectors.toMap(JoinedChatEntity::getChatId, Function.identity()));
       Map<String,JoinedChatEntity> modelForSave = Stream.concat(allModel.entrySet().stream(), allModelFromApi.entrySet().stream())
                .distinct()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue));


    return joinedChatRepository.saveAll(modelForSave.values());
    }

    @Override
    public List<JoinedChatEntity> findAllByCreatedByAndStatusAndDeletedFalse(UUID userId, JoinedChatStatus status) {
        return joinedChatRepository.findAllByCreatedByAndStatusAndDeletedFalse(userId,status);
    }

    private JoinedChatEntity convertToJoinedChatEntity(ResultDto r) {
            JoinedChatEntity anonim = new JoinedChatEntity();
            anonim.setChatId(String.valueOf(r.getMyChatMember().getChat().getId()));
            anonim.setType(r.getMyChatMember().getChat().getType());
            anonim.setTitle(r.getMyChatMember().getChat().getTitle());
            anonim.setMyChatMember(r.getMyChatMember().getChatMin());
        return anonim;
    }
}
