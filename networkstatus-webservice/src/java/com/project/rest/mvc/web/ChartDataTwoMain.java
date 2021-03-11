/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.mvc.web;

import java.util.List;

/**
 *
 * @author samri_g64pbd3
 */
public class ChartDataTwoMain {

    List<ChartDataTwo> nodes;

    List<ChartDataTwoEdge> edges;

    public List<ChartDataTwo> getNodes() {
        return nodes;
    }

    public void setNodes(List<ChartDataTwo> nodes) {
        this.nodes = nodes;
    }

    public List<ChartDataTwoEdge> getEdges() {
        return edges;
    }

    public void setEdges(List<ChartDataTwoEdge> edges) {
        this.edges = edges;
    }

}
