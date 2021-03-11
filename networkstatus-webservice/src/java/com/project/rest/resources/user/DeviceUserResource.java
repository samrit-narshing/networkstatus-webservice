/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.resources.user;

import com.project.core.models.entities.user.DeviceUser;
import org.springframework.hateoas.ResourceSupport;

/**
 *
 * @author Samrit
 */
public class DeviceUserResource extends ResourceSupport {

    private Long tableId;
    private String username;
    private String tokenName;
    private Boolean active;
    private Long entryDate;

    private String entryByUsername = "";
    private String entryByUserType = "";

    private Long lastModifiedDate;

    private String adminUsername = "";
    private String adminOperatorUsername = "";

    private String adminUserType = "";
    private String adminOperatorUserType = "";

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

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Long entryDate) {
        this.entryDate = entryDate;
    }

    public Long getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Long lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getEntryByUsername() {
        return entryByUsername;
    }

    public void setEntryByUsername(String entryByUsername) {
        this.entryByUsername = entryByUsername;
    }

    public String getEntryByUserType() {
        return entryByUserType;
    }

    public void setEntryByUserType(String entryByUserType) {
        this.entryByUserType = entryByUserType;
    }

    public DeviceUser toDeviceUser() {
        DeviceUser deviceUser = new DeviceUser();
        deviceUser.setUsername(username);
        deviceUser.setTokenName(tokenName);
        deviceUser.setActive(active);
        deviceUser.setEntryDate(entryDate);
        deviceUser.setEntryByUserType(getEntryByUserType());
        deviceUser.setEntryByUsername(getEntryByUsername());
        deviceUser.setLastModifiedDate(lastModifiedDate);
        deviceUser.setAdminUsername(getAdminUsername());
        deviceUser.setAdminOperatorUsername(getAdminOperatorUsername());

        deviceUser.setAdminUserType(getAdminUserType());
        deviceUser.setAdminOperatorUserType(getAdminOperatorUserType());
        return deviceUser;
    }

}
