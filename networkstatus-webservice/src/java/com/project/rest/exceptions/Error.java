/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.exceptions;

import org.springframework.http.HttpStatus;

/**
 *
 * @author Samrit
 */
public enum Error {

    BAD_REQUEST(HttpStatus.ALREADY_REPORTED, Code.USER_CONFLICT, Message.CONFLICT_REQUEST);

    private HttpStatus status;
    private int code;
    private String message;

    private Error(HttpStatus status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "{\"status\":" + status + ",\"code\":" + code + ",\"message\":" + "\"" + message + "\"}";
    }

    public static class Code {

        /**
         * Application specific server Errors Range: 1xxx
         */
        public static final int USER_CONFLICT = 1001;
    }

    public static class Message {

        public static final String CONFLICT_REQUEST = "CONFLICT USER";
    }
}
