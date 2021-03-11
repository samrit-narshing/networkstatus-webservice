package com.project.core.services.util;


import com.project.core.models.entities.network.Edge;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samrit
 */
public class EdgeList {

    private Long totalPages = new Long(0);
    private Long totalDocuments = new Long(0);

    private List<Edge> edges = new ArrayList<>();

    public EdgeList(List<Edge> list) {
        this.edges = list;
    }

    public EdgeList(List<Edge> list, Long totalDocuments, Long totalPages) {
        this.edges = list;
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

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

}
