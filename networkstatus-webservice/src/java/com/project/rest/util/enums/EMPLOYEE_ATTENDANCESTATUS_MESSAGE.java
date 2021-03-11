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
public enum EMPLOYEE_ATTENDANCESTATUS_MESSAGE {

//    ALREADY_JOB_STARTED("ALERT TO DEVICE USER!!!! The job is already started."),
//    NO_JOB_STARTED("ALERT TO DEVICE USER!!!! No Job is started yet. Please create new job before contiune."),
//    ALREADY_REMANING_JOB("ALERT TO DEVICE USER!!!! There are already pending entries that are needed to completed."),
//    ALREADY_FINISHED_JOB("ALERT TO DEVICE USER!!!! The job is already completed. Please create new job before contiune."),
    NOT_CHECKED_IN("ALERT TO DEVICE USER!!!! You have not made your attendance today (checked-in) in office. Please make your attendance first."),
    ALREADY_CHECKED_IN("You are already checked-in. If you want to check out please press 'Checked Out' button."),
    ALREADY_CHECKED_OUT("You are already checked-out. If you want to check in please press 'Checked In' button."),
    ALREADY_CHECKED_IN_OUT("You are already checked-in and checked-out. Please try next day."),
    INVALID_CORDINATES("The cordinates are invalid. It cannot be 0,0 . Please check the device if location service is enabled."),
    INVALID_LOCATION("Your location is not in the range near to Organization to proceed. Please be near to office.");

    private final String message;

    EMPLOYEE_ATTENDANCESTATUS_MESSAGE(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }

}
