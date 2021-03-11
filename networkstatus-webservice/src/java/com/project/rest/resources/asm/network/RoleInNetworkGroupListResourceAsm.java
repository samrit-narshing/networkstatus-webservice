package com.project.rest.resources.asm.network;

import com.project.core.services.util.RoleInNetworkGroupList;
import com.project.rest.mvc.web.NetworkGroupRESTController_ForWeb;
import com.project.rest.resources.network.RoleInNetworkGroupListResource;
import com.project.rest.resources.network.RoleInNetworkGroupResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.List;

/**
 *
 * @author Samrit
 */
public class RoleInNetworkGroupListResourceAsm extends ResourceAssemblerSupport<RoleInNetworkGroupList, RoleInNetworkGroupListResource> {

    public RoleInNetworkGroupListResourceAsm() {
        super(NetworkGroupRESTController_ForWeb.class, RoleInNetworkGroupListResource.class);
    }

    @Override
    public RoleInNetworkGroupListResource toResource(RoleInNetworkGroupList userList) {
        List<RoleInNetworkGroupResource> resList = new RoleInNetworkGroupResourceAsm().toResources(userList.getRoleInNetworkGroup());
        RoleInNetworkGroupListResource finalRes = new RoleInNetworkGroupListResource();
        finalRes.setRoleInNetworkGroupResources(resList);
        finalRes.setTotalPages(userList.getTotalPages());
        finalRes.setTotalDocuments(userList.getTotalDocuments());
        return finalRes;
    }
}
