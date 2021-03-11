package com.project.rest.resources.asm.network;

import com.project.core.services.util.NetworkGroupList;
import com.project.rest.mvc.web.NetworkGroupRESTController_ForWeb;
import com.project.rest.resources.network.NetworkGroupListResource;
import com.project.rest.resources.network.NetworkGroupResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.List;

/**
 *
 * @author Samrit
 */
public class NetworkGroupListResourceAsm extends ResourceAssemblerSupport<NetworkGroupList, NetworkGroupListResource> {

    public NetworkGroupListResourceAsm() {
        super(NetworkGroupRESTController_ForWeb.class, NetworkGroupListResource.class);
    }

    @Override
    public NetworkGroupListResource toResource(NetworkGroupList userList) {
        List<NetworkGroupResource> resList = new NetworkGroupResourceAsm().toResources(userList.getNetworkGroup());
        NetworkGroupListResource finalRes = new NetworkGroupListResource();
        finalRes.setNetworkGroupResources(resList);
        finalRes.setTotalPages(userList.getTotalPages());
        finalRes.setTotalDocuments(userList.getTotalDocuments());
        return finalRes;
    }
}
