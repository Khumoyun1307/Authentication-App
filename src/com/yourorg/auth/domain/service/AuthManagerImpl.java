package com.yourorg.auth.domain.service;

import com.yourorg.auth.domain.exception.InvalidCredentialsException;
import com.yourorg.auth.domain.exception.UserAlreadyExistsException;
import com.yourorg.auth.domain.model.User;
import com.yourorg.auth.domain.repository.UserRepository;
import com.yourorg.auth.domain.security.PasswordEncoder;
import com.yourorg.auth.domain.security.TokenService;
import java.util.Optional;

/**
 * Implementation of {@link AuthManager} for managing user authentication and registration.
 * <p>
 * Handles user registration, login, and token-based authentication using
 * a user repository, password encoder, and token service.
 * </p>
 */
public class AuthManagerImpl implements AuthManager {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    /**
     * Constructs a new AuthManagerImpl with the required dependencies.
     *
     * @param userRepo the user repository for user persistence
     * @param passwordEncoder the password encoder for hashing and verifying passwords
     * @param tokenService the token service for token creation and validation
     */
    public AuthManagerImpl(UserRepository userRepo,
                           PasswordEncoder passwordEncoder,
                           TokenService tokenService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    /**
     * Registers a new user with the specified username and raw password.
     *
     * @param username the username for the new user
     * @param rawPassword the raw password for the new user
     * @return the registered {@link User}
     * @throws UserAlreadyExistsException if a user with the given username already exists
     */
    @Override
    public User register(String username, String rawPassword) throws UserAlreadyExistsException {
        if (userRepo.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException(username);
        }
        String hash = passwordEncoder.hash(rawPassword);
        User user = new User(username, hash);
        userRepo.save(user);
        return user;
    }

    /**
     * Authenticates a user with the provided username and raw password.
     *
     * @param username the username of the user
     * @param rawPassword the raw password of the user
     * @return an authentication token as a String
     * @throws InvalidCredentialsException if authentication fails (e.g., invalid credentials)
     */
    @Override
    public String login(String username, String rawPassword) throws InvalidCredentialsException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.verify(rawPassword, user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        return tokenService.createToken(user);
    }

    /**
     * Authenticates a user based on the provided authentication token.
     *
     * @param token the authentication token
     * @return an {@link Optional} containing the authenticated user if the token is valid, or empty if invalid
     */
    @Override
    public Optional<User> authenticate(String token) {
        return tokenService.validateToken(token);
    }
}