package com.project.rest.resources.asm.network;

import com.project.core.models.entities.network.RoleInNetworkGroup;
import com.project.rest.mvc.web.NetworkGroupRESTController_ForWeb;
import com.project.rest.resources.asm.user.RoleResourceAsm;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import com.project.rest.resources.network.RoleInNetworkGroupResource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 *
 * @author Samrit
 */
public class RoleInNetworkGroupResourceAsm extends ResourceAssemblerSupport<RoleInNetworkGroup, RoleInNetworkGroupResource> {

    public RoleInNetworkGroupResourceAsm() {
        super(NetworkGroupRESTController_ForWeb.class, RoleInNetworkGroupResource.class);
    }

    @Override
    public RoleInNetworkGroupResource toResource(RoleInNetworkGroup roleInNetworkGroup) {
        RoleInNetworkGroupResource res = new RoleInNetworkGroupResource();
        res.setRoleInNetworkGroupID(roleInNetworkGroup.getId());
        res.setRoleResource(new RoleResourceAsm().toResource(roleInNetworkGroup.getRole()));
        res.add(linkTo(methodOn(NetworkGroupRESTController_ForWeb.class).getRoleInNetworkGroup(roleInNetworkGroup.getId())).withSelfRel());
        System.out.println("2010 >>>>>>> "+res.getLinks().get(0));
        return res;
    }

}
