package com.plm.poelman.java_api.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.stereotype.Component;

@Component
public class PasswordUtils {

    private static final int SALT_LENGTH = 16; // 128-bit salt
    private static final int ITERATIONS = 210_000; // modern baseline
    private static final int KEY_LENGTH = 256; // bits
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";

    public byte[] generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    public byte[] hash(char[] password, byte[] salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException("Error hashing password", e);
        }
    }

    public boolean verify(char[] rawPassword, byte[] salt, byte[] expectedHash) {
        byte[] actual = hash(rawPassword, salt);

        return constantTimeEquals(actual, expectedHash);
    }

    private boolean constantTimeEquals(byte[] a, byte[] b) {
        if (a == null || b == null || a.length != b.length)
            return false;
        int result = 0;
        for (int i = 0; i < a.length; i++)
            result |= (a[i] ^ b[i]);
        return result == 0;
    }

}
