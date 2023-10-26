package com.ChaTopApiSpring2.exceptions;

/**
 * Custom exception class to handle account-related issues.
 * This exception is thrown when there are issues specific to user accounts.
 */
public class AccountException extends RuntimeException {

    /**
     * Constructs a new AccountException with the specified error message.
     * @param message The detail message explaining the reason for the exception.
     */
    public AccountException(String message) {
        super(message);
    }
}
