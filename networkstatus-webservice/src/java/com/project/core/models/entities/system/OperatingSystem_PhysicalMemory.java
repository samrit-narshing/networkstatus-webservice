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
public class OperatingSystem_PhysicalMemory {

    private Double total;
    private Double free;
    private Double used;

    public Double getUsed() {
        return total - free;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getFree() {
        return free;
    }

    public void setFree(Double free) {
        this.free = free;
    }
}
