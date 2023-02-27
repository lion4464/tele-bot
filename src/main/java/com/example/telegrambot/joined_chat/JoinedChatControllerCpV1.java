package com.example.telegrambot.joined_chat;

import com.example.telegrambot.configuration.UserDetailsImpl;
import com.example.telegrambot.exceptions.ConflictException;
import com.example.telegrambot.exceptions.DataNotFoundException;
import com.example.telegrambot.file.FileConverter;
import com.example.telegrambot.file.FileDto;
import com.example.telegrambot.file.FileService;
import com.example.telegrambot.generic.GenericController;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@PreAuthorize("hasAnyAuthority('ADMIN')")
@RequestMapping("/api/joined_chat")
@RequiredArgsConstructor
public class JoinedChatControllerCpV1 extends GenericController {
    private final JoinedChatService joinedChatService;
    private final JoinedChatConverter joinedChatConverter;
    private final Logger logger = LogManager.getLogger();


    @GetMapping("/sync")
    public ResponseEntity<List<JoinedChatDto>> sync(@RequestParam("userName") String userName) throws ConflictException {
            userName = absoluteUserName(userName);
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(joinedChatConverter.convertFromEntities(joinedChatService.syncBotChat(userName,userDetails.getId())));
    }

}
