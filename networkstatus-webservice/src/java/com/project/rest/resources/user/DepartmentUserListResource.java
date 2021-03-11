package com.project.rest.resources.user;

import org.springframework.hateoas.ResourceSupport;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samrit
 */
public class DepartmentUserListResource extends ResourceSupport {

    private List<DepartmentUserResource> departmentUserResources = new ArrayList<>();
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

    public List<DepartmentUserResource> getDepartmentUserResources() {
        return departmentUserResources;
    }

    public void setDepartmentUserResources(List<DepartmentUserResource> departmentUserResources) {
        this.departmentUserResources = departmentUserResources;
    }

}
