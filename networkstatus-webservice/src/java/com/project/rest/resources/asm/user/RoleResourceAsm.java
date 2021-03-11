package com.project.rest.resources.asm.user;

import com.project.core.models.entities.user.Role;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import com.project.rest.mvc.device.UserRESTController_ForDevice;
import com.project.rest.resources.user.RoleResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 *
 * @author Samrit
 */
public class RoleResourceAsm extends ResourceAssemblerSupport<Role, RoleResource> {
    
    public RoleResourceAsm() {
        super(UserRESTController_ForDevice.class, RoleResource.class);
    }
    
    @Override
    public RoleResource toResource(Role role) {
        RoleResource res = new RoleResource();
        res.setRoleID(role.getId());
        res.setName(role.getName());
        res.setDescription(role.getDescription());
        res.setEnabled(role.isEnabled());
        res.add(linkTo(methodOn(UserRESTController_ForDevice.class).getRole(role.getId())).withSelfRel());
//        res.add(linkTo(methodOn(UserController.class).findAllBlogs(user.getId())).withRel("blogs"));
        return res;
    }
    
}
