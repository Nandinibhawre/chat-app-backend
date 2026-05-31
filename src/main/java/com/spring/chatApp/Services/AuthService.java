package com.spring.chatApp.Services;

import com.spring.chatApp.Dto.LoginRequest;
import com.spring.chatApp.Dto.LoginResponse;
import com.spring.chatApp.Dto.RegisterRequest;
import com.spring.chatApp.Model.User;
import com.spring.chatApp.Repository.UserRepository;
import com.spring.chatApp.Security.JwtUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    // ================= REGISTER =================

    public String register(RegisterRequest request) {

        // CHECK EMAIL EXISTS
        boolean exists = userRepository
                .   findByEmail(request.getEmail())
                .isPresent();

        if (exists) {

            throw new RuntimeException(
                    "Email Already Exists"
            );
        }

        // CREATE USER
        User user = new User();
        user.setUsername(request.getUsername());

        user.setEmail(request.getEmail());

        // ENCODE PASSWORD
        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        User savedUser = userRepository.save(user);

// Send welcome email
        emailService.sendWelcomeEmail(
                savedUser.getEmail(),
                savedUser.getUsername()
        );
        return "User Registered Successfully";
    }

    // ================= LOGIN =================
    public LoginResponse login(
            LoginRequest request
    ) {
        User user = userRepository
                .findByEmail(
                        request.getEmail()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "User Not Found"
                        )
                );

        boolean matches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if (!matches) {

            throw new RuntimeException(
                    "Invalid Password"
            );
        }
        String token =
                jwtUtil.generateToken(

                        user.getEmail(),

                        user.getUsername(),

                        user.getId()
                );
        return new LoginResponse(
                token,
                user.getEmail(),
                user.getUsername(),
                user.getId()
        );
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public String forgotPassword(String email) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        // Generate unique token
        String token = UUID.randomUUID().toString();

        user.setResetToken(token);

        user.setResetTokenExpiry(
                LocalDateTime.now().plusMinutes(15)
        );

        userRepository.save(user);

        // Frontend reset URL
        String resetLink =
                "https://chat-app-frontend-fawn-three.vercel.app/reset-password?token=" + token;

        // Send email
        emailService.sendForgotPasswordEmail(
                user.getEmail(),
                user.getUsername(),
                resetLink
        );
    
        return "Password reset link sent to email";
    }
    public String resetPassword(
            String token,
            String newPassword
    ) {

        User user = userRepository
                .findByResetToken(token)
                .orElseThrow(() ->
                        new RuntimeException("Invalid Token")
                );

        // Check expiry
        if (user.getResetTokenExpiry()
                .isBefore(LocalDateTime.now())) {

            throw new RuntimeException(
                    "Token Expired"
            );
        }

        // Update password
        user.setPassword(
                passwordEncoder.encode(newPassword)
        );

        // Remove token
        user.setResetToken(null);
        user.setResetTokenExpiry(null);

        userRepository.save(user);

        return "Password reset successful";
    }
}