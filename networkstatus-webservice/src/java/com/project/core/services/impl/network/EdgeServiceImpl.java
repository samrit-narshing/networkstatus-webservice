package com.project.core.services.impl.network;

import com.project.core.models.entities.network.Edge;
import com.project.core.repositories.network.EdgeRepo;
import com.project.core.services.exceptions.EdgeExistsException;
import com.project.core.services.network.EdgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.project.core.services.util.EdgeList;
import com.project.rest.util.searchcriteria.network.EdgeSearchCriteria;

/**
 *
 * @author Samrit
 */
@Service
@Transactional
public class EdgeServiceImpl implements EdgeService {

    @Autowired
    private EdgeRepo edgeRepo;

    @Override
    public Edge create(Edge edge) throws Exception {
        Edge record = edgeRepo.findByLabel(edge.getLabel());
        if (record != null) {
            throw new EdgeExistsException();
        }
        return edgeRepo.create(edge);
    }

    @Override
    public Edge find(Long id) throws Exception {
        return edgeRepo.find(id);
    }

    @Override
    public Edge findByLabel(String code) throws Exception {
        return edgeRepo.findByLabel(code);
    }

    @Override
    public Edge findEnabledByLabel(String code) throws Exception {
        return edgeRepo.findEnabledByLabel(code);
    }

    @Override
    public EdgeList findAll() throws Exception {
        return new EdgeList(edgeRepo.findAll());
    }

    @Override
    public EdgeList findAllEnabled() throws Exception {
        return new EdgeList(edgeRepo.findAllEnabled());
    }

    @Override
    public EdgeList findBySearchCriteria(EdgeSearchCriteria searchCriteria) throws Exception {
        Long totalDocuments = countTotalDocumentsBySearchCriteria(searchCriteria);
        Long totalPages = (long) Math.ceil(totalDocuments / searchCriteria.getLimitResult().doubleValue());
        EdgeList userList = new EdgeList(edgeRepo.findBySearchCriteria(searchCriteria), totalDocuments, totalPages);
        return userList;
    }

    @Override
    public Long countTotalDocumentsBySearchCriteria(EdgeSearchCriteria searchCriteria) throws Exception {
        return edgeRepo.countTotalDocumentsBySearchCriteria(searchCriteria);
    }

    @Override
    public Edge update(Long id, Edge data) throws Exception {
        return edgeRepo.update(id, data);
    }

    @Override
    public Edge delete(Long id) throws Exception {
        return edgeRepo.delete(id);
    }

    @Override
    public Edge updateEnabledStatus(Long id) throws Exception {
        return edgeRepo.updateEnabledStatus(id);
    }

    @Override
    public Edge updateStatusAsEnabled(Long id) throws Exception {
        return edgeRepo.updateStatusAsEnabled(id);
    }

    @Override
    public Edge updateStatusAsDisabled(Long id) throws Exception {
        return edgeRepo.updateStatusAsDisabled(id);
    }

    @Override
    public Edge findEnabledByNodeIDs(Long nodeID1, Long nodeID2) throws Exception {
        return edgeRepo.findEnabledByNodeIDs(nodeID1, nodeID2);
    }

    @Override
    public int deleteAllEdges() throws Exception {
        return edgeRepo.deleteAllEdges();
    }

}
