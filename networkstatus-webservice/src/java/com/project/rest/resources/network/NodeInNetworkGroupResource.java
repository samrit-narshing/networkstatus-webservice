/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.resources.network;

import com.project.core.models.entities.network.NodeInNetworkGroup;
import org.springframework.hateoas.ResourceSupport;

/**
 *
 * @author samri
 */
public class NodeInNetworkGroupResource extends ResourceSupport {

    private Long nodeInNetworkGroupID = 0L;
    private String description = " ";
    private NodeResource nodeResource = new NodeResource();

    private double domXValue = 0D;

    private double domYValue = 0D;
    private double canvasXValue = 0D;

    private double canvasYValue = 0D;

    private double zoomScale = 0D;

    private boolean enabled = false;

    private boolean selected = false;

    public Long getNodeInNetworkGroupID() {
        return nodeInNetworkGroupID;
    }

    public void setNodeInNetworkGroupID(Long nodeInNetworkGroupID) {
        this.nodeInNetworkGroupID = nodeInNetworkGroupID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public NodeResource getNodeResource() {
        return nodeResource;
    }

    public void setNodeResource(NodeResource nodeResource) {
        this.nodeResource = nodeResource;
    }

    public double getDomXValue() {
        return domXValue;
    }

    public void setDomXValue(double domXValue) {
        this.domXValue = domXValue;
    }

    public double getDomYValue() {
        return domYValue;
    }

    public void setDomYValue(double domYValue) {
        this.domYValue = domYValue;
    }

    public double getCanvasXValue() {
        return canvasXValue;
    }

    public void setCanvasXValue(double canvasXValue) {
        this.canvasXValue = canvasXValue;
    }

    public double getCanvasYValue() {
        return canvasYValue;
    }

    public void setCanvasYValue(double canvasYValue) {
        this.canvasYValue = canvasYValue;
    }

    public double getZoomScale() {
        return zoomScale;
    }

    public void setZoomScale(double zoomScale) {
        this.zoomScale = zoomScale;
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

    public NodeInNetworkGroup toNodeInNetworkGroup() {
        NodeInNetworkGroup nodeInNetworkGroup = new NodeInNetworkGroup();
        nodeInNetworkGroup.setId(getNodeInNetworkGroupID());
        nodeInNetworkGroup.setDescription(getDescription());
        nodeInNetworkGroup.setNode(getNodeResource().toNode());
        nodeInNetworkGroup.setCanvasXValue(getCanvasXValue());
        nodeInNetworkGroup.setCanvasYValue(getCanvasYValue());
        nodeInNetworkGroup.setDomXValue(getDomXValue());
        nodeInNetworkGroup.setDomYValue(getDomYValue());
        nodeInNetworkGroup.setEnabled(isEnabled());
        nodeInNetworkGroup.setZoomScale(getZoomScale());
        nodeInNetworkGroup.setSelected(isSelected());
        
        return nodeInNetworkGroup;
    }
}
