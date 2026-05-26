package com.spring.chatApp.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET =
            "chatapplicationsecretchatapplicationsecret123";

    private final Key key =
            Keys.hmacShaKeyFor(SECRET.getBytes());

    // GENERATE TOKEN
    public String generateToken(
            String email,
            String username,
            String userId

    ) {

        return Jwts.builder()
                .setSubject(email)
                .claim("username", username)
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 86400000
                        )
                )
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // EXTRACT CLAIMS
    public Claims extractClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // EXTRACT EMAIL
    public String extractEmail(String token) {

        return extractClaims(token)
                .getSubject();
    }

    // EXTRACT USER ID
    public String extractUserId(String token) {

        return extractClaims(token)
                .get("userId", String.class);
    }

    // EXTRACT USERNAME
    public String extractUsername(String token) {

        return extractClaims(token)
                .get("username", String.class);
    }


    // TOKEN VALIDATION
    public boolean isTokenValid(String token) {

        try {

            extractClaims(token);

            return true;

        } catch (Exception e) {

            return false;
        }
    }
}