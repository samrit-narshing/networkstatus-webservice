package com.project.core.services.exceptions;

/**
 *
 * @author Samrit
 */
public class NodeNotFoundException extends RuntimeException {

    public NodeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NodeNotFoundException(String message) {
        super(message);
    }

    public NodeNotFoundException() {
        super();
    }
}
