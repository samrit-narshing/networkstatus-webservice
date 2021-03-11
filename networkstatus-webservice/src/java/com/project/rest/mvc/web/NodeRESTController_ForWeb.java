/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.mvc.web;

import com.opencsv.exceptions.CsvException;
import com.project.core.models.entities.common.ExceptionInfo;
import com.project.core.models.entities.network.Edge;
import com.project.core.models.entities.network.NetworkGroup;
import com.project.core.models.entities.network.Node;
import com.project.core.models.entities.network.NodeAlert;
import com.project.core.models.entities.network.NodeAlertInfo;
import com.project.core.models.entities.network.NodeAlertInfoArchive;
import com.project.core.models.entities.network.NodeImageLink;
import com.project.core.models.entities.network.NodeInNetworkGroup;
import com.project.core.models.entities.user.User;
import com.project.core.security.PropertiesConfig;
import com.project.core.services.network.EdgeService;
import com.project.core.services.network.NetworkGroupService;
import com.project.core.services.network.NodeAlertInfoArchiveService;
import com.project.core.services.network.NodeService;
import com.project.core.services.user.UserService;
import com.project.core.services.util.NetworkGroupList;
import com.project.core.services.util.NodeList;
import com.project.core.util.CsvParserUtil;
import com.project.core.util.DateConverter;
import com.project.core.util.Log4jUtil;
import com.project.core.util.Validate;
import com.project.rest.exceptions.BadRequestException;
import com.project.rest.exceptions.ConflictException;
import com.project.rest.exceptions.NotFoundException;
import com.project.rest.mvc.common.BaseController;
import com.project.rest.resources.asm.network.NodeListResourceAsm;
import com.project.rest.resources.asm.network.NodeResourceAsm;
import com.project.rest.resources.network.EdgeResource;
import com.project.rest.resources.network.NodeAlertInfoResource;
import com.project.rest.resources.network.NodeAlertResource;
import com.project.rest.resources.network.NodeAndEdgeListResource;
import com.project.rest.resources.network.NodeImageLinkResource;
import com.project.rest.resources.network.NodeListResource;
import com.project.rest.resources.network.NodeResource;
import com.project.rest.util.GlobalExceptionMessage;
import com.project.rest.util.enums.NODE_TYPE;
import com.project.rest.util.searchcriteria.network.NodeSearchCriteria;
import com.project.test.NetworkCSVObject;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
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
@RequestMapping("/rest/web/network/node")
public class NodeRESTController_ForWeb extends BaseController {

    @Autowired
    private NodeService nodeService;

    @Autowired
    private EdgeService edgeService;

    @Autowired
    private UserService userService;

    @Autowired
    private NetworkGroupService networkGroupService;

    @Autowired
    @Resource(name = "propertiesConfig")
    private PropertiesConfig propertiesConfig;

    @Autowired
    private NodeAlertInfoArchiveService nodeAlertInfoArchiveService;

    private final String UNIQUE_APPLICATION_ID = "LALITPUR@MANGALBAZAR#5536666";

    @RequestMapping(value = "/find/{id}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> getNode(
            @PathVariable Long id
    ) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                Node node = nodeService.find(id);
                if (node != null) {
                    NodeResource res = new NodeResourceAsm().toResource(node);
//                    javax.swing.JOptionPane.showMessageDialog(null, res.getAlert().getNodeAlertInfoResources().size());
//                    System.out.println("-----------------res.getAlert().getNodeAlertInfoResources().size());
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
    public ResponseEntity<?> getNodeResourceByLabel(
            @PathVariable String label
    ) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
//                UserDetails details = (UserDetails) principal;
//                User loggedUser = userService.findUserByUsername(details.getUsername());
                Node node = nodeService.findByLabel(label);
                if (node != null) {
                    NodeResource res = new NodeResourceAsm().toResource(node);
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
    public ResponseEntity<?> findNodesBySearchCriteria(@RequestBody(required = false) NodeSearchCriteria searchCriteria) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
//                UserDetails details = (UserDetails) principal;
//                User loggedUser = userService.findUserByUsername(details.getUsername());
                System.out.println("ENTERING......");

                if (searchCriteria == null) {
                    searchCriteria = new NodeSearchCriteria();
                }

                if (searchCriteria.getLimitResult() <= 0) {
                    searchCriteria.setLimitResult(10);
                }
                NodeList list = nodeService.findBySearchCriteria(searchCriteria);
                NodeListResource res = new NodeListResourceAsm().toResource(list);

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
    public ResponseEntity<?> findAllNodes() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
//                UserDetails details = (UserDetails) principal;
//                User loggedUser = userService.findUserByUsername(details.getUsername());
                System.out.println("ENTERING......");
                NodeList list = nodeService.findAll();
                NodeListResource res = new NodeListResourceAsm().toResource(list);
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
    public ResponseEntity<?> findAllEnabledNodes() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
//                UserDetails details = (UserDetails) principal;
//                User loggedUser = userService.findUserByUsername(details.getUsername());
                System.out.println("ENTERING......");
                NodeList list = nodeService.findAllEnabled();
                NodeListResource res = new NodeListResourceAsm().toResource(list);
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
    public ResponseEntity<?> createNode(@RequestBody(required = false) NodeResource sentNodeResource
    ) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                UserDetails details = (UserDetails) principal;
                User loggedUser = userService.findUserByUsername(details.getUsername());

                boolean isValidated = true;
                Validate validate = new Validate();

                if (validate.isEmptyString(sentNodeResource.getLabel()) || (!validate.isValidDefaultTextLength(sentNodeResource.getLabel()))) {
                    isValidated = false;
                }

                if (validate.userTMP(sentNodeResource.getLabel())) {
                    if (isNodeAlreadyExist(sentNodeResource.getLabel(), loggedUser)) {
                        isValidated = false;
                    }

                    if (!validate.isValidDefaultTextLength(sentNodeResource.getLabel())) {
                        isValidated = false;
                    }

                } else {
                    isValidated = false;
                }

                if (!isValidated) {
                    throw new BadRequestException();
                }

                Node node = null;

                sentNodeResource.setNodeID(null);
                node = sentNodeResource.toNode();
                node.getFill().setId(null);
                node.getAlert().setId(null);
                Node createdNode = nodeService.create(node);

                NodeResource res = new NodeResourceAsm().toResource(createdNode);
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
    public ResponseEntity<?> updateNode(
            @PathVariable Long id, @RequestBody NodeResource sentNodeResource) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {

                boolean isValidated = true;
                Validate validate = new Validate();

                if (validate.isEmptyString(sentNodeResource.getLabel()) || (!validate.isValidDefaultTextLength(sentNodeResource.getLabel()))) {
                    isValidated = false;
                }

                if (!isValidated) {
                    throw new BadRequestException();
                }
//javax.swing.JOptionPane.showMessageDialog(null, sentNodeResource.getFill().getSrc());
//                UserDetails details = (UserDetails) principal;
//                User loggedUser = userService.findUserByUsername(details.getUsername());
                Node updatedEntry = nodeService.update(id, sentNodeResource.toNode());
                if (updatedEntry != null) {
                    NodeResource res = new NodeResourceAsm().toResource(updatedEntry);
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
                Node updatedEntry = nodeService.updateEnabledStatus(id);

                if (updatedEntry != null) {
                    NodeResource res = new NodeResourceAsm().toResource(updatedEntry);
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
    public ResponseEntity<?> deleteNode(
            @PathVariable Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                Node entry = nodeService.delete(id);
                if (entry != null) {
                    NodeResource res = new NodeResourceAsm().toResource(entry);
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

    private boolean isNodeAlreadyExist(String nodeLabel, User loggedUser) throws Exception {
        Node node = nodeService.findByLabel(nodeLabel);
        return node != null;
    }

    @RequestMapping(value = "/update_alert/{id}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> updateNodeAlertOfNode(
            @PathVariable Long id, @RequestBody(required = false) NodeResource sentNodeResource) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {

                boolean isValidated = true;
                Validate validate = new Validate();

//                if(sentNodeResource==null)
//                {
//                    sentNodeResource= new NodeResource();
//                    NodeAlertResource alert = new NodeAlertResource();
//                    alert.setType(12);
//                     alert.setDescription("Descrption");
//                    sentNodeResource.setAlert(alert);
//                    sentNodeResource.setFill(new NodeImageLinkResource());
//                }
                Node updatedEntry = nodeService.updateNodeAlert(id, sentNodeResource.toNode());
                if (updatedEntry != null) {
                    NodeResource res = new NodeResourceAsm().toResource(updatedEntry);
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
            } catch (ConflictException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
            } catch (Exception exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN.value(), GlobalExceptionMessage.FORBIDDEN_APPLICATION_CODE, GlobalExceptionMessage.FORBIDDEN_MESSAGE, false, "N/A");
            writeLogMessage(info.getMessage());
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/update_alert/reset/{id}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> updateNodeAlertOfNodeAsReset(
            @PathVariable Long id) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                Node readEntry = nodeService.find(id);

                UserDetails details = (UserDetails) principal;
                User loggedUser = userService.findUserByUsername(details.getUsername());
                for (NodeAlertInfo nodeAlertInfo : readEntry.getAlert().getNodeAlertInfos()) {
                    NodeAlertInfoArchive nodeAlertInfoArchive = new NodeAlertInfoArchive();
                    nodeAlertInfoArchive.setDescription(nodeAlertInfo.getDescription());
                    nodeAlertInfoArchive.setEntryDate(DateConverter.getCurrentConvertedDateAndTimeInUnixDate());
                    nodeAlertInfoArchive.setLoggedUsername(loggedUser.getUsername());
                    nodeAlertInfoArchive.setNodeName(readEntry.getLabel());
                    nodeAlertInfoArchive.setNodeType(readEntry.getType());
                    nodeAlertInfoArchive.setType(nodeAlertInfo.getType());

                    nodeAlertInfoArchiveService.createNodeAlertInfoArchive(nodeAlertInfoArchive);
                }

                Node updatedEntry = nodeService.updateNodeAlertAsReset(id);

                //Update Alert To Node That Represents NodeGroup Containing The Updated Node
                NetworkGroupList networkGroupContainingNodes = networkGroupService.findAllNetworkGroupsByNodeId(updatedEntry.getId());
                for (NetworkGroup networkGroupContainingNode : networkGroupContainingNodes.getNetworkGroup()) {
                    Node nodeRepresentingNodeGroup = nodeService.findByLabel(networkGroupContainingNode.getCode());
                    if (nodeRepresentingNodeGroup != null) {
                        boolean clearToReset = true;
                        for (NodeInNetworkGroup nodeInNetworkGroup : networkGroupContainingNode.getNodesInNetworkGroup()) {
                            if (nodeInNetworkGroup.getNode().getAlert().getType() != 1) {
                                clearToReset = false;
                                break;
                            }
                        }
                        if (clearToReset) {
                            nodeService.updateNodeAlertAsReset(nodeRepresentingNodeGroup.getId());
                        }
                    }
                }
                //End Of Update Alert To Node That Represents NodeGroup Containing The Updated Node

                if (updatedEntry != null) {
                    NodeResource res = new NodeResourceAsm().toResource(updatedEntry);
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

    @RequestMapping(value = "/chartData1", method = RequestMethod.GET)
    public ResponseEntity<ChartDataTwoMain> testForResolveSampleStatusab() {
        try {

            List<Node> nodes = nodeService.findAllEnabled().getNodes();
            List<Edge> edgeList = edgeService.findAllEnabled().getEdges();

            List<ChartDataTwo> nodesvalue = new ArrayList<>();

            String baseUrl = propertiesConfig.getProperty("web_app_url");

            String imagePath = baseUrl + "/resources/icons/";

            String fullImagePath = imagePath + "edit_32x32.png";

            for (Node node : nodes) {
                ChartDataTwo res = new ChartDataTwo();

                String name1 = node.getLabel();
                res.setNumId(node.getId().intValue());
                res.setId(name1);
                res.setHeight(50);
                res.setLink(node.getFill().getSrc());
                ChartDataTwoFill fill = new ChartDataTwoFill();

                if (node.getType().equalsIgnoreCase(NODE_TYPE.TYPE_ROUTER.type())) {
                    if (node.getAlert().getType() == 1) {
                        fullImagePath = imagePath + "router-type.png";
                    } else {
                        fullImagePath = imagePath + "router-alert-type.png";
                    }

                } else if (node.getType().equalsIgnoreCase(NODE_TYPE.TYPE_SERVER.type())) {
//                    fullImagePath = imagePath + "server-type.png";
                    if (node.getAlert().getType() == 1) {
                        fullImagePath = imagePath + "server-type.png";
                    } else {
                        fullImagePath = imagePath + "server-alert-type.png";
                    }

                } else if (node.getType().equalsIgnoreCase(NODE_TYPE.TYPE_NETWORKGROUP.type())) {
//                    fullImagePath = imagePath + "server-type.png";
                    if (node.getAlert().getType() == 1) {
                        fullImagePath = imagePath + "networkgroup-type.png";
                    } else {
                        fullImagePath = imagePath + "networkgroup-alert-type.png";
                    }

                } else if (node.getType().equalsIgnoreCase(NODE_TYPE.TYPE_DATABASE.type())) {
                    if (node.getAlert().getType() == 1) {
                        fullImagePath = imagePath + "database-type.png";
                    } else {
                        fullImagePath = imagePath + "database-alert-type.png";
                    }

                } else if (node.getType().equalsIgnoreCase(NODE_TYPE.TYPE_HUB.type())) {
                    if (node.getAlert().getType() == 1) {
                        fullImagePath = imagePath + "hub-type.png";
                    } else {
                        fullImagePath = imagePath + "hub-alert-type.png";
                    }

                } else if (node.getType().equalsIgnoreCase(NODE_TYPE.TYPE_PRINTER.type())) {
                    if (node.getAlert().getType() == 1) {
                        fullImagePath = imagePath + "printer-type.png";
                    } else {
                        fullImagePath = imagePath + "printer-alert-type.png";
                    }

                } else if (node.getType().equalsIgnoreCase(NODE_TYPE.TYPE_SWITCH.type())) {
                    if (node.getAlert().getType() == 1) {
                        fullImagePath = imagePath + "switch-type.png";
                    } else {
                        fullImagePath = imagePath + "switch-alert-type.png";
                    }

                } else if (node.getType().equalsIgnoreCase(NODE_TYPE.TYPE_COMPUTER.type())) {
                    if (node.getAlert().getType() == 1) {
                        fullImagePath = imagePath + "cmputer-type.png";
                    } else {
                        fullImagePath = imagePath + "cmputer-alert-type.png";
                    }

                } else {
//                    fullImagePath = imagePath + "cmputer-type.png";
                    if (node.getAlert().getType() == 1) {
                        fullImagePath = imagePath + "cmputer-type.png";
                    } else {
                        fullImagePath = imagePath + "cmputer-alert-type.png";
                    }
                }

                fill.setSrc(fullImagePath);
//                fill.setSrc(node.getFill().getSrc());
                res.setFill(fill);
                res.setAlert(Boolean.TRUE);
                nodesvalue.add(res);
            }
            List<ChartDataTwoEdge> edges = new ArrayList<>();
            for (Edge edge : edgeList) {

                ChartDataTwoEdge edge1 = new ChartDataTwoEdge();
                edge1.setFrom(edge.getFromNode().getLabel());
                edge1.setTo(edge.getToNode().getLabel());
                edges.add(edge1);
            }

            ChartDataTwoMain chartDataTwoMain = new ChartDataTwoMain();

            chartDataTwoMain.setNodes(nodesvalue);
            chartDataTwoMain.setEdges(edges);

            return new ResponseEntity<ChartDataTwoMain>(chartDataTwoMain, HttpStatus.OK);
        } catch (Exception e) {
            writeLogMessage(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/update_alert/alert_info/add/{id}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> updateNodeAlertOfNodeToAddNodeAlertInfo(
            @PathVariable Long id, @RequestBody(required = false) NodeResource sentNodeResource) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {

                Node updatedEntry = nodeService.updateNodeAlertToAddNodeAlertInfo(id, sentNodeResource.toNode());

                //Update Alert To Node That Represents NodeGroup Containing The Updated Node
                NetworkGroupList networkGroupContainingNodes = networkGroupService.findAllNetworkGroupsByNodeId(updatedEntry.getId());
                for (NetworkGroup networkGroupContainingNode : networkGroupContainingNodes.getNetworkGroup()) {
                    Node nodeRepresentingNodeGroup = nodeService.findByLabel(networkGroupContainingNode.getCode());
                    if (nodeRepresentingNodeGroup != null) {
                        nodeService.updateNodeAlert(nodeRepresentingNodeGroup.getId(), updatedEntry);
                    }
                }
                //End Of Update Alert To Node That Represents NodeGroup Containing The Updated Node

                if (updatedEntry != null) {
                    NodeResource res = new NodeResourceAsm().toResource(updatedEntry);
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
            } catch (ConflictException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
            } catch (Exception exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN.value(), GlobalExceptionMessage.FORBIDDEN_APPLICATION_CODE, GlobalExceptionMessage.FORBIDDEN_MESSAGE, false, "N/A");
            writeLogMessage(info.getMessage());
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/test/create",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> createTestNode() {

        try {

            NodeResource sentNodeResource = new NodeResource();

            int num = (int) (Math.random() * 100);
            sentNodeResource.setDescription("Test Descriptions " + num);
            sentNodeResource.setEnabled(true);
            sentNodeResource.setHeight(num);
            sentNodeResource.setLabel("Test Label " + num);
            sentNodeResource.setNodeID((long) num);
            sentNodeResource.setNodeValue(num);
            sentNodeResource.setRedirectingURLLink("Test Value " + num);
            sentNodeResource.setTitle("Test Value " + num);
            sentNodeResource.setType("Test Value " + num);
            sentNodeResource.setUniqueID("Test Value " + num);

            NodeImageLinkResource fill = new NodeImageLinkResource();
            fill.setNodeImageLinkID((long) num);
            fill.setSrc("Test Value " + num);

            NodeAlertResource nodeAlert = new NodeAlertResource();
            nodeAlert.setDescription("Test Value " + num);
            nodeAlert.setNodeAlertID(null);
            nodeAlert.setType(num);

            NodeAlertInfoResource alertInfoResource1 = new NodeAlertInfoResource();
            alertInfoResource1.setDescription("First Test Value " + num);
            alertInfoResource1.setNodeAlertInfoID(null);
            alertInfoResource1.setType(num);

            NodeAlertInfoResource alertInfoResource2 = new NodeAlertInfoResource();
            alertInfoResource2.setDescription("Second Test Value " + num);
            alertInfoResource2.setNodeAlertInfoID(null);
            alertInfoResource2.setType(num);

            NodeAlertInfoResource alertInfoResource3 = new NodeAlertInfoResource();
            alertInfoResource3.setDescription("Third Test Value " + num);
            alertInfoResource3.setNodeAlertInfoID(null);
            alertInfoResource3.setType(num);

            List<NodeAlertInfoResource> alertInfoResourcesTest = new ArrayList<>();
            alertInfoResourcesTest.add(alertInfoResource1);
            alertInfoResourcesTest.add(alertInfoResource2);
            alertInfoResourcesTest.add(alertInfoResource3);
//            javax.swing.JOptionPane.showMessageDialog(null, alertInfoResourcesTest.size());
//            nodeAlert.getNodeAlertInfoResources().add(alertInfoResource1);
            nodeAlert.setNodeAlertInfoResources(alertInfoResourcesTest);
//            nodeAlert.getNodeAlertInfoResources().add(alertInfoResource2);
//            nodeAlert.getNodeAlertInfoResources().add(alertInfoResource3);

            sentNodeResource.setAlert(nodeAlert);
            sentNodeResource.setFill(fill);

//            javax.swing.JOptionPane.showMessageDialog(null, nodeAlert.getNodeAlertInfoResources().size());
            Node node = null;

            sentNodeResource.setNodeID(null);
            node = sentNodeResource.toNode();
            node.getFill().setId(null);
            node.getAlert().setId(null);

//            javax.swing.JOptionPane.showMessageDialog(null, node.getAlert().getNodeAlertInfos().size());
            Node createdNode = nodeService.create(node);

            NodeResource res = new NodeResourceAsm().toResource(createdNode);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(res.getLink("self").getHref()));
            return new ResponseEntity<>(res, headers, HttpStatus.CREATED);
        } catch (Exception exception) {
            writeLogMessage(exception);
            Log4jUtil.error(exception.toString());
            Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
            ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
            return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
//                throw new InternalServertException(e);
        }

    }

    @RequestMapping(value = "/test/update/add/nodealertinfo/{id}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> updateAddNodeAlertInfo(
            @PathVariable Long id) {

        try {
            Node node = nodeService.find(id);
            NodeAlertInfoResource alertInfoResource3 = new NodeAlertInfoResource();
            alertInfoResource3.setDescription("Added Third Test Value ");
            alertInfoResource3.setNodeAlertInfoID(null);
            alertInfoResource3.setType(122);
            node.getAlert().getNodeAlertInfos().add(alertInfoResource3.toNNodeAlertInfo());

            Node updatedEntry = nodeService.updateNodeAlertToAddNodeAlertInfo(id, node);
            if (updatedEntry != null) {
                NodeResource res = new NodeResourceAsm().toResource(updatedEntry);
                return new ResponseEntity<>(res, HttpStatus.OK);
            } else {
                throw new NotFoundException();
            }
        } catch (Exception exception) {
            writeLogMessage(exception);
            Log4jUtil.error(exception.toString());
            Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
            ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
            return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
//                throw new InternalServertException(e);
        }

    }

    @RequestMapping(value = "/test/update/remove/nodealertinfo/{id}/{alertid}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> updateRemoveNodeAlertInfo(
            @PathVariable Long id, @PathVariable Long alertid) {

        try {

            NodeAlertInfoResource alertInfoResource3 = new NodeAlertInfoResource();
            alertInfoResource3.setDescription("Added Third Test Value ");
            alertInfoResource3.setNodeAlertInfoID(alertid);
            alertInfoResource3.setType(81);

            Node updatedEntry = nodeService.updateNodeAlertToRemoveNodeAlertInfo(id, alertInfoResource3.toNNodeAlertInfo());
            if (updatedEntry != null) {
                NodeResource res = new NodeResourceAsm().toResource(updatedEntry);
                return new ResponseEntity<>(res, HttpStatus.OK);
            } else {
                throw new NotFoundException();
            }
        } catch (Exception exception) {
            writeLogMessage(exception);
            Log4jUtil.error(exception.toString());
            Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
            ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
            return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
//                throw new InternalServertException(e);
        }

    }

    @RequestMapping(value = "/update/cordinates",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> updateCordinatesOfNode(@RequestBody(required = false) NodeListResource nodeListResource) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {

                boolean isValidated = true;
                Validate validate = new Validate();
                Node updatedEntry = null;
                for (NodeResource nodeResource : nodeListResource.getNodeResources()) {
                    updatedEntry = nodeService.updateNodeCordinates(nodeResource.getNodeID(), nodeResource.toNode());
                    if (updatedEntry == null) {
                        throw new NotFoundException();
                    }
                }

                if (updatedEntry != null) {
                    NodeResource res = new NodeResourceAsm().toResource(updatedEntry);
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
            } catch (ConflictException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
            } catch (Exception exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN.value(), GlobalExceptionMessage.FORBIDDEN_APPLICATION_CODE, GlobalExceptionMessage.FORBIDDEN_MESSAGE, false, "N/A");
            writeLogMessage(info.getMessage());
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/uploaded/csv/list",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> findAllCSVUploadedNodes() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                UserDetails details = (UserDetails) principal;
                User loggedUser = userService.findUserByUsername(details.getUsername());
                String UPLOADED_FOLDER = propertiesConfig.getProperty("upload_csv_toadd_node_path");
                String fileName = UPLOADED_FOLDER + loggedUser.getUsername() + ".csv";
                List<NetworkCSVObject> networkCSVObjects = new CsvParserUtil().readNetworkCSVObject(fileName);
                networkCSVObjects.forEach(x -> System.out.println(x.getHostName() + "-" + x.getIpAddress()));

                List<Node> nodes = new ArrayList<>();
                long i = 0;
                for (NetworkCSVObject object : networkCSVObjects) {
                    i++;
                    Node node = new Node();
                    node.setLabel(object.getHostName());
                    node.setType(object.getDeviceType());
                    node.setDescription(object.getSystem());
                    node.setTitle(object.getIpAddress());
                    node.setId(i);
                    nodes.add(node);
                    node.setFill(new NodeImageLink());
                    node.setAlert(new NodeAlert());
                    node.setEnabled(true);
                    node.setRedirectingURLLink("http://www.google.com");
//                node.getAlert().setId(null);

                }
//javax.swing.JOptionPane.showMessageDialog(null, nodes.size());
                System.out.println("ENTERING......");
//                NodeList list = nodeService.findAllEnabled();

                NodeList list = new NodeList(nodes);
                NodeListResource res = new NodeListResourceAsm().toResource(list);
                return new ResponseEntity<>(res, HttpStatus.OK);
            } catch (IOException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
//                throw new NotFoundException(exception);
            } catch (CsvException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
//                throw new NotFoundException(exception);
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

    @RequestMapping(value = "/uploaded/csv/nodes/add_update",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> addAndUpdateCSVNodes(@RequestBody(required = false) NodeListResource nodeListResource) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                UserDetails details = (UserDetails) principal;
                User loggedUser = userService.findUserByUsername(details.getUsername());

                List<Node> entryNodes = new ArrayList<>();

                int addSucess = 0;
                int updateSucess = 0;

                for (NodeResource sentNodeResource : nodeListResource.getNodeResources()) {

                    if (sentNodeResource.isEnabled()) {
                        if (isNodeAlreadyExist(sentNodeResource.getLabel(), loggedUser)) {
                            Node node = nodeService.findByLabel(sentNodeResource.getLabel());
                            Node updatedEntry = nodeService.update(node.getId(), sentNodeResource.toNode());
                            entryNodes.add(updatedEntry);
                            updateSucess++;

                        } else {

                            Node node = null;
                            sentNodeResource.setNodeID(null);
                            node = sentNodeResource.toNode();
                            node.getFill().setId(null);
                            node.getAlert().setId(null);

                            if (sentNodeResource.getType().equalsIgnoreCase("Router") || sentNodeResource.getType().equalsIgnoreCase("type-router")) {
                                node.setType("type-router");
                            } else if (sentNodeResource.getType().equalsIgnoreCase("Server") || sentNodeResource.getType().equalsIgnoreCase("type-server")) {
                                node.setType("type-server");
                            } else if (sentNodeResource.getType().equalsIgnoreCase("Database") || sentNodeResource.getType().equalsIgnoreCase("type-database")) {
                                node.setType("type-database");
                            } else if (sentNodeResource.getType().equalsIgnoreCase("Hub") || sentNodeResource.getType().equalsIgnoreCase("type-hub")) {
                                node.setType("type-hub");
                            } else if (sentNodeResource.getType().equalsIgnoreCase("Printer") || sentNodeResource.getType().equalsIgnoreCase("type-printer")) {
                                node.setType("type-printer");
                            } else if (sentNodeResource.getType().equalsIgnoreCase("Switch") || sentNodeResource.getType().equalsIgnoreCase("type-switch")) {
                                node.setType("type-switch");
                            } else if (sentNodeResource.getType().equalsIgnoreCase("Computer") || sentNodeResource.getType().equalsIgnoreCase("type-cmputer")) {
                                node.setType("type-cmputer");
                            } else {
                                node.setType("type-cmputer");
                            }

                            Node createdNode = nodeService.create(node);
                            entryNodes.add(createdNode);
                            addSucess++;
                        }
                    }
                }
//                boolean isValidated = true;
//                Validate validate = new Validate();
//                Node updatedEntry = null;
//                for (NodeResource nodeResource : nodeListResource.getNodeResources()) {
//                    updatedEntry = nodeService.updateNodeCordinates(nodeResource.getNodeID(), nodeResource.toNode());
//                    if (updatedEntry == null) {
//                        throw new NotFoundException();
//                    }
//                }
//
//                if (updatedEntry != null) {
//                    NodeResource res = new NodeResourceAsm().toResource(updatedEntry);
//                    return new ResponseEntity<>(res, HttpStatus.OK);
//                } else {
//                    throw new NotFoundException();
//                }
                NodeList list = new NodeList(entryNodes);
                NodeListResource res = new NodeListResourceAsm().toResource(list);
                res.setStatus("Added :- " + addSucess + "; Updated :- " + updateSucess);
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
            } catch (ConflictException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
            } catch (Exception exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN.value(), GlobalExceptionMessage.FORBIDDEN_APPLICATION_CODE, GlobalExceptionMessage.FORBIDDEN_MESSAGE, false, "N/A");
            writeLogMessage(info.getMessage());
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/uploaded/csv/nodes_edges/add_update",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> addAndUpdateCSVNodesAndEdges(@RequestBody(required = false) NodeAndEdgeListResource nodeAndEdgeListResource) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                UserDetails details = (UserDetails) principal;
                User loggedUser = userService.findUserByUsername(details.getUsername());

                List<Node> entryNodes = new ArrayList<>();

                int addSucess = 0;
                int updateSucess = 0;

                for (NodeResource sentNodeResource : nodeAndEdgeListResource.getNodeListResource().getNodeResources()) {

                    if (sentNodeResource.isEnabled()) {
                        if (isNodeAlreadyExist(sentNodeResource.getLabel(), loggedUser)) {
                            Node node = nodeService.findByLabel(sentNodeResource.getLabel());
                            Node updatedEntry = nodeService.update(node.getId(), sentNodeResource.toNode());
                            entryNodes.add(updatedEntry);
                            updateSucess++;

                        } else {

                            Node node = null;
                            sentNodeResource.setNodeID(null);
                            node = sentNodeResource.toNode();
                            node.getFill().setId(null);
                            node.getAlert().setId(null);

                            if (sentNodeResource.getType().equalsIgnoreCase("Router") || sentNodeResource.getType().equalsIgnoreCase("type-router")) {
                                node.setType("type-router");
                            } else if (sentNodeResource.getType().equalsIgnoreCase("Server") || sentNodeResource.getType().equalsIgnoreCase("type-server")) {
                                node.setType("type-server");
                            } else if (sentNodeResource.getType().equalsIgnoreCase("Database") || sentNodeResource.getType().equalsIgnoreCase("type-database")) {
                                node.setType("type-database");
                            } else if (sentNodeResource.getType().equalsIgnoreCase("Hub") || sentNodeResource.getType().equalsIgnoreCase("type-hub")) {
                                node.setType("type-hub");
                            } else if (sentNodeResource.getType().equalsIgnoreCase("Printer") || sentNodeResource.getType().equalsIgnoreCase("type-printer")) {
                                node.setType("type-printer");
                            } else if (sentNodeResource.getType().equalsIgnoreCase("Switch") || sentNodeResource.getType().equalsIgnoreCase("type-switch")) {
                                node.setType("type-switch");
                            } else if (sentNodeResource.getType().equalsIgnoreCase("Computer") || sentNodeResource.getType().equalsIgnoreCase("type-cmputer")) {
                                node.setType("type-cmputer");
                            } else {
                                node.setType("type-cmputer");
                            }

                            Node createdNode = nodeService.create(node);
                            entryNodes.add(createdNode);
                            addSucess++;
                        }
                    }
                }

                for (EdgeResource sentEdgeResource : nodeAndEdgeListResource.getEdgeListResource().getEdgeResources()) {

                    if (isEdgeAlreadyExist(sentEdgeResource.getFromNodeResource().getNodeID(), sentEdgeResource.getToNodeResource().getNodeID())) {
//Update Or Skip
                    } else {
//Insert New Edges

                        Node fromNode = nodeService.findByLabel(sentEdgeResource.getFromNodeResource().getLabel());
                        Node toNode = nodeService.findByLabel(sentEdgeResource.getToNodeResource().getLabel());

                        sentEdgeResource.setEdgeID(null);
                        sentEdgeResource.setArrows("");
                        sentEdgeResource.setDashes(false);
                        sentEdgeResource.setEdge_length(15);
                        sentEdgeResource.setEdge_value(15);
                        sentEdgeResource.setEnabled(true);
                        sentEdgeResource.setLabel("");
                        sentEdgeResource.setTitle(sentEdgeResource.getFromNodeResource().getLabel() + "-To-" + sentEdgeResource.getToNodeResource().getLabel());

                        NodeResource fromNodeResource = new NodeResourceAsm().toResource(fromNode);
                        NodeResource toNodeResource = new NodeResourceAsm().toResource(toNode);

                        sentEdgeResource.setFromNodeResource(fromNodeResource);
                        sentEdgeResource.setToNodeResource(toNodeResource);

                        Edge edge = null;
                        edge = sentEdgeResource.toEdge();

//                edge.getFill().setId(null);
                        Edge createdEdge = edgeService.create(edge);
                    }

                }

//                boolean isValidated = true;
//                Validate validate = new Validate();
//                Node updatedEntry = null;
//                for (NodeResource nodeResource : nodeListResource.getNodeResources()) {
//                    updatedEntry = nodeService.updateNodeCordinates(nodeResource.getNodeID(), nodeResource.toNode());
//                    if (updatedEntry == null) {
//                        throw new NotFoundException();
//                    }
//                }
//
//                if (updatedEntry != null) {
//                    NodeResource res = new NodeResourceAsm().toResource(updatedEntry);
//                    return new ResponseEntity<>(res, HttpStatus.OK);
//                } else {
//                    throw new NotFoundException();
//                }
                NodeList list = new NodeList(entryNodes);
                NodeListResource res = new NodeListResourceAsm().toResource(list);
                NodeAndEdgeListResource nodeAndEdgeResource = new NodeAndEdgeListResource();
                res.setStatus("Added :- " + addSucess + "; Updated :- " + updateSucess);
                nodeAndEdgeResource.setNodeListResource(res);
                nodeAndEdgeResource.setStatus("Added :- " + addSucess + "; Updated :- " + updateSucess);
                return new ResponseEntity<>(nodeAndEdgeResource, HttpStatus.OK);
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
            } catch (ConflictException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
            } catch (Exception exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN.value(), GlobalExceptionMessage.FORBIDDEN_APPLICATION_CODE, GlobalExceptionMessage.FORBIDDEN_MESSAGE, false, "N/A");
            writeLogMessage(info.getMessage());
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
        }
    }

    private boolean isEdgeAlreadyExist(Long node1ID, Long node2ID) throws Exception {
        Edge edge = edgeService.findEnabledByNodeIDs(node1ID, node2ID);
        return edge != null;
    }

    @RequestMapping(value = "/listallenabled/networkgroup/{networkGroupId}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> findAllEnabledNodesExculdingNodesInNetworkGroup(@PathVariable Long networkGroupId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
//                UserDetails details = (UserDetails) principal;
//                User loggedUser = userService.findUserByUsername(details.getUsername());
                System.out.println("ENTERING......");
                NetworkGroup networkGroup = networkGroupService.findNetworkGroup(networkGroupId);
                List<Long> exculdingNodeIdList = new ArrayList<>();
                for (NodeInNetworkGroup nodeInNetworkGroup : networkGroup.getNodesInNetworkGroup()) {
                    exculdingNodeIdList.add(nodeInNetworkGroup.getNode().getId());
                }

                //For Exculing Current NodeGroup's Node
                Node groupNode = nodeService.findByLabel(networkGroup.getCode());
                if (groupNode != null) {
                    exculdingNodeIdList.add(groupNode.getId());
                }
                //End For Exculing Current NodeGroup's Node

                NodeList list = nodeService.findAllEnabledNodesExculdingNodesInNetworkGroup(exculdingNodeIdList);
                NodeListResource res = new NodeListResourceAsm().toResource(list);
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
}
