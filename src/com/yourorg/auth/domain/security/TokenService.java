package com.yourorg.auth.domain.security;

import com.yourorg.auth.domain.model.User;
import java.util.Optional;

/**
 * Service interface for creating and validating authentication tokens.
 * <p>
 * Implementations are responsible for generating tokens for authenticated users
 * and validating tokens to retrieve the associated user.
 * </p>
 */
public interface TokenService {
    /**
     * Creates a new authentication token for the specified user.
     *
     * @param user the user for whom to create the token
     * @return the generated authentication token as a String
     */
    String createToken(User user);

    /**
     * Validates the provided authentication token and retrieves the associated user.
     *
     * @param token the authentication token to validate
     * @return an {@link Optional} containing the user if the token is valid, or empty if invalid or expired
     */
    Optional<User> validateToken(String token);
}
