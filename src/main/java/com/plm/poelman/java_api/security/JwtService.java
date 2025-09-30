package com.plm.poelman.java_api.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private final SecretKey key;
    private final String issuer;
    private final String audience;
    private final long ttlMinutes;

    public JwtService(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.issuer}") String issuer,
            @Value("${security.jwt.audience}") String audience,
            @Value("${security.jwt.access-ttl-min}") long ttlMinutes) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.issuer = issuer;
        this.audience = audience;
        this.ttlMinutes = ttlMinutes;
    }

    public String generate(String subject, Map<String, Object> extraClaims) {
        Instant now = Instant.now();

        return Jwts.builder()
                .claims() // enter claims context
                .issuer(issuer)
                .audience().add(audience).and() // audience is now a nested builder
                .subject(subject)
                .add(extraClaims)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(ttlMinutes * 60)))
                .and() // exit claims context
                .signWith(key) // algorithm inferred from key (HS256 here)
                .compact();
    }

    public Jws<Claims> parse(String token) {
        return Jwts.parser()
                .requireAudience(audience)
                .requireIssuer(issuer)
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
    }
}
