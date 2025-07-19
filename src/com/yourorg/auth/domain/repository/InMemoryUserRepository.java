package com.yourorg.auth.domain.repository;

import com.yourorg.auth.domain.model.User;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of {@link UserRepository}.
 * <p>
 * Stores users in a thread-safe map, keyed by username.
 * Intended for testing or development; not suitable for production use.
 * </p>
 */
public class InMemoryUserRepository implements UserRepository {
    /** Thread-safe map to store users by their username. */
    private final Map<String, User> usersByUsername = new ConcurrentHashMap<>();

    /**
     * Finds a user by their username.
     *
     * @param username the username to search for
     * @return an {@link Optional} containing the user if found, or empty if not found
     */
    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(usersByUsername.get(username));
    }

    /**
     * Saves a new user to the repository.
     * <p>
     * If a user with the same username already exists, it will be overwritten.
     * </p>
     *
     * @param user the user to save
     */
    @Override
    public void save(User user) {
        usersByUsername.put(user.getUsername(), user);
    }

    /**
     * Updates an existing user in the repository.
     * <p>
     * If the user does not exist, this will add a new entry.
     * </p>
     *
     * @param user the user to update
     */
    @Override
    public void update(User user) {
        usersByUsername.put(user.getUsername(), user);
    }
}
