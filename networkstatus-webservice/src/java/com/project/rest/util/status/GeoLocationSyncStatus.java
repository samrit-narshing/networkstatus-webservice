package com.project.rest.util.status;

import java.io.Serializable;

/**
 *
 * @author Samrit
 */
public class GeoLocationSyncStatus implements Serializable {

    private Long scheduleTableId = 0L;
    private String name = "";
    private Boolean enabled = false;
    private Long insert = 0L;
    private Long update = 0L;
    private Long delete = 0L;
    private Long error = 0L;

    public Long getScheduleTableId() {
        return scheduleTableId;
    }

    public void setScheduleTableId(Long scheduleTableId) {
        this.scheduleTableId = scheduleTableId;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Long getInsert() {
        return insert;
    }

    public void setInsert(Long insert) {
        this.insert = insert;
    }

    public Long getUpdate() {
        return update;
    }

    public void setUpdate(Long update) {
        this.update = update;
    }

    public Long getDelete() {
        return delete;
    }

    public void setDelete(Long delete) {
        this.delete = delete;
    }

    public Long getError() {
        return error;
    }

    public void setError(Long error) {
        this.error = error;
    }

}
