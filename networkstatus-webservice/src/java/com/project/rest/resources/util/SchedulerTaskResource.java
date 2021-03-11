/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.resources.util;

import com.project.core.models.entities.util.SchedulerTask;
import org.springframework.hateoas.ResourceSupport;

/**
 *
 * @author Samrit
 */
public class SchedulerTaskResource extends ResourceSupport {

    private Long tableId;
    private String name;
    private Boolean active;

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public SchedulerTask toSchedulerTask() {
        SchedulerTask schedulerTask = new SchedulerTask();
        schedulerTask.setName(name);
        schedulerTask.setActive(active);
        return schedulerTask;
    }

}
