/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.resources.network;

import org.springframework.hateoas.ResourceSupport;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author samri_g64pbd3
 */
public class NodeAlertInfoListResource {

    private List<NodeAlertInfoResource> nodeAlertInfoResources = new ArrayList<>();
    private Long totalPages = new Long(0);
    private Long totalDocuments = new Long(0);

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalDocuments() {
        return totalDocuments;
    }

    public void setTotalDocuments(Long totalDocuments) {
        this.totalDocuments = totalDocuments;
    }

    public List<NodeAlertInfoResource> getNodeAlertInfoResources() {
        return nodeAlertInfoResources;
    }

    public void setNodeAlertInfoResources(List<NodeAlertInfoResource> nodeAlertInfoResources) {
        this.nodeAlertInfoResources = nodeAlertInfoResources;
    }

}
