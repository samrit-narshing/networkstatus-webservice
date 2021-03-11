package com.project.rest.resources.asm.user;

import com.project.core.models.entities.user.Role;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import com.project.core.models.entities.user.User;
import com.project.core.services.util.RoleList;
import com.project.rest.mvc.device.UserRESTController_ForDevice;
import com.project.rest.resources.user.RoleResource;
import com.project.rest.resources.user.RoletListResource;
import com.project.rest.resources.user.UserResource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 *
 * @author Samrit
 */
public class UserResourceAsm extends ResourceAssemblerSupport<User, UserResource> {

    public UserResourceAsm() {
        super(UserRESTController_ForDevice.class, UserResource.class);
    }

    @Override
    public UserResource toResource(User user) {
        UserResource res = new UserResource();
        res.setUserID(user.getId());
        res.setUsername(user.getUsername());
        res.setPassword(user.getPassword());

        res.setEnabled(user.isEnabled());
        res.setAccountExpiration(user.getAccountExpiration());
        res.setNeverExpire(user.isNeverExpire());
        res.setSessionTimeout(user.getSessionTimeout());

        res.setAddress1(user.getAddress1());
        res.setAddress2(user.getAddress2());
        res.setEmail(user.getEmail());
        res.setMobileNo(user.getMobileNo());
        res.setPhoneNo(user.getPhoneNo());
        res.setGender(user.getGender());

        res.setFirstName(user.getFirstName());
        res.setMiddleName(user.getMiddleName());
        res.setLastName(user.getLastName());
        res.setUserType(user.getUserType());
        res.setPasswordExpire(user.isPasswordExpire());

        RoleList roleList = new RoleList(new ArrayList<>(user.getRoles()));

        List<RoleResource> resList = new RoleResourceAsm().toResources(roleList.getRoles());
        RoletListResource finalRes = new RoletListResource();
        finalRes.setRoles(resList);

        Set<RoleResource> set = new HashSet(finalRes.getRoles());
        res.setRoles(set);

        res.setLastModifiedUnixTime(user.getLastModifiedUnixTime());

        res.add(linkTo(methodOn(UserRESTController_ForDevice.class).getUser(user.getId())).withSelfRel());
//        res.add(linkTo(methodOn(UserController.class).findAllBlogs(user.getId())).withRel("blogs"));
        res.setProfileImage(user.getProfileImage());

        res.setEntryByUserType(user.getEntryByUserType());
        res.setEntryByUsername(user.getEntryByUsername());
        
        res.setLastUpdateByUserType(user.getLastUpdateByUserType());
        res.setLastUpdateByUsername(user.getLastUpdateByUsername());
        res.setLastModifiedUnixTime(user.getLastModifiedUnixTime());

        res.setAdminUsername(user.getAdminUsername());
        res.setAdminOperatorUsername(user.getAdminOperatorUsername());

        res.setAdminUserType(user.getAdminUserType());
        res.setAdminOperatorUserType(user.getAdminOperatorUserType());
        res.setSyncronizedVersion(user.getSyncronizedVersion());
        return res;
    }

}
