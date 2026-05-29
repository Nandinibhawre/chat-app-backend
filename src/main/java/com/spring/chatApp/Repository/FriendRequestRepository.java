package com.spring.chatApp.Repository;

import com.spring.chatApp.Model.FriendRequest;
import com.spring.chatApp.Model.FriendRequestStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends MongoRepository<FriendRequest, String> {

    List<FriendRequest> findByReceiverIdAndStatus(
            String receiverId,
            FriendRequestStatus status
    );

    List<FriendRequest> findBySenderIdAndStatus(
            String senderId,
            FriendRequestStatus status
    );

    Optional<FriendRequest> findBySenderIdAndReceiverId(
            String senderId,
            String receiverId
    );

    List<FriendRequest> findByStatusAndSenderIdOrStatusAndReceiverId(
            FriendRequestStatus s1,
            String senderId,
            FriendRequestStatus s2,
            String receiverId
    );
}