package com.project.rest.util.status;

import java.io.Serializable;

/**
 *
 * @author Samrit
 */
public class FirebaseRealtimeDatabaseCleanScheduleStatus implements Serializable {

    private Long scheduleTableId = 0L;
    private String name = "";
    private Boolean enabled = false;
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

    public Long getError() {
        return error;
    }

    public void setError(Long error) {
        this.error = error;
    }

}
