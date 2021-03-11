package com.project.rest.resources.asm.user;

import com.project.core.services.util.DepartmentUserList;
import com.project.rest.mvc.web.DepartmentUserRESTController_ForWeb;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import com.project.rest.resources.user.DepartmentUserListResource;
import com.project.rest.resources.user.DepartmentUserResource;

import java.util.List;

/**
 *
 * @author Samrit
 */
public class DepartmentUserListResourceAsm extends ResourceAssemblerSupport<DepartmentUserList, DepartmentUserListResource> {

    public DepartmentUserListResourceAsm() {
        super(DepartmentUserRESTController_ForWeb.class, DepartmentUserListResource.class);
    }

    @Override
    public DepartmentUserListResource toResource(DepartmentUserList userList) {
        List<DepartmentUserResource> resList = new DepartmentUserResourceAsm().toResources(userList.getDepartmentUsers());
        DepartmentUserListResource finalRes = new DepartmentUserListResource();
        finalRes.setDepartmentUserResources(resList);
        finalRes.setTotalPages(userList.getTotalPages());
        finalRes.setTotalDocuments(userList.getTotalDocuments());
        return finalRes;
    }
}
