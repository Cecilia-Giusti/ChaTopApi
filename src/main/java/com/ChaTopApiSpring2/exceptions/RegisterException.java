package com.ChaTopApiSpring2.exceptions;

/**
 * Custom exception class to handle registration-related issues.
 * This exception is thrown when there are issues specific to user registration operations.
 */
public class RegisterException extends RuntimeException {

    /**
     * Constructs a new RegisterException with the specified error message.
     * @param message The detail message explaining the reason for the exception.
     */
    public RegisterException(String message) {
        super(message);
    }
}
