package com.ChaTopApiSpring2.exceptions;

/**
 * Custom exception class to handle login-related issues.
 * This exception is thrown when there are issues specific to user login operations.
 */
public class LoginException extends RuntimeException {

    /**
     * Constructs a new LoginException with the specified error message.
     * @param message The detail message explaining the reason for the exception.
     */
    public LoginException(String message) {
        super(message);
    }
}
