package com.project.rest.resources.asm.user;

import com.project.core.services.util.RoleList;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import com.project.rest.mvc.web.UserController_ForWeb;
import com.project.rest.resources.user.RoleResource;
import com.project.rest.resources.user.RoletListResource;

import java.util.List;

/**
 *
 * @author Samrit
 */
public class RoleListResourceAsm extends ResourceAssemblerSupport<RoleList, RoletListResource> {


    public RoleListResourceAsm() {
        super(UserController_ForWeb.class, RoletListResource.class);
    }

    @Override
    public RoletListResource toResource(RoleList roleList) {
        List<RoleResource> resList = new RoleResourceAsm().toResources(roleList.getRoles());
        RoletListResource finalRes = new RoletListResource();
        finalRes.setRoles(resList);
        return finalRes;
    }
}
