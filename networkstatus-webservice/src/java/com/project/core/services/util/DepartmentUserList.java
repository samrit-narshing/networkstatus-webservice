package com.project.core.services.util;

import com.project.core.models.entities.user.DepartmentUser;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samrit
 */
public class DepartmentUserList {

    private Long totalPages = new Long(0);
    private Long totalDocuments = new Long(0);

    private List<DepartmentUser> departmentUsers = new ArrayList<>();

    public DepartmentUserList(List<DepartmentUser> list) {
        this.departmentUsers = list;
    }

    public DepartmentUserList(List<DepartmentUser> list, Long totalDocuments, Long totalPages) {
        this.departmentUsers = list;
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

    public List<DepartmentUser> getDepartmentUsers() {
        return departmentUsers;
    }

    public void setDepartmentUsers(List<DepartmentUser> departmentUsers) {
        this.departmentUsers = departmentUsers;
    }

}
