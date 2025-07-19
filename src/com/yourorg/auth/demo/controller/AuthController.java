package com.yourorg.auth.demo.controller;

import com.yourorg.auth.domain.service.AuthManager;
import com.yourorg.auth.domain.model.User;
import com.yourorg.auth.domain.exception.AuthException;
import java.util.Optional;

/**
 * Controller class responsible for handling authentication-related operations.
 * <p>
 * Provides methods for user registration, login, and token-based authentication.
 * Delegates authentication logic to the {@link AuthManager}.
 * </p>
 */
public class AuthController {
    private final AuthManager authManager;
    public AuthController(AuthManager authManager) {
        this.authManager = authManager;
    }
    public User register(String username, String password) throws AuthException {
        return authManager.register(username, password);
    }
    public String login(String username, String password) throws AuthException {
        return authManager.login(username, password);
    }
    public Optional<User> authenticate(String token) {
        return authManager.authenticate(token);
    }
}