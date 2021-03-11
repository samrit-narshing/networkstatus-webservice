package com.project.core.services.util;


import com.project.core.models.entities.network.RoleInNetworkGroup;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samrit
 */
public class RoleInNetworkGroupList {

    private Long totalPages = new Long(0);
    private Long totalDocuments = new Long(0);

    private List<RoleInNetworkGroup> roleInNetworkGroup = new ArrayList<>();

    public RoleInNetworkGroupList(List<RoleInNetworkGroup> list) {
        this.roleInNetworkGroup = list;
    }

    public RoleInNetworkGroupList(List<RoleInNetworkGroup> list, Long totalDocuments, Long totalPages) {
        this.roleInNetworkGroup = list;
        this.totalPages = totalPages;
        this.totalDocuments = totalDocuments;
    }

    public List<RoleInNetworkGroup> getRoleInNetworkGroup() {
        return roleInNetworkGroup;
    }

    public void setRoleInNetworkGroup(List<RoleInNetworkGroup> roleInNetworkGroup) {
        this.roleInNetworkGroup = roleInNetworkGroup;
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
