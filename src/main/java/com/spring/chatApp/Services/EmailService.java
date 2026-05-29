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

            System.out.println("API KEY = " + apiKey);
            headers.set("api-key", apiKey);

            String htmlContent = """
        
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
</head>

<body style="
    margin:0;
    padding:0;
    background:#f4f7fb;
    font-family:Arial,sans-serif;
">

<div style="
    max-width:650px;
    margin:40px auto;
    background:white;
    border-radius:20px;
    overflow:hidden;
    box-shadow:0 5px 20px rgba(0,0,0,0.1);
">

    <!-- HEADER -->
    <div style="
        background:linear-gradient(135deg,#6C63FF,#5A54E8);
        padding:40px;
        text-align:center;
        color:white;
    ">

        <h1 style="
            margin:0;
            font-size:36px;
        ">
            Welcome to ChatApp 🚀
        </h1>

        <p style="
            margin-top:10px;
            font-size:18px;
            opacity:0.9;
        ">
            Your new chat journey starts here
        </p>

    </div>

    <!-- BODY -->
    <div style="
        padding:40px;
        color:#333;
    ">

        <h2 style="
            margin-top:0;
            font-size:28px;
        ">
            Hey %s 👋
        </h2>

        <p style="
            font-size:16px;
            line-height:1.8;
            color:#555;
        ">
            We are super excited to have you in our community 🎉
        </p>

        <div style="
            margin:30px 0;
            background:#f7f8ff;
            border-left:5px solid #6C63FF;
            padding:20px;
            border-radius:10px;
        ">

            <h3 style="
                margin-top:0;
                color:#6C63FF;
            ">
                Your account is ready ✅
            </h3>

            <p style="
                margin-bottom:0;
                color:#555;
            ">
                Start chatting with your friends, share moments,
                and enjoy real-time conversations 💬
            </p>

        </div>

        <!-- BUTTON -->
        <div style="
            text-align:center;
            margin-top:40px;
        ">

            <a href="#"
               style="
                    background:#6C63FF;
                    color:white;
                    text-decoration:none;
                    padding:15px 35px;
                    border-radius:50px;
                    display:inline-block;
                    font-size:16px;
                    font-weight:bold;
               ">
                Start Chatting
            </a>

        </div>

    </div>

    <!-- FOOTER -->
    <div style="
        background:#f4f4f4;
        padding:25px;
        text-align:center;
        color:#888;
        font-size:14px;
    ">

        <p style="margin:0;">
            Thank you for joining ChatApp 💜
        </p>

        <p style="
            margin-top:10px;
            font-size:13px;
        ">
            © 2026 ChatApp. All rights reserved.
        </p>

    </div>

</div>

</body>
</html>

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

    public void sendForgotPasswordEmail(
            String toEmail,
            String username,
            String resetLink
    ) {

        try {

            String url =
                    "https://api.brevo.com/v3/smtp/email";

            HttpHeaders headers =
                    new HttpHeaders();

            headers.setContentType(
                    MediaType.APPLICATION_JSON
            );

            headers.set(
                    "api-key",
                    apiKey.trim()
            );

            // HTML TEMPLATE

            String htmlContent = """

<!DOCTYPE html>
<html>

<body style="
    background:#f4f7fb;
    padding:40px;
    font-family:Arial,sans-serif;
">

<div style="
    max-width:600px;
    margin:auto;
    background:white;
    border-radius:20px;
    overflow:hidden;
">

    <div style="
        background:#ff4d6d;
        padding:35px;
        text-align:center;
        color:white;
    ">

        <h1>
            Reset Your Password 🔐
        </h1>

    </div>

    <div style="padding:40px;">

        <h2>
            Hello %s 👋
        </h2>

        <p>
            Click the button below to reset your password.
        </p>

        <div style="
            text-align:center;
            margin-top:40px;
        ">

            <a href="%s"
               style="
                    background:#ff4d6d;
                    color:white;
                    padding:15px 30px;
                    border-radius:50px;
                    text-decoration:none;
                    font-weight:bold;
               ">
                Reset Password
            </a>

        </div>

    </div>

</div>

</body>
</html>

""".formatted(username, resetLink);

            // SENDER

            Map<String, Object> sender =
                    new HashMap<>();

            sender.put(
                    "name",
                    senderName
            );

            sender.put(
                    "email",
                    senderEmail
            );

            // RECEIVER

            Map<String, String> receiver =
                    new HashMap<>();

            receiver.put(
                    "email",
                    toEmail
            );

            receiver.put(
                    "name",
                    username
            );

            List<Map<String, String>> toList =
                    new ArrayList<>();

            toList.add(receiver);

            // REQUEST BODY

            Map<String, Object> body =
                    new HashMap<>();

            body.put(
                    "sender",
                    sender
            );

            body.put(
                    "to",
                    toList
            );

            body.put(
                    "subject",
                    "Reset Your Password 🔐"
            );

            body.put(
                    "htmlContent",
                    htmlContent
            );

            HttpEntity<Map<String, Object>>
                    request =
                    new HttpEntity<>(
                            body,
                            headers
                    );

            // DEBUG

            System.out.println(
                    "API KEY = " + apiKey
            );

            System.out.println(
                    "SENDER = " + senderEmail
            );

            System.out.println(
                    "RESET LINK = " + resetLink
            );

            // API CALL

            ResponseEntity<String> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            request,
                            String.class
                    );

            System.out.println(
                    "RESET EMAIL SENT"
            );

            System.out.println(
                    response.getBody()
            );

        } catch (Exception e) {

            System.out.println(
                    "RESET EMAIL FAILED"
            );

            e.printStackTrace();
        }
    }
   }