/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.mvc.device;

import com.project.core.models.entities.common.ExceptionInfo;
import com.project.core.models.entities.system.ControlPanel;
import com.project.core.models.entities.system.NetworkInfo;
import com.project.core.models.entities.system.SystemInfo;
import com.project.core.models.entities.system.WebServerInfo;
import com.project.core.security.PropertiesConfig;
import com.project.core.services.system.SystemManagementService;
import com.project.core.util.Log4jUtil;
import com.project.rest.exceptions.BadRequestException;
import com.project.rest.exceptions.ConflictException;
import com.project.rest.exceptions.ForbiddenException;
import com.project.rest.exceptions.InternalServertException;
import com.project.rest.exceptions.NotFoundException;
import com.project.rest.util.GlobalExceptionMessage;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Samrit
 */
@Controller
@RequestMapping("/rest/device/info")
public class SystemManagementRESTController_ForDevice {

    @Autowired
    @Resource(name = "propertiesConfig")
    private PropertiesConfig propertiesConfig;

    @Autowired
    private SystemManagementService systemManagementService;

    @RequestMapping(value = "/system",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> readSystemInfo() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                UserDetails details = (UserDetails) principal;
                SystemInfo systemInfo = systemManagementService.readSystemInfo();
                return new ResponseEntity<>(systemInfo, HttpStatus.OK);
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

    @RequestMapping(value = "/network",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> readNetworkInfo() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                UserDetails details = (UserDetails) principal;
                NetworkInfo networkInfo = systemManagementService.readNetworkInfo();
                return new ResponseEntity<>(networkInfo, HttpStatus.OK);
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

    @RequestMapping(value = "/webserver",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> readWebServerInfo(HttpServletRequest request,
            HttpServletResponse response) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                UserDetails details = (UserDetails) principal;
                WebServerInfo webServerInfo = systemManagementService.readWebServerInfo(request, response);
                return new ResponseEntity<>(webServerInfo, HttpStatus.OK);
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

    @RequestMapping(value = "/control_panel/unsecure/ajax_physicalmemory_status", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    String getCurrentPhysicalMemorystatus() {
        try {
            return systemManagementService.getCurrentPhysicalMemorystatus();
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

    @RequestMapping(value = "/control_panel/unsecure/ajax_swapmemory_status", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    String getCurrentSwapMemorystatus() {
        try {
            return systemManagementService.getCurrentSwapMemorystatus();
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

    @RequestMapping(value = "/control_panel/unsecure/ajax_jvm_status", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    String getCurrentJVMStatus() {
        try {
            return systemManagementService.getCurrentJVMStatus();
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

    @RequestMapping(value = "/control_panel/unsecure/read",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> readControlPanelInfo(HttpServletRequest request,
            HttpServletResponse response) {
        try {
            ControlPanel controlPanel = systemManagementService.readControlPanelInfo(request, response);
            return new ResponseEntity<>(controlPanel, HttpStatus.OK);
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

    }

    @RequestMapping(value = "/control_panel/unsecure/call_garbagecollection",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<Integer> forceGarbageCollectionServiceForWebServer() {
        try {
            int status = systemManagementService.forceGarbageCollectionServiceForWebServer();
            return new ResponseEntity<>(status, HttpStatus.OK);
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
