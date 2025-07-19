package com.yourorg.auth.domain.exception;

public class UserAlreadyExistsException extends AuthException {
    public UserAlreadyExistsException(String username) {
        super("User already exists: " + username);
    }
}