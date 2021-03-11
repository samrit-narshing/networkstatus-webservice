package com.project.core.services.util;


import com.project.core.models.entities.network.Node;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samrit
 */
public class NodeList {

    private Long totalPages = new Long(0);
    private Long totalDocuments = new Long(0);

    private List<Node> nodes = new ArrayList<>();

    public NodeList(List<Node> list) {
        this.nodes = list;
    }

    public NodeList(List<Node> list, Long totalDocuments, Long totalPages) {
        this.nodes = list;
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

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

}
