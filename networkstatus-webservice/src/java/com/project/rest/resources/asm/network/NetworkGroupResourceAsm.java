package com.project.rest.resources.asm.network;

import com.project.core.models.entities.network.NetworkGroup;
import com.project.core.models.entities.network.NodeInNetworkGroup;
import com.project.core.services.util.EdgeInNetworkGroupList;
import com.project.core.services.util.NodeInNetworkGroupList;
import com.project.core.services.util.RoleInNetworkGroupList;
import com.project.rest.mvc.web.NetworkGroupRESTController_ForWeb;
import com.project.rest.resources.network.EdgeInNetworkGroupListResource;
import com.project.rest.resources.network.EdgeInNetworkGroupResource;
import com.project.rest.resources.network.NetworkGroupResource;
import com.project.rest.resources.network.NodeInNetworkGroupListResource;
import com.project.rest.resources.network.NodeInNetworkGroupResource;
import com.project.rest.resources.network.RoleInNetworkGroupListResource;
import com.project.rest.resources.network.RoleInNetworkGroupResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.LazyInitializationException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 *
 * @author Samrit
 */
public class NetworkGroupResourceAsm extends ResourceAssemblerSupport<NetworkGroup, NetworkGroupResource> {

    public NetworkGroupResourceAsm() {
        super(NetworkGroupRESTController_ForWeb.class, NetworkGroupResource.class);
    }

    @Override
    public NetworkGroupResource toResource(NetworkGroup networkGroup) {
        NetworkGroupResource res = new NetworkGroupResource();
        res.setNetworkGroupID(networkGroup.getId());
        res.setDescription(networkGroup.getDescription());
        res.setRoles(networkGroup.getRoles());
        res.setEntryDate(networkGroup.getEntryDate());

        res.setName(networkGroup.getName());
        res.setEnabled(networkGroup.isEnabled());
        res.setCode(networkGroup.getCode());
        res.setStatusCode(networkGroup.getStatusCode());

        NodeInNetworkGroupList nodeInNetworkGroupList = null;
        try {
            nodeInNetworkGroupList = new NodeInNetworkGroupList(new ArrayList<>(networkGroup.getNodesInNetworkGroup()));
            networkGroup.getNodesInNetworkGroup().size();
        } catch (LazyInitializationException e) {
            nodeInNetworkGroupList = new NodeInNetworkGroupList(new ArrayList<>());
        }

//        NodeInNetworkGroupList nodeInNetworkGroupList = new NodeInNetworkGroupList(new ArrayList<>(networkGroup.getNodesInNetworkGroup()));
        List<NodeInNetworkGroupResource> nodeInNetworkGroupResList = new NodeInNetworkGroupResourceAsm().toResources(nodeInNetworkGroupList.getNodeInNetworkGroup());
        NodeInNetworkGroupListResource nodeInNetworkGroupListFinalRes = new NodeInNetworkGroupListResource();
        nodeInNetworkGroupListFinalRes.setNodeInNetworkGroupResources(nodeInNetworkGroupResList);
        Set<NodeInNetworkGroupResource> travelerInNetworkGroupResourcesSet = new HashSet(nodeInNetworkGroupListFinalRes.getNodeInNetworkGroupResources());
        res.setNodeInNetworkGroupResources(travelerInNetworkGroupResourcesSet);

        EdgeInNetworkGroupList edgeInNetworkGroupList = new EdgeInNetworkGroupList(new ArrayList<>(networkGroup.getEdgesInNetworkGroup()));
        List<EdgeInNetworkGroupResource> edgeInNetworkGroupResList = new EdgeInNetworkGroupResourceAsm().toResources(edgeInNetworkGroupList.getEdgeInNetworkGroup());
        EdgeInNetworkGroupListResource edgeInNetworkGroupListFinalRes = new EdgeInNetworkGroupListResource();
        edgeInNetworkGroupListFinalRes.setEdgeInNetworkGroupResources(edgeInNetworkGroupResList);
        Set<EdgeInNetworkGroupResource> edgeInNetworkGroupResourcesSet = new HashSet(edgeInNetworkGroupListFinalRes.getEdgeInNetworkGroupResources());
        res.setEdgeInNetworkGroupResources(edgeInNetworkGroupResourcesSet);

        RoleInNetworkGroupList roleInNetworkGroupList = new RoleInNetworkGroupList(new ArrayList<>(networkGroup.getRolesInNetworkGroup()));
        List<RoleInNetworkGroupResource> roleInNetworkGroupResList = new RoleInNetworkGroupResourceAsm().toResources(roleInNetworkGroupList.getRoleInNetworkGroup());
        RoleInNetworkGroupListResource roleInNetworkGroupListFinalRes = new RoleInNetworkGroupListResource();
        roleInNetworkGroupListFinalRes.setRoleInNetworkGroupResources(roleInNetworkGroupResList);
        Set<RoleInNetworkGroupResource> roleInNetworkGroupResourcesSet = new HashSet(roleInNetworkGroupListFinalRes.getRoleInNetworkGroupResources());
        res.setRoleInNetworkGroupResources(roleInNetworkGroupResourcesSet);

        res.setEntryByUserType(networkGroup.getEntryByUserType());
        res.setEntryByUsername(networkGroup.getEntryByUsername());

        res.setLastUpdateByUserType(networkGroup.getLastUpdateByUserType());
        res.setLastUpdateByUsername(networkGroup.getLastUpdateByUsername());
        res.setLastModifiedUnixTime(networkGroup.getLastModifiedUnixTime());

        res.add(linkTo(methodOn(NetworkGroupRESTController_ForWeb.class).getNetworkGroup(networkGroup.getId())).withSelfRel());

        return res;
    }

}
