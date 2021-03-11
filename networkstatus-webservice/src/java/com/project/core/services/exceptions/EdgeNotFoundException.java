package com.project.core.services.exceptions;

/**
 *
 * @author Samrit
 */
public class EdgeNotFoundException extends RuntimeException {

    public EdgeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EdgeNotFoundException(String message) {
        super(message);
    }

    public EdgeNotFoundException() {
        super();
    }
}
