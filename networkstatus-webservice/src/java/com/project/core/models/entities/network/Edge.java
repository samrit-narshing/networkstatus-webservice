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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author samri
 */
@Entity
@Table(name = "edge")
public class Edge implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "edge-seqid-gen")
    @SequenceGenerator(name = "edge-seqid-gen", sequenceName = "hibernate_edge_sequence")
    private Long id;

    @OneToOne
    private Node fromNode;

    @OneToOne
    private Node toNode;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Node getFromNode() {
        return fromNode;
    }

    public void setFromNode(Node fromNode) {
        this.fromNode = fromNode;
    }

    public Node getToNode() {
        return toNode;
    }

    public void setToNode(Node toNode) {
        this.toNode = toNode;
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

}
