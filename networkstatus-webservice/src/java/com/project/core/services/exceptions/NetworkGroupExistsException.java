package com.project.core.services.exceptions;

/**
 *
 * @author Samrit
 */
public class NetworkGroupExistsException extends RuntimeException {

    public NetworkGroupExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NetworkGroupExistsException(String message) {
        super(message);
    }

    public NetworkGroupExistsException() {
        super();
    }
}
