package com.spring.chatApp.Config;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;

import org.springframework.web.socket.WebSocketHandler;

import org.springframework.web.socket.server.support.
        DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

public class UserHandshakeHandler
        extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(

            ServerHttpRequest request,

            WebSocketHandler wsHandler,

            Map<String, Object> attributes
    ) {

        if (request instanceof ServletServerHttpRequest servletRequest) {

            HttpServletRequest httpRequest =
                    servletRequest.getServletRequest();

            String email =
                    httpRequest.getParameter("email");

            System.out.println(
                    "HANDSHAKE EMAIL = " + email
            );

            return () -> email;
        }

        return null;
    }
}