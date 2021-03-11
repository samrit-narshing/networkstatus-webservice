package com.project.core.services.exceptions;

/**
 *
 * @author Samrit
 */
public class EdgeExistsException extends RuntimeException {

    public EdgeExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EdgeExistsException(String message) {
        super(message);
    }

    public EdgeExistsException() {
        super();
    }
}
