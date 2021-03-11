package com.project.rest.resources.asm.network;

import com.project.core.services.util.EdgeInNetworkGroupList;
import com.project.rest.mvc.web.NetworkGroupRESTController_ForWeb;
import com.project.rest.resources.network.EdgeInNetworkGroupListResource;
import com.project.rest.resources.network.EdgeInNetworkGroupResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.List;

/**
 *
 * @author Samrit
 */
public class EdgeInNetworkGroupListResourceAsm extends ResourceAssemblerSupport<EdgeInNetworkGroupList, EdgeInNetworkGroupListResource> {

    public EdgeInNetworkGroupListResourceAsm() {
        super(NetworkGroupRESTController_ForWeb.class, EdgeInNetworkGroupListResource.class);
    }

    @Override
    public EdgeInNetworkGroupListResource toResource(EdgeInNetworkGroupList userList) {
        List<EdgeInNetworkGroupResource> resList = new EdgeInNetworkGroupResourceAsm().toResources(userList.getEdgeInNetworkGroup());
        EdgeInNetworkGroupListResource finalRes = new EdgeInNetworkGroupListResource();
        finalRes.setEdgeInNetworkGroupResources(resList);
        finalRes.setTotalPages(userList.getTotalPages());
        finalRes.setTotalDocuments(userList.getTotalDocuments());
        return finalRes;
    }
}
