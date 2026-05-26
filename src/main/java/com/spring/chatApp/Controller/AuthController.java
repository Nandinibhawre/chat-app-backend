package com.spring.chatApp.Controller;

import com.spring.chatApp.Dto.LoginRequest;
import com.spring.chatApp.Dto.RegisterRequest;
import com.spring.chatApp.Model.Message;
import com.spring.chatApp.Model.User;
import com.spring.chatApp.Services.AuthService;
import com.spring.chatApp.Services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;
    private final ChatService chatService;
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {

        return authService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request
    ) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {

        return authService.getAllUsers();
    }


}