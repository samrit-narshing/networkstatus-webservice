package com.project.core.services.network;

import com.project.core.models.entities.network.Edge;
import com.project.core.services.util.EdgeList;
import com.project.rest.util.searchcriteria.network.EdgeSearchCriteria;

/**
 *
 * @author Samrit
 */
public interface EdgeService {

    public Edge create(Edge edge) throws Exception;

    public Edge find(Long id) throws Exception;

    public Edge findByLabel(String code) throws Exception;

    public Edge findEnabledByLabel(String code) throws Exception;

    public Edge findEnabledByNodeIDs(Long nodeID1, Long nodeID2) throws Exception;

    public EdgeList findAll() throws Exception;

    public EdgeList findAllEnabled() throws Exception;

    public EdgeList findBySearchCriteria(EdgeSearchCriteria searchCriteria) throws Exception;

    public Long countTotalDocumentsBySearchCriteria(EdgeSearchCriteria searchCriteria) throws Exception;

    public Edge update(Long id, Edge data) throws Exception;

    public Edge updateEnabledStatus(Long id) throws Exception;

    public Edge updateStatusAsEnabled(Long id) throws Exception;

    public Edge updateStatusAsDisabled(Long id) throws Exception;

    public Edge delete(Long id) throws Exception;

    public int deleteAllEdges() throws Exception;
}
