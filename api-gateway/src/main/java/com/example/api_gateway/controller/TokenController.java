package com.example.api_gateway.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class TokenController {

    private final SecretKey secretKey;

    public TokenController(
            @Value("${jwt.secret}") String secret) {

        this.secretKey = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }

    @PostMapping("/token")
    public Map<String, String> generateToken(
            @RequestParam String username,
            @RequestParam String role) {

        String token = Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 60 * 60 * 1000
                        )
                )
                .signWith(secretKey)
                .compact();

        return Map.of(
                "token", token,
                "username", username,
                "role", role
        );
    }
}