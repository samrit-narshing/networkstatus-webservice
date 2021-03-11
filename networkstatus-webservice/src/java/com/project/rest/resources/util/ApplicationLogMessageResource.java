/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.resources.util;

import com.project.core.models.entities.util.ApplicationLogMessage;
import org.springframework.hateoas.ResourceSupport;

/**
 *
 * @author Samrit
 */
public class ApplicationLogMessageResource extends ResourceSupport {

    private Long tableId;

    private String senderUsername = "";
    private String loggedUsername = "";
    private String deviceType = "";
    private String deviceModelName = "";
    private byte[] logMessageBlop;
    private String logMessage = "";
    private Long entryDate = 0L;
    private String adminUsername = "";
    private String adminOperatorUsername = "";
    private String adminUserType = "";
    private String adminOperatorUserType = "";

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getLoggedUsername() {
        return loggedUsername;
    }

    public void setLoggedUsername(String loggedUsername) {
        this.loggedUsername = loggedUsername;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceModelName() {
        return deviceModelName;
    }

    public void setDeviceModelName(String deviceModelName) {
        this.deviceModelName = deviceModelName;
    }

    public byte[] getLogMessageBlop() {
        return logMessageBlop;
    }

    public void setLogMessageBlop(byte[] logMessageBlop) {
        this.logMessageBlop = logMessageBlop;
    }

    public Long getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Long entryDate) {
        this.entryDate = entryDate;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public String getAdminOperatorUsername() {
        return adminOperatorUsername;
    }

    public void setAdminOperatorUsername(String adminOperatorUsername) {
        this.adminOperatorUsername = adminOperatorUsername;
    }

    public String getAdminUserType() {
        return adminUserType;
    }

    public void setAdminUserType(String adminUserType) {
        this.adminUserType = adminUserType;
    }

    public String getAdminOperatorUserType() {
        return adminOperatorUserType;
    }

    public void setAdminOperatorUserType(String adminOperatorUserType) {
        this.adminOperatorUserType = adminOperatorUserType;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }
    
    

    public ApplicationLogMessage toApplicationLogMessage() {
        ApplicationLogMessage applicationLogMessage = new ApplicationLogMessage();
        applicationLogMessage.setId(tableId);
        applicationLogMessage.setEntryDate(entryDate);
        applicationLogMessage.setDeviceModelName(deviceModelName);
        applicationLogMessage.setDeviceType(deviceType);
        applicationLogMessage.setLogMessageBlop(logMessageBlop);
        applicationLogMessage.setLoggedUsername(loggedUsername);
        applicationLogMessage.setSenderUsername(senderUsername);

        applicationLogMessage.setAdminUsername(getAdminUsername());
        applicationLogMessage.setAdminOperatorUsername(getAdminOperatorUsername());
        applicationLogMessage.setAdminUserType(getAdminUserType());
        applicationLogMessage.setAdminOperatorUserType(getAdminOperatorUserType());

        return applicationLogMessage;
    }

}
