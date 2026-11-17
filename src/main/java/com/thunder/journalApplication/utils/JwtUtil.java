package com.thunder.journalApplication.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")                 // Base64 string from environment
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    private SecretKey key;

    @PostConstruct
    void init() {
        // Fails fast at startup if the secret is missing or too short
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String generateToken(String username) {
        Date now = new Date();
        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expirationMs))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    /** True only if the signature is valid, the token is not expired, and it belongs to this user. */
    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            Claims claims = parseClaims(token);   // throws if signature is bad or token expired
            return claims.getSubject().equals(userDetails.getUsername());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}