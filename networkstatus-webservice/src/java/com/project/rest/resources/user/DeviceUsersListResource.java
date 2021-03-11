package com.project.rest.resources.user;

import org.springframework.hateoas.ResourceSupport;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samrit
 */
public class DeviceUsersListResource extends ResourceSupport {

    private List<DeviceUserResource> deviceUsers = new ArrayList<>();
    private Long totalPages = new Long(0);
    private Long totalDocuments = new Long(0);

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

    public List<DeviceUserResource> getDeviceUsers() {
        return deviceUsers;
    }

    public void setDeviceUsers(List<DeviceUserResource> deviceUsers) {
        this.deviceUsers = deviceUsers;
    }

}
