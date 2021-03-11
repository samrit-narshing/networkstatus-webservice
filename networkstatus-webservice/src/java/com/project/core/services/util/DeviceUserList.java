package com.project.core.services.util;

import com.project.core.models.entities.user.DeviceUser;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samrit
 */
public class DeviceUserList {
    private Long totalPages = new Long(0);
    private Long totalDocuments = new Long(0);

    private List<DeviceUser> deviceUsers = new ArrayList<>();

    public DeviceUserList(List<DeviceUser> deviceUsers) {
        this.deviceUsers = deviceUsers;
    }

    public DeviceUserList(List<DeviceUser> deviceUsers, Long totalDocuments, Long totalPages) {
        this.deviceUsers = deviceUsers;
        this.totalPages = totalPages;
        this.totalDocuments = totalDocuments;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalDocuments() {
        return totalDocuments;
    }

    public void setTotalDocuments(Long totalDocuments) {
        this.totalDocuments = totalDocuments;
    }

    public List<DeviceUser> getDeviceUsers() {
        return deviceUsers;
    }

    public void setDeviceUsers(List<DeviceUser> deviceUsers) {
        this.deviceUsers = deviceUsers;
    }

}
