package com.spring.chatApp.Controller;


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

    @GetMapping("/{email}")
    public boolean isOnline(

            @PathVariable
            String email
    ) {

        return onlineUserService
                .isOnline(email);
    }
}