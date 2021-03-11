/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.util.enums;

/**
 *
 * @author samri
 */
public enum EMPLOYEE_ATTENDANCESTATUS {

//    JOB_START("job_start"),
//    JOB_STOP("job_stop"),
    CHECKED_IN("checked_in"),
    CHECKED_OUT("checked_out");

    private final String status;

    EMPLOYEE_ATTENDANCESTATUS(String status) {
        this.status = status;
    }

    public String status() {
        return status;
    }

}
