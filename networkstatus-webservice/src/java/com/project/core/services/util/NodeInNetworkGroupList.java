package com.project.core.services.util;


import com.project.core.models.entities.network.NodeInNetworkGroup;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samrit
 */
public class NodeInNetworkGroupList {

    private Long totalPages = new Long(0);
    private Long totalDocuments = new Long(0);

    private List<NodeInNetworkGroup> nodeInNetworkGroup = new ArrayList<>();

    public NodeInNetworkGroupList(List<NodeInNetworkGroup> list) {
        this.nodeInNetworkGroup = list;
    }

    public NodeInNetworkGroupList(List<NodeInNetworkGroup> list, Long totalDocuments, Long totalPages) {
        this.nodeInNetworkGroup = list;
        this.totalPages = totalPages;
        this.totalDocuments = totalDocuments;
    }

    public List<NodeInNetworkGroup> getNodeInNetworkGroup() {
        return nodeInNetworkGroup;
    }

    public void setNodeInNetworkGroup(List<NodeInNetworkGroup> nodeInNetworkGroup) {
        this.nodeInNetworkGroup = nodeInNetworkGroup;
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
