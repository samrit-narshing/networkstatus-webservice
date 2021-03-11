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
public enum EMPLOYEE_ATTENDANCESTATUS_INFO {

//    ALREADY_JOB_STARTED("already_job_started"),
    NOT_CHECKED_IN("not_checked_in"),
//    NO_JOB_STARTED("no_job_started"),
//    ALREADY_REMANING_JOB("already_remaning_job"),
//    ALREADY_FINISHED_JOB("already_finished_job"),
    ALREADY_CHECKED_IN("already_checked_in"),
    ALREADY_CHECKED_OUT("already_checked_out"),
    ALREADY_CHECKED_IN_OUT("already_checked_in_out"),
    INVALID_CORDINATES("invalid_cordinates"),
    INVALID_LOCATION("invalid_location");

    private final String status;

    EMPLOYEE_ATTENDANCESTATUS_INFO(String status) {
        this.status = status;
    }

    public String status() {
        return status;
    }

}
