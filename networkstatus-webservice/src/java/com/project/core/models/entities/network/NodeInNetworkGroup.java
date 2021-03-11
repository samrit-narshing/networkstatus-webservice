/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.models.entities.network;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Samrit
 */
@Entity
@Table(name = "node_networkgroup")
public class NodeInNetworkGroup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "node-networkgroup-seqid-gen")
    @SequenceGenerator(name = "node-networkgroup-seqid-gen", sequenceName = "hibernate_node_networkgroup_sequence")
    private Long id;
    private String description;

    @OneToOne
    private Node node;

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

    private boolean enabled;

    private boolean selected;

    @Transient
    private String networkGroupCode;

    @Transient
    private Long networkGroupId;

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

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public String getNetworkGroupCode() {
        return networkGroupCode;
    }

    public void setNetworkGroupCode(String networkGroupCode) {
        this.networkGroupCode = networkGroupCode;
    }

    public Long getNetworkGroupId() {
        return networkGroupId;
    }

    public void setNetworkGroupId(Long networkGroupId) {
        this.networkGroupId = networkGroupId;
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

}
