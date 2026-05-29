package com.spring.chatApp.Controller;

import com.spring.chatApp.Dto.LoginRequest;
import com.spring.chatApp.Dto.RegisterRequest;
import com.spring.chatApp.Dto.ResetPasswordRequest;
import com.spring.chatApp.Model.Message;
import com.spring.chatApp.Model.User;
import com.spring.chatApp.Repository.UserRepository;
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
    private final UserRepository userRepository;
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
    @PostMapping("/forgot-password")
    public String forgotPassword(
            @RequestParam String email
    ) {
        return authService
                .forgotPassword(email);
    }

    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestBody ResetPasswordRequest request
    ) {
        return authService.resetPassword(
                request.getToken(),
                request.getNewPassword()
        );
    }


    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam String query) {

        return userRepository
                .findByUsernameContainingIgnoreCase(query);
    }
}