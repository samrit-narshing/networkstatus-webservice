package com.project.rest.resources.asm.network;

import com.project.core.models.entities.network.NodeAlertInfo;
import com.project.rest.mvc.web.NodeRESTController_ForWeb;
import com.project.rest.resources.network.NodeAlertInfoResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 *
 * @author Samrit
 */
public class NodeAlertInfoResourceAsm extends ResourceAssemblerSupport<NodeAlertInfo, NodeAlertInfoResource> {

    public NodeAlertInfoResourceAsm() {
        super(NodeRESTController_ForWeb.class, NodeAlertInfoResource.class);
    }

    @Override
    public NodeAlertInfoResource toResource(NodeAlertInfo object) {
        NodeAlertInfoResource res = new NodeAlertInfoResource();
        res.setNodeAlertInfoID(object.getId());
        res.setType(object.getType());
        res.setDescription(object.getDescription());
        return res;
    }

}
