package com.ChaTopApiSpring2.service;

import com.ChaTopApiSpring2.exceptions.AccountException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service class responsible for JWT token generation, validation, and claims extraction.
 * Provides methods for token creation, extraction of information from tokens,
 * and token validation.
 */
@Component
public class JwtService {

    /** Secret key for JWT token generation and validation. */
    @Value("${jwt.secret}")
    private String SECRET;

    /**
     * Extract the email (subject) from the provided JWT token.
     *
     * @param token JWT token.
     * @return Email from the token.
     */
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extract the expiration date from the provided JWT token.
     *
     * @param token JWT token.
     * @return Expiration date of the token.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extract specific claims from the provided JWT token using a claims resolver function.
     *
     * @param token JWT token.
     * @param claimsResolver Function to extract specific claim from token.
     * @return Extracted claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extract all claims from the provided JWT token.
     *
     * @param token JWT token.
     * @return All claims from the token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Check if the provided JWT token is valid.
     *
     * @param token JWT token.
     * @return Boolean indicating if the token is valid.
     */
    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if the provided JWT token has expired.
     *
     * @param token JWT token.
     * @return Boolean indicating if the token is expired.
     */
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extract the JWT token from the Authorization header.
     *
     * @param headerValue Authorization header value.
     * @return Extracted JWT token.
     */
    public String extractTokenFromHeader(String headerValue) {
        if (headerValue != null && headerValue.startsWith("Bearer ")) {
            return headerValue.replace("Bearer ", "");
        } else {
            throw new AccountException("Bad token");
        }
    }

    /**
     * Generate a new JWT token for the provided email.
     *
     * @param email Email for the token subject.
     * @return Generated JWT token.
     */
    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, email);
    }

    /**
     * Create a new JWT token with the provided claims and email.
     *
     * @param claims Claims for the token.
     * @param email Email for the token subject.
     * @return Generated JWT token.
     */
    private String createToken(Map<String, Object> claims, String email) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    /**
     * Generate a signing key from the secret.
     *
     * @return Generated signing key.
     */
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
