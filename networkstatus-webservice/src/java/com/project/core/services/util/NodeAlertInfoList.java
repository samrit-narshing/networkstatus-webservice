/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.services.util;

import com.project.core.models.entities.network.NodeAlertInfo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author samri_g64pbd3
 */
public class NodeAlertInfoList {

    private Long totalPages = new Long(0);
    private Long totalDocuments = new Long(0);

    private List<NodeAlertInfo> nodeAlertInfos = new ArrayList<>();

    public NodeAlertInfoList(List<NodeAlertInfo> list) {
        this.nodeAlertInfos = list;
    }

    public NodeAlertInfoList(List<NodeAlertInfo> list, Long totalDocuments, Long totalPages) {
        this.nodeAlertInfos = list;
        this.totalPages = totalPages;
        this.totalDocuments = totalDocuments;
    }

    public List<NodeAlertInfo> getNodeAlertInfos() {
        return nodeAlertInfos;
    }

    public void setNodeAlertInfos(List<NodeAlertInfo> nodeAlertInfos) {
        this.nodeAlertInfos = nodeAlertInfos;
    }

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
}
