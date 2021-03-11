/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.mvc.common;

import com.project.core.models.entities.user.User;
import com.project.core.models.entities.util.ApplicationLogMessage;
import com.project.core.services.util.ApplicationLogMessageService;
import com.project.core.util.DateConverter;
import com.project.core.util.Log4jUtil;

import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

    @Autowired
    private ApplicationLogMessageService applicationLogMessageService;

    public void writeLogMessage(String message, User loggedUser) {
        try {
            // For System Log Messages
            ApplicationLogMessage object = new ApplicationLogMessage();

            object.setId(null);
            object.setEntryDate(DateConverter.getCurrentConvertedDateAndTimeInUnixDate());
            object.setLoggedUsername(loggedUser.getUsername());
            object.setDeviceType("DEFAULT");
            object.setDeviceModelName("DEFAULT");

            object.setSenderUsername(loggedUser.getUsername());
            object.setAdminUsername(loggedUser.getAdminUsername());
            object.setAdminUserType(loggedUser.getAdminUserType());
            object.setAdminOperatorUsername(loggedUser.getAdminOperatorUsername());
            object.setAdminOperatorUserType(loggedUser.getAdminOperatorUserType());
            object.setLogMessageBlop(message.getBytes());

            applicationLogMessageService.createApplicationLogMessage(object);
            // End For System Log Messages
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void writeLogMessage(Exception exception, User loggedUser) {
        try {
            // For System Log Messages

            ApplicationLogMessage object = new ApplicationLogMessage();
            object.setId(null);
            object.setLoggedUsername(loggedUser.getUsername());
            object.setDeviceType("DEFAULT");
            object.setDeviceModelName("DEFAULT");

            object.setEntryDate(DateConverter.getCurrentConvertedDateAndTimeInUnixDate());
            object.setSenderUsername(loggedUser.getUsername());
            object.setAdminUsername(loggedUser.getAdminUsername());
            object.setAdminUserType(loggedUser.getAdminUserType());
            object.setAdminOperatorUsername(loggedUser.getAdminOperatorUsername());
            object.setAdminOperatorUserType(loggedUser.getAdminOperatorUserType());
            object.setLogMessageBlop((Log4jUtil.getFormattedMessageFromStackTraceElements(exception)).getBytes());

            applicationLogMessageService.createApplicationLogMessage(object);
            // End For System Log Messages
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void writeLogMessage(String message) {
        try {
            // For System Log Messages
            ApplicationLogMessage object = new ApplicationLogMessage();

            object.setId(null);
            object.setEntryDate(DateConverter.getCurrentConvertedDateAndTimeInUnixDate());
            object.setSenderUsername("DEFAULT");
            object.setAdminUsername("DEFAULT");
            object.setAdminUserType("DEFAULT");
            object.setAdminOperatorUsername("DEFAULT");
            object.setAdminOperatorUserType("DEFAULT");
            object.setLogMessageBlop(message.getBytes());

            object.setLoggedUsername("DEFAULT");
            object.setDeviceType("DEFAULT");
            object.setDeviceModelName("DEFAULT");

            applicationLogMessageService.createApplicationLogMessage(object);
            // End For System Log Messages
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void writeLogMessage(Exception exception) {
        try {
            // For System Log Messages

            ApplicationLogMessage object = new ApplicationLogMessage();
            object.setId(null);
            object.setEntryDate(DateConverter.getCurrentConvertedDateAndTimeInUnixDate());
            object.setSenderUsername("DEFAULT");
            object.setAdminUsername("DEFAULT");
            object.setAdminUserType("DEFAULT");
            object.setAdminOperatorUsername("DEFAULT");
            object.setAdminOperatorUserType("DEFAULT");
            object.setLoggedUsername("DEFAULT");
            object.setDeviceType("DEFAULT");
            object.setDeviceModelName("DEFAULT");
            object.setLogMessageBlop((Log4jUtil.getFormattedMessageFromStackTraceElements(exception)).getBytes());
            applicationLogMessageService.createApplicationLogMessage(object);
            // End For System Log Messages
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
