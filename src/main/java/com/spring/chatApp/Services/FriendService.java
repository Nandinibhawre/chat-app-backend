package com.spring.chatApp.Services;

import com.spring.chatApp.Model.FriendRequest;
import com.spring.chatApp.Model.FriendRequestStatus;
import com.spring.chatApp.Model.User;
import com.spring.chatApp.Repository.FriendRequestRepository;
import com.spring.chatApp.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;

    public FriendRequest sendRequest(String senderId, String receiverId) {

        FriendRequest existing = friendRequestRepository
                .findBySenderIdAndReceiverId(senderId, receiverId)
                .orElse(null);

        if (existing != null) {
            return existing;
        }

        FriendRequest request = FriendRequest.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .status(FriendRequestStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        return friendRequestRepository.save(request);
    }

    public FriendRequest acceptRequest(String requestId) {

        FriendRequest request = friendRequestRepository
                .findById(requestId)
                .orElseThrow();

        request.setStatus(FriendRequestStatus.ACCEPTED);

        return friendRequestRepository.save(request);
    }

    public FriendRequest rejectRequest(String requestId) {

        FriendRequest request = friendRequestRepository
                .findById(requestId)
                .orElseThrow();

        request.setStatus(FriendRequestStatus.REJECTED);

        return friendRequestRepository.save(request);
    }

    public List<User> getFriends(String userId) {

        List<FriendRequest> accepted =
                friendRequestRepository.findByStatusAndSenderIdOrStatusAndReceiverId(
                        FriendRequestStatus.ACCEPTED,
                        userId,
                        FriendRequestStatus.ACCEPTED,
                        userId
                );

        List<User> friends = new ArrayList<>();

        for (FriendRequest fr : accepted) {

            String friendId;

            if (fr.getSenderId().equals(userId)) {
                friendId = fr.getReceiverId();
            } else {
                friendId = fr.getSenderId();
            }

            userRepository.findById(friendId)
                    .ifPresent(friends::add);
        }

        return friends;
    }
    public List<FriendRequest> getPendingRequests(
            String receiverId
    ) {

        return friendRequestRepository
                .findByReceiverIdAndStatus(
                        receiverId,
                        FriendRequestStatus.PENDING
                );
    }
}