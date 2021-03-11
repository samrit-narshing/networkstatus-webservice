package com.project.core.services.util;

import com.project.core.models.entities.network.NodeAlertInfoArchive;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samrit
 */
public class NodeAlertInfoArchiveList {

    private Long totalPages = new Long(0);
    private Long totalDocuments = new Long(0);

    private List<NodeAlertInfoArchive> nodeAlertInfoArchives = new ArrayList<>();

    public NodeAlertInfoArchiveList(List<NodeAlertInfoArchive> nodeAlertInfoArchives) {
        this.nodeAlertInfoArchives = nodeAlertInfoArchives;
    }

    public NodeAlertInfoArchiveList(List<NodeAlertInfoArchive> nodeAlertInfoArchives, Long totalDocuments, Long totalPages) {
        this.nodeAlertInfoArchives = nodeAlertInfoArchives;
        this.totalPages = totalPages;
        this.totalDocuments = totalDocuments;
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

    public List<NodeAlertInfoArchive> getNodeAlertInfoArchives() {
        return nodeAlertInfoArchives;
    }

    public void setNodeAlertInfoArchives(List<NodeAlertInfoArchive> nodeAlertInfoArchives) {
        this.nodeAlertInfoArchives = nodeAlertInfoArchives;
    }

}
