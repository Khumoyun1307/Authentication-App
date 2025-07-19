package com.yourorg.auth.domain.security;

import com.yourorg.auth.domain.model.User;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple token service implementation using randomly generated UUIDs as tokens.
 * <p>
 * Stores tokens and associated users in a thread-safe in-memory map.
 * Intended for development or testing; not suitable for production use.
 * </p>
 */
public class UuidTokenService implements TokenService {
    /** Thread-safe map storing tokens and their associated users. */
    private final Map<String, User> tokenStore = new ConcurrentHashMap<>();

    /**
     * Creates a new authentication token for the specified user.
     * <p>
     * Generates a random UUID as the token and stores the mapping in memory.
     * </p>
     *
     * @param user the user for whom to create the token
     * @return the generated authentication token as a String
     */
    @Override
    public String createToken(User user) {
        String token = UUID.randomUUID().toString();
        tokenStore.put(token, user);
        return token;
    }

    /**
     * Validates the provided authentication token and retrieves the associated user.
     * <p>
     * Looks up the token in the in-memory store.
     * </p>
     *
     * @param token the authentication token to validate
     * @return an {@link Optional} containing the user if the token is valid, or empty if not found
     */
    @Override
    public Optional<User> validateToken(String token) {
        return Optional.ofNullable(tokenStore.get(token));
    }
}