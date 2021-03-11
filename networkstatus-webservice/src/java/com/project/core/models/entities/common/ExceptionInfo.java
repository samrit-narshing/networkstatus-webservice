/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.models.entities.common;

import java.io.Serializable;

/**
 *
 * @author samri
 */
public class ExceptionInfo implements Serializable {

    private int httpStatusCode = 0;
    private int applicationStatusCode = 0;
    private String message = "";
    private Boolean status = false;
    private String intenalExceptionMessage = "";

    public ExceptionInfo() {
    }

    public ExceptionInfo(int httpStatusCode, int applicationStatusCode, String message, Boolean status, String intenalExceptionMessage) {
        this.httpStatusCode = httpStatusCode;
        this.applicationStatusCode = applicationStatusCode;
        this.message = message;
        this.status = status;
        this.intenalExceptionMessage = intenalExceptionMessage;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public int getApplicationStatusCode() {
        return applicationStatusCode;
    }

    public void setApplicationStatusCode(int applicationStatusCode) {
        this.applicationStatusCode = applicationStatusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getIntenalExceptionMessage() {
        return intenalExceptionMessage;
    }

    public void setIntenalExceptionMessage(String intenalExceptionMessage) {
        this.intenalExceptionMessage = intenalExceptionMessage;
    }

}
