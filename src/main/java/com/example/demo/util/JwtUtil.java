package com.example.demo.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Admin;
import com.example.demo.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private long JWT_EXPIRATION;

    /**
     * Generate Signing Key
     */
    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8)
        );
    }

    /**
     * Generate JWT Token for USER
     */
    public String generateToken(User user) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("userId", user.getUserId());
        claims.put("role", "ROLE_" + user.getRole().name());

        return Jwts.builder()
                .claims(claims)
                .subject(user.getCustomer().getMobileNumber())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Generate JWT Token for ADMIN
     */
    public String generateToken(Admin admin) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("userId", admin.getAdminId());
        claims.put("role", "ROLE_ADMIN");

        return Jwts.builder()
                .claims(claims)
                .subject(admin.getMobileNumber())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Extract Mobile Number
     */
    public String extractMobileNumber(String token) {

        return extractClaim(token, Claims::getSubject);

    }

    /**
     * Generic Claim Extractor
     */
    public <T> T extractClaim(String token,
                              Function<Claims, T> claimsResolver) {

        Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    /**
     * Extract All Claims
     */
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Check Token Expiry
     */
    public boolean isTokenExpired(String token) {

        Date expiration = extractClaim(
                token,
                Claims::getExpiration
        );

        return expiration.before(new Date());

    }

    /**
     * Validate Token
     */
    public boolean validateToken(String token,
                                 UserDetails userDetails) {

        String mobileNumber = extractMobileNumber(token);

        return mobileNumber.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    /**
     * Extract Role
     */
    public String extractRole(String token) {

        return extractClaim(
                token,
                claims -> claims.get("role", String.class)
        );

    }

    /**
     * Extract User/Admin ID
     */
    public Long extractUserId(String token) {

        return extractClaim(
                token,
                claims -> claims.get("userId", Long.class)
        );

    }

}