package com.spring.chatApp.Services;

import com.spring.chatApp.Model.Message;
import com.spring.chatApp.Repository.MessageRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor

public class MessageServices {

    private final MessageRepository
            messageRepository;

    // SAVE MESSAGE
    public Message saveMessage(
            Message message
    ) {

        return messageRepository
                .save(message);
    }

    // GET BOTH SIDE CHAT
    public List<Message> getChatMessages(

            String user1,
            String user2
    ) {

        // USER1 -> USER2
        List<Message> sentMessages =

                messageRepository
                        .findBySenderAndReceiver(

                                user1,
                                user2
                        );

        // USER2 -> USER1
        List<Message> receivedMessages =

                messageRepository
                        .findBySenderAndReceiver(

                                user2,
                                user1
                        );

        // MERGE BOTH
        List<Message> allMessages =
                new ArrayList<>();

        allMessages.addAll(
                sentMessages
        );

        allMessages.addAll(
                receivedMessages
        );

        // SORT BY TIME
        allMessages.sort(

                Comparator.comparing(
                        Message::getTimestamp
                )
        );

        return allMessages;
    }
}