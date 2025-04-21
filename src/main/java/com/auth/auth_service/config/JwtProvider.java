package com.auth.auth_service.config;

import com.auth.auth_service.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;


public class JwtProvider {
    private static final SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public static String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("user_type", user.getUserType().getRole())
                .claim("id", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 86400000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}