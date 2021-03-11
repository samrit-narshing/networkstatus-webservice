package com.project.core.services.exceptions;

/**
 *
 * @author Samrit
 */
public class DepartmentUserNotFoundException extends RuntimeException {
    public DepartmentUserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DepartmentUserNotFoundException(String message) {
        super(message);
    }

    public DepartmentUserNotFoundException() {
    }
}
