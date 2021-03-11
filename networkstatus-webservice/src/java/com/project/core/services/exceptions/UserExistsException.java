package com.project.core.services.exceptions;

/**
 *
 * @author Samrit
 */
public class UserExistsException extends RuntimeException {

    public UserExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserExistsException(String message) {
        super(message);
    }

    public UserExistsException() {
        super();
    }
}
