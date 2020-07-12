package com.test.mongodb.exception;

/**
 * Custom exception to be used in case of failures.
 */
public class CustomAppException extends Exception {
    /**
     * Constructor.
     *
     * @param message message to be passed to the constructor of the super class.
     */
    public CustomAppException(final String message) {
        super(message);
    }
}
