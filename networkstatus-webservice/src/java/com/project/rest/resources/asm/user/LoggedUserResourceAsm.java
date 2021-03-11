package com.project.rest.resources.asm.user;

import com.project.core.models.entities.user.Role;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import com.project.core.models.entities.user.User;
import com.project.core.services.util.RoleList;
import com.project.rest.mvc.device.UserRESTController_ForDevice;
import com.project.rest.resources.user.LoggedUserResource;
import com.project.rest.resources.user.RoleResource;
import com.project.rest.resources.user.RoletListResource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 *
 * @author Samrit
 */
public class LoggedUserResourceAsm extends ResourceAssemblerSupport<User, LoggedUserResource> {

    public LoggedUserResourceAsm() {
        super(UserRESTController_ForDevice.class, LoggedUserResource.class);
    }

    @Override
    public LoggedUserResource toResource(User user) {
        LoggedUserResource res = new LoggedUserResource();
        res.setUserID(user.getId());
        res.setUsername(user.getUsername());
        res.setPassword(user.getPassword());

        res.setEnabled(user.isEnabled());
        res.setAccountExpiration(user.getAccountExpiration());
        res.setNeverExpire(user.isNeverExpire());
        res.setSessionTimeout(user.getSessionTimeout());

        RoleList roleList = new RoleList(new ArrayList<>(user.getRoles()));

        List<RoleResource> resList = new RoleResourceAsm().toResources(roleList.getRoles());
        RoletListResource finalRes = new RoletListResource();
        finalRes.setRoles(resList);

        Set<RoleResource> set = new HashSet(finalRes.getRoles());
        res.setRoles(set);

        res.add(linkTo(methodOn(UserRESTController_ForDevice.class).getUser(user.getId())).withSelfRel());
//        res.add(linkTo(methodOn(UserController.class).findAllBlogs(user.getId())).withRel("blogs"));
        return res;
    }

}
