/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.resources.network;

import com.project.core.models.entities.network.NodeAlertInfoArchive;
import com.project.rest.resources.util.*;
import org.springframework.hateoas.ResourceSupport;

/**
 *
 * @author Samrit
 */
public class NodeAlertInfoArchiveResource extends ResourceSupport {

    private Long tableId;
    private int type;
    private String description = "";
    private String loggedUsername = "";
    private String nodeType = "";
    private String nodeName = "";
    private Long entryDate = 0L;

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
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

    public NodeAlertInfoArchive toNodeAlertInfoArchive() {
        NodeAlertInfoArchive nodeAlertInfoArchive = new NodeAlertInfoArchive();
        nodeAlertInfoArchive.setId(tableId);
        nodeAlertInfoArchive.setEntryDate(entryDate);
        nodeAlertInfoArchive.setLoggedUsername(getLoggedUsername());
        nodeAlertInfoArchive.setDescription(getDescription());
        nodeAlertInfoArchive.setNodeName(getNodeName());
        nodeAlertInfoArchive.setNodeType(getNodeType());
        nodeAlertInfoArchive.setType(getType());
        return nodeAlertInfoArchive;
    }

}
