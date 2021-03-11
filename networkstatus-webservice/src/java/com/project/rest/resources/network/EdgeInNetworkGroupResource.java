/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.resources.network;

import com.project.core.models.entities.network.EdgeInNetworkGroup;
import com.project.core.models.entities.network.NodeInNetworkGroup;
import org.springframework.hateoas.ResourceSupport;

/**
 *
 * @author samri
 */
public class EdgeInNetworkGroupResource extends ResourceSupport {

    private Long edgeInNetworkGroupID = 0L;

    private String description = " ";

    private EdgeResource edgeResource = new EdgeResource();

    private NodeInNetworkGroupResource fromNodeInNetworkGroupResource = new NodeInNetworkGroupResource();

    private NodeInNetworkGroupResource toNodeInNetworkGroupResource = new NodeInNetworkGroupResource();

    private String label = " ";

    private boolean dashes = false;

    private int edge_length = 0;

    private int edge_value = 0;

    private String arrows = " ";

    private String title = " ";

    private boolean enabled = false;

    private boolean selected = false;

    public NodeInNetworkGroupResource getFromNodeInNetworkGroupResource() {
        return fromNodeInNetworkGroupResource;
    }

    public void setFromNodeInNetworkGroupResource(NodeInNetworkGroupResource fromNodeInNetworkGroupResource) {
        this.fromNodeInNetworkGroupResource = fromNodeInNetworkGroupResource;
    }

    public NodeInNetworkGroupResource getToNodeInNetworkGroupResource() {
        return toNodeInNetworkGroupResource;
    }

    public void setToNodeInNetworkGroupResource(NodeInNetworkGroupResource toNodeInNetworkGroupResource) {
        this.toNodeInNetworkGroupResource = toNodeInNetworkGroupResource;
    }

    public Long getEdgeInNetworkGroupID() {
        return edgeInNetworkGroupID;
    }

    public void setEdgeInNetworkGroupID(Long edgeInNetworkGroupID) {
        this.edgeInNetworkGroupID = edgeInNetworkGroupID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EdgeResource getEdgeResource() {
        return edgeResource;
    }

    public void setEdgeResource(EdgeResource edgeResource) {
        this.edgeResource = edgeResource;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isDashes() {
        return dashes;
    }

    public void setDashes(boolean dashes) {
        this.dashes = dashes;
    }

    public int getEdge_length() {
        return edge_length;
    }

    public void setEdge_length(int edge_length) {
        this.edge_length = edge_length;
    }

    public int getEdge_value() {
        return edge_value;
    }

    public void setEdge_value(int edge_value) {
        this.edge_value = edge_value;
    }

    public String getArrows() {
        return arrows;
    }

    public void setArrows(String arrows) {
        this.arrows = arrows;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public EdgeInNetworkGroup toEdgeInNetworkGroup() {
        EdgeInNetworkGroup edgeInNetworkGroup = new EdgeInNetworkGroup();
        edgeInNetworkGroup.setId(getEdgeInNetworkGroupID());
        edgeInNetworkGroup.setDescription(getDescription());

        edgeInNetworkGroup.setEnabled(isEnabled());
        edgeInNetworkGroup.setSelected(isSelected());

        edgeInNetworkGroup.setArrows(getArrows());
        edgeInNetworkGroup.setDashes(isDashes());
        edgeInNetworkGroup.setEdge_length(getEdge_length());
        edgeInNetworkGroup.setEdge_value(getEdge_value());
        edgeInNetworkGroup.setEnabled(isEnabled());
        edgeInNetworkGroup.setFromNodeInNetworkGroup(getFromNodeInNetworkGroupResource().toNodeInNetworkGroup());
        edgeInNetworkGroup.setLabel(getLabel());
        edgeInNetworkGroup.setToNodeInNetworkGroup(getToNodeInNetworkGroupResource().toNodeInNetworkGroup());
        edgeInNetworkGroup.setTitle(getTitle());

        return edgeInNetworkGroup;
    }
}
