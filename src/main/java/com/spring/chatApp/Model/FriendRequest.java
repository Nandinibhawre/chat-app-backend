package com.spring.chatApp.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "friend_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendRequest {

    @Id
    private String id;

    private String senderId;

    private String receiverId;

    private FriendRequestStatus status;

    private LocalDateTime createdAt;
}
