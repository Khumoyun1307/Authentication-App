package com.yourorg.auth.domain.model;

import java.time.Instant;
import java.util.UUID;

/**
 * Represents a user in the authentication system.
 * <p>
 * Each user has a unique identifier, username, password hash, and creation timestamp.
 * </p>
 */
public class User {
    /** Unique identifier for the user (UUID as String). */
    private final String id;
    /** Username of the user. */
    private final String username;
    /** Hashed password for authentication. */
    private String passwordHash;
    /** Timestamp when the user was created. */
    private final Instant createdAt;

    /**
     * Constructs a new User with the specified username and password hash.
     * The user ID is generated as a random UUID and the creation time is set to now.
     *
     * @param username the username of the user
     * @param passwordHash the hashed password of the user
     */
    public User(String username, String passwordHash) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.passwordHash = passwordHash;
        this.createdAt = Instant.now();
    }

    /**
     * Returns the unique identifier of the user.
     *
     * @return the user ID as a String
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the username of the user.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the hashed password of the user.
     *
     * @return the password hash
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Sets a new password hash for the user.
     * <p>
     * This should be used when the user changes their password.
     * </p>
     *
     * @param passwordHash the new hashed password
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Returns the timestamp when the user was created.
     *
     * @return the creation time as an {@link Instant}
     */
    public Instant getCreatedAt() {
        return createdAt;
    }
}
