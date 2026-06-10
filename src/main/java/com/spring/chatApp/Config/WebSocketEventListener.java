package com.spring.chatApp.Config;
import com.spring.chatApp.Services.OnlineUserService;
import lombok.RequiredArgsConstructor;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;

import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

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

        StompHeaderAccessor accessor =
                StompHeaderAccessor.wrap(
                        event.getMessage()
                );

        String email =
                accessor.getFirstNativeHeader(
                        "userEmail"
                );

        if (email != null) {

            onlineUserService
                    .userOffline(email);

            System.out.println(
                    email + " is offline"
            );
        }
    }
}