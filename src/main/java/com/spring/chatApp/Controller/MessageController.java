package com.spring.chatApp.Controller;
import com.spring.chatApp.Model.Message;
import com.spring.chatApp.Repository.MessageRepository;
import com.spring.chatApp.Services.MessageServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")

@CrossOrigin(origins = "*")
public class MessageController {

    private final MessageServices messageService;
    private final MessageRepository messageRepository;

    @GetMapping

    public List<Message> getMessages(

            @RequestParam String sender,

            @RequestParam String receiver
    ) {

        return messageService
                .getChatMessages(
                        sender,
                        receiver
                );
    }
}