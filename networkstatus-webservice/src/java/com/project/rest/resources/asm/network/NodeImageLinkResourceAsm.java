package com.project.rest.resources.asm.network;

import com.project.core.models.entities.network.Node;
import com.project.core.models.entities.network.NodeImageLink;
import com.project.rest.mvc.web.NodeRESTController_ForWeb;
import com.project.rest.resources.network.NodeImageLinkResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import com.project.rest.resources.network.NodeResource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 *
 * @author Samrit
 */
public class NodeImageLinkResourceAsm extends ResourceAssemblerSupport<NodeImageLink, NodeImageLinkResource> {

    public NodeImageLinkResourceAsm() {
        super(NodeRESTController_ForWeb.class, NodeImageLinkResource.class);
    }

    @Override
    public NodeImageLinkResource toResource(NodeImageLink object) {
        NodeImageLinkResource res = new NodeImageLinkResource();
        res.setNodeImageLinkID(object.getId());
        res.setSrc(object.getSrc());
        return res;
    }

}
