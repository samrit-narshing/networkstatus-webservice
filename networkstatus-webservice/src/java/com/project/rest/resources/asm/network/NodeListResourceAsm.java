package com.project.rest.resources.asm.network;

import com.project.core.services.util.NodeList;
import com.project.rest.mvc.web.NodeRESTController_ForWeb;
import com.project.rest.resources.network.NodeListResource;
import com.project.rest.resources.network.NodeResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.List;

/**
 *
 * @author Samrit
 */
public class NodeListResourceAsm extends ResourceAssemblerSupport<NodeList, NodeListResource> {

    public NodeListResourceAsm() {
        super(NodeRESTController_ForWeb.class, NodeListResource.class);
    }

    @Override
    public NodeListResource toResource(NodeList list) {
        List<NodeResource> resList = new NodeResourceAsm().toResources(list.getNodes());
        NodeListResource finalRes = new NodeListResource();
        finalRes.setNodeResources(resList);
        finalRes.setTotalPages(list.getTotalPages());
        finalRes.setTotalDocuments(list.getTotalDocuments());
        return finalRes;
    }
}
