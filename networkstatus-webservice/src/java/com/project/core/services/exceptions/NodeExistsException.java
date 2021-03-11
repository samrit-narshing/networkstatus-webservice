package com.project.core.services.exceptions;

/**
 *
 * @author Samrit
 */
public class NodeExistsException extends RuntimeException {

    public NodeExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NodeExistsException(String message) {
        super(message);
    }

    public NodeExistsException() {
        super();
    }
}
