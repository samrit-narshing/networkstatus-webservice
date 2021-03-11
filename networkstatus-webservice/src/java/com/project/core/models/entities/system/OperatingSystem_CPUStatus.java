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
public class OperatingSystem_CPUStatus {

    private Double idle;
    private Double usage;

    public Double getIdle() {
        return idle;
    }

    public void setIdle(Double idle) {
        this.idle = idle;
    }

    public Double getUsage() {
        return 100 - getIdle();
    }
}
