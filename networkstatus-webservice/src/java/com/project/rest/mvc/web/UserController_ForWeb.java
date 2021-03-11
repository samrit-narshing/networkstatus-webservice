package com.project.rest.mvc.web;

import com.project.core.models.entities.user.PageUtil;
import com.project.core.models.entities.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.project.core.models.entities.user.User;
import com.project.core.services.exceptions.UserExistsException;
import com.project.core.services.user.RoleService;
import com.project.core.services.user.UserService;
import com.project.core.services.util.RoleList;
import com.project.core.services.util.UserList;
import com.project.rest.exceptions.ConflictException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import com.project.rest.exceptions.ForbiddenException;
import com.project.rest.mvc.common.BaseController;
import com.project.rest.resources.asm.user.RoleListResourceAsm;
import com.project.rest.resources.asm.user.UserListResourceAsm;
import com.project.rest.resources.asm.user.UserResourceAsm;
import com.project.rest.resources.user.RoleResource;
import com.project.rest.resources.user.RoletListResource;
import com.project.rest.resources.user.UserResource;
import com.project.rest.resources.user.UsersListResource;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Samrit
 */
@Controller
@RequestMapping("/rest/web_old/users")
public class UserController_ForWeb extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/listUser/{pageNumber}", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<UsersListResource> findUsersWithPagination(@PathVariable(value = "pageNumber") Integer pageNumber, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "searchText", required = false) String searchText) {

        try {
            UserList list = null;

            if (name == null) {
//            list = userService.findUserListWithAllUsers();
//            if (searchText == null || searchText.trim().equals("")) {
//                list = userService.findUserListWithSearchedUsersBySearchCriteria(pageNumber, 5);
//            } else {
//                list = userService.findUserListWithSearchedUsersBySearchCriteria(searchText, pageNumber, 5);
//            }

                if (searchText == null) {
                    searchText = "";
                }
                list = userService.findUsersWithPagination(searchText, pageNumber, 5);
            } else {
                User user = userService.findUserByUsername(name);
                if (user == null) {
                    list = new UserList(new ArrayList<User>());
                } else {
                    list = new UserList(Arrays.asList(user));
                }
            }
            UsersListResource res = new UserListResourceAsm().toResource(list);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            writeLogMessage(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/listUser/countPageNumber", method = RequestMethod.GET)
    public ResponseEntity<PageUtil> countPageNumber(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "searchText", required = false) String searchText) {
        try {
            Integer pages = 1;
            if (name == null) {
//            pages = userService.countNoOfPagesForUsersWithPagination(5) + 1;
//            if (searchText == null || searchText.trim().equals("")) {
//                pages = userService.countNoOfPagesForUsersWithPagination(5) + 1;
//            } else {
//                pages = userService.countNoOfPagesForUsersWithPagination(searchText, 5) + 1;
//            }

                if (searchText == null) {
                    searchText = "";
                }
                pages = userService.countNoOfPagesForUsersWithPagination(searchText, 5) + 1;

            } else {
                User user = userService.findUserByUsername(name);
                if (user == null) {
                    pages = 1;
                } else {
                    pages = 1;
                }
            }

            PageUtil pageUtil = new PageUtil();
            pageUtil.setPageCount(new Long(pages));

            return new ResponseEntity<>(pageUtil, HttpStatus.OK);
        } catch (Exception e) {
            writeLogMessage(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserResource> createUser(
            @RequestBody UserResource sentUser
    ) {

        //Test Principal Check
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                UserDetails details = (UserDetails) principal;
                User loggedIn = userService.findUserByUsername(details.getUsername());
                if (loggedIn.getId() != null) {

                    System.out.println(">>>>>>>>>>>>>" + loggedIn.getUsername() + ":Auhorities:" + details.getAuthorities().toString());

                    Collection<? extends GrantedAuthority> authorities = details.getAuthorities();

                    List<String> roles = new ArrayList<>();

                    for (GrantedAuthority a : authorities) {
                        roles.add(a.getAuthority());
                    }

                    for (String role : roles) {
                        System.out.println(role);
                    }

                    try {
                        User localUser = sentUser.toUser();
                        System.out.println(">>>BOOOM 1<<<" + sentUser.getRoles());

                        Set<Role> userRoles = new HashSet<>();

                        for (RoleResource role : sentUser.getRoles()) {
                            if (role.getSelected() != null && role.getSelected()) {
                                System.out.println("Selected " + role.getName() + " " + role.getSelected() + " " + role.getRoleID());
                                userRoles.add(roleService.findRole(role.getRoleID()));
                            }
                        }

                        localUser.setRoles(userRoles);

                        User createdUser = userService.createUser(localUser);
                        UserResource res = new UserResourceAsm().toResource(createdUser);
                        HttpHeaders headers = new HttpHeaders();
                        headers.setLocation(URI.create(res.getLink("self").getHref()));
                        return new ResponseEntity<>(res, headers, HttpStatus.CREATED);
                    } catch (UserExistsException exception) {
                        throw new ConflictException(exception);
                    }

                } else {
                    throw new ForbiddenException();
                }

            } catch (Exception e) {
                writeLogMessage(e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            throw new ForbiddenException();
        }
        // End of Test Principal Check

    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<UserResource> getUser(@PathVariable Long userId) {
        try {
            User user = userService.findUser(userId);
            if (user != null) {
                UserResource res = new UserResourceAsm().toResource(user);
                return new ResponseEntity<>(res, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            writeLogMessage(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public ResponseEntity<RoletListResource> findAllRoles() {
        try {
            RoleList list = roleService.findAllRoles();
            RoletListResource res = new RoleListResourceAsm().toResource(list);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            writeLogMessage(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{userId}/roles", method = RequestMethod.GET)
    public ResponseEntity<RoletListResource> getUserRoles(@PathVariable Long userId) {
        try {
            User user = userService.findUser(userId);
            RoleList list = roleService.findAllRoles();

            if (user != null && list != null) {
                RoletListResource res = new RoleListResourceAsm().toResource(list);
                List<RoleResource> roleResources = res.getRoles();
                for (RoleResource resource : roleResources) {
                    for (Role userRole : user.getRoles()) {
                        if (userRole.getId().equals(resource.getRoleID())) {
                            resource.setSelected(true);
                        }
                    }
                }

                return new ResponseEntity<>(res, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            writeLogMessage(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/delete/{userId}",
            method = RequestMethod.DELETE)
    public ResponseEntity<UserResource> deleteUser(
            @PathVariable Long userId) {
        try {
            User user = userService.deleteUser(userId);
            if (user != null) {
                UserResource res = new UserResourceAsm().toResource(user);
                return new ResponseEntity<>(res, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            writeLogMessage(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @RequestMapping(value = "/update/{userId}",
//            method = RequestMethod.POST)
//    public ResponseEntity<UserResource> updateUser(
//            @PathVariable Long userId, @RequestBody UserResource sentUserResource) {
//        System.out.println("Updating...."+sentUserResource.getPassword());
//        User updatedEntry = userService.updateUser(userId, sentUserResource.toUser());
//        if (updatedEntry != null) {
//            UserResource res = new UserResourceAsm().toResource(updatedEntry);
//            return new ResponseEntity<>(res, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<UserResource> updateUser(
            @RequestBody UserResource sentUser
    ) {

        //Test Principal Check
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                UserDetails details = (UserDetails) principal;
                User loggedIn = userService.findUserByUsername(details.getUsername());
                if (loggedIn.getId() != null) {

                    System.out.println(">>>>>>>>>>>>>" + loggedIn.getUsername() + ":Auhorities:" + details.getAuthorities().toString());

                    Collection<? extends GrantedAuthority> authorities = details.getAuthorities();

                    List<String> roles = new ArrayList<>();

                    for (GrantedAuthority a : authorities) {
                        roles.add(a.getAuthority());
                    }

                    for (String role : roles) {
                        System.out.println(role);
                    }

                    try {
                        User localUser = sentUser.toUser();
                        System.out.println(">>>BOOOM 2<<<" + sentUser.getRoles());
                        System.out.println(">>>BOOOM 2<<<" + sentUser.getUsername() + ":::" + sentUser.getPassword());
                        Set<Role> userRoles = new HashSet<>();

                        for (RoleResource role : sentUser.getRoles()) {
                            if (role.getSelected() != null && role.getSelected()) {
                                System.out.println("Selected " + role.getName() + " " + role.getSelected() + " " + role.getRoleID());
                                userRoles.add(roleService.findRole(role.getRoleID()));
                            }
                        }

                        localUser.setRoles(userRoles);

                        User createdUser = userService.updateUser(sentUser.getUserID(), localUser);
                        UserResource res = new UserResourceAsm().toResource(createdUser);
                        HttpHeaders headers = new HttpHeaders();
                        headers.setLocation(URI.create(res.getLink("self").getHref()));
                        return new ResponseEntity<>(res, headers, HttpStatus.CREATED);
                    } catch (UserExistsException exception) {
                        throw new ConflictException(exception);
                    }

                } else {
                    throw new ForbiddenException();
                }

            } catch (Exception e) {
                writeLogMessage(e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            throw new ForbiddenException();
        }
        // End of Test Principal Check

    }

}
