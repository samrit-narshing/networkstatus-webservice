/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.mvc.web;

import com.project.core.models.entities.common.ExceptionInfo;
import com.project.core.models.entities.util.SchedulerTask;
import com.project.core.services.util.SchedulerTaskService;
import com.project.core.util.Log4jUtil;
import com.project.rest.exceptions.BadRequestException;
import com.project.rest.exceptions.ConflictException;
import com.project.rest.exceptions.NotFoundException;
import com.project.rest.mvc.common.BaseController;
import com.project.rest.resources.asm.util.SchedulerTaskResourceAsm;
import com.project.rest.resources.util.SchedulerTaskResource;
import com.project.rest.util.GlobalExceptionMessage;
import com.project.rest.util.JsonParsingUtil;
import java.net.URI;
import javax.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Samrit
 */
@Controller
@RequestMapping("/rest/web/scheduler")
public class SchedulerRESTController_ForWeb extends BaseController {

    @Autowired
    SchedulerTaskService schedulerTaskService;

    @RequestMapping(value = "/create",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> createGeoLocations(
            @RequestBody SchedulerTaskResource object
    ) {
        try {

            SchedulerTask entry2 = new SchedulerTask();
            entry2.setName("Test");
            entry2.setActive(Boolean.FALSE);

            SchedulerTask entry = schedulerTaskService.createSchedulerTask(object.toSchedulerTask());
            System.out.println(entry.getName());
            System.out.println("--- one ---");

            SchedulerTaskResource res = new SchedulerTaskResourceAsm().toResource(entry);
            System.out.println("---" + res.getName());
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

    }

    @RequestMapping(value = "/find/{id}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> getScheduleTaskByID(
            @PathVariable Long id
    ) {
        try {
            System.out.println("id----" + id);
            SchedulerTask entry = schedulerTaskService.findSchedulerTask(id);
            System.out.println("location----" + entry);
            if (entry != null) {
                SchedulerTaskResource res = new SchedulerTaskResourceAsm().toResource(entry);
                return new ResponseEntity<>(res, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
    }

    @RequestMapping(value = "/find/name/{name}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> getScheduleTaskByID(
            @PathVariable String name
    ) {
        try {
            System.out.println("id----" + name);
            SchedulerTask entry = schedulerTaskService.findSchedulerTaskByName(name);
            System.out.println("location----" + entry);
            if (entry != null) {
                SchedulerTaskResource res = new SchedulerTaskResourceAsm().toResource(entry);
                return new ResponseEntity<>(res, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
            Log4jUtil.error(exception.toString());
            Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
            ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
            return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
//                throw new InternalServertException(e);
        }
    }

    @RequestMapping(value = "/update/{id}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> updateBlogEntry(
            @PathVariable Long id, @RequestBody SchedulerTaskResource object) {
        try {
            SchedulerTask entry = schedulerTaskService.updateSchedulerTask(id, object.toSchedulerTask());
            if (entry != null) {
                SchedulerTaskResource res = new SchedulerTaskResourceAsm().toResource(entry);
                return new ResponseEntity<>(res, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
    }

    @RequestMapping(value = "/find_or_create/name/{name}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> getOrCreateScheduleTaskByName(
            @PathVariable String name
    ) {
        try {

            SchedulerTask entry = schedulerTaskService.findSchedulerTaskByName(name);
            if (entry != null) {
                SchedulerTaskResource res = new SchedulerTaskResourceAsm().toResource(entry);
                return new ResponseEntity<>(res, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (NoResultException exception) {
            try {
                SchedulerTask schedulerTask = new SchedulerTask();
                schedulerTask.setActive(false);
                schedulerTask.setId(null);
                schedulerTask.setName(name);
                SchedulerTask st = schedulerTaskService.createSchedulerTask(schedulerTask);
                SchedulerTaskResource res = new SchedulerTaskResourceAsm().toResource(st);
                return new ResponseEntity<>(res, HttpStatus.OK);
            } catch (Exception e) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
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
    }

    @RequestMapping(value = "/update_status/name/{name}/{status}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> getOrCreateScheduleTaskByName(
            @PathVariable String name, @PathVariable int status
    ) {
        try {

            SchedulerTask entry = schedulerTaskService.findSchedulerTaskByName(name);
            if (status == 1) {
                entry.setActive(Boolean.TRUE);
            } else {
                entry.setActive(Boolean.FALSE);

            }
            SchedulerTask update = schedulerTaskService.updateSchedulerTask(entry.getId(), entry);

            if (update != null) {
                SchedulerTaskResource res = new SchedulerTaskResourceAsm().toResource(update);
                return new ResponseEntity<>(res, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (NoResultException exception) {
            try {
                SchedulerTask schedulerTask = new SchedulerTask();
                schedulerTask.setActive(false);
                schedulerTask.setId(null);
                schedulerTask.setName(name);
                SchedulerTask st = schedulerTaskService.createSchedulerTask(schedulerTask);
                SchedulerTaskResource res = new SchedulerTaskResourceAsm().toResource(st);
                return new ResponseEntity<>(res, HttpStatus.OK);
            } catch (Exception e) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
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
    }

    @RequestMapping(value = "/parse/jsonurl",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> parseJSONDataFromSource() {
        try {
            return new ResponseEntity<>(JsonParsingUtil.getChartData(), HttpStatus.OK);
        } catch (Exception exception) {
            writeLogMessage(exception);
            Log4jUtil.error(exception.toString());
            Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
            ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
            return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
//                throw new InternalServertException(e);
        }
    }

}
