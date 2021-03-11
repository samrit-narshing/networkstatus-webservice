package com.project.rest.resources.asm.user;

import com.project.core.models.entities.user.DepartmentUser;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import com.project.core.services.util.RoleList;
import com.project.rest.mvc.web.DepartmentUserRESTController_ForWeb;
import com.project.rest.resources.user.DepartmentUserResource;
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
public class DepartmentUserResourceAsm extends ResourceAssemblerSupport<DepartmentUser, DepartmentUserResource> {

    public DepartmentUserResourceAsm() {
        super(DepartmentUserRESTController_ForWeb.class, DepartmentUserResource.class);
    }

    @Override
    public DepartmentUserResource toResource(DepartmentUser user) {
        DepartmentUserResource res = new DepartmentUserResource();
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

        RoleList roleList = new RoleList(new ArrayList<>(user.getRoles()));

        List<RoleResource> resList = new RoleResourceAsm().toResources(roleList.getRoles());
        RoletListResource finalRes = new RoletListResource();
        finalRes.setRoles(resList);

        Set<RoleResource> set = new HashSet(finalRes.getRoles());
        res.setRoles(set);

        res.add(linkTo(methodOn(DepartmentUserRESTController_ForWeb.class).getDepartmentUser(user.getId())).withSelfRel());

        res.setDepartmentUserField(user.getDepartmentUserField());
        res.setSocialID(user.getSocialID());

        res.setProfileImage(user.getProfileImage());

        res.setEntryByUserType(user.getEntryByUserType());
        res.setEntryByUsername(user.getEntryByUsername());

        res.setLastUpdateByUserType(user.getLastUpdateByUserType());
        res.setLastUpdateByUsername(user.getLastUpdateByUsername());
        res.setLastModifiedUnixTime(user.getLastModifiedUnixTime());

        res.setAdminUsername(user.getAdminUsername());
        res.setAdminOperatorUsername(user.getAdminOperatorUsername());
        res.setAdminUserType(user.getAdminOperatorUserType());
        res.setAdminOperatorUserType(user.getAdminOperatorUserType());

        return res;
    }

}
