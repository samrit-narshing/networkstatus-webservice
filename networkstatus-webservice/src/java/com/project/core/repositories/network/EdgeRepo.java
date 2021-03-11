package com.project.core.repositories.network;

import com.project.core.models.entities.network.Edge;
import com.project.rest.util.searchcriteria.network.EdgeSearchCriteria;
import java.util.List;

/**
 *
 * @author Samrit
 */
public interface EdgeRepo {

    public Edge create(Edge node);

    public Edge find(Long id);

    public Edge findByLabel(String label);

    public Edge findEnabledByLabel(String label);

    public Edge findEnabledByNodeIDs(Long nodeID1, Long nodeID2);

    public List<Edge> findAll();

    public List<Edge> findAllEnabled();

    public List<Edge> findBySearchCriteria(EdgeSearchCriteria searchCriteria);

    public Long countTotalDocumentsBySearchCriteria(EdgeSearchCriteria searchCriteria);

    public Edge update(Long id, Edge data);

    public Edge updateEnabledStatus(Long id);

    public Edge updateStatusAsEnabled(Long id);

    public Edge updateStatusAsDisabled(Long id);

    public Edge delete(Long id);

    public int deleteAllEdges();
}
