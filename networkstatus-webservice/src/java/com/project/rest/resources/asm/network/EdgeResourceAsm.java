package com.project.rest.resources.asm.network;

import com.project.core.models.entities.network.Edge;
import com.project.rest.mvc.web.EdgeRESTController_ForWeb;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import com.project.rest.resources.network.EdgeResource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 *
 * @author Samrit
 */
public class EdgeResourceAsm extends ResourceAssemblerSupport<Edge, EdgeResource> {

    public EdgeResourceAsm() {
        super(EdgeRESTController_ForWeb.class, EdgeResource.class);
    }

    @Override
    public EdgeResource toResource(Edge object) {
        EdgeResource res = new EdgeResource();
        res.setEdgeID(object.getId());
        res.setArrows(object.getArrows());
        res.setDashes(object.isDashes());
        res.setEdge_length(object.getEdge_length());
        res.setEdge_value(object.getEdge_value());
        res.setEnabled(object.isEnabled());
        res.setFromNodeResource(new NodeResourceAsm().toResource(object.getFromNode()));
        res.setLabel(object.getLabel());
        res.setTitle(object.getTitle());
        res.setToNodeResource(new NodeResourceAsm().toResource(object.getToNode()));

        res.add(linkTo(methodOn(EdgeRESTController_ForWeb.class).getEdge(object.getId())).withSelfRel());
        return res;
    }

}
