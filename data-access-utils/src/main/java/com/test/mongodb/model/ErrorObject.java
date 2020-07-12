package com.test.mongodb.model;

import lombok.Getter;
import lombok.ToString;

/**
 * Wrapper for error information to be returned in case of failures when executing HTTP requests.
 */
@Getter
@ToString
public class ErrorObject {
    /**
     * HTTP status code.
     */
    private int status;

    /**
     * HTTP status message description.
     */
    private String message;

    /**
     * Error message associated with the failure.
     */
    private String error;

    public ErrorObject(final int status, final String message, final String error) {
        this.status = status;
        this.message = message;
        this.error = error;
    }
}
