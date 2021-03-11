package com.project.rest.resources.asm.network;

import com.project.core.models.entities.network.EdgeInNetworkGroup;
import com.project.rest.mvc.web.NetworkGroupRESTController_ForWeb;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import com.project.rest.resources.network.EdgeInNetworkGroupResource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 *
 * @author Samrit
 */
public class EdgeInNetworkGroupResourceAsm extends ResourceAssemblerSupport<EdgeInNetworkGroup, EdgeInNetworkGroupResource> {

    public EdgeInNetworkGroupResourceAsm() {
        super(NetworkGroupRESTController_ForWeb.class, EdgeInNetworkGroupResource.class);
    }

    @Override
    public EdgeInNetworkGroupResource toResource(EdgeInNetworkGroup user) {
        EdgeInNetworkGroupResource res = new EdgeInNetworkGroupResource();
        res.setEdgeInNetworkGroupID(user.getId());
        res.setDescription(user.getDescription());
        res.setEnabled(user.isEnabled());
        res.setSelected(user.isSelected());
        res.setArrows(user.getArrows());
        res.setDashes(user.isDashes());
        res.setEdge_length(user.getEdge_length());
        res.setEdge_value(user.getEdge_value());
        res.setFromNodeInNetworkGroupResource(new NodeInNetworkGroupResourceAsm().toResource(user.getFromNodeInNetworkGroup()));
        res.setLabel(user.getLabel());
        res.setToNodeInNetworkGroupResource(new NodeInNetworkGroupResourceAsm().toResource(user.getToNodeInNetworkGroup()));
        res.setTitle(user.getTitle());

        res.add(linkTo(methodOn(NetworkGroupRESTController_ForWeb.class).getEdgeInNetworkGroup(user.getId())).withSelfRel());
        return res;
    }

}
