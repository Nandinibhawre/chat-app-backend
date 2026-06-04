package com.spring.chatApp.Controller;

import com.spring.chatApp.Model.Message;
import com.spring.chatApp.Model.TypingStatus;
import com.spring.chatApp.Repository.MessageRepository;

import com.spring.chatApp.Services.ChatService;
import lombok.RequiredArgsConstructor;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    private final MessageRepository messageRepository;
    private final ChatService messageService;

    @MessageMapping("/sendMessage")
    public void sendMessage(Message message) {

        message.setTimestamp(
                LocalDateTime.now(
                        ZoneId.of("Asia/Kolkata")
                )
        );
        // SAVE MESSAGE
        messageRepository.save(message);

        // SEND TO RECEIVER
        messagingTemplate.convertAndSendToUser(
                message.getReceiver(),
                "/queue/messages",
                message
        );

        // SEND BACK TO SENDER
        messagingTemplate.convertAndSendToUser(
                message.getSender(),
                "/queue/messages",
                message
        );
    }

    @MessageMapping("/typing")
    public void typing(
            TypingStatus typingStatus
    ) {

        messagingTemplate.convertAndSendToUser(

                typingStatus.getReceiver(),

                "/queue/typing",

                typingStatus
        );
    }

}