package com.project.rest.resources.asm.network;

import com.project.core.services.util.EdgeList;
import com.project.rest.mvc.web.EdgeRESTController_ForWeb;
import com.project.rest.resources.network.EdgeListResource;
import com.project.rest.resources.network.EdgeResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.List;

/**
 *
 * @author Samrit
 */
public class EdgeListResourceAsm extends ResourceAssemblerSupport<EdgeList, EdgeListResource> {

    public EdgeListResourceAsm() {
        super(EdgeRESTController_ForWeb.class, EdgeListResource.class);
    }

    @Override
    public EdgeListResource toResource(EdgeList list) {
        List<EdgeResource> resList = new EdgeResourceAsm().toResources(list.getEdges());
        EdgeListResource finalRes = new EdgeListResource();
        finalRes.setEdgeResources(resList);
        finalRes.setTotalPages(list.getTotalPages());
        finalRes.setTotalDocuments(list.getTotalDocuments());
        return finalRes;
    }
}
