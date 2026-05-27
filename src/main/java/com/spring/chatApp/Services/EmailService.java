package com.spring.chatApp.Services;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final RestTemplate restTemplate;

    @Value("${brevo.api.key}")
    private String apiKey;

    @Value("${brevo.sender.email}")
    private String senderEmail;

    @Value("${brevo.sender.name}")
    private String senderName;

    public void sendWelcomeEmail(String Email, String username) {

        try {

            String url = "https://api.brevo.com/v3/smtp/email";

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("accept", "application/json");
            headers.set("api-key", apiKey);

            String htmlContent = """
                    
                    <div style="
                        font-family: Arial, sans-serif;
                        max-width: 600px;
                        margin: auto;
                        padding: 20px;
                        border-radius: 10px;
                        border: 1px solid #e0e0e0;
                    ">

                        <h1 style="color:#4CAF50;">
                            Welcome to ChatApp 🚀
                        </h1>

                        <p>Hello <b>%s</b>,</p>

                        <p>
                            Your account has been created successfully.
                        </p>

                        <p>
                            We are excited to have you in our chat community 🎉
                        </p>

                        <div style="
                            background:#f5f5f5;
                            padding:15px;
                            border-radius:8px;
                            margin-top:20px;
                        ">
                            Start chatting with your friends now 💬
                        </div>

                        <br>

                        <p>
                            Thank you,<br>
                            <b>ChatApp Team</b>
                        </p>

                    </div>

                    """.formatted(username);

            Map<String, Object> sender = new HashMap<>();
            sender.put("name", senderName);
            sender.put("email", senderEmail);

            Map<String, String> recipient = new HashMap<>();
            recipient.put("email", Email);
            recipient.put("name", username);

            List<Map<String, String>> toList = new ArrayList<>();
            toList.add(recipient);

            Map<String, Object> requestBody = new HashMap<>();

            requestBody.put("sender", sender);
            requestBody.put("to", toList);
            requestBody.put("subject", "Welcome to ChatApp 🚀");
            requestBody.put("htmlContent", htmlContent);

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            request,
                            String.class
                    );

            System.out.println("Email Sent Successfully");
            System.out.println(response.getBody());

        } catch (Exception e) {

            System.out.println("Email Sending Failed");

            e.printStackTrace();
        }
    }
}