package com.spring.chatApp.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.
        UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.
        SecurityContextHolder;

import org.springframework.security.web.authentication.
        WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(

            HttpServletRequest request,

            HttpServletResponse response,

            FilterChain filterChain

    ) throws ServletException, IOException {

        // HANDLE CORS PREFLIGHT
        if ("OPTIONS".equalsIgnoreCase(
                request.getMethod()
        )) {

            filterChain.doFilter(
                    request,
                    response
            );

            return;
        }

        // GET PATH
        String path =
                request.getServletPath();

        // SKIP AUTH APIs + WEBSOCKET
        if (
                path.startsWith("/api/auth")||
                        path.startsWith("/api/friends") ||
                        path.startsWith("/ws")
        ) {

            filterChain.doFilter(
                    request,
                    response
            );

            return;
        }

        // GET AUTH HEADER
        String authHeader =
                request.getHeader(
                        "Authorization"
                );

        // CHECK TOKEN
        if (
                authHeader != null
                        &&
                        authHeader.startsWith("Bearer ")
        ) {

            String token =
                    authHeader.substring(7);

            // VALIDATE TOKEN
            if (jwtUtil.isTokenValid(token)) {

                String email =
                        jwtUtil.extractEmail(token);

                // CREATE AUTH OBJECT
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                null
                        );

                auth.setDetails(

                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                // SET SECURITY CONTEXT
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(auth);
            }
        }

        // CONTINUE FILTER
        filterChain.doFilter(
                request,
                response
        );
    }
}