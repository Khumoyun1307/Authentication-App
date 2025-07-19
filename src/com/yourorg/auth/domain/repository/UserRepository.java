package com.yourorg.auth.domain.repository;

import com.yourorg.auth.domain.model.User;
import java.util.Optional;

/**
 * Repository interface for managing {@link User} entities.
 * <p>
 * Provides methods to find, save, and update users in a data store.
 * Implementations may use different storage mechanisms.
 * </p>
 */
public interface UserRepository {
    /**
     * Finds a user by their username.
     *
     * @param username the username to search for
     * @return an {@link Optional} containing the user if found, or empty if not found
     */
    Optional<User> findByUsername(String username);

    /**
     * Saves a new user to the repository.
     *
     * @param user the user to save
     */
    void save(User user);

    /**
     * Updates an existing user in the repository.
     *
     * @param user the user to update
     */
    void update(User user);
}
