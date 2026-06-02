package com.appreciate.educationweb.security;

import com.appreciate.educationweb.student.repository.entity.StudentEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private static final long EXPIRATION_MS = 86400000; // 24 saat

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(StudentEntity student) {
        return Jwts.builder()
                .subject(student.getEmail())
                .claim("id", student.getId())
                .claim("name", student.getName())
                .claim("surname", student.getSurname())
                .claim("age", student.getAge())
                .claim("role", "STUDENT")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(secretKey)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Integer extractId(String token) {
        return extractAllClaims(token).get("id", Integer.class);
    }

    public String extractName(String token) {
        return extractAllClaims(token).get("name", String.class);
    }

    public String extractSurname(String token) {
        return extractAllClaims(token).get("surname", String.class);
    }

    public Integer extractAge(String token) {
        return extractAllClaims(token).get("age", Integer.class);
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
