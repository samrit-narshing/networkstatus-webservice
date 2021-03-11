package com.project.rest.resources.network;

import com.project.core.models.entities.network.Node;
import org.springframework.hateoas.ResourceSupport;

/**
 *
 * @author Samrit
 */
public class NodeResource extends ResourceSupport {

    private Long nodeID = 0L;
    private String description = "";
    private String uniqueID = "";
    private int height;
    private String label = "";
    private String redirectingURLLink = "";
    private String type = "";
    private boolean enabled = false;
    private NodeImageLinkResource fill = new NodeImageLinkResource();
    private int nodeValue = 0;
    private String title = "";
    private NodeAlertResource alert = new NodeAlertResource();

    private double domXValue = 0.0D;
    private double domYValue = 0.0D;
    private double canvasXValue = 0.0D;
    private double canvasYValue = 0.0D;
    private double zoomScale = 0.0D;

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

    public NodeImageLinkResource getFill() {
        return fill;
    }

    public void setFill(NodeImageLinkResource fill) {
        this.fill = fill;
    }

    public Long getNodeID() {
        return nodeID;
    }

    public void setNodeID(Long nodeID) {
        this.nodeID = nodeID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getRedirectingURLLink() {
        return redirectingURLLink;
    }

    public void setRedirectingURLLink(String redirectingURLLink) {
        this.redirectingURLLink = redirectingURLLink;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(int nodeValue) {
        this.nodeValue = nodeValue;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NodeAlertResource getAlert() {
        return alert;
    }

    public void setAlert(NodeAlertResource alert) {
        this.alert = alert;
    }

    public Node toNode() {
        Node object = new Node();
        object.setId(getNodeID());
        object.setDescription(getDescription());
        object.setEnabled(isEnabled());
        object.setHeight(getHeight());
        object.setLabel(getLabel());
        object.setRedirectingURLLink(getRedirectingURLLink());
        object.setType(getType());
        object.setUniqueID(getUniqueID());
        object.setFill(getFill().toNodeImageLink());
        object.setNodeValue(getNodeValue());
        object.setTitle(getTitle());
        object.setAlert(getAlert().toNNodeAlert());

        object.setDomXValue(getDomXValue());
        object.setDomYValue(getDomYValue());
        object.setCanvasXValue(getCanvasXValue());
        object.setCanvasYValue(getCanvasYValue());
        object.setZoomScale(getZoomScale());

        return object;
    }
}
