package com.project.rest.resources.network;

import org.springframework.hateoas.ResourceSupport;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samrit
 */
public class NodeAndEdgeListResource extends ResourceSupport {

    private NodeListResource nodeListResource = new NodeListResource();
    private EdgeListResource edgeListResource = new EdgeListResource();

    private String status = new String();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public NodeListResource getNodeListResource() {
        return nodeListResource;
    }

    public void setNodeListResource(NodeListResource nodeListResource) {
        this.nodeListResource = nodeListResource;
    }

    public EdgeListResource getEdgeListResource() {
        return edgeListResource;
    }

    public void setEdgeListResource(EdgeListResource edgeListResource) {
        this.edgeListResource = edgeListResource;
    }

}
