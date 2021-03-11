package com.project.rest.resources.network;

import org.springframework.hateoas.ResourceSupport;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samrit
 */
public class NodeAndEdgeListInNetworkGroupResource extends ResourceSupport {

    private NodeInNetworkGroupListResource nodeInNetworkGroupListResource = new NodeInNetworkGroupListResource();
    private EdgeInNetworkGroupListResource edgeInNetworkGroupListResource = new EdgeInNetworkGroupListResource();

    private String status = new String();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public NodeInNetworkGroupListResource getNodeInNetworkGroupListResource() {
        return nodeInNetworkGroupListResource;
    }

    public void setNodeInNetworkGroupListResource(NodeInNetworkGroupListResource nodeInNetworkGroupListResource) {
        this.nodeInNetworkGroupListResource = nodeInNetworkGroupListResource;
    }

    public EdgeInNetworkGroupListResource getEdgeInNetworkGroupListResource() {
        return edgeInNetworkGroupListResource;
    }

    public void setEdgeInNetworkGroupListResource(EdgeInNetworkGroupListResource edgeInNetworkGroupListResource) {
        this.edgeInNetworkGroupListResource = edgeInNetworkGroupListResource;
    }

}
