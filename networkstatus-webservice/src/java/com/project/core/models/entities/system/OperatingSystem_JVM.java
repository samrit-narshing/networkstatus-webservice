/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.models.entities.system;

/**
 *
 * @author Samrit
 */
public class OperatingSystem_JVM {
    
    private String javaVersion;
    private Double usedMemory;
    private Double freeMemory;
    private Double totalMemory;
    private String maxMemory;

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public Double getUsedMemory() {
        return getTotalMemory() - getFreeMemory();
    }

    public void setUsedMemory(Double usedMemory) {
        this.usedMemory = usedMemory;
    }

    public Double getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(Double freeMemory) {
        this.freeMemory = freeMemory;
    }

    public Double getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(Double totalMemory) {
        this.totalMemory = totalMemory;
    }

    public String getMaxMemory() {
        return maxMemory;
    }

    public void setMaxMemory(String maxMemory) {
        this.maxMemory = maxMemory;
    }

}
