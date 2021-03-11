/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.mvc.web;

import com.project.core.models.entities.common.ExceptionInfo;
import com.project.core.models.entities.network.Edge;
import com.project.core.models.entities.user.User;
import com.project.core.services.network.EdgeService;
import com.project.core.services.user.UserService;
import com.project.core.services.util.EdgeList;
import com.project.core.util.Log4jUtil;
import com.project.core.util.Validate;
import com.project.rest.exceptions.BadRequestException;
import com.project.rest.exceptions.ConflictException;
import com.project.rest.exceptions.NotFoundException;
import com.project.rest.mvc.common.BaseController;
import com.project.rest.resources.asm.network.EdgeListResourceAsm;
import com.project.rest.resources.asm.network.EdgeResourceAsm;
import com.project.rest.resources.network.EdgeListResource;
import com.project.rest.resources.network.EdgeResource;
import com.project.rest.util.GlobalExceptionMessage;
import com.project.rest.util.searchcriteria.network.EdgeSearchCriteria;
import java.net.URI;
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
@RequestMapping("/rest/web/network/edge")
public class EdgeRESTController_ForWeb extends BaseController {

    @Autowired
    private EdgeService edgeService;

    @Autowired
    private UserService userService;

    private final String UNIQUE_APPLICATION_ID = "LALITPUR@MANGALBAZAR#5536666";

    @RequestMapping(value = "/find/{id}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> getEdge(
            @PathVariable Long id
    ) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                Edge edge = edgeService.find(id);
                if (edge != null) {
                    EdgeResource res = new EdgeResourceAsm().toResource(edge);
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

            writeLogMessage(info.getMessage());
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
//            throw new ForbiddenException();
        }

    }

    @RequestMapping(value = "/find/label/{label}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> getEdgeResourceByLabel(
            @PathVariable String label
    ) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
//                UserDetails details = (UserDetails) principal;
//                User loggedUser = userService.findUserByUsername(details.getUsername());
                Edge edge = edgeService.findByLabel(label);
                if (edge != null) {
                    EdgeResource res = new EdgeResourceAsm().toResource(edge);
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
            writeLogMessage(info.getMessage());
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
//            throw new ForbiddenException();
        }

    }

    @RequestMapping(value = "/list",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> findEdgesBySearchCriteria(@RequestBody(required = false) EdgeSearchCriteria searchCriteria) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
//                UserDetails details = (UserDetails) principal;
//                User loggedUser = userService.findUserByUsername(details.getUsername());
                System.out.println("KOKO--ENTERING......");

                if (searchCriteria == null) {
                    searchCriteria = new EdgeSearchCriteria();
                }

                if (searchCriteria.getLimitResult() <= 0) {
                    searchCriteria.setLimitResult(10);
                }
                EdgeList list = edgeService.findBySearchCriteria(searchCriteria);
                EdgeListResource res = new EdgeListResourceAsm().toResource(list);

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
            writeLogMessage(info.getMessage());
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
//            throw new ForbiddenException();
        }
    }

    @RequestMapping(value = "/listall",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> findAllEdges() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
//                UserDetails details = (UserDetails) principal;
//                User loggedUser = userService.findUserByUsername(details.getUsername());
                System.out.println("ENTERING......");
                EdgeList list = edgeService.findAll();
                EdgeListResource res = new EdgeListResourceAsm().toResource(list);
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
            writeLogMessage(info.getMessage());
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
//            throw new ForbiddenException();
        }
    }

    @RequestMapping(value = "/listallenabled",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> findAllEnabledEdges() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
//                UserDetails details = (UserDetails) principal;
//                User loggedUser = userService.findUserByUsername(details.getUsername());
                System.out.println("ENTERING......");
                EdgeList list = edgeService.findAllEnabled();
                EdgeListResource res = new EdgeListResourceAsm().toResource(list);
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
            writeLogMessage(info.getMessage());
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
//            throw new ForbiddenException();
        }
    }

    @RequestMapping(value = "/create",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> createEdge(@RequestBody(required = false) EdgeResource sentEdgeResource
    ) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                UserDetails details = (UserDetails) principal;
                User loggedUser = userService.findUserByUsername(details.getUsername());

                boolean isValidated = true;
                Validate validate = new Validate();

                if ((!validate.isValidDefaultTextLength(sentEdgeResource.getLabel()))) {
                    isValidated = false;
                }

                if (validate.isEmptyString(sentEdgeResource.getTitle()) || (!validate.isValidDefaultTextLength(sentEdgeResource.getTitle()))) {
                    isValidated = false;
                }

                if (isEdgeAlreadyExist(sentEdgeResource.getFromNodeResource().getNodeID(), sentEdgeResource.getToNodeResource().getNodeID())) {
                    isValidated = false;

                }

                if (!validate.isValidDefaultTextLength(sentEdgeResource.getLabel())) {
                    isValidated = false;

                }

                if (!isValidated) {
                    throw new BadRequestException();
                }

                Edge edge = null;

                sentEdgeResource.setEdgeID(null);
                edge = sentEdgeResource.toEdge();
//                edge.getFill().setId(null);
                Edge createdEdge = edgeService.create(edge);

                EdgeResource res = new EdgeResourceAsm().toResource(createdEdge);
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
            writeLogMessage(info.getMessage());
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
//            throw new ForbiddenException();
        }

    }

    @RequestMapping(value = "/update/{id}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> updateEdge(
            @PathVariable Long id, @RequestBody EdgeResource sentEdgeResource) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {

                boolean isValidated = true;
                Validate validate = new Validate();

                if ((!validate.isValidDefaultTextLength(sentEdgeResource.getLabel()))) {
                    isValidated = false;
                }

                if (validate.isEmptyString(sentEdgeResource.getTitle()) || (!validate.isValidDefaultTextLength(sentEdgeResource.getTitle()))) {
                    isValidated = false;
                }

                if (!isValidated) {
                    throw new BadRequestException();
                }

//                UserDetails details = (UserDetails) principal;
//                User loggedUser = userService.findUserByUsername(details.getUsername());
                Edge updatedEntry = edgeService.update(id, sentEdgeResource.toEdge());
                if (updatedEntry != null) {
                    EdgeResource res = new EdgeResourceAsm().toResource(updatedEntry);
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
            writeLogMessage(info.getMessage());
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
//                UserDetails details = (UserDetails) principal;
//                User loggedUser = userService.findUserByUsername(details.getUsername());
                Edge updatedEntry = edgeService.updateEnabledStatus(id);

                if (updatedEntry != null) {
                    EdgeResource res = new EdgeResourceAsm().toResource(updatedEntry);
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
            writeLogMessage(info.getMessage());
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
//            throw new ForbiddenException();
        }

    }

    @RequestMapping(value = "/delete/{id}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> deleteEdge(
            @PathVariable Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                Edge entry = edgeService.delete(id);
                if (entry != null) {
                    EdgeResource res = new EdgeResourceAsm().toResource(entry);
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
            writeLogMessage(info.getMessage());
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
//            throw new ForbiddenException();
        }
    }

    private boolean isEdgeAlreadyExist(String edgeLabel, User loggedUser) throws Exception {
        Edge edge = edgeService.findByLabel(edgeLabel);
        return edge != null;
    }

    @RequestMapping(value = "/find/nodeid1/{nodeid1}/nodeid2/{nodeid2}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> getEdgeResourceByNodeIds(
            @PathVariable Long nodeid1, @PathVariable Long nodeid2
    ) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
//                UserDetails details = (UserDetails) principal;
//                User loggedUser = userService.findUserByUsername(details.getUsername());
                Edge edge = edgeService.findEnabledByNodeIDs(nodeid1, nodeid2);
                if (edge != null) {
                    EdgeResource res = new EdgeResourceAsm().toResource(edge);
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
            writeLogMessage(info.getMessage());
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
//            throw new ForbiddenException();
        }

    }

    private boolean isEdgeAlreadyExist(Long node1ID, Long node2ID) throws Exception {
        Edge edge = edgeService.findEnabledByNodeIDs(node1ID, node2ID);
        return edge != null;
    }

}
