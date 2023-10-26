package com.ChaTopApiSpring2.exceptions;

/**
 * Custom exception class to handle rental-related issues.
 * This exception is thrown when there are issues specific to rental operations.
 */
public class RentalException extends RuntimeException {

    /**
     * Constructs a new RentalException with the specified error message.
     * @param message The detail message explaining the reason for the exception.
     */
    public RentalException(String message) {
        super(message);
    }
}
