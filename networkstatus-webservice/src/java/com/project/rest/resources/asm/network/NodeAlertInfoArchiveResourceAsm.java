package com.project.rest.resources.asm.network;

import com.project.core.models.entities.network.NodeAlertInfoArchive;
import com.project.rest.resources.asm.util.*;
import com.project.rest.mvc.web.NodeAlertInfoArchiveRESTController_ForWeb;
import com.project.rest.resources.network.NodeAlertInfoArchiveResource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 *
 * @author Samrit
 */
public class NodeAlertInfoArchiveResourceAsm extends ResourceAssemblerSupport<NodeAlertInfoArchive, NodeAlertInfoArchiveResource> {

    public NodeAlertInfoArchiveResourceAsm() {
        super(NodeAlertInfoArchiveRESTController_ForWeb.class, NodeAlertInfoArchiveResource.class);
    }

    @Override
    public NodeAlertInfoArchiveResource toResource(NodeAlertInfoArchive object) {
        NodeAlertInfoArchiveResource res = new NodeAlertInfoArchiveResource();
        res.setEntryDate(object.getEntryDate());
        res.setLoggedUsername(object.getLoggedUsername());
        res.setTableId(object.getId());
        res.setDescription(object.getDescription());
        res.setNodeName(object.getNodeName());
        res.setNodeType(object.getNodeType());
        res.setType(object.getType());

        res.add(linkTo(methodOn(NodeAlertInfoArchiveRESTController_ForWeb.class).getNodeAlertInfoArchiveByID(object.getId())).withSelfRel());

        return res;
    }
}
