/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.mvc.device;

import com.project.core.models.entities.user.Role;
import com.project.core.models.entities.user.User;
import com.project.core.services.user.RoleService;
import com.project.core.services.user.UserService;
import com.project.core.util.CryptUtil;
import com.project.core.util.DateConverter;
import com.project.core.util.SHA1;
import com.project.rest.exceptions.BadRequestException;
import com.project.rest.exceptions.ConflictException;
import com.project.rest.exceptions.InternalServertException;
import com.project.rest.exceptions.NotFoundException;
import com.project.rest.resources.asm.user.UserResourceAsm;
import com.project.rest.resources.user.UserResource;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author samri
 */
@Controller
@RequestMapping("/rest/device/initialize")
public class InitializeRESTController_ForDevice {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/data/reset",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<UserResource> isReInitializeInitialData() {
        try {
            initializeRoles();
            User modifiedUser = createSuperUser();
            User readUser = userService.findUserByUsername(modifiedUser.getUsername());
            System.out.println(readUser.getUsername() + "---");
            UserResource res = new UserResourceAsm().toResource(readUser);
            System.out.println("---" + res.getUsername());
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(res.getLink("self").getHref()));
            return new ResponseEntity<>(res, headers, HttpStatus.CREATED);
        } catch (NotFoundException exception) {
            throw new NotFoundException(exception);
        } catch (BadRequestException exception) {
            throw new BadRequestException(exception);
        } catch (ConflictException exception) {
            throw new ConflictException(exception);
        } catch (Exception e) {
            throw new InternalServertException(e);
        }

    }

//       @RequestMapping(value = "/data/reset",
//            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
//    public ResponseEntity<Boolean> isReInitializeInitialData() throws Exception {
//        initializeRoles();
//        createSuperUser();
//        return new ResponseEntity<>(true, HttpStatus.OK);
//    }
    private User createSuperUser() throws Exception {

        //sha password encryption
        String username = "super-user";
        String plainPassword = "super-user";
        CryptUtil cryptUtil = new CryptUtil();
        String encryptedPassword = (cryptUtil.asHex(SHA1.SHA1(plainPassword)));

        User readUser = userService.findUserByUsername(username);

        User modifiedUser = new User();

        if (readUser == null) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(encryptedPassword);
            newUser.setFirstName("Anonymous");
            newUser.setMiddleName("Anonymous");
            newUser.setLastName("Anonymous");
            newUser.setAddress1("Anonymous");
            newUser.setAddress2("Anonymous");
            newUser.setEmail("seemsserver.siamsecure@gmail.com");

            newUser.setGender("person-male");

            newUser.setMobileNo("12345678");
            newUser.setPhoneNo("87654321");

            newUser.setNeverExpire(true);
            newUser.setPasswordExpire(false);
            newUser.setEnabled(true);

            newUser.setLastModifiedUnixTime(new DateConverter().getCurrentConvertedDateAndTimeInUnixDate());
            newUser.setSessionTimeout("28800");
            newUser.setAccountExpiration("2020-07-06");

            Set<Role> roles = new HashSet<>();
            roles.add(roleService.findRole(1L));
            roles.add(roleService.findRole(2L));
            roles.add(roleService.findRole(3L));

            newUser.setRoles(roles);

            modifiedUser = userService.createUser(newUser);

        } else {

            readUser.setPassword(encryptedPassword);

            readUser.setNeverExpire(true);
            readUser.setPasswordExpire(false);
            readUser.setEnabled(true);

            readUser.setLastModifiedUnixTime(new DateConverter().getCurrentConvertedDateAndTimeInUnixDate());
            readUser.setAccountExpiration("2020-07-06");

            Set<Role> roles = new HashSet<>();
            roles.add(roleService.findRole(1L));
            roles.add(roleService.findRole(2L));
            roles.add(roleService.findRole(3L));

            readUser.setRoles(roles);

            modifiedUser = userService.updateUser(readUser.getId(), readUser);
        }

        return modifiedUser;

    }

    private void initializeRoles() {
        try {
            // For ADMINISTRATOR Role
            long adminRoleID = 1;
            Role findAdminRole = roleService.findRole(adminRoleID);
            Role adminRole = new Role();
            adminRole.setId(adminRoleID);
            adminRole.setDescription("ADMINISTRATOR");
            adminRole.setName("ROLE_ADMINISTRATOR");
            adminRole.setEnabled(true);
            adminRole.setUsers(new HashSet<>());
            if (findAdminRole == null) {
                roleService.createRole(adminRole);
            } else {
                roleService.updateRole(adminRoleID, adminRole);
            }
            // End For ADMINISTRATOR Role

            // For USER Role
            long userRoleID = 2;
            Role findUserRole = roleService.findRole(userRoleID);
            Role userRole = new Role();
            userRole.setId(userRoleID);
            userRole.setDescription("USER");
            userRole.setName("ROLE_USER");
            userRole.setEnabled(true);
            userRole.setUsers(new HashSet<>());
            if (findUserRole == null) {
                roleService.createRole(userRole);
            } else {
                roleService.updateRole(userRoleID, userRole);
            }
            // End For USER Role

            // For DEVICE Role
            long deviceRoleID = 3;
            Role findDeviceRole = roleService.findRole(deviceRoleID);
            Role deviceRole = new Role();
            deviceRole.setId(deviceRoleID);
            deviceRole.setDescription("DEVICE");
            deviceRole.setName("ROLE_DEVICE");
            deviceRole.setEnabled(true);
            deviceRole.setUsers(new HashSet<>());
            if (findDeviceRole == null) {
                roleService.createRole(deviceRole);
            } else {
                roleService.updateRole(deviceRoleID, deviceRole);
            }
            // End For DEVICE Role

            // For DEPARTMENT USER Role
            long departmentUserRoleID = 4;
            Role findDepartmentUserRole = roleService.findRole(departmentUserRoleID);
            Role departmentUserRole = new Role();
            departmentUserRole.setId(departmentUserRoleID);
            departmentUserRole.setDescription("DEPARTMENT USER");
            departmentUserRole.setName("ROLE_DEPARTMENT_USER");
            departmentUserRole.setEnabled(true);
            departmentUserRole.setUsers(new HashSet<>());
            if (findDepartmentUserRole == null) {
                roleService.createRole(departmentUserRole);
            } else {
                roleService.updateRole(departmentUserRoleID, departmentUserRole);
            }
            // End For DEPARTMENT_USER Role
            roleService.initializeHibernateCounterForRole();
        } catch (NotFoundException exception) {
            throw new NotFoundException(exception);
        } catch (BadRequestException exception) {
            throw new BadRequestException(exception);
        } catch (ConflictException exception) {
            throw new ConflictException(exception);
        } catch (Exception e) {
            throw new InternalServertException(e);
        }

    }

}
