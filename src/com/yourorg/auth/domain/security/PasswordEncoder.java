package com.yourorg.auth.domain.security;

/**
 * Interface for password encoding and verification.
 * <p>
 * Provides methods to hash raw passwords and verify them against stored hashes.
 * Implementations may use different hashing algorithms.
 * </p>
 */
public interface PasswordEncoder {
    /**
     * Hashes the provided raw password.
     *
     * @param rawPassword the raw password to hash
     * @return the hashed password as a String
     */
    String hash(CharSequence rawPassword);

    /**
     * Verifies that the provided raw password matches the hashed password.
     *
     * @param rawPassword the raw password to verify
     * @param hashedPassword the previously hashed password to compare against
     * @return {@code true} if the password matches, {@code false} otherwise
     */
    boolean verify(CharSequence rawPassword, String hashedPassword);
}
