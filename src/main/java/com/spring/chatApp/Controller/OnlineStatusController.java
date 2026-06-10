package com.spring.chatApp.Controller;


import com.spring.chatApp.Dto.UserStatusDTO;
import com.spring.chatApp.Services
        .OnlineUserService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/status")
public class OnlineStatusController {

    private final OnlineUserService
            onlineUserService;
//
//    @GetMapping("/{email}")
//    public boolean isOnline(
//
//            @PathVariable
//            String email
//    ) {
//
//        return onlineUserService
//                .isOnline(email);
//    }
//}
@GetMapping("/{email}")
public UserStatusDTO getStatus(

        @PathVariable String email
) {

    boolean online =
            onlineUserService.isOnline(email);

    String lastSeen = null;

    if (!online &&
            onlineUserService.getLastSeen(email) != null) {

        lastSeen =
                onlineUserService
                        .getLastSeen(email)
                        .toString();
    }

    return new UserStatusDTO(

            online,

            lastSeen
    );
}
}