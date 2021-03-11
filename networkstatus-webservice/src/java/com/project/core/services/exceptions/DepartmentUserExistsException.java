package com.project.core.services.exceptions;

/**
 *
 * @author Samrit
 */
public class DepartmentUserExistsException extends RuntimeException {

    public DepartmentUserExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public DepartmentUserExistsException(String message) {
        super(message);
    }

    public DepartmentUserExistsException() {
        super();
    }
}
