package com.spring.chatApp.Controller;

import com.spring.chatApp.Dto.FriendRequestDTO;
import com.spring.chatApp.Model.FriendRequest;
import com.spring.chatApp.Model.User;
import com.spring.chatApp.Services.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
@CrossOrigin("*")
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/request")
    public FriendRequest sendRequest(
            @RequestBody Map<String, String> body
    ) {

        return friendService.sendRequest(
                body.get("senderId"),
                body.get("receiverId")
        );
    }

    @PutMapping("/accept/{id}")
    public FriendRequest acceptRequest(@PathVariable String id) {
        return friendService.acceptRequest(id);
    }

    @PutMapping("/reject/{id}")
    public FriendRequest rejectRequest(@PathVariable String id) {
        return friendService.rejectRequest(id);
    }

    @GetMapping("/pending/{receiverId}")
    public List<FriendRequestDTO> getPendingRequests(
            @PathVariable String receiverId
    ) {
        return friendService.getPendingRequests(receiverId);
    }

    @GetMapping("/list/{userId}")
    public List<User> getFriends(@PathVariable String userId) {
        return friendService.getFriends(userId);
    }
}
