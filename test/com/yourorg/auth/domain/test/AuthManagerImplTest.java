package com.yourorg.auth.domain.test;

import com.yourorg.auth.domain.model.User;
import com.yourorg.auth.domain.repository.InMemoryUserRepository;
import com.yourorg.auth.domain.security.Pbkdf2PasswordEncoder;
import com.yourorg.auth.domain.security.UuidTokenService;
import com.yourorg.auth.domain.service.AuthManager;
import com.yourorg.auth.domain.service.AuthManagerImpl;
import com.yourorg.auth.domain.exception.AuthException;
import com.yourorg.auth.domain.exception.UserAlreadyExistsException;
import com.yourorg.auth.domain.exception.InvalidCredentialsException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

class AuthManagerImplTest {
    private AuthManager authManager;

    @BeforeEach
    void setUp() {
        authManager = new AuthManagerImpl(
                new InMemoryUserRepository(),
                new Pbkdf2PasswordEncoder(),
                new UuidTokenService()
        );
    }

    @Test
    void testRegisterAndLoginSuccess() throws AuthException {
        User user = authManager.register("user1", "password");
        assertNotNull(user.getId());
        String token = authManager.login("user1", "password");
        assertNotNull(token);
    }

    @Test
    void testRegisterDuplicateUsername() throws AuthException {
        authManager.register("user1", "password");
        assertThrows(UserAlreadyExistsException.class, () ->
                authManager.register("user1", "password2")
        );
    }

    @Test
    void testLoginInvalidCredentials() throws AuthException {
        assertThrows(InvalidCredentialsException.class, () ->
                authManager.login("nonexistent", "password")
        );
        authManager.register("user1", "password");
        assertThrows(InvalidCredentialsException.class, () ->
                authManager.login("user1", "wrongpassword")
        );
    }

    @Test
    void testAuthenticateValidToken() throws AuthException {
        authManager.register("user1", "password");
        String token = authManager.login("user1", "password");
        assertTrue(authManager.authenticate(token).isPresent());
    }

    @Test
    void testAuthenticateInvalidToken() {
        assertFalse(authManager.authenticate("invalidtoken").isPresent());
    }
}
