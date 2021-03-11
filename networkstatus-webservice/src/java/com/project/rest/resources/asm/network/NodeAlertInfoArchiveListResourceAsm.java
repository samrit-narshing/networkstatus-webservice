package com.project.rest.resources.asm.network;

import com.project.rest.resources.asm.util.*;
import com.project.core.services.util.NodeAlertInfoArchiveList;
import com.project.rest.mvc.web.NodeAlertInfoArchiveRESTController_ForWeb;
import com.project.rest.resources.network.NodeAlertInfoArchiveListResource;
import com.project.rest.resources.network.NodeAlertInfoArchiveResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.List;

/**
 *
 * @author Samrit
 */
public class NodeAlertInfoArchiveListResourceAsm extends ResourceAssemblerSupport<NodeAlertInfoArchiveList, NodeAlertInfoArchiveListResource> {

    public NodeAlertInfoArchiveListResourceAsm() {
        super(NodeAlertInfoArchiveRESTController_ForWeb.class, NodeAlertInfoArchiveListResource.class);
    }

    @Override
    public NodeAlertInfoArchiveListResource toResource(NodeAlertInfoArchiveList userList) {
        List<NodeAlertInfoArchiveResource> resList = new NodeAlertInfoArchiveResourceAsm().toResources(userList.getNodeAlertInfoArchives());
        NodeAlertInfoArchiveListResource finalRes = new NodeAlertInfoArchiveListResource();
        finalRes.setNodeAlertInfoArchiveResources(resList);
        finalRes.setTotalPages(userList.getTotalPages());
        finalRes.setTotalDocuments(userList.getTotalDocuments());
        return finalRes;
    }
}
