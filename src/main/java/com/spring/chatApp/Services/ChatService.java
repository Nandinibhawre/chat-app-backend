package com.spring.chatApp.Services;


import com.spring.chatApp.Dto.ChatMessageDto;
import com.spring.chatApp.Model.Message;
import com.spring.chatApp.Repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final MessageRepository messageRepository;

    public Message saveMessage(ChatMessageDto dto) {

        Message message = new Message();

        message.setSender(dto.getSender());
        message.setReceiver(dto.getReceiver());
        message.setContent(dto.getContent());
        message.setTimestamp(LocalDateTime.now());

        return messageRepository.save(message);
    }


}