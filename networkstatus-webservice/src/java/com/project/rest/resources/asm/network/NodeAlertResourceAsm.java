package com.project.rest.resources.asm.network;

import com.project.core.models.entities.network.NodeAlert;
import com.project.core.services.util.NodeAlertInfoList;
import com.project.rest.mvc.web.NodeRESTController_ForWeb;
import com.project.rest.resources.network.NodeAlertInfoListResource;
import com.project.rest.resources.network.NodeAlertInfoResource;
import com.project.rest.resources.network.NodeAlertResource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 *
 * @author Samrit
 */
public class NodeAlertResourceAsm extends ResourceAssemblerSupport<NodeAlert, NodeAlertResource> {

    public NodeAlertResourceAsm() {
        super(NodeRESTController_ForWeb.class, NodeAlertResource.class);
    }

    @Override
    public NodeAlertResource toResource(NodeAlert object) {
        NodeAlertResource res = new NodeAlertResource();
        res.setNodeAlertID(object.getId());
        res.setType(object.getType());
        res.setDescription(object.getDescription());

        NodeAlertInfoList nodeAlertInfoList = new NodeAlertInfoList(new ArrayList<>(object.getNodeAlertInfos()));
        List<NodeAlertInfoResource> nodeAlertInfoResList = new NodeAlertInfoResourceAsm().toResources(nodeAlertInfoList.getNodeAlertInfos());
        NodeAlertInfoListResource nodeAlertInfoListFinalRes = new NodeAlertInfoListResource();
        nodeAlertInfoListFinalRes.setNodeAlertInfoResources(nodeAlertInfoResList);
        List<NodeAlertInfoResource> nodeAlertInfoResourcesSet = new ArrayList<>(nodeAlertInfoListFinalRes.getNodeAlertInfoResources());
        res.setNodeAlertInfoResources(nodeAlertInfoResourcesSet);

        return res;
    }

}
