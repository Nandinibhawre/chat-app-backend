package com.spring.chatApp.Config;
import com.spring.chatApp.Services.OnlineUserService;
import lombok.RequiredArgsConstructor;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;

import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {


    private final OnlineUserService onlineUserService;
    @EventListener
    public void handleWebSocketConnect(
            SessionConnectEvent event
    ) {

        StompHeaderAccessor accessor =
                StompHeaderAccessor.wrap(
                        event.getMessage()
                );

        System.out.println(
                "HEADERS = "
                        + accessor.toNativeHeaderMap()
        );

        String email =
                accessor.getFirstNativeHeader(
                        "userEmail"
                );

        System.out.println(
                "EMAIL = " + email
        );

        if(email != null) {

            accessor.getSessionAttributes()
                    .put("email", email);

            onlineUserService.userOnline(email);

            System.out.println(email + " is online");

        }
    }
    @EventListener
    public void handleWebSocketDisconnect(
            SessionDisconnectEvent event
    ) {

        System.out.println(
                "DISCONNECT EVENT"
        );

        Principal principal =
                event.getUser();

        System.out.println(
                "PRINCIPAL = " + principal
        );

        if(principal != null){

            System.out.println(
                    "EMAIL = " + principal.getName()
            );

            onlineUserService.userOffline(
                    principal.getName()
            );

            System.out.println(
                    principal.getName() + " is offline"
            );
        }
    }
    }
