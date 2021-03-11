/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.mvc.web;

import com.project.core.models.entities.common.ExceptionInfo;
import com.project.core.models.entities.network.Edge;
import com.project.core.models.entities.network.EdgeInNetworkGroup;
import com.project.core.models.entities.network.NetworkGroup;
import com.project.core.models.entities.network.Node;
import com.project.core.models.entities.network.NodeAlert;
import com.project.core.models.entities.network.NodeImageLink;
import com.project.core.models.entities.network.NodeInNetworkGroup;
import com.project.core.models.entities.network.RoleInNetworkGroup;

import com.project.core.models.entities.user.User;
import com.project.core.services.exceptions.NodeExistsException;
import com.project.core.services.exceptions.NodeNotFoundException;
import com.project.core.services.network.EdgeService;
import com.project.core.services.network.NetworkGroupService;
import com.project.core.services.network.NodeService;

//import com.project.core.services.exceptions.NodeExistsException;
import com.project.core.services.user.UserService;
import com.project.core.services.util.NetworkGroupList;
import com.project.core.util.DateConverter;
import com.project.core.util.Log4jUtil;
import com.project.core.util.Validate;
import com.project.rest.exceptions.BadRequestException;
import com.project.rest.exceptions.ConflictException;
import com.project.rest.exceptions.ForbiddenException;
import com.project.rest.exceptions.InternalServertException;
import com.project.rest.exceptions.NotFoundException;
import com.project.rest.mvc.common.BaseController;
import com.project.rest.resources.asm.network.EdgeInNetworkGroupResourceAsm;
import com.project.rest.resources.asm.network.NetworkGroupListResourceAsm;
import com.project.rest.resources.asm.network.NetworkGroupResourceAsm;
import com.project.rest.resources.asm.network.NodeInNetworkGroupListResourceAsm;
import com.project.rest.resources.asm.network.NodeInNetworkGroupResourceAsm;
import com.project.rest.resources.asm.network.RoleInNetworkGroupResourceAsm;
import com.project.rest.resources.network.EdgeInNetworkGroupListResource;
import com.project.rest.resources.network.EdgeInNetworkGroupResource;
import com.project.rest.resources.network.NetworkGroupListResource;
import com.project.rest.resources.network.NetworkGroupResource;
import com.project.rest.resources.network.NodeAndEdgeListInNetworkGroupResource;
import com.project.rest.resources.network.NodeInNetworkGroupListResource;
import com.project.rest.resources.network.NodeInNetworkGroupResource;
import com.project.rest.resources.network.NodeResource;
import com.project.rest.resources.network.RoleInNetworkGroupResource;
import com.project.rest.util.Global;
import com.project.rest.util.GlobalExceptionMessage;
import com.project.rest.util.searchcriteria.network.NetworkGroupSearchCriteria;
import java.net.URI;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
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
@RequestMapping("/rest/web/networkgroup")
public class NetworkGroupRESTController_ForWeb extends BaseController {

    @Autowired
    private NetworkGroupService networkGroupService;

    @Autowired
    private NodeService nodeService;

    @Autowired
    private EdgeService edgeService;

    @Autowired
    private UserService userService;

    private final String UNIQUE_APPLICATION_ID = "LALITPUR@MANGALBAZAR#5536666";

    @RequestMapping(value = "/find/{id}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> getNetworkGroup(
            @PathVariable Long id
    ) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                NetworkGroup networkGroup = networkGroupService.findNetworkGroup(id);
                if (networkGroup != null) {
                    NetworkGroupResource res = new NetworkGroupResourceAsm().toResource(networkGroup);
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
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/find/code/{code}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> getNetworkGroupResourceByCode(
            @PathVariable String code
    ) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                NetworkGroup networkGroup = networkGroupService.findNetworkGroupByCode(code);
                if (networkGroup != null) {
                    NetworkGroupResource res = new NetworkGroupResourceAsm().toResource(networkGroup);
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
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/create",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> createNetworkGroup(@RequestBody(required = false) NetworkGroupResource sentNetworkGroupResource
    ) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                UserDetails details = (UserDetails) principal;
                User loggedUser = userService.findUserByUsername(details.getUsername());

//                //START TEST
//                User loggedUser = null;
//                if (loggedUser == null) {
//                    loggedUser = new User();
//                    loggedUser.setUsername("TERST");
//                }
//                if (sentNetworkGroupResource == null) {
//                    sentNetworkGroupResource = new NetworkGroupResource();
//                    sentNetworkGroupResource.setDescription("TestData");
//                    sentNetworkGroupResource.setEntryByUserType("TestData");
//                    sentNetworkGroupResource.setEntryByUsername("TestData");
//                    sentNetworkGroupResource.setLastUpdateByUserType("TestData");
//                    sentNetworkGroupResource.setLastUpdateByUsername("TestData");
//                    sentNetworkGroupResource.setName("TestData");
//                    sentNetworkGroupResource.setStatusCode(0);
//                }
//                //END OF TEST
                //Validation Start
                boolean isValidated = true;

                Validate validate = new Validate();

                if (validate.isEmptyString(sentNetworkGroupResource.getName()) || (!validate.isValidDefaultTextLength(sentNetworkGroupResource.getName()))) {
                    isValidated = false;
                }

                if (validate.isEmptyString(sentNetworkGroupResource.getDescription()) || (!validate.isValidDefaultTextLength(sentNetworkGroupResource.getDescription()))) {
                    isValidated = false;
                }

                if (!isValidated) {
                    throw new BadRequestException();
                }
                //Validation Stop

                NetworkGroup networkGroup = null;

                String newRandomCode = RandomStringUtils.randomAlphanumeric(15);
                sentNetworkGroupResource.setCode(newRandomCode);

                sentNetworkGroupResource.setEntryByUserType(loggedUser.getUserType());
                sentNetworkGroupResource.setEntryByUsername(loggedUser.getUsername());
                sentNetworkGroupResource.setLastUpdateByUserType(loggedUser.getUserType());
                sentNetworkGroupResource.setLastUpdateByUsername(loggedUser.getUsername());
                sentNetworkGroupResource.setLastModifiedUnixTime(DateConverter.getCurrentConvertedDateAndTimeInUnixDate());

                networkGroup = sentNetworkGroupResource.toNetworkGroup();

                networkGroup.setId(null);
                NetworkGroup createdNetworkGroup = networkGroupService.createNetworkGroup(networkGroup);

                String newCode = "ng-" + createdNetworkGroup.getId();
                createdNetworkGroup.setCode(newCode);

                NetworkGroup readNetworkGroup = networkGroupService.findNetworkGroup(createdNetworkGroup.getId());
                readNetworkGroup.setCode(newCode);
                NetworkGroup createdAndUpdatedNetworkGroup = networkGroupService.updateNetworkGroupCode(createdNetworkGroup.getId(), readNetworkGroup);

                System.out.println(createdAndUpdatedNetworkGroup.getName() + "---");
                NetworkGroupResource res = new NetworkGroupResourceAsm().toResource(createdAndUpdatedNetworkGroup);

                // Create Node Of Type type-networkgroup
                Node node = new Node();
                node.setId(null);
                double domX = (int) (Math.random() * 800);
                double domY = (int) (Math.random() * 800);

                double convasX = (int) (Math.random() * 800);
                double convasY = (int) (Math.random() * 800);

                node.setCanvasXValue(convasX);
                node.setCanvasYValue(convasY);
                node.setDomXValue(domX);
                node.setDomYValue(domY);
                node.setZoomScale(1.0D);

                node.setEnabled(true);
                node.setHeight(10);
                node.setLabel(readNetworkGroup.getCode());
                node.setNodeValue(0);
                node.setTitle(readNetworkGroup.getName());
                node.setRedirectingURLLink("www.bbc.com");
                node.setType("type-networkgroup");
                node.setZoomScale(0);
                node.setDescription(readNetworkGroup.getName());
                node.setUniqueID("");

                NodeAlert nodeAlert = new NodeAlert();
                nodeAlert.setId(null);
                nodeAlert.setType(1);
                nodeAlert.setDescription("");
                node.setAlert(nodeAlert);

                NodeImageLink nodeImageLink = new NodeImageLink();
                nodeImageLink.setId(null);
                nodeImageLink.setSrc("networkgroup-type.png");
                node.setFill(nodeImageLink);

                Node createdNode = nodeService.create(node);
                // End Of Creating Node

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
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/update/{id}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> updateTraveleGroupBackup(
            @PathVariable Long id, @RequestBody(required = false) NetworkGroupResource sentNetworkGroupResource) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                UserDetails details = (UserDetails) principal;
                User loggedUser = userService.findUserByUsername(details.getUsername());

//                //START TEST
//                User loggedUser = null;
//                if (loggedUser == null) {
//                    loggedUser = new User();
//                    loggedUser.setUsername("UpdateTestData");
//                }
//                if (sentNetworkGroupResource == null) {
//                    sentNetworkGroupResource = new NetworkGroupResource();
//                    sentNetworkGroupResource.setDescription("UpdateTestData");
//                    sentNetworkGroupResource.setEntryByUserType("UpdateTestData");
//                    sentNetworkGroupResource.setEntryByUsername("UpdateTestData");
//                    sentNetworkGroupResource.setLastUpdateByUserType("UpdateTestData");
//                    sentNetworkGroupResource.setLastUpdateByUsername("UpdateTestData");
//                    sentNetworkGroupResource.setName("UpdateTestData");
//                    sentNetworkGroupResource.setStatusCode(1);
//                }
//                //END OF TEST
                //Validation Start
                boolean isValidated = true;
                Validate validate = new Validate();

                if (validate.isEmptyString(sentNetworkGroupResource.getDescription()) || (!validate.isValidDefaultTextLength(sentNetworkGroupResource.getDescription()))) {
                    isValidated = false;
                }

                if (!isValidated) {
                    throw new BadRequestException();
                }

                //Validation Stop
                NetworkGroup networkGroup = sentNetworkGroupResource.toNetworkGroup();

                NetworkGroup readNetworkGroup = networkGroupService.findNetworkGroup(id);
                readNetworkGroup.setDescription(networkGroup.getDescription());
                readNetworkGroup.setEnabled(networkGroup.isEnabled());
                readNetworkGroup.setLastUpdateByUserType(loggedUser.getUserType());
                readNetworkGroup.setLastUpdateByUsername(loggedUser.getUsername());
                readNetworkGroup.setName(networkGroup.getName());
                readNetworkGroup.setStatusCode(networkGroup.getStatusCode());

                readNetworkGroup.setRolesInNetworkGroup(networkGroup.getRolesInNetworkGroup());

                //Clearing All And Inserting New
                readNetworkGroup.getRolesInNetworkGroup().clear();
                for (RoleInNetworkGroupResource inNetworkGroupResource : sentNetworkGroupResource.getRoleInNetworkGroupListResources()) {
                    RoleInNetworkGroup roleInNetworkGroup = inNetworkGroupResource.toRoleInNetworkGroup();
                    roleInNetworkGroup.setId(null);
                    readNetworkGroup.getRolesInNetworkGroup().add(roleInNetworkGroup);
                }

//                 javax.swing.JOptionPane.showMessageDialog(null, roleNames);
                String roleNames = sentNetworkGroupResource.getRoleInNetworkGroupListResources()
                        .stream()
                        .map(a -> String.valueOf(a.getRoleResource().getName()))
                        .collect(Collectors.joining("#"));
//                javax.swing.JOptionPane.showMessageDialog(null, roleNames);
                readNetworkGroup.setRoles(roleNames);
//  String nodeIds = StringUtils.join(sentNetworkGroupResource.getRoleInNetworkGroupListResources().get, ',');
                NetworkGroup updatedEntry = networkGroupService.updateNetworkGroup(id, readNetworkGroup);

                if (updatedEntry != null) {

                    // Updating Nodes 
                    Node node = nodeService.findByLabel(updatedEntry.getCode());
                    node.setLabel(readNetworkGroup.getCode());
                    node.setNodeValue(0);
                    node.setTitle(updatedEntry.getName());
                    node.setDescription(readNetworkGroup.getName());
                    Node updateNode = nodeService.update(node.getId(), node);
                    // Finish Updating Nodes

                    NetworkGroupResource res = new NetworkGroupResourceAsm().toResource(updatedEntry);
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
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/list",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> findNetworkGroupsBySearchCriteria(@RequestBody(required = false) NetworkGroupSearchCriteria searchCriteria) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {

            try {
                UserDetails details = (UserDetails) principal;
                User loggedUser = userService.findUserByUsername(details.getUsername());

//            //START TEST
//            User loggedUser = null;
//            if (loggedUser == null) {
//                loggedUser = new User();
//                loggedUser.setUsername("UpdateTestData");
//            }
//            //END OF TEST
                if (searchCriteria == null) {
                    searchCriteria = new NetworkGroupSearchCriteria();
                }

                if (searchCriteria.getLimitResult() <= 0) {
                    searchCriteria.setLimitResult(10);
                }

                fixRemainingValuesInSearchCriteria(searchCriteria);

                NetworkGroupList list = networkGroupService.findNetworkGroupsBySearchCriteria(searchCriteria, loggedUser);
                NetworkGroupListResource res = new NetworkGroupListResourceAsm().toResource(list);

                return new ResponseEntity<>(res, HttpStatus.OK);
            } catch (NotFoundException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
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
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/delete/{id}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> deleteNetworkGroup(
            @PathVariable Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                NetworkGroup entry = networkGroupService.deleteNetworkGroup(id);
                //Deleteing Nodes
                nodeService.deleteNodeByLabel(entry.getCode());
                if (entry != null) {
                    NetworkGroupResource res = new NetworkGroupResourceAsm().toResource(entry);
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
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/update/{id}/add/nodeId/{nodeId}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> updateTraveleGroupByAddNode(
            @PathVariable Long id, @PathVariable Long nodeId, @RequestBody(required = false) NodeInNetworkGroupResource sentNodeInNetworkGroupResource) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {

                //START TEST
                if (sentNodeInNetworkGroupResource == null) {
                    NodeResource nodeResource = new NodeResource();
                    nodeResource.setNodeID(417L);

                    sentNodeInNetworkGroupResource = new NodeInNetworkGroupResource();
                    sentNodeInNetworkGroupResource.setDescription("Default Description");
                    sentNodeInNetworkGroupResource.setCanvasXValue(10);
                    sentNodeInNetworkGroupResource.setCanvasYValue(10);
                    sentNodeInNetworkGroupResource.setDomXValue(10);
                    sentNodeInNetworkGroupResource.setDomYValue(10);
                    sentNodeInNetworkGroupResource.setEnabled(true);
                    sentNodeInNetworkGroupResource.setNodeInNetworkGroupID(null);
                    sentNodeInNetworkGroupResource.setSelected(true);
                    sentNodeInNetworkGroupResource.setZoomScale(5);

                    sentNodeInNetworkGroupResource.setNodeResource(nodeResource);

                }
                //END OF TEST

                //Validation Start
                boolean isValidated = true;
                Validate validate = new Validate();

                if (validate.isEmptyString(sentNodeInNetworkGroupResource.getDescription()) || (!validate.isValidDefaultTextLength(sentNodeInNetworkGroupResource.getDescription()))) {
                    isValidated = false;
                }

                if (!isValidated) {
                    throw new BadRequestException();
                }
                //Validation Stop

                NetworkGroup readNetworkGroup = networkGroupService.findNetworkGroup(id);

                Node readNode = nodeService.find(nodeId);
                if (readNode == null) {
                    throw new NodeNotFoundException();
                }

                NodeInNetworkGroup sentNodeInNetworkGroup = sentNodeInNetworkGroupResource.toNodeInNetworkGroup();

                sentNodeInNetworkGroup.setNode(readNode);
                readNetworkGroup.getNodesInNetworkGroup().add(sentNodeInNetworkGroup);

                NetworkGroup updatedEntry = networkGroupService.updateNetworkGroupForAddNodes(id, readNetworkGroup, readNode);

                if (updatedEntry != null) {
                    NetworkGroupResource res = new NetworkGroupResourceAsm().toResource(updatedEntry);
                    return new ResponseEntity<>(res, HttpStatus.OK);
                } else {
                    throw new NotFoundException();
                }
            } catch (NodeNotFoundException | NotFoundException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
            } catch (BadRequestException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
            } catch (NodeExistsException | ConflictException exception) {
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
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
        }

    }

    @RequestMapping(value = "/update/{id}/remove/nodenetworkgroupid/{nodenetworkgroupId}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> updateTraveleGroupByRemovingNode(
            @PathVariable Long id, @PathVariable Long nodenetworkgroupId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {

            try {
                NetworkGroup readNetworkGroup = networkGroupService.findNetworkGroup(id);
                Iterator<NodeInNetworkGroup> iter = readNetworkGroup.getNodesInNetworkGroup().iterator();
                while (iter.hasNext()) {
                    NodeInNetworkGroup str = iter.next();
                    if (Objects.equals(str.getId(), nodenetworkgroupId)) {
                        iter.remove();
                        break;
                    }
                }
                readNetworkGroup.setLastModifiedUnixTime(DateConverter.getCurrentConvertedDateAndTimeInUnixDate());
                NetworkGroup updatedEntry = networkGroupService.updateNetworkGroupForUpdateAndRemoveNodes(id, readNetworkGroup);
                if (updatedEntry != null) {
                    NetworkGroupResource res = new NetworkGroupResourceAsm().toResource(updatedEntry);
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
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/update/{id}/update/nodenetowrkgroupid/{nodenetworkgroupId}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> updateTraveleGroupByUpdateingNode(
            @PathVariable Long id, @PathVariable Long nodenetworkgroupId, @RequestBody(required = false) NodeInNetworkGroupResource sentNodeInNetworkGroupResource) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {

                //Validation Start
                boolean isValidated = true;
                Validate validate = new Validate();

                if (validate.isEmptyString(sentNodeInNetworkGroupResource.getDescription()) || (!validate.isValidDefaultTextLength(sentNodeInNetworkGroupResource.getDescription()))) {
                    isValidated = false;

                }

                if (!isValidated) {
                    throw new BadRequestException();
                }
                //Validation Stop

                NetworkGroup readNetworkGroup = networkGroupService.findNetworkGroup(id);
                Iterator<NodeInNetworkGroup> iter = readNetworkGroup.getNodesInNetworkGroup().iterator();
                while (iter.hasNext()) {
                    NodeInNetworkGroup str = iter.next();
                    if (Objects.equals(str.getId(), nodenetworkgroupId)) {
                        str.setDescription(sentNodeInNetworkGroupResource.getDescription());
                        break;
                    }
                }
                NetworkGroup updatedEntry = networkGroupService.updateNetworkGroupForUpdateAndRemoveNodes(id, readNetworkGroup);
                if (updatedEntry != null) {
                    NetworkGroupResource res = new NetworkGroupResourceAsm().toResource(updatedEntry);
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
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
        }

    }

    @RequestMapping(value = "/update_new/{id}/add/nodes",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> updateNewNetworkGroupByAddNode(
            @PathVariable Long id, @RequestBody(required = false) NodeInNetworkGroupListResource nodeInNetworkGroupListResource) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {

                if (nodeInNetworkGroupListResource == null) {
                    nodeInNetworkGroupListResource = new NodeInNetworkGroupListResource();

                    NodeInNetworkGroupResource sentNodeInNetworkGroupResource = new NodeInNetworkGroupResource();
                    NodeResource nodeResource = new NodeResource();
                    nodeResource.setNodeID(417L);
                    sentNodeInNetworkGroupResource = new NodeInNetworkGroupResource();
                    sentNodeInNetworkGroupResource.setDescription("Default Description Update A10");
                    sentNodeInNetworkGroupResource.setCanvasXValue(1000);
                    sentNodeInNetworkGroupResource.setCanvasYValue(1000);
                    sentNodeInNetworkGroupResource.setDomXValue(1000);
                    sentNodeInNetworkGroupResource.setDomYValue(1000);
                    sentNodeInNetworkGroupResource.setEnabled(true);
                    sentNodeInNetworkGroupResource.setNodeInNetworkGroupID(null);
                    sentNodeInNetworkGroupResource.setSelected(true);
                    sentNodeInNetworkGroupResource.setZoomScale(500);
                    sentNodeInNetworkGroupResource.setNodeResource(nodeResource);

                    NodeResource nodeResource2 = new NodeResource();
                    nodeResource2.setNodeID(419L);
                    NodeInNetworkGroupResource sentNodeInNetworkGroupResource2 = new NodeInNetworkGroupResource();
                    sentNodeInNetworkGroupResource2.setDescription("Default Description Update C10");
                    sentNodeInNetworkGroupResource2.setCanvasXValue(2000);
                    sentNodeInNetworkGroupResource2.setCanvasYValue(2000);
                    sentNodeInNetworkGroupResource2.setDomXValue(2000);
                    sentNodeInNetworkGroupResource2.setDomYValue(2000);
                    sentNodeInNetworkGroupResource2.setEnabled(true);
                    sentNodeInNetworkGroupResource2.setNodeInNetworkGroupID(null);
                    sentNodeInNetworkGroupResource2.setSelected(true);
                    sentNodeInNetworkGroupResource2.setZoomScale(2500);

                    sentNodeInNetworkGroupResource2.setNodeResource(nodeResource2);

                    nodeInNetworkGroupListResource.getNodeInNetworkGroupResources().add(sentNodeInNetworkGroupResource);
                    nodeInNetworkGroupListResource.getNodeInNetworkGroupResources().add(sentNodeInNetworkGroupResource2);

                }

                List<NodeInNetworkGroup> toAddNodeInNetworkGroup = new ArrayList<>();
                List<NodeInNetworkGroup> toUpdateNodeInNetworkGroup = new ArrayList<>();

                int addSucess = 0;
                int updateSucess = 0;

                NetworkGroup readNetworkGroup = networkGroupService.findNetworkGroup(id);

                for (NodeInNetworkGroupResource sentNodeInNetworkGroupResource : nodeInNetworkGroupListResource.getNodeInNetworkGroupResources()) {

                    if (sentNodeInNetworkGroupResource.isSelected()) {
                        if (isNetworkGroupAlreadyExist(readNetworkGroup.getCode(), sentNodeInNetworkGroupResource.getNodeResource().toNode())) {
                            toUpdateNodeInNetworkGroup.add(sentNodeInNetworkGroupResource.toNodeInNetworkGroup());
                        } else {
                            toAddNodeInNetworkGroup.add(sentNodeInNetworkGroupResource.toNodeInNetworkGroup());
                        }
                    }
                }

                NetworkGroup updatedEntryAfterAdd = networkGroupService.updateNetworkGroupForAddNodes_NEW(id, toAddNodeInNetworkGroup);
                NetworkGroup updatedEntryAfterEdit = networkGroupService.updateNetworkGroupForUpdateNodes_NEW(id, toUpdateNodeInNetworkGroup);

                NetworkGroup updatedEntry = networkGroupService.findNetworkGroup(id);

                if (updatedEntry != null) {
                    NetworkGroupResource res = new NetworkGroupResourceAsm().toResource(updatedEntry);
                    return new ResponseEntity<>(res, HttpStatus.OK);
                } else {
                    throw new NotFoundException();
                }
            } catch (NodeNotFoundException | NotFoundException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
            } catch (BadRequestException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
            } catch (NodeExistsException | ConflictException exception) {
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
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
        }

    }

    @RequestMapping(value = "/update_new/{id}/add/nodes_edges",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> updateNewNetworkGroupByAddNodesAndEdges(@PathVariable Long id, @RequestBody(required = false) NodeAndEdgeListInNetworkGroupResource nodeAndEdgeListResource) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                UserDetails details = (UserDetails) principal;
                User loggedUser = userService.findUserByUsername(details.getUsername());
                List<NodeInNetworkGroup> toAddNodeInNetworkGroup = new ArrayList<>();
                List<NodeInNetworkGroup> toUpdateNodeInNetworkGroup = new ArrayList<>();

                int addSucess = 0;
                int updateSucess = 0;

                List<Node> entryNodes = new ArrayList<>();

                NetworkGroup readNetworkGroup = networkGroupService.findNetworkGroup(id);

                for (NodeInNetworkGroupResource sentNodeInNetworkGroupResource : nodeAndEdgeListResource.getNodeInNetworkGroupListResource().getNodeInNetworkGroupResources()) {

                    if (sentNodeInNetworkGroupResource.isSelected()) {
                        if (isNetworkGroupAlreadyExist(readNetworkGroup.getCode(), sentNodeInNetworkGroupResource.getNodeResource().toNode())) {
                            toUpdateNodeInNetworkGroup.add(sentNodeInNetworkGroupResource.toNodeInNetworkGroup());
                        } else {
                            toAddNodeInNetworkGroup.add(sentNodeInNetworkGroupResource.toNodeInNetworkGroup());
                        }
                    }
                }

                NetworkGroup updatedEntryAfterAdd = networkGroupService.updateNetworkGroupForAddNodes_NEW(id, toAddNodeInNetworkGroup);
                NetworkGroup updatedEntryAfterEdit = networkGroupService.updateNetworkGroupForUpdateNodes_NEW(id, toUpdateNodeInNetworkGroup);

//                for (EdgeResource sentEdgeResource : nodeAndEdgeListResource.getEdgeListResource().getEdgeResources()) {
//
//                    if (isEdgeAlreadyExist(sentEdgeResource.getFromNodeResource().getNodeID(), sentEdgeResource.getToNodeResource().getNodeID())) {
////Update Or Skip
//                    } else {
////Insert New Edges
//
//                        Node fromNode = nodeService.findByLabel(sentEdgeResource.getFromNodeResource().getLabel());
//                        Node toNode = nodeService.findByLabel(sentEdgeResource.getToNodeResource().getLabel());
//
//                        sentEdgeResource.setEdgeID(null);
//                        sentEdgeResource.setArrows("");
//                        sentEdgeResource.setDashes(false);
//                        sentEdgeResource.setEdge_length(15);
//                        sentEdgeResource.setEdge_value(15);
//                        sentEdgeResource.setEnabled(true);
//                        sentEdgeResource.setLabel("");
//                        sentEdgeResource.setTitle(sentEdgeResource.getFromNodeResource().getLabel() + "-To-" + sentEdgeResource.getToNodeResource().getLabel());
//
//                        NodeResource fromNodeResource = new NodeResourceAsm().toResource(fromNode);
//                        NodeResource toNodeResource = new NodeResourceAsm().toResource(toNode);
//
//                        sentEdgeResource.setFromNodeResource(fromNodeResource);
//                        sentEdgeResource.setToNodeResource(toNodeResource);
//
//                        Edge edge = null;
//                        edge = sentEdgeResource.toEdge();
//;
//                        Edge createdEdge = edgeService.create(edge);
//                    }
//
//                }
//                NodeList list = new NodeList(entryNodes);
//                NodeListResource res = new NodeListResourceAsm().toResource(list);
//                NodeAndEdgeListInNetworkGroupResource nodeAndEdgeResource = new NodeAndEdgeListInNetworkGroupResource();
//                res.setStatus("Added :- " + addSucess + "; Updated :- " + updateSucess);
//                nodeAndEdgeResource.setNodeListResource(res);
//                nodeAndEdgeResource.setStatus("Added :- " + addSucess + "; Updated :- " + updateSucess);
                NetworkGroup updatedEntry = networkGroupService.findNetworkGroup(id);
                if (updatedEntry != null) {
                    NetworkGroupResource res = new NetworkGroupResourceAsm().toResource(updatedEntry);
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

    @RequestMapping(value = "/update_new/{id}/remove/nodes",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> updateNewTraveleGroupToRemovedNodes(
            @PathVariable Long id, @RequestBody(required = false) NodeInNetworkGroupListResource nodeInNetworkGroupListResource) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {

                if (nodeInNetworkGroupListResource == null) {
                    nodeInNetworkGroupListResource = new NodeInNetworkGroupListResource();

                    NodeInNetworkGroupResource sentNodeInNetworkGroupResource = new NodeInNetworkGroupResource();
                    NodeResource nodeResource = new NodeResource();
                    nodeResource.setNodeID(417L);
                    sentNodeInNetworkGroupResource = new NodeInNetworkGroupResource();
                    sentNodeInNetworkGroupResource.setDescription("Default Description Update A1");
                    sentNodeInNetworkGroupResource.setCanvasXValue(100);
                    sentNodeInNetworkGroupResource.setCanvasYValue(100);
                    sentNodeInNetworkGroupResource.setDomXValue(100);
                    sentNodeInNetworkGroupResource.setDomYValue(100);
                    sentNodeInNetworkGroupResource.setEnabled(true);
                    sentNodeInNetworkGroupResource.setNodeInNetworkGroupID(null);
                    sentNodeInNetworkGroupResource.setSelected(true);
                    sentNodeInNetworkGroupResource.setZoomScale(50);
                    sentNodeInNetworkGroupResource.setNodeResource(nodeResource);
                    nodeInNetworkGroupListResource.getNodeInNetworkGroupResources().add(sentNodeInNetworkGroupResource);

                }

                List<NodeInNetworkGroup> toRemoveNodesInNetworkGroup = new ArrayList<>();

                int addSucess = 0;
                int updateSucess = 0;

                NetworkGroup readNetworkGroup = networkGroupService.findNetworkGroup(id);

                for (NodeInNetworkGroupResource sentNodeInNetworkGroupResource : nodeInNetworkGroupListResource.getNodeInNetworkGroupResources()) {
                    if (isNetworkGroupAlreadyExistForNodeInNetworkGroup(readNetworkGroup.getCode(), sentNodeInNetworkGroupResource.toNodeInNetworkGroup())) {
                        toRemoveNodesInNetworkGroup.add(sentNodeInNetworkGroupResource.toNodeInNetworkGroup());
                    }
                }

                // Filtering Out Edges To Be Removed
                List<EdgeInNetworkGroup> toRemoveEdgesInNetworkGroup = new ArrayList<>();
//                NetworkGroup readNetworkGroupForDeleteEdges = networkGroupService.findNetworkGroup(id);
                for (EdgeInNetworkGroup edgeInNetworkGroup : readNetworkGroup.getEdgesInNetworkGroup()) {
                    for (NodeInNetworkGroup nodeInNetworkGroup : toRemoveNodesInNetworkGroup) {
                        if (edgeInNetworkGroup.getFromNodeInNetworkGroup().getId().equals(nodeInNetworkGroup.getId()) || edgeInNetworkGroup.getToNodeInNetworkGroup().getId().equals(nodeInNetworkGroup.getId())) {
                            toRemoveEdgesInNetworkGroup.add(edgeInNetworkGroup);
                        }
                    }

                }

                NetworkGroup updatedEntryAfterRemovingEdges = networkGroupService.updateNetworkGroupForRemoveEdges_NEW(id, toRemoveEdgesInNetworkGroup);

                NetworkGroup updatedEntryAfterRemovingNodes = networkGroupService.updateNetworkGroupForRemoveNodes_NEW(id, toRemoveNodesInNetworkGroup);

                if (updatedEntryAfterRemovingNodes != null) {
                    NetworkGroupResource res = new NetworkGroupResourceAsm().toResource(updatedEntryAfterRemovingNodes);
                    return new ResponseEntity<>(res, HttpStatus.OK);
                } else {
                    throw new NotFoundException();
                }
            } catch (NodeNotFoundException | NotFoundException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
            } catch (BadRequestException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
            } catch (NodeExistsException | ConflictException exception) {
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
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
        }

    }

    private void fixRemainingValuesInSearchCriteria(NetworkGroupSearchCriteria searchCriteria) {

        if (searchCriteria.getDateFrom() == null || searchCriteria.getDateFrom().trim().equals("")) {
            searchCriteria.setDateFrom(DateConverter.getCurrentDate());
        }

        if (searchCriteria.getDateTo() == null || searchCriteria.getDateTo().trim().equals("")) {
            searchCriteria.setDateTo(DateConverter.getCurrentDate());
        }

        Timestamp fromDeviceTimestamp = Timestamp.valueOf(searchCriteria.getDateFrom() + " 00:00:00.000000000");
        int fromTime = (int) (fromDeviceTimestamp.getTime() / 1000L);

        Timestamp toDeviceTimestamp = Timestamp.valueOf(searchCriteria.getDateTo() + " 23:59:59.999999999");
        int toTime = (int) (toDeviceTimestamp.getTime() / 1000L);

        if (toTime < fromTime) {
            toTime = fromTime;
        }

        searchCriteria.setFromDateTime(fromTime);
        searchCriteria.setToDateTime(toTime);

        if (searchCriteria.getSearchedText() == null) {
            searchCriteria.setSearchedText("");
        }

    }

    @RequestMapping(value = "/nodeinnetworkgroup/find/{id}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> getNodeInNetworkGroup(
            @PathVariable Long id
    ) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                NodeInNetworkGroup nodeInNetworkGroup = networkGroupService.findNodeInNetworkGroup(id);

                if (nodeInNetworkGroup != null) {
                    NodeInNetworkGroupResource res = new NodeInNetworkGroupResourceAsm().toResource(nodeInNetworkGroup);
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

    @RequestMapping(value = "/roleinnetworkgroup/find/{id}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> getRoleInNetworkGroup(
            @PathVariable Long id
    ) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                RoleInNetworkGroup roleInNetworkGroup = networkGroupService.findRoleInNetworkGroup(id);

                if (roleInNetworkGroup != null) {
                    RoleInNetworkGroupResource res = new RoleInNetworkGroupResourceAsm().toResource(roleInNetworkGroup);
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

    @RequestMapping(value = "/nodeinnetworkgroup/find/{id}/networkgroup",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> getNetworkGroupByNodeInNetworkGroupResourceId(@PathVariable Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                NetworkGroup nodeInNetworkGroup = networkGroupService.findNetworkGroupByNodeInNetworkGroupId(id);

                if (nodeInNetworkGroup != null) {
                    NetworkGroupResource res = new NetworkGroupResourceAsm().toResource(nodeInNetworkGroup);
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

    @RequestMapping(value = "/update_new/{id}/add/edges",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> updateNewNetworkGroupByAddEdges(
            @PathVariable Long id, @RequestBody(required = false) EdgeInNetworkGroupListResource edgeInNetworkGroupListResource) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {

                if (edgeInNetworkGroupListResource == null) {
                    edgeInNetworkGroupListResource = new EdgeInNetworkGroupListResource();

                }

                List<EdgeInNetworkGroup> toAddEdgesInNetworkGroup = new ArrayList<>();
                List<EdgeInNetworkGroup> toUpdateEdgesInNetworkGroup = new ArrayList<>();

                int addSucess = 0;
                int updateSucess = 0;

                NetworkGroup readNetworkGroup = networkGroupService.findNetworkGroup(id);

                for (EdgeInNetworkGroupResource sentNodeInNetworkGroupResource : edgeInNetworkGroupListResource.getEdgeInNetworkGroupResources()) {

//                        if (isNetworkGroupAlreadyExist(readNetworkGroup.getCode(), sentNodeInNetworkGroupResource.getEdgeResource().toEdge())) {
//                            toUpdateEdgesInNetworkGroup.add(sentNodeInNetworkGroupResource.toNodeInNetworkGroup());
//                        } else {
                    sentNodeInNetworkGroupResource.setEdgeInNetworkGroupID(null);
                    toAddEdgesInNetworkGroup.add(sentNodeInNetworkGroupResource.toEdgeInNetworkGroup());
//                        }

                }

                NetworkGroup updatedEntryAfterAdd = networkGroupService.updateNetworkGroupForAddEdges_NEW(id, toAddEdgesInNetworkGroup);
                NetworkGroup updatedEntryAfterEdit = networkGroupService.updateNetworkGroupForUpdateEdges_NEW(id, toUpdateEdgesInNetworkGroup);

                NetworkGroup updatedEntry = networkGroupService.findNetworkGroup(id);

                if (updatedEntry != null) {
                    NetworkGroupResource res = new NetworkGroupResourceAsm().toResource(updatedEntry);
                    return new ResponseEntity<>(res, HttpStatus.OK);
                } else {
                    throw new NotFoundException();
                }
            } catch (NodeNotFoundException | NotFoundException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
            } catch (BadRequestException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
            } catch (NodeExistsException | ConflictException exception) {
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
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
        }

    }

    @RequestMapping(value = "/edgeinnetworkgroup/find/{id}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> getEdgeInNetworkGroup(
            @PathVariable Long id
    ) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                EdgeInNetworkGroup edgeInNetworkGroup = networkGroupService.findEdgeInNetworkGroup(id);

                if (edgeInNetworkGroup != null) {
                    EdgeInNetworkGroupResource res = new EdgeInNetworkGroupResourceAsm().toResource(edgeInNetworkGroup);
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

    @RequestMapping(value = "/update_new/{id}/remove/edges",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> updateNewNetworkGroupToRemoveEdges(
            @PathVariable Long id, @RequestBody(required = false) EdgeInNetworkGroupListResource edgeInNetworkGroupListResource) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {

                List<EdgeInNetworkGroup> toRemoveNodesInNetworkGroup = new ArrayList<>();

                int addSucess = 0;
                int updateSucess = 0;

                NetworkGroup readNetworkGroup = networkGroupService.findNetworkGroup(id);

                for (EdgeInNetworkGroupResource sentEdgeInNetworkGroupResource : edgeInNetworkGroupListResource.getEdgeInNetworkGroupResources()) {
                    if (isNetworkGroupAlreadyExistForEdgeInNetworkGroup(readNetworkGroup.getCode(), sentEdgeInNetworkGroupResource.toEdgeInNetworkGroup())) {
                        toRemoveNodesInNetworkGroup.add(sentEdgeInNetworkGroupResource.toEdgeInNetworkGroup());
                    }
                }

                NetworkGroup updatedEntry = networkGroupService.updateNetworkGroupForRemoveEdges_NEW(id, toRemoveNodesInNetworkGroup);

                if (updatedEntry != null) {
                    NetworkGroupResource res = new NetworkGroupResourceAsm().toResource(updatedEntry);
                    return new ResponseEntity<>(res, HttpStatus.OK);
                } else {
                    throw new NotFoundException();
                }
            } catch (NodeNotFoundException | NotFoundException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
            } catch (BadRequestException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
            } catch (NodeExistsException | ConflictException exception) {
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
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
        }

    }

    @RequestMapping(value = "/update_new/{id}/update/edges",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> updateNewNetworkGroupByUpdateEdges(
            @PathVariable Long id, @RequestBody(required = false) EdgeInNetworkGroupListResource edgeInNetworkGroupListResource) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {

                if (edgeInNetworkGroupListResource == null) {
                    edgeInNetworkGroupListResource = new EdgeInNetworkGroupListResource();

                }

//                List<EdgeInNetworkGroup> toAddEdgesInNetworkGroup = new ArrayList<>();
                List<EdgeInNetworkGroup> toUpdateEdgesInNetworkGroup = new ArrayList<>();

                int addSucess = 0;
                int updateSucess = 0;

                NetworkGroup readNetworkGroup = networkGroupService.findNetworkGroup(id);

                for (EdgeInNetworkGroupResource sentNodeInNetworkGroupResource : edgeInNetworkGroupListResource.getEdgeInNetworkGroupResources()) {

//                        if (isNetworkGroupAlreadyExist(readNetworkGroup.getCode(), sentNodeInNetworkGroupResource.getEdgeResource().toEdge())) {
                    toUpdateEdgesInNetworkGroup.add(sentNodeInNetworkGroupResource.toEdgeInNetworkGroup());
//                        } else {
//                    sentNodeInNetworkGroupResource.setEdgeInNetworkGroupID(null);
//                    toAddEdgesInNetworkGroup.add(sentNodeInNetworkGroupResource.toEdgeInNetworkGroup());
//                        }

                }

//                NetworkGroup updatedEntryAfterAdd = networkGroupService.updateNetworkGroupForAddEdges_NEW(id, toAddEdgesInNetworkGroup);
                NetworkGroup updatedEntryAfterEdit = networkGroupService.updateNetworkGroupForUpdateEdges_NEW(id, toUpdateEdgesInNetworkGroup);

                NetworkGroup updatedEntry = networkGroupService.findNetworkGroup(id);

                if (updatedEntry != null) {
                    NetworkGroupResource res = new NetworkGroupResourceAsm().toResource(updatedEntry);
                    return new ResponseEntity<>(res, HttpStatus.OK);
                } else {
                    throw new NotFoundException();
                }
            } catch (NodeNotFoundException | NotFoundException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
            } catch (BadRequestException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
            } catch (NodeExistsException | ConflictException exception) {
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
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
        }

    }

    @RequestMapping(value = "/find/{id}/nodeinnetworkgroupid_1/{nodeInNetworkGroupId1}/nodeinnetworkgroupid_2/{nodeInNetworkGroupId2}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> getNetworkGroupByMatchingAnyTwoNodes(
            @PathVariable Long id, @PathVariable Long nodeInNetworkGroupId1, @PathVariable Long nodeInNetworkGroupId2) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {

                NetworkGroup networkGroup = networkGroupService.findNetworkGroupIfNodeInNetworkGroupsAreAlreadyExists(id, nodeInNetworkGroupId1, nodeInNetworkGroupId2);

                if (networkGroup != null) {
                    NetworkGroupResource res = new NetworkGroupResourceAsm().toResource(networkGroup);
                    return new ResponseEntity<>(res, HttpStatus.OK);
                } else {
                    throw new NotFoundException();
                }
            } catch (NodeNotFoundException | NotFoundException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
            } catch (BadRequestException exception) {
                writeLogMessage(exception);
                Log4jUtil.error(exception.toString());
                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
            } catch (NodeExistsException | ConflictException exception) {
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
            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
        }

    }

    @RequestMapping(value = "/find_all_enabled_networkgroupid",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> getAllEnabledNetworkGroupId() {

        try {
            List<String> longs = networkGroupService.findAllEnabledNetworkGroupsIdsWithNames();
            if (longs != null) {
            } else {
                longs = new ArrayList<>();
            }

            String nodeIds = StringUtils.join(longs, ',');
            return new ResponseEntity<>(nodeIds, HttpStatus.OK);
        } catch (NotFoundException exception) {
            writeLogMessage(exception);
            Log4jUtil.error(exception.toString());
            Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
            ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
            return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
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

    }

    @RequestMapping(value = "/update/{id}/nodeinnetworkgroups/cordinates",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> updateCordinatesOfNode(@PathVariable Long id, @RequestBody(required = false) NodeInNetworkGroupListResource nodeListResource) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {

                boolean isValidated = true;
                Validate validate = new Validate();
                NetworkGroup networkGroup = networkGroupService.findNetworkGroup(id);
                if (networkGroup != null) {
                    NodeInNetworkGroup updatedEntry = null;

                    for (NodeInNetworkGroupResource nodeResource : nodeListResource.getNodeInNetworkGroupResources()) {
                        updatedEntry = networkGroupService.updateNodeInNetworkGroupCordinates(nodeResource.getNodeInNetworkGroupID(), nodeResource.toNodeInNetworkGroup());
                        if (updatedEntry == null) {
                            throw new NotFoundException();
                        }
                    }

                    NetworkGroupResource res = new NetworkGroupResourceAsm().toResource(networkGroup);
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

    @RequestMapping(value = "/update/{id}/add/edges_from_parent",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> updateNetworkGroupToAddEdgeInNetworkGroupsByFetchingFromRootEdges(@PathVariable Long id) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof UserDetails) {
        try {
            NetworkGroup networkGroup = networkGroupService.findNetworkGroup(id);

            HashMap<Long, NodeInNetworkGroup> nodes = new HashMap<>();

            for (NodeInNetworkGroup nodeInNetworkGroup1 : networkGroup.getNodesInNetworkGroup()) {
                nodes.put(nodeInNetworkGroup1.getNode().getId(), nodeInNetworkGroup1);
            }

            HashMap<Long, Edge> edges = new HashMap<>();
            List<EdgeInNetworkGroup> edgeInNetworkGroups = new ArrayList<EdgeInNetworkGroup>();

            for (NodeInNetworkGroup nodeInNetworkGroup1 : networkGroup.getNodesInNetworkGroup()) {
                for (NodeInNetworkGroup nodeInNetworkGroup2 : networkGroup.getNodesInNetworkGroup()) {
                    Edge edge = edgeService.findEnabledByNodeIDs(nodeInNetworkGroup1.getNode().getId(), nodeInNetworkGroup2.getNode().getId());

                    if (edge != null) {
                        edges.put(edge.getId(), edge);

                    }

                }
            }
//            javax.swing.JOptionPane.showMessageDialog(null, edges.size());

            for (Map.Entry me : edges.entrySet()) {
//                System.out.println("Key: " + me.getKey() + " & Value: " + me.getValue());
                System.out.println("_________matchings_________");
                System.out.println(((Edge) me.getValue()).getFromNode().getLabel() + " To " + ((Edge) me.getValue()).getToNode().getLabel());
                Edge edge = ((Edge) me.getValue());
                EdgeInNetworkGroup edgeInNetworkGroup = new EdgeInNetworkGroup();
                edgeInNetworkGroup.setArrows(edge.getArrows());
                edgeInNetworkGroup.setDashes(edge.isDashes());
                edgeInNetworkGroup.setDescription("");
                edgeInNetworkGroup.setEdge_length(edge.getEdge_length());
                edgeInNetworkGroup.setEdge_value(edge.getEdge_value());
                edgeInNetworkGroup.setEnabled(true);
                edgeInNetworkGroup.setId(null);
                edgeInNetworkGroup.setLabel(edge.getLabel());
                edgeInNetworkGroup.setTitle(edge.getTitle());

                edgeInNetworkGroup.setFromNodeInNetworkGroup(nodes.get(edge.getFromNode().getId()));
                edgeInNetworkGroup.setToNodeInNetworkGroup(nodes.get(edge.getToNode().getId()));

                edgeInNetworkGroups.add(edgeInNetworkGroup);

            }
            networkGroupService.clearAndUpdateNetworkGroupForAddEdges_NEW(id, edgeInNetworkGroups);

            if (networkGroup != null) {
                NetworkGroupResource res = new NetworkGroupResourceAsm().toResource(networkGroup);
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
//        } else {
//            ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN.value(), GlobalExceptionMessage.FORBIDDEN_APPLICATION_CODE, GlobalExceptionMessage.FORBIDDEN_MESSAGE, false, "N/A");
//            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
//        }
    }

    //New But Not Used
//    @RequestMapping(value = "/update_new/{id}/update/edge/{edgeId}",
//            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
//    public ResponseEntity<?> updateEdge(
//            @PathVariable Long id, @PathVariable Long edgeId, @RequestBody EdgeInNetworkGroupResource sentEdgeInNetworkGroupResource) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof UserDetails) {
//            try {
//
//                boolean isValidated = true;
//                Validate validate = new Validate();
//
//                if (!isValidated) {
//                    throw new BadRequestException();
//                }
//
////                UserDetails details = (UserDetails) principal;
////                User loggedUser = userService.findUserByUsername(details.getUsername());
//                EdgeInNetworkGroup updatedEntry = networkGroupService.updateEdgeInNetworkGroup(edgeId, sentEdgeInNetworkGroupResource.toEdgeInNetworkGroup());
//                if (updatedEntry != null) {
//                    EdgeInNetworkGroupResource res = new EdgeInNetworkGroupResourceAsm().toResource(updatedEntry);
//                    return new ResponseEntity<>(res, HttpStatus.OK);
//                } else {
//                    throw new NotFoundException();
//                }
//            } catch (NotFoundException exception) {
//                writeLogMessage(exception);
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
//            } catch (BadRequestException exception) {
//                writeLogMessage(exception);
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
//            } catch (ConflictException exception) {
//                writeLogMessage(exception);
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
//            } catch (Exception exception) {
//                writeLogMessage(exception);
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        } else {
//            ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN.value(), GlobalExceptionMessage.FORBIDDEN_APPLICATION_CODE, GlobalExceptionMessage.FORBIDDEN_MESSAGE, false, "N/A");
//            writeLogMessage(info.getMessage());
//            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
//        }
//    }
//    @RequestMapping(value = "/update/{id}/add/travelguideid/{edgeId}",
//            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
//    public ResponseEntity<?> updateTraveleGroupByAddEdge(
//            @PathVariable Long id, @PathVariable Long edgeId, @RequestBody(required = false) EdgeInNetworkGroupResource sentEdgeInNetworkGroupResource) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof UserDetails) {
//            try {
//                //Validation Start
//                boolean isValidated = true;
//                Validate validate = new Validate();
//
//                if (validate.isEmptyString(sentEdgeInNetworkGroupResource.getDescription()) || (!validate.isValidDefaultTextLength(sentEdgeInNetworkGroupResource.getDescription()))) {
//                    isValidated = false;
//                }
//
//                if (!isValidated) {
//                    throw new BadRequestException();
//                }
//                //Validation Stop
//
//                UserDetails details = (UserDetails) principal;
//                User loggedUser = userService.findUserByUsername(details.getUsername());
//                NetworkGroup readNetworkGroup = networkGroupService.findNetworkGroup(id);
//
//                if (sentEdgeInNetworkGroupResource == null) {
//                    sentEdgeInNetworkGroupResource = new EdgeInNetworkGroupResource();
//                    sentEdgeInNetworkGroupResource.setDescription("DES");
//                }
//
//                Edge readEdge = edgeService.findEdge(edgeId);
//                if (readEdge == null) {
//                    throw new EdgeNotFoundException();
//                }
//
//                sentEdgeInNetworkGroupResource.setAdminUsername(loggedUser.getAdminUsername());
//                sentEdgeInNetworkGroupResource.setAdminUserType(loggedUser.getAdminUserType());
//                sentEdgeInNetworkGroupResource.setAdminOperatorUsername(Global.SYSTEM_DEFAULT_NO_OPERATOR_USER);
//                sentEdgeInNetworkGroupResource.setAdminOperatorUserType(Global.SYSTEM_DEFAULT_NO_OPERATOR_USER_TYPE);
//
//                EdgeInNetworkGroup sentNodeInNetworkGroup = sentEdgeInNetworkGroupResource.toEdgeInNetworkGroup();
//
//                sentNodeInNetworkGroup.setEdge(readEdge);
//                readNetworkGroup.setLastModifiedUnixTime(DateConverter.getCurrentConvertedDateAndTimeInUnixDate());
//                readNetworkGroup.getEdgeInNetworkGroups().add(sentNodeInNetworkGroup);
//
//                NetworkGroup updatedEntry = networkGroupService.updateNetworkGroupForAddEdge(id, readNetworkGroup, readEdge);
//
//                if (updatedEntry != null) {
//                    NetworkGroupResource res = new NetworkGroupResourceAsm().toResource(updatedEntry);
//                    return new ResponseEntity<>(res, HttpStatus.OK);
//                } else {
//                    throw new NotFoundException();
//                }
//            } catch (EdgeNotFoundException | NotFoundException exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
////                throw new NotFoundException(exception);
//            } catch (BadRequestException exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
////                throw new BadRequestException(exception);
//            } catch (EdgeExistsException | ConflictException exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
////                throw new ConflictException(exception);
//            } catch (Exception exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
////                throw new InternalServertException(e);
//            }
//        } else {
//            ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN.value(), GlobalExceptionMessage.FORBIDDEN_APPLICATION_CODE, GlobalExceptionMessage.FORBIDDEN_MESSAGE, false, "N/A");
//            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
////            throw new ForbiddenException();
//        }
//
//    }
//
//    @RequestMapping(value = "/update/{id}/remove/travelguidenetworkgroupid/{edgeNetworkGroupId}",
//            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
//    public ResponseEntity<?> updateTraveleGroupByRemovingEdge(
//            @PathVariable Long id, @PathVariable Long edgeNetworkGroupId) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof UserDetails) {
//
//            try {
//                NetworkGroup readNetworkGroup = networkGroupService.findNetworkGroup(id);
//                Iterator<EdgeInNetworkGroup> iter = readNetworkGroup.getEdgeInNetworkGroups().iterator();
//                while (iter.hasNext()) {
//                    EdgeInNetworkGroup str = iter.next();
//                    if (Objects.equals(str.getId(), edgeNetworkGroupId)) {
//                        iter.remove();
//                        break;
//                    }
//                }
//                readNetworkGroup.setLastModifiedUnixTime(DateConverter.getCurrentConvertedDateAndTimeInUnixDate());
//                NetworkGroup updatedEntry = networkGroupService.updateNetworkGroupForUpdateAndRemoveEdge(id, readNetworkGroup);
//                if (updatedEntry != null) {
//                    NetworkGroupResource res = new NetworkGroupResourceAsm().toResource(updatedEntry);
//                    return new ResponseEntity<>(res, HttpStatus.OK);
//                } else {
//                    throw new NotFoundException();
//                }
//            } catch (NotFoundException exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
////                throw new NotFoundException(exception);
//            } catch (BadRequestException exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
////                throw new BadRequestException(exception);
//            } catch (ConflictException exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
////                throw new ConflictException(exception);
//            } catch (Exception exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
////                throw new InternalServertException(e);
//            }
//        } else {
//            ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN.value(), GlobalExceptionMessage.FORBIDDEN_APPLICATION_CODE, GlobalExceptionMessage.FORBIDDEN_MESSAGE, false, "N/A");
//            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
////            throw new ForbiddenException();
//        }
//    }
//
//    @RequestMapping(value = "/travelguideinnetworkgroup/find/{id}",
//            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
//    public ResponseEntity<?> getEdgeInNetworkGroup(
//            @PathVariable Long id
//    ) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof UserDetails) {
//            try {
//                EdgeInNetworkGroup edgeInNetworkGroup = networkGroupService.findEdgeInNetworkGroup(id);
//                if (edgeInNetworkGroup != null) {
//                    EdgeInNetworkGroupResource res = new EdgeInNetworkGroupResourceAsm().toResource(edgeInNetworkGroup);
//                    return new ResponseEntity<>(res, HttpStatus.OK);
//                } else {
//                    throw new NotFoundException();
//                }
//            } catch (NotFoundException exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
////                throw new NotFoundException(exception);
//            } catch (BadRequestException exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
////                throw new BadRequestException(exception);
//            } catch (ConflictException exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
////                throw new ConflictException(exception);
//            } catch (Exception exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
////                throw new InternalServertException(e);
//            }
//        } else {
//            ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN.value(), GlobalExceptionMessage.FORBIDDEN_APPLICATION_CODE, GlobalExceptionMessage.FORBIDDEN_MESSAGE, false, "N/A");
//            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
////            throw new ForbiddenException();
//        }
//    }
//
//    @RequestMapping(value = "/update/{id}/update/travelguidenetworkgroupid/{edgeNetworkGroupId}",
//            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
//    public ResponseEntity<?> updateNetworkGroupByUpdatingEdge(
//            @PathVariable Long id, @PathVariable Long edgeNetworkGroupId, @RequestBody(required = false) EdgeInNetworkGroupResource sentEdgeNetworkGroupResource) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof UserDetails) {
//            try {
//
//                //Validation Start
//                boolean isValidated = true;
//                Validate validate = new Validate();
//
//                if (validate.isEmptyString(sentEdgeNetworkGroupResource.getDescription()) || (!validate.isValidDefaultTextLength(sentEdgeNetworkGroupResource.getDescription()))) {
//                    isValidated = false;
//                }
//
//                if (!isValidated) {
//                    throw new BadRequestException();
//                }
//                //Validation Stop
//                NetworkGroup readNetworkGroup = networkGroupService.findNetworkGroup(id);
//                Iterator<EdgeInNetworkGroup> iter = readNetworkGroup.getEdgeInNetworkGroups().iterator();
//                while (iter.hasNext()) {
//                    EdgeInNetworkGroup str = iter.next();
//                    if (Objects.equals(str.getId(), edgeNetworkGroupId)) {
//                        str.setDescription(sentEdgeNetworkGroupResource.getDescription());
//                        break;
//                    }
//                }
//                readNetworkGroup.setLastModifiedUnixTime(DateConverter.getCurrentConvertedDateAndTimeInUnixDate());
//                NetworkGroup updatedEntry = networkGroupService.updateNetworkGroupForUpdateAndRemoveEdge(id, readNetworkGroup);
//                if (updatedEntry != null) {
//                    NetworkGroupResource res = new NetworkGroupResourceAsm().toResource(updatedEntry);
//                    return new ResponseEntity<>(res, HttpStatus.OK);
//                } else {
//                    throw new NotFoundException();
//                }
//            } catch (NotFoundException exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
////                throw new NotFoundException(exception);
//            } catch (BadRequestException exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
////                throw new BadRequestException(exception);
//            } catch (ConflictException exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
////                throw new ConflictException(exception);
//            } catch (Exception exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
////                throw new InternalServertException(e);
//            }
//        } else {
//            ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN.value(), GlobalExceptionMessage.FORBIDDEN_APPLICATION_CODE, GlobalExceptionMessage.FORBIDDEN_MESSAGE, false, "N/A");
//            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
////            throw new ForbiddenException();
//        }
//    }
//
//    @RequestMapping(value = "/checklist",
//            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
//    public ResponseEntity<?> findNetworkGroupsBySearchCriteriaCheckList() {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof UserDetails) {
//            try {
//                System.out.println("ENTERING......");
//
//                NodeInNetworkGroupSearchCriteria searchCriteria = new NodeInNetworkGroupSearchCriteria();
//                searchCriteria.setLimitResult(3);
//                searchCriteria.setPageNo(1);
//
//                NetworkGroupIDList networkGroupIDList = networkGroupService.findAllNetworkGroupsIDAssociatedWithUser(2102L);
////            javax.swing.JOptionPane.showMessageDialog(null, networkGroupIDList.getNetworkGroupID().size());
////            for(Long id : networkGroupIDList.getNetworkGroupID())
////            {
////                System.out.println("------------- chock ---"+id);
////            }
////            
////            List<Long> networkGroupIDs = new ArrayList<>();
////            networkGroupIDs.add(3052L);
////            networkGroupIDs.add(3053L);
//
//                NodeInNetworkGroupList list = networkGroupService.findNodeInNetworkGroupsBySearchCriteria_JDBC(searchCriteria, networkGroupIDList.getNetworkGroupID());
//                for (NodeInNetworkGroup tg : list.getNodeInNetworkGroup()) {
////                    tg.getNodeInNetworkGroups()
//
//                    System.out.println("For Node --------------------------------------------------");
//                    System.out.println("Node In NetworkGroup ID :" + tg.getId());
//                    System.out.println("Node Group ID :" + tg.getNetworkGroupId());
//                    System.out.println("Node Group Code :" + tg.getNetworkGroupCode());
//                    System.out.println("Node Social ID :" + tg.getNode().getSocialID());
//                    System.out.println("Node ID :" + tg.getNode().getId());
//                    System.out.println("Node Username :" + tg.getNode().getUsername());
////                for (NodeInNetworkGroup node : tg.getNodeInNetworkGroups()) {
////                    System.out.println("Node Name :" + node.getNode().getUsername());
////                }
//
////                System.out.println("For Travel Guide ----------------------------------------------------");
////                for (EdgeInNetworkGroup guideInNetworkGroup : tg.getEdgeInNetworkGroups()) {
////                    System.out.println("Node Name :" + guideInNetworkGroup.getEdge().getUsername());
////                }
//                    System.out.println("--------------------END");
//                }
//
////            NetworkGroupListResource res = new NetworkGroupListResourceAsm().toResource(list);
//                return new ResponseEntity<>(list, HttpStatus.OK);
//            } catch (NotFoundException exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
////                throw new NotFoundException(exception);
//            } catch (BadRequestException exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
////                throw new BadRequestException(exception);
//            } catch (ConflictException exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
////                throw new ConflictException(exception);
//            } catch (Exception exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
////                throw new InternalServertException(e);
//            }
//        } else {
//            ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN.value(), GlobalExceptionMessage.FORBIDDEN_APPLICATION_CODE, GlobalExceptionMessage.FORBIDDEN_MESSAGE, false, "N/A");
//            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
////            throw new ForbiddenException();
//        }
//    }
//
//    @RequestMapping(value = "/listallforuser",
//            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
//    public ResponseEntity<?> findAllNetworkGroupsForNodeBySearchCriteria() {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof UserDetails) {
//            try {
//                UserDetails details = (UserDetails) principal;
//                User loggedUser = userService.findUserByUsername(details.getUsername());
////            User loggedUser = userService.findUserByUsername("TR-3953");
//                System.out.println("ENTERING......");
//
//                NetworkGroupList list = networkGroupService.findAllNetworkGroupsForUser(loggedUser);
//
//                NetworkGroupListResource res = new NetworkGroupListResourceAsm().toResource(list);
//
//                return new ResponseEntity<>(res, HttpStatus.OK);
//            } catch (NotFoundException exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
////                throw new NotFoundException(exception);
//            } catch (BadRequestException exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
////                throw new BadRequestException(exception);
//            } catch (ConflictException exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
////                throw new ConflictException(exception);
//            } catch (Exception exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
////                throw new InternalServertException(e);
//            }
//        } else {
//            ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN.value(), GlobalExceptionMessage.FORBIDDEN_APPLICATION_CODE, GlobalExceptionMessage.FORBIDDEN_MESSAGE, false, "N/A");
//            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
////            throw new ForbiddenException();
//        }
//    }
//
//    @RequestMapping(value = "/nodeinnetworkgroup/listbynode",
//            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
//    public ResponseEntity<?> findNetworkGroupsBySearchCriteriaCheckList(@RequestBody(required = false) NodeInNetworkGroupSearchCriteria searchCriteria) {
//
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof UserDetails) {
//
//            try {
//                UserDetails details = (UserDetails) principal;
//                Node node = nodeService.findNodeByUsername((details.getUsername()));
//
////        try {
////            System.out.println("ENTERING......");
////
////            Node node = nodeService.findNodeByUsername(username);
//                if (node != null) {
//                    if (searchCriteria == null) {
//                        searchCriteria = new NodeInNetworkGroupSearchCriteria();
//                    }
//
//                    if (searchCriteria.getLimitResult() <= 0) {
//                        searchCriteria.setLimitResult(10);
//                    }
//
//                    NetworkGroupIDList networkGroupIDList = networkGroupService.findAllValidNetworkGroupsIDAssociatedWithNode(node.getId(), DateConverter.getCurrentConvertedDateInUnixDate(), searchCriteria.getNetworkGroupCode());
//
//                    NodeInNetworkGroupList list = networkGroupService.findNodeInNetworkGroupsBySearchCriteria_JDBC(searchCriteria, networkGroupIDList.getNetworkGroupID());
//                    for (NodeInNetworkGroup tg : list.getNodeInNetworkGroup()) {
//                        System.out.println("For Node --------------------------------------------------");
//                        System.out.println("Node In NetworkGroup ID :" + tg.getId());
//                        System.out.println("Node In NetworkGroup Desc :" + tg.getDescription());
//                        System.out.println("Node Group ID :" + tg.getNetworkGroupId());
//                        System.out.println("Node Group Code :" + tg.getNetworkGroupCode());
//                        System.out.println("Node Social ID :" + tg.getNode().getSocialID());
//                        System.out.println("Node ID :" + tg.getNode().getId());
//                        System.out.println("Node Username :" + tg.getNode().getUsername());
//                        System.out.println("--------------------END");
//                    }
//
//                    NodeInNetworkGroupListResource res = new NodeInNetworkGroupListResourceAsm().toResource(list);
//                    return new ResponseEntity<>(res, HttpStatus.OK);
//                } else {
//                    throw new NotFoundException();
//                }
//
//            } catch (NotFoundException exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
////                throw new NotFoundException(exception);
//            } catch (BadRequestException exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
////                throw new BadRequestException(exception);
//            } catch (ConflictException exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
////                throw new ConflictException(exception);
//            } catch (Exception exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
////                throw new InternalServertException(e);
//            }
//        } else {
//            ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN.value(), GlobalExceptionMessage.FORBIDDEN_APPLICATION_CODE, GlobalExceptionMessage.FORBIDDEN_MESSAGE, false, "N/A");
//            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
////            throw new ForbiddenException();
//        }
//
//    }
//    @RequestMapping(value = "/travelguideinnetworkgroup/listbynode",
//            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
//    public ResponseEntity<?> findEdgeInNetworkGroupBySearchCriteriaCheckList(@RequestBody(required = false) EdgeInNetworkGroupSearchCriteria searchCriteria) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof UserDetails) {
//
//            try {
//                UserDetails details = (UserDetails) principal;
//                Node node = nodeService.findNodeByUsername((details.getUsername()));
//
////            Node node = nodeService.findNodeByUsername(username);
//                if (node != null) {
//                    if (searchCriteria == null) {
//                        searchCriteria = new EdgeInNetworkGroupSearchCriteria();
//                    }
//
//                    if (searchCriteria.getLimitResult() <= 0) {
//                        searchCriteria.setLimitResult(10);
//                    }
//
////                    NetworkGroupIDList networkGroupIDList = networkGroupService.findAllNetworkGroupsIDAssociatedWithUser(node.getId());
//                    NetworkGroupIDList networkGroupIDList = networkGroupService.findAllValidNetworkGroupsIDAssociatedWithNode(node.getId(), DateConverter.getCurrentConvertedDateInUnixDate(), searchCriteria.getNetworkGroupCode());
//
//                    EdgeInNetworkGroupList list = networkGroupService.findEdgeInNetworkGroupsBySearchCriteria_JDBC(searchCriteria, networkGroupIDList.getNetworkGroupID());
//                    for (EdgeInNetworkGroup tg : list.getEdgeInNetworkGroup()) {
//                        System.out.println("For Node --------------------------------------------------");
//                        System.out.println("Edge In NetworkGroup ID :" + tg.getId());
//                        System.out.println("Travel Guide Group ID :" + tg.getNetworkGroupId());
//                        System.out.println("Travel Guide Group Code :" + tg.getNetworkGroupCode());
//                        System.out.println("Travel Guide Social ID :" + tg.getEdge().getSocialID());
//                        System.out.println("Travel Guide ID :" + tg.getEdge().getId());
//                        System.out.println("Travel Guide Username :" + tg.getEdge().getUsername());
//                        System.out.println("--------------------END");
//                    }
//                    EdgeInNetworkGroupListResource res = new EdgeInNetworkGroupListResourceAsm().toResource(list);
//                    return new ResponseEntity<>(res, HttpStatus.OK);
//                } else {
//                    throw new NotFoundException();
//                }
//
//            } catch (NotFoundException exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
////                throw new NotFoundException(exception);
//            } catch (BadRequestException exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
////                throw new BadRequestException(exception);
//            } catch (ConflictException exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
////                throw new ConflictException(exception);
//            } catch (Exception exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
////                throw new InternalServertException(e);
//            }
//        } else {
//            ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN.value(), GlobalExceptionMessage.FORBIDDEN_APPLICATION_CODE, GlobalExceptionMessage.FORBIDDEN_MESSAGE, false, "N/A");
//            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
////            throw new ForbiddenException();
//        }
//    }
//    @RequestMapping(value = "/listbynode",
//            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
//    public ResponseEntity<?> findNetworkGroupsForNodes() {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof UserDetails) {
//            try {
//                UserDetails details = (UserDetails) principal;
//                User loggedUser = userService.findUserByUsername(details.getUsername());
//                NetworkGroupList list = networkGroupService.findAllValidNetworkGroupsForNode(loggedUser.getId());
//                NetworkGroupListResource res = new NetworkGroupListResourceAsm().toResource(list);
//                return new ResponseEntity<>(res, HttpStatus.OK);
//            } catch (NotFoundException exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.NOT_FOUND.value(), GlobalExceptionMessage.NOTFOUND_APPLICATION_CODE, GlobalExceptionMessage.NOTFOUND_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
////                throw new NotFoundException(exception);
//            } catch (BadRequestException exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.BAD_REQUEST.value(), GlobalExceptionMessage.BADREQUEST_APPLICATION_CODE, GlobalExceptionMessage.BADREQUST_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
////                throw new BadRequestException(exception);
//            } catch (ConflictException exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.CONFLICT.value(), GlobalExceptionMessage.CONFLICT_APPLICATION_CODE, GlobalExceptionMessage.CONFLICT_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.CONFLICT);
////                throw new ConflictException(exception);
//            } catch (Exception exception) {
//                Log4jUtil.error(exception.toString());
//                Log4jUtil.error(Log4jUtil.getFormattedMessageFromStackTraceElements(exception));
//                ExceptionInfo info = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), GlobalExceptionMessage.INTERNALSERVERERROR_APPLICATION_CODE, GlobalExceptionMessage.INTERNALSERVERERROR_MESSAGE, false, exception.getMessage());
//                return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
////                throw new InternalServertException(e);
//            }
//        } else {
//            ExceptionInfo info = new ExceptionInfo(HttpStatus.FORBIDDEN.value(), GlobalExceptionMessage.FORBIDDEN_APPLICATION_CODE, GlobalExceptionMessage.FORBIDDEN_MESSAGE, false, "N/A");
//            return new ResponseEntity<>(info, HttpStatus.FORBIDDEN);
////            throw new ForbiddenException();
//        }
//    }
    private boolean isNetworkGroupAlreadyExist(String code, Node node) throws Exception {
        NetworkGroup networkGroup = networkGroupService.findNetworkGroupByNode(code, node);
        return networkGroup != null;
    }

    private boolean isNetworkGroupAlreadyExistForNodeInNetworkGroup(String networkGroupCode, NodeInNetworkGroup nodeInNetworkGroup) throws Exception {
        NetworkGroup networkGroup = networkGroupService.findNetworkGroupByNodeInNetworkGroup(networkGroupCode, nodeInNetworkGroup);
        return networkGroup != null;
    }

    private boolean isNetworkGroupAlreadyExistForEdgeInNetworkGroup(String networkGroupCode, EdgeInNetworkGroup edgeInNetworkGroup) throws Exception {
        NetworkGroup networkGroup = networkGroupService.findNetworkGroupByEdgeInNetworkGroup(networkGroupCode, edgeInNetworkGroup);
        return networkGroup != null;
    }

    @RequestMapping(value = "/test/find/{id}",
            method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    public ResponseEntity<?> getTestNetworkGroup(
            @PathVariable Long id
    ) {

        try {
            NetworkGroup networkGroup = networkGroupService.findNetworkGroup(id);
            if (networkGroup != null) {
                NetworkGroupResource res = new NetworkGroupResourceAsm().toResource(networkGroup);
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

    }
}
