/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.services.impl.network;

import com.project.core.models.entities.network.NodeAlertInfoArchive;
import com.project.core.repositories.network.NodeAlertInfoArchiveRepo;
import com.project.core.services.network.NodeAlertInfoArchiveService;
import com.project.core.services.util.NodeAlertInfoArchiveList;
import com.project.rest.util.searchcriteria.network.NodeAlertInfoArchiveSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Samrit
 */
@Service
@Transactional
public class NodeAlertInfoArchiveServiceImpl implements NodeAlertInfoArchiveService {

    @Autowired
    private NodeAlertInfoArchiveRepo nodeAlertInfoArchiveRepo;

    @Override
    public NodeAlertInfoArchive createNodeAlertInfoArchive(NodeAlertInfoArchive data) throws Exception {
        return nodeAlertInfoArchiveRepo.createNodeAlertInfoArchive(data);
    }

    @Override
    public NodeAlertInfoArchive findNodeAlertInfoArchive(Long id) throws Exception {
        return nodeAlertInfoArchiveRepo.findNodeAlertInfoArchive(id);
    }

    @Override
    public NodeAlertInfoArchiveList findAllNodeAlertInfoArchive() throws Exception {
        return new NodeAlertInfoArchiveList(nodeAlertInfoArchiveRepo.findAllNodeAlertInfoArchive());
    }

    @Override
    public NodeAlertInfoArchiveList findNodeAlertInfoArchiveBySearchCriteria(NodeAlertInfoArchiveSearchCriteria searchCriteria) throws Exception {
        Long totalDocuments = countTotalDocumentsOfNodeAlertInfoArchiveBySearchCriteria(searchCriteria);
        Long totalPages = (long) Math.ceil(totalDocuments / searchCriteria.getLimitResult().doubleValue());
        NodeAlertInfoArchiveList nodeAlertInfoArchiveList = new NodeAlertInfoArchiveList(nodeAlertInfoArchiveRepo.findNodeAlertInfoArchiveBySearchCriteria(searchCriteria), totalDocuments, totalPages);
        return nodeAlertInfoArchiveList;
    }

    @Override
    public Long countTotalDocumentsOfNodeAlertInfoArchiveBySearchCriteria(NodeAlertInfoArchiveSearchCriteria searchCriteria) throws Exception {
        return nodeAlertInfoArchiveRepo.countTotalDocumentsOfNodeAlertInfoArchiveBySearchCriteria(searchCriteria);
    }

    @Override
    public NodeAlertInfoArchive deleteNodeAlertInfoArchive(Long id) throws Exception {
        return nodeAlertInfoArchiveRepo.deleteNodeAlertInfoArchive(id);
    }

    @Override
    public int deleteAllNodeAlertInfoArchive() throws Exception {
        return nodeAlertInfoArchiveRepo.deleteAllNodeAlertInfoArchive();
    }

}
