package com.project.core.services.util;

import com.project.core.models.entities.network.NetworkGroup;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samrit
 */
public class NetworkGroupList {

    private Long totalPages = new Long(0);
    private Long totalDocuments = new Long(0);

    private List<NetworkGroup> networkGroup = new ArrayList<>();

    public NetworkGroupList(List<NetworkGroup> list) {
        this.networkGroup = list;
    }

    public NetworkGroupList(List<NetworkGroup> list, Long totalDocuments, Long totalPages) {
        this.networkGroup = list;
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

    public List<NetworkGroup> getNetworkGroup() {
        return networkGroup;
    }

    public void setNetworkGroup(List<NetworkGroup> networkGroup) {
        this.networkGroup = networkGroup;
    }

}
