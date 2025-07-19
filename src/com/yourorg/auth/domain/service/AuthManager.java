package com.yourorg.auth.domain.service;

import com.yourorg.auth.domain.exception.AuthException;
import com.yourorg.auth.domain.model.User;
import java.util.Optional;

/**
 * Service interface for managing user authentication and registration.
 * <p>
 * Provides methods for registering users, logging in, and authenticating tokens.
 * </p>
 */
public interface AuthManager {
    /**
     * Registers a new user with the specified username and raw password.
     *
     * @param username the username for the new user
     * @param rawPassword the raw password for the new user
     * @return the registered {@link User}
     * @throws AuthException if registration fails (e.g., username already exists)
     */
    User register(String username, String rawPassword) throws AuthException;

    /**
     * Authenticates a user with the provided username and raw password.
     *
     * @param username the username of the user
     * @param rawPassword the raw password of the user
     * @return an authentication token as a String
     * @throws AuthException if authentication fails (e.g., invalid credentials)
     */
    String login(String username, String rawPassword) throws AuthException;

    /**
     * Authenticates a user based on the provided authentication token.
     *
     * @param token the authentication token
     * @return an {@link Optional} containing the authenticated user if the token is valid, or empty if invalid
     */
    Optional<User> authenticate(String token);
}
