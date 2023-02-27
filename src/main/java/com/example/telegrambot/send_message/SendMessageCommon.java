package com.example.telegrambot.send_message;

import com.example.telegrambot.exceptions.DataNotFoundException;
import com.example.telegrambot.file.FileDto;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/send_message_common")
@RequiredArgsConstructor
public class SendMessageCommon {
    private final SendMessageService sendMessageService;
    private final SendMessageConverter sendMessageConverter;
    private final Logger logger = LogManager.getLogger();

}
