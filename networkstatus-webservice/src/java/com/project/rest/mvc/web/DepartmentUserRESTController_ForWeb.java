/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.mvc.web;

import com.project.core.models.entities.common.ExceptionInfo;
import com.project.core.models.entities.user.DepartmentUser;
import com.project.core.models.entities.user.Role;
import com.project.core.models.entities.user.User;
import com.project.core.services.user.DepartmentUserService;
import com.project.core.services.user.RoleService;
import com.project.core.services.user.UserService;
import com.project.core.services.util.DepartmentUserList;
import com.project.core.services.util.RoleList;
import com.project.core.util.DateConverter;
import com.project.core.util.Log4jUtil;
import com.project.core.util.Validate;
import com.project.rest.exceptions.BadRequestException;
import com.project.rest.exceptions.ConflictException;
import com.project.rest.exceptions.ForbiddenException;
import com.project.rest.exceptions.InternalServertException;
import com.project.rest.exceptions.NotFoundException;
import com.project.rest.mvc.common.BaseController;
//import com.project.rest.resources.asm.user.LoggedUserResourceAsm;
import com.project.rest.resources.asm.user.DepartmentUserListResourceAsm;
import com.project.rest.resources.asm.user.DepartmentUserResourceAsm;
import com.project.rest.resources.asm.user.RoleListResourceAsm;
import com.project.rest.resources.asm.user.RoleResourceAsm;
//import com.project.rest.resources.asm.user.UserResourceAsm;
//import com.project.rest.resources.user.LoggedUserResource;
import com.project.rest.resources.user.DepartmentUserListResource;
import com.project.rest.resources.user.DepartmentUserResource;
import com.project.rest.resources.user.RoleResource;
import com.project.rest.resources.user.RoletListResource;
//import com.project.rest.resources.user.UserResource; 
import com.project.rest.util.Global;
import com.project.rest.util.GlobalExceptionMessage;
import com.project.rest.util.searchcriteria.user.DepartmentUserSearchCriteria;
import java.net.URI;
import java.util.HashSet;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author samri
 */
@Controller
@RequestMapping("/rest/web/departmentuser")
public class DepartmentUserRESTController_ForWeb extends BaseController {

    @Autowired
    private DepartmentUserService departmentUserService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    private final String UNIQUE_APPLICATION_ID = "LALITPUR@MANGALBAZAR#5536666";

    @RequestMapping(value = "/list",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> findDepartmentUsersBySearchCriteria(@RequestBody(required = false) DepartmentUserSearchCriteria searchCriteria) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {

            try {
                UserDetails details = (UserDetails) principal;
                User loggedUser = userService.findUserByUsername(details.getUsername());
                System.out.println("ENTERING......");

                if (searchCriteria == null) {
                    searchCriteria = new DepartmentUserSearchCriteria();
                }

                if (searchCriteria.getLimitResult() <= 0) {
                    searchCriteria.setLimitResult(10);
                }
                DepartmentUserList list = departmentUserService.findDepartmentUserListBySearchCriteria(searchCriteria, loggedUser);

                DepartmentUserListResource res = new DepartmentUserListResourceAsm().toResource(list);

                return new ResponseEntity<>(res, HttpStatus.OK);
            } catch (NotFoundException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
//                throw new NotFoundException(exception);
            } catch (BadRequestException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
//                throw new BadRequestException(exception);
            } catch (ConflictException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
//                throw new ConflictException(exception);
            } catch (Exception exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
//                throw new InternalServertException(e);
            }
        } else {
            ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN.value(), GlobalExceptionMessage.FORBIDDEN_APPLICATION_CODE, GlobalExceptionMessage.FORBIDDEN_MESSAGE, false, "N/A");
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
//            throw new ForbiddenException();
        }

    }

    @RequestMapping(value = "/create",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> createDepartmentUser(@RequestBody(required = false) DepartmentUserResource sentDepartmentUserResource
    ) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {

            try {

                boolean isValidated = true;

                Validate validate = new Validate();

                if (validate.isEmptyString(sentDepartmentUserResource.getFirstName()) || (!validate.isValidDefaultTextLength(sentDepartmentUserResource.getFirstName()))) {
                    isValidated = false;
                }

                if (!validate.isEmptyString(sentDepartmentUserResource.getMiddleName())) {
                    if (!validate.isValidDefaultTextLength(sentDepartmentUserResource.getMiddleName())) {
                        isValidated = false;
                    }
                }

                if (validate.isEmptyString(sentDepartmentUserResource.getLastName()) || (!validate.isValidDefaultTextLength(sentDepartmentUserResource.getLastName()))) {
                    isValidated = false;
                }

                if (!validate.isEmptyString(sentDepartmentUserResource.getAddress1())) {
                    if (!validate.isValidDefaultTextLength(sentDepartmentUserResource.getAddress1())) {
                        isValidated = false;
                    }
                }

                if (!validate.isEmptyString(sentDepartmentUserResource.getAddress2())) {
                    if (!validate.isValidDefaultTextLength(sentDepartmentUserResource.getAddress2())) {
                        isValidated = false;
                    }
                }

                if (sentDepartmentUserResource.getRoles() == null) {
                    sentDepartmentUserResource.setRoles(new HashSet<>());
                }

                if (!(validate.isValidRADIUS_Timeout(sentDepartmentUserResource.getSessionTimeout()))) {
                    isValidated = false;
                }
                if (!(validate.isValidCalendarDate(sentDepartmentUserResource.getAccountExpiration()))) {
                    isValidated = false;
                }

                if (sentDepartmentUserResource.getEmail().trim().equals("") || !(validate.isValidEmail(sentDepartmentUserResource.getEmail())) || (!validate.isValidDefaultTextLength(sentDepartmentUserResource.getEmail()))) {
                    isValidated = false;
                }

                if (!sentDepartmentUserResource.getPhoneNo().equals("") && !(validate.isValidPhoneNumber(sentDepartmentUserResource.getPhoneNo()))) {
                    isValidated = false;
                }

                if (!sentDepartmentUserResource.getMobileNo().equals("") && !(validate.isValidPhoneNumber(sentDepartmentUserResource.getMobileNo()))) {
                    isValidated = false;
                }

                if (validate.isEmptyString(sentDepartmentUserResource.getSocialID()) || (!validate.isValidDefaultTextLength(sentDepartmentUserResource.getSocialID()))) {
                    isValidated = false;
                }

                if (validate.isEmptyString(sentDepartmentUserResource.getDepartmentUserField()) || (!validate.isValidDefaultTextLength(sentDepartmentUserResource.getDepartmentUserField()))) {
                    isValidated = false;
                }

                if (!isValidated) {
                    throw new BadRequestException();
                }

                UserDetails details = (UserDetails) principal;
                User loggedUser = userService.findUserByUsername(details.getUsername());
                DepartmentUser departmentUser = null;
                String newUsername = RandomStringUtils.randomAlphanumeric(15);
                //sha password encryption
//            CryptUtil cryptUtil = new CryptUtil();
//            String encryptedPassword = (cryptUtil.asHex(SHA1.SHA1("P@ssw0rd")));

                sentDepartmentUserResource.setUsername(newUsername);
//          sentParentResource.setPassword(encryptedPassword);

//Adding Parent Role
                RoleResource departmentUserRole = new RoleResourceAsm().toResource(roleService.findRole(4L));
                sentDepartmentUserResource.getRoles().add(departmentUserRole);
//End of adding Parent Role  

                sentDepartmentUserResource.setEntryByUserType(loggedUser.getUserType());
                sentDepartmentUserResource.setEntryByUsername(loggedUser.getUsername());
                sentDepartmentUserResource.setLastUpdateByUserType(loggedUser.getUserType());
                sentDepartmentUserResource.setLastUpdateByUsername(loggedUser.getUsername());
                sentDepartmentUserResource.setLastModifiedUnixTime(DateConverter.getCurrentConvertedDateAndTimeInUnixDate());

                sentDepartmentUserResource.setAdminUsername(loggedUser.getAdminUsername());
                sentDepartmentUserResource.setAdminUserType(loggedUser.getAdminUserType());
                sentDepartmentUserResource.setAdminOperatorUsername(Global.SYSTEM_DEFAULT_NO_OPERATOR_USER);
                sentDepartmentUserResource.setAdminOperatorUserType(Global.SYSTEM_DEFAULT_NO_OPERATOR_USER_TYPE);

                departmentUser = sentDepartmentUserResource.toDepartmentUser();

                DepartmentUser createdUser = departmentUserService.createDepartmentUser(departmentUser);
                String newUserID = "dep-" + createdUser.getId();
                createdUser.setUsername(newUserID);
                DepartmentUser createdAndUpdatedUser = departmentUserService.updateDepartmentUserUsername(createdUser.getId(), createdUser);

                System.out.println(createdAndUpdatedUser.getUsername() + "---");
                DepartmentUserResource res = new DepartmentUserResourceAsm().toResource(createdAndUpdatedUser);
                System.out.println("---" + res.getUsername());
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(URI.create(res.getLink("self").getHref()));
                return new ResponseEntity<>(res, headers, HttpStatus.CREATED);
            } catch (NotFoundException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
//                throw new NotFoundException(exception);
            } catch (BadRequestException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
//                throw new BadRequestException(exception);
            } catch (ConflictException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
//                throw new ConflictException(exception);
            } catch (Exception exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
//                throw new InternalServertException(e);
            }
        } else {
            ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN.value(), GlobalExceptionMessage.FORBIDDEN_APPLICATION_CODE, GlobalExceptionMessage.FORBIDDEN_MESSAGE, false, "N/A");
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
//            throw new ForbiddenException();
        }

    }

    @RequestMapping(value = "/role/list",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> findAllRoles() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                RoleList roleList = roleService.findAllRolesForDepartmentUser();
                RoletListResource res = new RoleListResourceAsm().toResource(roleList);
                return new ResponseEntity<>(res, HttpStatus.OK);
            } catch (NotFoundException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
//                throw new NotFoundException(exception);
            } catch (BadRequestException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
//                throw new BadRequestException(exception);
            } catch (ConflictException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
//                throw new ConflictException(exception);
            } catch (Exception exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
//                throw new InternalServertException(e);
            }
        } else {
            ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN.value(), GlobalExceptionMessage.FORBIDDEN_APPLICATION_CODE, GlobalExceptionMessage.FORBIDDEN_MESSAGE, false, "N/A");
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
//            throw new ForbiddenException();
        }
    }

    @RequestMapping(value = "/role/find/{id}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> getRole(
            @PathVariable Long id
    ) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                Role role = roleService.findRole(id);
                if (role != null) {
                    RoleResource res = new RoleResourceAsm().toResource(role);
                    return new ResponseEntity<>(res, HttpStatus.OK);
                } else {
                    throw new NotFoundException();
                }
            } catch (NotFoundException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
//                throw new NotFoundException(exception);
            } catch (BadRequestException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
//                throw new BadRequestException(exception);
            } catch (ConflictException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
//                throw new ConflictException(exception);
            } catch (Exception exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
//                throw new InternalServertException(e);
            }
        } else {
            ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN.value(), GlobalExceptionMessage.FORBIDDEN_APPLICATION_CODE, GlobalExceptionMessage.FORBIDDEN_MESSAGE, false, "N/A");
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
//            throw new ForbiddenException();
        }
    }

    @RequestMapping(value = "/find/{id}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> getDepartmentUser(
            @PathVariable Long id
    ) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                DepartmentUser departmentUser = departmentUserService.findDepartmentUser(id);
                if (departmentUser != null) {
                    DepartmentUserResource res = new DepartmentUserResourceAsm().toResource(departmentUser);
                    return new ResponseEntity<>(res, HttpStatus.OK);
                } else {
                    throw new NotFoundException();
                }
            } catch (NotFoundException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
//                throw new NotFoundException(exception);
            } catch (BadRequestException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
//                throw new BadRequestException(exception);
            } catch (ConflictException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
//                throw new ConflictException(exception);
            } catch (Exception exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
//                throw new InternalServertException(e);
            }
        } else {
            ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN.value(), GlobalExceptionMessage.FORBIDDEN_APPLICATION_CODE, GlobalExceptionMessage.FORBIDDEN_MESSAGE, false, "N/A");
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
//            throw new ForbiddenException();
        }
    }

    @RequestMapping(value = "/find/username/{username}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> getDepartmentUserResourceByName(
            @PathVariable String username
    ) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                DepartmentUser departmentUser = departmentUserService.findDepartmentUserByUsername(username);
                if (departmentUser != null) {
                    DepartmentUserResource res = new DepartmentUserResourceAsm().toResource(departmentUser);
                    return new ResponseEntity<>(res, HttpStatus.OK);
                } else {
                    throw new NotFoundException();
                }
            } catch (NotFoundException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
//                throw new NotFoundException(exception);
            } catch (BadRequestException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
//                throw new BadRequestException(exception);
            } catch (ConflictException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
//                throw new ConflictException(exception);
            } catch (Exception exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
//                throw new InternalServertException(e);
            }
        } else {
            ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN.value(), GlobalExceptionMessage.FORBIDDEN_APPLICATION_CODE, GlobalExceptionMessage.FORBIDDEN_MESSAGE, false, "N/A");
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
//            throw new ForbiddenException();
        }
    }

//    @RequestMapping(value = "/find/authcheck/encrypted",
//            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
//    public ResponseEntity<LoggedUserResource> getProfessorResourceByUserNameAndPasswordForPublicValidation(@RequestParam(value = "username", required = true) String username, @RequestParam(value = "password", required = true) String password) {
//        try {
//            System.out.println("id----" + username + "_____" + password);
//            DepartmentUser departmentUser = null;
//            if (password.equals(UNIQUE_APPLICATION_ID)) {
//                departmentUser = departmentUserService.findDepartmentUserByUsername(username);
//            } else {
//                departmentUser = departmentUserService.findDepartmentUserByUsernameAndPassword(username, password);
//            }
//
//            if (departmentUser != null) {
//                if (departmentUser.isEnabled()) {
//                    if (departmentUser.isNeverExpire()) {
//                        LoggedUserResource res = new LoggedUserResourceAsm().toResource(departmentUser);
//                        return new ResponseEntity<>(res, HttpStatus.OK);
//                    } else {
//                        long accountExpirationDate = DateConverter.getFirstHourOfDate(departmentUser.getAccountExpiration());
//                        long currentDate = DateConverter.getCurrentConvertedDateInUnixDate();
//                        if (accountExpirationDate > currentDate) {
//                            LoggedUserResource res = new LoggedUserResourceAsm().toResource(departmentUser);
//                            return new ResponseEntity<>(res, HttpStatus.OK);
//                        } else {
//                            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//                        }
//                    }
//                } else {
//                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//                }
//
//            } else {
//                throw new NotFoundException();
//            }
//        } catch (NotFoundException exception) {
//            throw new NotFoundException(exception);
//        } catch (BadRequestException exception) {
//            throw new BadRequestException(exception);
//        } catch (ConflictException exception) {
//            throw new ConflictException(exception);
//        } catch (Exception e) {
//            throw new InternalServertException(e);
//        }
//
//    }
//
//    @RequestMapping(value = "/find/username/{username}/password/{password}",
//            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
//    public ResponseEntity<DepartmentUserResource> getDepartmentUserResourceByUserNameAndPassword(
//            @PathVariable String username, @PathVariable String password
//    ) {
//        try {
//            System.out.println("id----" + username);
//            DepartmentUser departmentUser = departmentUserService.findDepartmentUserByUsernameAndPassword(username, password);
//            if (departmentUser != null) {
//                DepartmentUserResource res = new DepartmentUserResourceAsm().toResource(departmentUser);
//                return new ResponseEntity<>(res, HttpStatus.OK);
//            } else {
//                throw new NotFoundException();
//            }
//        } catch (NotFoundException exception) {
//            throw new NotFoundException(exception);
//        } catch (BadRequestException exception) {
//            throw new BadRequestException(exception);
//        } catch (ConflictException exception) {
//            throw new ConflictException(exception);
//        } catch (Exception e) {
//            throw new InternalServertException(e);
//        }
//
//    }
    @RequestMapping(value = "/update/{id}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> updateDepartmentUserDetails(
            @PathVariable Long id, @RequestBody DepartmentUserResource sentDepartmentUserResource) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {

            try {

                // Validation Start
                boolean isValidated = true;
                Validate validate = new Validate();

                if (validate.isEmptyString(sentDepartmentUserResource.getFirstName()) || (!validate.isValidDefaultTextLength(sentDepartmentUserResource.getFirstName()))) {
                    isValidated = false;
                }

                if (!validate.isEmptyString(sentDepartmentUserResource.getMiddleName())) {
                    if (!validate.isValidDefaultTextLength(sentDepartmentUserResource.getMiddleName())) {
                        isValidated = false;
                    }
                }

                if (validate.isEmptyString(sentDepartmentUserResource.getLastName()) || (!validate.isValidDefaultTextLength(sentDepartmentUserResource.getLastName()))) {
                    isValidated = false;
                }

                if (!validate.isEmptyString(sentDepartmentUserResource.getAddress1())) {
                    if (!validate.isValidDefaultTextLength(sentDepartmentUserResource.getAddress1())) {
                        isValidated = false;
                    }
                }

                if (!validate.isEmptyString(sentDepartmentUserResource.getAddress2())) {
                    if (!validate.isValidDefaultTextLength(sentDepartmentUserResource.getAddress2())) {
                        isValidated = false;
                    }
                }

                if (sentDepartmentUserResource.getRoles() == null) {
                    sentDepartmentUserResource.setRoles(new HashSet<>());
                }

                if (!(validate.isValidRADIUS_Timeout(sentDepartmentUserResource.getSessionTimeout()))) {
                    isValidated = false;
                }
                if (!(validate.isValidCalendarDate(sentDepartmentUserResource.getAccountExpiration()))) {
                    isValidated = false;
                }

                if (sentDepartmentUserResource.getEmail().trim().equals("") || !(validate.isValidEmail(sentDepartmentUserResource.getEmail())) || (!validate.isValidDefaultTextLength(sentDepartmentUserResource.getEmail()))) {
                    isValidated = false;
                }

                if (!sentDepartmentUserResource.getPhoneNo().equals("") && !(validate.isValidPhoneNumber(sentDepartmentUserResource.getPhoneNo()))) {
                    isValidated = false;

                }

                if (!sentDepartmentUserResource.getMobileNo().equals("") && !(validate.isValidPhoneNumber(sentDepartmentUserResource.getMobileNo()))) {
                    isValidated = false;
                }

                if (validate.isEmptyString(sentDepartmentUserResource.getSocialID()) || (!validate.isValidDefaultTextLength(sentDepartmentUserResource.getSocialID()))) {
                    isValidated = false;
                }

                if (validate.isEmptyString(sentDepartmentUserResource.getDepartmentUserField()) || (!validate.isValidDefaultTextLength(sentDepartmentUserResource.getDepartmentUserField()))) {
                    isValidated = false;
                }

                if (!isValidated) {
                    throw new BadRequestException();
                }

                //Validation End
                UserDetails details = (UserDetails) principal;
                User loggedUser = userService.findUserByUsername(details.getUsername());
                //Adding DepartmentUser Role
                RoleResource departmentUserRole = new RoleResourceAsm().toResource(roleService.findRole(4L));
                sentDepartmentUserResource.getRoles().add(departmentUserRole);
//End of adding DepartmentUser Role  
                sentDepartmentUserResource.setLastUpdateByUserType(loggedUser.getUserType());
                sentDepartmentUserResource.setLastUpdateByUsername(loggedUser.getUsername());
                sentDepartmentUserResource.setLastModifiedUnixTime(DateConverter.getCurrentConvertedDateAndTimeInUnixDate());
                DepartmentUser updatedEntry = departmentUserService.updateDepartmentUserDetails(id, sentDepartmentUserResource.toDepartmentUser());
                if (updatedEntry != null) {
                    DepartmentUserResource res = new DepartmentUserResourceAsm().toResource(updatedEntry);
                    return new ResponseEntity<>(res, HttpStatus.OK);
                } else {
                    throw new NotFoundException();
                }
            } catch (NotFoundException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
//                throw new NotFoundException(exception);
            } catch (BadRequestException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
//                throw new BadRequestException(exception);
            } catch (ConflictException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
//                throw new ConflictException(exception);
            } catch (Exception exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
//                throw new InternalServertException(e);
            }
        } else {
            ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN.value(), GlobalExceptionMessage.FORBIDDEN_APPLICATION_CODE, GlobalExceptionMessage.FORBIDDEN_MESSAGE, false, "N/A");
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
//            throw new ForbiddenException();
        }
    }

    @RequestMapping(value = "/update/password/{id}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> updateDepartmentUserPassword(
            @PathVariable Long id, @RequestBody DepartmentUserResource sentDepartmentUserResource) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                UserDetails details = (UserDetails) principal;
                User loggedUser = userService.findUserByUsername(details.getUsername());
                sentDepartmentUserResource.setLastUpdateByUserType(loggedUser.getUserType());
                sentDepartmentUserResource.setLastUpdateByUsername(loggedUser.getUsername());
                sentDepartmentUserResource.setLastModifiedUnixTime(DateConverter.getCurrentConvertedDateAndTimeInUnixDate());
                DepartmentUser updatedEntry = departmentUserService.updateDepartmentUserPassword(id, sentDepartmentUserResource.toDepartmentUser());
                if (updatedEntry != null) {
                    DepartmentUserResource res = new DepartmentUserResourceAsm().toResource(updatedEntry);
                    return new ResponseEntity<>(res, HttpStatus.OK);
                } else {
                    throw new NotFoundException();
                }
            } catch (NotFoundException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
//                throw new NotFoundException(exception);
            } catch (BadRequestException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
//                throw new BadRequestException(exception);
            } catch (ConflictException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
//                throw new ConflictException(exception);
            } catch (Exception exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
//                throw new InternalServertException(e);
            }
        } else {
            ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN.value(), GlobalExceptionMessage.FORBIDDEN_APPLICATION_CODE, GlobalExceptionMessage.FORBIDDEN_MESSAGE, false, "N/A");
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
//            throw new ForbiddenException();
        }

    }

    @RequestMapping(value = "/delete/{id}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> deleteDepartmentUser(
            @PathVariable Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                DepartmentUser entry = departmentUserService.deleteDepartmentUser(id);
                if (entry != null) {
                    DepartmentUserResource res = new DepartmentUserResourceAsm().toResource(entry);
                    return new ResponseEntity<>(res, HttpStatus.OK);
                } else {
                    throw new NotFoundException();
                }
            } catch (NotFoundException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
//                throw new NotFoundException(exception);
            } catch (BadRequestException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
//                throw new BadRequestException(exception);
            } catch (ConflictException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
//                throw new ConflictException(exception);
            } catch (Exception exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
//                throw new InternalServertException(e);
            }
        } else {
            ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN.value(), GlobalExceptionMessage.FORBIDDEN_APPLICATION_CODE, GlobalExceptionMessage.FORBIDDEN_MESSAGE, false, "N/A");
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
//            throw new ForbiddenException();
        }
    }

    @RequestMapping(value = "/update/changeenabledstatus/{id}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> updateToChangeEnabledStatus(
            @PathVariable Long id) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                UserDetails details = (UserDetails) principal;
                User loggedUser = userService.findUserByUsername(details.getUsername());
                DepartmentUser updatedEntry = departmentUserService.updateDepartmentUserEnabledStatus(id, loggedUser);
                if (updatedEntry != null) {
                    DepartmentUserResource res = new DepartmentUserResourceAsm().toResource(updatedEntry);
                    return new ResponseEntity<>(res, HttpStatus.OK);
                } else {
                    throw new NotFoundException();
                }
            } catch (NotFoundException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
//                throw new NotFoundException(exception);
            } catch (BadRequestException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
//                throw new BadRequestException(exception);
            } catch (ConflictException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
//                throw new ConflictException(exception);
            } catch (Exception exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
//                throw new InternalServertException(e);
            }
        } else {
            ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN.value(), GlobalExceptionMessage.FORBIDDEN_APPLICATION_CODE, GlobalExceptionMessage.FORBIDDEN_MESSAGE, false, "N/A");
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
//            throw new ForbiddenException();
        }

    }

}
