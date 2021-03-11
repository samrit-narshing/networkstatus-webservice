package com.project.rest.resources.asm.network;

import com.project.core.models.entities.network.Node;
import com.project.rest.mvc.web.NodeRESTController_ForWeb;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import com.project.rest.resources.network.NodeResource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 *
 * @author Samrit
 */
public class NodeResourceAsm extends ResourceAssemblerSupport<Node, NodeResource> {

    public NodeResourceAsm() {
        super(NodeRESTController_ForWeb.class, NodeResource.class);
    }

    @Override
    public NodeResource toResource(Node object) {
        NodeResource res = new NodeResource();
        res.setNodeID(object.getId());
        res.setDescription(object.getDescription());
        res.setHeight(object.getHeight());
        res.setEnabled(object.isEnabled());
        res.setLabel(object.getLabel());
        res.setRedirectingURLLink(object.getRedirectingURLLink());
        res.setType(object.getType());
        res.setUniqueID(object.getUniqueID());
        res.setFill(new NodeImageLinkResourceAsm().toResource(object.getFill()));
        res.setAlert(new NodeAlertResourceAsm().toResource(object.getAlert()));
        res.setNodeValue(object.getNodeValue());
        res.setTitle(object.getTitle());

        res.setDomXValue(object.getDomXValue());
        res.setDomYValue(object.getDomYValue());
        res.setCanvasXValue(object.getCanvasXValue());
        res.setCanvasYValue(object.getCanvasYValue());
        res.setZoomScale(object.getZoomScale());

        res.add(linkTo(methodOn(NodeRESTController_ForWeb.class).getNode(object.getId())).withSelfRel());
        return res;
    }

}
