/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.models.entities.network;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
//import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author samri
 */
@Entity
@Table(name = "node")
public class Node implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "node-seqid-gen")
    @SequenceGenerator(name = "node-seqid-gen", sequenceName = "hibernate_node_sequence")
    private Long id;

    @Lob
    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "unique_id")
    private String uniqueID;
    private int height;

    @Column(name = "label")
    private String label;

    @Column(name = "redirecting_url_link")
    private String redirectingURLLink;

    @Column(name = "type")
    private String type;

    private boolean enabled;

    @OneToOne(cascade = CascadeType.ALL)
    private NodeImageLink fill;

    @Column(name = "node_value")
    private int nodeValue;

    @Column(name = "title")
    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    private NodeAlert alert;

    @Column(name = "dom_x_value")
    private double domXValue;

    @Column(name = "dom_y_value")
    private double domYValue;

    @Column(name = "canvas_x_value")
    private double canvasXValue;

    @Column(name = "canvas_y_value")
    private double canvasYValue;

    @Column(name = "zoom_scale")
    private double zoomScale;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(int nodeValue) {
        this.nodeValue = nodeValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public NodeImageLink getFill() {
        return fill;
    }

    public void setFill(NodeImageLink fill) {
        this.fill = fill;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public NodeAlert getAlert() {
        return alert;
    }

    public void setAlert(NodeAlert alert) {
        this.alert = alert;
    }

}
