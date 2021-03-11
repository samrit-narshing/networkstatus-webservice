package com.project.core.services.util;


import com.project.core.models.entities.network.EdgeInNetworkGroup;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samrit
 */
public class EdgeInNetworkGroupList {

    private Long totalPages = new Long(0);
    private Long totalDocuments = new Long(0);

    private List<EdgeInNetworkGroup> edgeInNetworkGroup = new ArrayList<>();

    public EdgeInNetworkGroupList(List<EdgeInNetworkGroup> list) {
        this.edgeInNetworkGroup = list;
    }

    public EdgeInNetworkGroupList(List<EdgeInNetworkGroup> list, Long totalDocuments, Long totalPages) {
        this.edgeInNetworkGroup = list;
        this.totalPages = totalPages;
        this.totalDocuments = totalDocuments;
    }

    public List<EdgeInNetworkGroup> getEdgeInNetworkGroup() {
        return edgeInNetworkGroup;
    }

    public void setEdgeInNetworkGroup(List<EdgeInNetworkGroup> edgeInNetworkGroup) {
        this.edgeInNetworkGroup = edgeInNetworkGroup;
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
