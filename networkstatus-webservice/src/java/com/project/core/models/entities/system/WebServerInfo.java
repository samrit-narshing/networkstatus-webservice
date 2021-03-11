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
public class WebServerInfo implements Serializable {

    private Java java = new Java();
    private Memory jvmMemory = new Memory();
    private SimCLogger simCLogger = new SimCLogger();
    private UserLicenseDetail userLicenseDetail = new UserLicenseDetail();

    public UserLicenseDetail getUserLicenseDetail() {
        return userLicenseDetail;
    }

    public void setUserLicenseDetail(String userName, String expiryDate) {
        userLicenseDetail = new UserLicenseDetail();
        userLicenseDetail.setExpiryDate(expiryDate);
        userLicenseDetail.setUsername(userName);
    }

    public Java getJava() {
        return java;
    }

    public void setJava(String version) {
        java = new Java();
        java.setName(version);
    }

    public Memory getJvmMemory() {
        return jvmMemory;
    }

    public void setJvmMemory(String maxMemory, String totalMemory, String usedMemory, String availableMemory) {
        jvmMemory = new Memory();
        jvmMemory.setMaxMemory(maxMemory);
        jvmMemory.setTotalMemory(totalMemory);
        jvmMemory.setUsedMemory(usedMemory);
        jvmMemory.setAvailableMemory(availableMemory);
    }

    public SimCLogger getSimCLogger() {
        return simCLogger;
    }

    public void setSimCLogger(String version, String expiryDate) {
        simCLogger = new SimCLogger();
        simCLogger.setVersion(version);
        simCLogger.setExpiryDate(expiryDate);
    }

    private class Java {

        private String name = "N/A";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private class Memory {

        private String maxMemory = "N/A";
        private String totalMemory = "N/A";
        private String usedMemory = "N/A";
        private String availableMemory = "N/A";

        public String getMaxMemory() {
            return maxMemory;
        }

        public void setMaxMemory(String maxMemory) {
            this.maxMemory = maxMemory;
        }

        public String getTotalMemory() {
            return totalMemory;
        }

        public void setTotalMemory(String totalMemory) {
            this.totalMemory = totalMemory;
        }

        public String getUsedMemory() {
            return usedMemory;
        }

        public void setUsedMemory(String usedMemory) {
            this.usedMemory = usedMemory;
        }

        public String getAvailableMemory() {
            return availableMemory;
        }

        public void setAvailableMemory(String availableMemory) {
            this.availableMemory = availableMemory;
        }

    }

    private class SimCLogger {

        private String expiryDate = "N/A";
        private String version = "N/A";

        public String getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(String expiryDate) {
            this.expiryDate = expiryDate;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

    }

    private class UserLicenseDetail {

        private String username = "N/A";
        private String expiryDate = "N/A";

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(String expiryDate) {
            this.expiryDate = expiryDate;
        }

    }

}
