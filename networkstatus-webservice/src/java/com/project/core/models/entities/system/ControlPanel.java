/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.models.entities.system;

import java.io.Serializable;

/**
 *
 * @author Samrit
 */
public class ControlPanel implements Serializable {

    private String username = "";
    private String userLicenseExpiryDate = "";
    private String macAddress = "";
    private String hostId = "";
    private String licenseExpiryDate = "";
    private String appVersion = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserLicenseExpiryDate() {
        return userLicenseExpiryDate;
    }

    public void setUserLicenseExpiryDate(String userLicenseExpiryDate) {
        this.userLicenseExpiryDate = userLicenseExpiryDate;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    public void setLicenseExpiryDate(String licenseExpiryDate) {
        this.licenseExpiryDate = licenseExpiryDate;
    }

}
