package com.project.rest.resources.asm.network;

import com.project.core.services.util.NodeInNetworkGroupList;
import com.project.rest.mvc.web.NetworkGroupRESTController_ForWeb;
import com.project.rest.resources.network.NodeInNetworkGroupListResource;
import com.project.rest.resources.network.NodeInNetworkGroupResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.List;

/**
 *
 * @author Samrit
 */
public class NodeInNetworkGroupListResourceAsm extends ResourceAssemblerSupport<NodeInNetworkGroupList, NodeInNetworkGroupListResource> {

    public NodeInNetworkGroupListResourceAsm() {
        super(NetworkGroupRESTController_ForWeb.class, NodeInNetworkGroupListResource.class);
    }

    @Override
    public NodeInNetworkGroupListResource toResource(NodeInNetworkGroupList userList) {
        List<NodeInNetworkGroupResource> resList = new NodeInNetworkGroupResourceAsm().toResources(userList.getNodeInNetworkGroup());
        NodeInNetworkGroupListResource finalRes = new NodeInNetworkGroupListResource();
        finalRes.setNodeInNetworkGroupResources(resList);
        finalRes.setTotalPages(userList.getTotalPages());
        finalRes.setTotalDocuments(userList.getTotalDocuments());
        return finalRes;
    }
}
