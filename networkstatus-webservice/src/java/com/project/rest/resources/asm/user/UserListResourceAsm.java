package com.project.rest.resources.asm.user;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import com.project.core.services.util.UserList;
import com.project.rest.mvc.web.UserController_ForWeb;
import com.project.rest.resources.user.UserResource;
import com.project.rest.resources.user.UsersListResource;

import java.util.List;

/**
 *
 * @author Samrit
 */
public class UserListResourceAsm extends ResourceAssemblerSupport<UserList, UsersListResource> {

    public UserListResourceAsm() {
        super(UserController_ForWeb.class, UsersListResource.class);
    }

    @Override
    public UsersListResource toResource(UserList userList) {
        List<UserResource> resList = new UserResourceAsm().toResources(userList.getUsers());
        UsersListResource finalRes = new UsersListResource();
        finalRes.setUsers(resList);
        finalRes.setTotalPages(userList.getTotalPages());
        finalRes.setTotalDocuments(userList.getTotalDocuments());
        return finalRes;
    }
}
