/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.mvc.device;

import com.project.core.models.entities.common.ExceptionInfo;
import com.project.core.models.entities.user.User;
import com.project.core.models.entities.util.ApplicationLogMessage;
import com.project.core.services.user.UserService;
import com.project.core.services.util.ApplicationLogMessageList;
import com.project.core.services.util.ApplicationLogMessageService;
import com.project.core.util.DateConverter;
import com.project.core.util.Log4jUtil;
import com.project.rest.exceptions.BadRequestException;
import com.project.rest.exceptions.ConflictException;
import com.project.rest.exceptions.NotFoundException;
import com.project.rest.resources.asm.util.ApplicationLogMessageListResourceAsm;
import com.project.rest.resources.asm.util.ApplicationLogMessageResourceAsm;
import com.project.rest.resources.util.ApplicationLogMessageListResource;
import com.project.rest.resources.util.ApplicationLogMessageResource;
import com.project.rest.util.GlobalExceptionMessage;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
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

/**
 *
 * @author samri
 */
@Controller
@RequestMapping("/rest/device/application_log_message")
public class ApplicationLogMessageRESTController_ForDevice {

    @Autowired
    private ApplicationLogMessageService applicationLogMessageService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/create",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> createApplicationLogMessage(
            @RequestBody ApplicationLogMessageResource object
    ) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                //Validation Stop
                UserDetails details = (UserDetails) principal;
                User loggedUser = userService.findUserByUsername(details.getUsername());

                object.setTableId(null);
                object.setEntryDate(DateConverter.getCurrentConvertedDateAndTimeInUnixDate());

                object.setAdminUsername(loggedUser.getAdminUsername());
                object.setAdminUserType(loggedUser.getAdminUserType());
                object.setAdminOperatorUsername(loggedUser.getAdminOperatorUsername());
                object.setAdminOperatorUserType(loggedUser.getAdminOperatorUserType());

                if (object.getLogMessageBlop() == null) {
                    object.setLogMessageBlop("".getBytes());
                }

                ApplicationLogMessage entry = applicationLogMessageService.createApplicationLogMessage(object.toApplicationLogMessage());
                System.out.println("--- one ---");

                ApplicationLogMessageResource res = new ApplicationLogMessageResourceAsm().toResource(entry);
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(URI.create(res.getLink("self").getHref()));
                return new ResponseEntity<>(entry, headers, HttpStatus.CREATED);
            } catch (NotFoundException exception) {
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
//                throw new NotFoundException(exception);
            } catch (BadRequestException exception) {
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
//                throw new BadRequestException(exception);
            } catch (ConflictException exception) {
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
//                throw new ConflictException(exception);
            } catch (Exception exception) {
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

    @RequestMapping(value = "/create_in_bluck",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> createApplicationLogMessages(
            @RequestBody ApplicationLogMessageListResource object
    ) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                //Validation Stop
                UserDetails details = (UserDetails) principal;
                User loggedUser = userService.findUserByUsername(details.getUsername());
                List<ApplicationLogMessage> insertedApplicationLogMessage = new ArrayList<ApplicationLogMessage>();
                for (ApplicationLogMessageResource logMessageResource : object.getApplicationLogMessageResources()) {

                    logMessageResource.setTableId(null);
                    logMessageResource.setEntryDate(DateConverter.getCurrentConvertedDateAndTimeInUnixDate());

                    logMessageResource.setSenderUsername(loggedUser.getUsername());
                //    logMessageResource.setLoggedUsername(loggedUser.getUsername());

                    logMessageResource.setAdminUsername(loggedUser.getAdminUsername());
                    logMessageResource.setAdminUserType(loggedUser.getAdminUserType());
                    logMessageResource.setAdminOperatorUsername(loggedUser.getAdminOperatorUsername());
                    logMessageResource.setAdminOperatorUserType(loggedUser.getAdminOperatorUserType());

                    if (logMessageResource.getLogMessageBlop() == null) {
                        logMessageResource.setLogMessageBlop("".getBytes());
                    }

                    ApplicationLogMessage entry = applicationLogMessageService.createApplicationLogMessage(logMessageResource.toApplicationLogMessage());
                    insertedApplicationLogMessage.add(entry);
                    System.out.println("--- one ---");

                }
                ApplicationLogMessageList applicationLogMessageList = new ApplicationLogMessageList(insertedApplicationLogMessage);
                ApplicationLogMessageListResource logMessageListResource = new ApplicationLogMessageListResourceAsm().toResource(applicationLogMessageList);
                return new ResponseEntity<>(logMessageListResource, HttpStatus.CREATED);
            } catch (NotFoundException exception) {
                exception.printStackTrace();
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
//                throw new NotFoundException(exception);
            } catch (BadRequestException exception) {
                exception.printStackTrace();
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
//                throw new BadRequestException(exception);
            } catch (ConflictException exception) {
                exception.printStackTrace();
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
//                throw new ConflictException(exception);
            } catch (Exception exception) {
                exception.printStackTrace();
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
    public ResponseEntity<?> getApplicationLogMessageByID(
            @PathVariable Long id
    ) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                System.out.println("id----" + id);
                ApplicationLogMessage entry = applicationLogMessageService.findApplicationLogMessage(id);
                System.out.println("location----" + entry);
                if (entry != null) {
                    ApplicationLogMessageResource res = new ApplicationLogMessageResourceAsm().toResource(entry);
                    return new ResponseEntity<>(res, HttpStatus.OK);
                } else {
                    throw new NotFoundException();
                }
            } catch (NotFoundException exception) {
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
//                throw new NotFoundException(exception);
            } catch (BadRequestException exception) {
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
//                throw new BadRequestException(exception);
            } catch (ConflictException exception) {
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
//                throw new ConflictException(exception);
            } catch (Exception exception) {
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
