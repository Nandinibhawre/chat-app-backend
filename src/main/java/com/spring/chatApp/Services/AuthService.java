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

import java.util.List;

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
                .findByEmail(request.getEmail())
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

                user.getUsername()
        );
    }
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }
}