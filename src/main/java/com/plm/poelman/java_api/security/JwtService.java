package com.plm.poelman.java_api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private final SecretKey key;
    private final String issuer;
    private final String audience;
    private final long ttlMinutes;

    public JwtService(
            @Value("${security.jwt.secret-base64}") String secret,
            @Value("${security.jwt.issuer}") String issuer,
            @Value("${security.jwt.audience}") String audience,
            @Value("${security.jwt.access-ttl-min}") long ttlMinutes) {

        // Decode the SAME Base64 secret for both signing and verification
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);

        this.issuer = issuer;
        this.audience = audience;
        this.ttlMinutes = ttlMinutes;
    }

    /** Build a signed JWT (HS256 inferred from key). */
    public String generate(String subject, Map<String, Object> extraClaims) {
        Instant now = Instant.now();
        return Jwts.builder()
                .claims() // new API
                    .issuer(issuer)
                    .audience().add(audience).and()
                    .subject(subject)
                    .add(extraClaims)
                    .issuedAt(Date.from(now))
                    .expiration(Date.from(now.plusSeconds(ttlMinutes * 60)))
                .and()
                .signWith(key, Jwts.SIG.HS256) // explicit and modern
                .compact();
    }

    /** Parse & verify signature and standard claims; throws if invalid. */
    public Jws<Claims> parse(String token) {
        return Jwts.parser()
                .requireIssuer(issuer)
                .requireAudience(audience)
                .clockSkewSeconds(60)      // helpful in dev
                .verifyWith(key)           // replaces setSigningKey
                .build()
                .parseSignedClaims(token); // new API -> getPayload() for Claims
    }

    @PostConstruct
    void logKeyInfo() {
        // Optional: quick fingerprint to ensure the same key is used across runs
        byte[] enc = key.getEncoded();
        String hash = Base64.getUrlEncoder().withoutPadding().encodeToString(sha256(enc)).substring(0, 12);
        System.out.println("[JWT] alg=" + key.getAlgorithm() + " keyLen=" + enc.length + " keyHash=" + hash);
    }
    private byte[] sha256(byte[] data) {
        try { return MessageDigest.getInstance("SHA-256").digest(data); }
        catch (Exception e) { return new byte[0]; }
    }
}
