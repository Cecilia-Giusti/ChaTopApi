package com.ChaTopApiSpring2.exceptions;

/**
 * Custom exception class to handle message-related issues.
 * This exception is thrown when there are issues specific to messaging operations.
 */
public class MessageException extends RuntimeException {

    /**
     * Constructs a new MessageException with the specified error message.
     * @param message The detail message explaining the reason for the exception.
     */
    public MessageException(String message) {
        super(message);
    }
}
