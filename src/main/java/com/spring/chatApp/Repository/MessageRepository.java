package com.spring.chatApp.Repository;


import com.spring.chatApp.Model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository
        extends MongoRepository<Message, String> {

    List<Message> findBySenderAndReceiver(
            String sender,
            String receiver
    );
}