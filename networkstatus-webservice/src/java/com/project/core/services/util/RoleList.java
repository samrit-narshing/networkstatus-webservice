package com.project.core.services.util;

import com.project.core.models.entities.user.Role;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samrit
 */
public class RoleList {

    private Long totalPages = new Long(0);
    private Long totalDocuments = new Long(0);

    private List<Role> roles = new ArrayList<>();

    public RoleList(List<Role> list) {
        this.roles = list;
    }

    public RoleList(List<Role> list, Long totalDocuments, Long totalPages) {
        this.roles = list;
        this.totalPages = totalPages;
        this.totalDocuments = totalDocuments;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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

}
