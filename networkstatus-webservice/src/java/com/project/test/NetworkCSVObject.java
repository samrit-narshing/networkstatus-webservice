/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.test;

import com.opencsv.bean.CsvBindByName;

/**
 *
 * @author samri_g64pbd3
 */
public class NetworkCSVObject {

    @CsvBindByName(column = "System")
    private String system;

    @CsvBindByName(column = "Function Tye")
    private String functionType;

    @CsvBindByName(column = "Device Type")
    private String deviceType;

    @CsvBindByName(column = "Host name")
    private String hostName;

    @CsvBindByName(column = "IP Address")
    private String ipAddress;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getFunctionType() {
        return functionType;
    }

    public void setFunctionType(String functionType) {
        this.functionType = functionType;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

}
