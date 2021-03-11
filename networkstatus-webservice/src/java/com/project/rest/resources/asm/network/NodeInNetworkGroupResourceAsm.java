package com.project.rest.resources.asm.network;

import com.project.core.models.entities.network.NodeInNetworkGroup;
import com.project.rest.mvc.web.NetworkGroupRESTController_ForWeb;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import com.project.rest.resources.network.NodeInNetworkGroupResource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 *
 * @author Samrit
 */
public class NodeInNetworkGroupResourceAsm extends ResourceAssemblerSupport<NodeInNetworkGroup, NodeInNetworkGroupResource> {

    public NodeInNetworkGroupResourceAsm() {
        super(NetworkGroupRESTController_ForWeb.class, NodeInNetworkGroupResource.class);
    }

    @Override
    public NodeInNetworkGroupResource toResource(NodeInNetworkGroup nodeInNetworkGroup) {
        NodeInNetworkGroupResource res = new NodeInNetworkGroupResource();
        res.setNodeInNetworkGroupID(nodeInNetworkGroup.getId());

        res.setDescription(nodeInNetworkGroup.getDescription());
        res.setNodeResource(new NodeResourceAsm().toResource(nodeInNetworkGroup.getNode()));
        res.setEnabled(nodeInNetworkGroup.isEnabled());
        res.setSelected(nodeInNetworkGroup.isSelected());
        res.setDomXValue(nodeInNetworkGroup.getDomXValue());
        res.setDomYValue(nodeInNetworkGroup.getDomYValue());
        res.setCanvasXValue(nodeInNetworkGroup.getCanvasXValue());
        res.setCanvasYValue(nodeInNetworkGroup.getCanvasYValue());
        res.setZoomScale(nodeInNetworkGroup.getZoomScale());

        res.add(linkTo(methodOn(NetworkGroupRESTController_ForWeb.class).getNodeInNetworkGroup(nodeInNetworkGroup.getId())).withSelfRel());
        System.out.println("2010 >>>>>>> "+res.getLinks().get(0));
        return res;
    }

}
