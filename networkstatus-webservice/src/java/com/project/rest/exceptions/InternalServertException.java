package com.project.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Samrit
 */
@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServertException extends RuntimeException {
    public InternalServertException() {
    }

    public InternalServertException(Throwable cause) {
        super(cause);
    }
}
