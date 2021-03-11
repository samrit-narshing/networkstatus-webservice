package com.project.rest.resources.network;

import com.project.rest.resources.util.*;
import org.springframework.hateoas.ResourceSupport;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samrit
 */
public class NodeAlertInfoArchiveListResource extends ResourceSupport {

    private List<NodeAlertInfoArchiveResource> nodeAlertInfoArchiveResources = new ArrayList<>();
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

    public List<NodeAlertInfoArchiveResource> getNodeAlertInfoArchiveResources() {
        return nodeAlertInfoArchiveResources;
    }

    public void setNodeAlertInfoArchiveResources(List<NodeAlertInfoArchiveResource> nodeAlertInfoArchiveResources) {
        this.nodeAlertInfoArchiveResources = nodeAlertInfoArchiveResources;
    }

}
