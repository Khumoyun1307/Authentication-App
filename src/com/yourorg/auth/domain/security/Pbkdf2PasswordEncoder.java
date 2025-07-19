// src/com/yourorg/auth/security/Pbkdf2PasswordEncoder.java
package com.yourorg.auth.domain.security;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Objects;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Password encoder implementation using PBKDF2 with HMAC SHA-256.
 * <p>
 * Generates a random salt for each password and encodes the salt and hash as Base64,
 * separated by a colon. Provides secure password hashing and verification.
 * </p>
 */
public class Pbkdf2PasswordEncoder implements PasswordEncoder {
    /** PBKDF2 algorithm with HMAC SHA-256. */
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    /** Number of PBKDF2 iterations. */
    private static final int ITERATIONS = 65_536;
    /** Key length in bits. */
    private static final int KEY_LENGTH = 256;
    /** Secure random number generator for salt generation. */
    private static final SecureRandom random = new SecureRandom();
    /** Base64 encoder for encoding salt and hash. */
    private static final Base64.Encoder encoder = Base64.getEncoder();
    /** Base64 decoder for decoding salt and hash. */
    private static final Base64.Decoder decoder = Base64.getDecoder();

    /**
     * Hashes the provided raw password using PBKDF2 with a random salt.
     * <p>
     * The result is a Base64-encoded salt and hash, separated by a colon.
     * </p>
     *
     * @param rawPassword the raw password to hash
     * @return the encoded salt and hash, separated by a colon
     */
    @Override
    public String hash(CharSequence rawPassword) {
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        byte[] hash = pbkdf2(rawPassword, salt);
        return encoder.encodeToString(salt) + ":" + encoder.encodeToString(hash);
    }

    /**
     * Verifies that the provided raw password matches the stored hash.
     * <p>
     * The stored value must be in the format "salt:hash", both Base64-encoded.
     * </p>
     *
     * @param rawPassword the raw password to verify
     * @param stored the stored salt and hash, separated by a colon
     * @return {@code true} if the password matches, {@code false} otherwise
     * @throws NullPointerException if {@code stored} is null
     */
    @Override
    public boolean verify(CharSequence rawPassword, String stored) {
        String[] parts = Objects.requireNonNull(stored).split(":");
        byte[] salt = decoder.decode(parts[0]);
        byte[] expectedHash = decoder.decode(parts[1]);
        byte[] actualHash = pbkdf2(rawPassword, salt);
        return MessageDigest.isEqual(expectedHash, actualHash);
    }

    /**
     * Computes the PBKDF2 hash of the given password and salt.
     *
     * @param raw the raw password
     * @param salt the salt
     * @return the hashed password as a byte array
     * @throws RuntimeException if hashing fails
     */
    private byte[] pbkdf2(CharSequence raw, byte[] salt) {
        try {
            KeySpec spec = new PBEKeySpec(raw.toString().toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
            return skf.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
