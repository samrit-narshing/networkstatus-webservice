package com.project.rest.resources.network;

import org.springframework.hateoas.ResourceSupport;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samrit
 */
public class NetworkGroupListResource extends ResourceSupport {

    private List<NetworkGroupResource> networkGroupResources = new ArrayList<>();
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

    public List<NetworkGroupResource> getNetworkGroupResources() {
        return networkGroupResources;
    }

    public void setNetworkGroupResources(List<NetworkGroupResource> networkGroupResources) {
        this.networkGroupResources = networkGroupResources;
    }

}
