/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.models.entities.network;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author samri_g64pbd3
 */
@Entity
@Table(name = "nodealertinfo_archive")
public class NodeAlertInfoArchive {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nodealertinfo_archive-seqid-gen")
    @SequenceGenerator(name = "nodealertinfo_archive-seqid-gen", sequenceName = "hibernate_nodealertinfo_archive_sequence")
    private Long id;

    @Column(name = "type")
    private int type;

    @Lob
    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "logged_username")
    private String loggedUsername;

    @Column(name = "node_type")
    private String nodeType;

    @Column(name = "node_name")
    private String nodeName;

    @Column(name = "entry_date")
    private Long entryDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLoggedUsername() {
        return loggedUsername;
    }

    public void setLoggedUsername(String loggedUsername) {
        this.loggedUsername = loggedUsername;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Long getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Long entryDate) {
        this.entryDate = entryDate;
    }

}
