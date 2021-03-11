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
@Table(name = "edge_networkgroup")
public class EdgeInNetworkGroup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "edge-networkgroup-seqid-gen")
    @SequenceGenerator(name = "edge-networkgroup-seqid-gen", sequenceName = "hibernate_edge_networkgroup_sequence")
    private Long id;
    private String description;

    @OneToOne
    private Edge edge;

    @OneToOne
    private NodeInNetworkGroup fromNodeInNetworkGroup;

    @OneToOne
    private NodeInNetworkGroup toNodeInNetworkGroup;

    @Column(name = "label")
    private String label;

    @Column(name = "dashes")
    private boolean dashes;

    @Column(name = "edge_length")
    private int edge_length;

    @Column(name = "edge_value")
    private int edge_value;

    @Column(name = "arrows")
    private String arrows;

    @Column(name = "title")
    private String title;

    private boolean enabled;

    private boolean selected;

    @Transient
    private String networkGroupCode;

    @Transient
    private Long networkGroupId;

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

    public Edge getEdge() {
        return edge;
    }

    public void setEdge(Edge edge) {
        this.edge = edge;
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

    public NodeInNetworkGroup getFromNodeInNetworkGroup() {
        return fromNodeInNetworkGroup;
    }

    public void setFromNodeInNetworkGroup(NodeInNetworkGroup fromNodeInNetworkGroup) {
        this.fromNodeInNetworkGroup = fromNodeInNetworkGroup;
    }

    public NodeInNetworkGroup getToNodeInNetworkGroup() {
        return toNodeInNetworkGroup;
    }

    public void setToNodeInNetworkGroup(NodeInNetworkGroup toNodeInNetworkGroup) {
        this.toNodeInNetworkGroup = toNodeInNetworkGroup;
    }

}
