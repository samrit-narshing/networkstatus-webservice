package com.project.core.services.impl.network;

import com.project.core.models.entities.network.Node;
import com.project.core.models.entities.network.NodeAlert;
import com.project.core.models.entities.network.NodeAlertInfo;
import com.project.core.models.entities.user.User;
import com.project.core.repositories.network.NodeRepo;
import com.project.core.services.exceptions.NodeExistsException;
import com.project.core.services.network.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.project.core.services.util.NodeList;
import com.project.rest.util.searchcriteria.network.NodeSearchCriteria;
import java.util.List;

/**
 *
 * @author Samrit
 */
@Service
@Transactional
public class NodeServiceImpl implements NodeService {

    @Autowired
    private NodeRepo nodeRepo;

    @Override
    public Node create(Node node) throws Exception {
        Node record = nodeRepo.findByLabel(node.getLabel());
        if (record != null) {
            throw new NodeExistsException();
        }
        return nodeRepo.create(node);
    }

    @Override
    public Node find(Long id) throws Exception {
        return nodeRepo.find(id);
    }

    @Override
    public Node findByLabel(String code) throws Exception {
        return nodeRepo.findByLabel(code);
    }

    @Override
    public Node findEnabledByLabel(String code) throws Exception {
        return nodeRepo.findEnabledByLabel(code);
    }

    @Override
    public NodeList findAll() throws Exception {
        return new NodeList(nodeRepo.findAll());
    }

    @Override
    public NodeList findAllEnabled() throws Exception {
        return new NodeList(nodeRepo.findAllEnabled());
    }

    @Override
    public NodeList findAllEnabledNodesExculdingNodesInNetworkGroup(List<Long> exculdingNodeIdList) throws Exception {
        return new NodeList(nodeRepo.findAllEnabledNodesExculdingNodesInNetworkGroup(exculdingNodeIdList));
    }

    @Override
    public NodeList findBySearchCriteria(NodeSearchCriteria searchCriteria) throws Exception {
        Long totalDocuments = countTotalDocumentsBySearchCriteria(searchCriteria);
        Long totalPages = (long) Math.ceil(totalDocuments / searchCriteria.getLimitResult().doubleValue());
        NodeList userList = new NodeList(nodeRepo.findBySearchCriteria(searchCriteria), totalDocuments, totalPages);
        return userList;
    }

    @Override
    public Long countTotalDocumentsBySearchCriteria(NodeSearchCriteria searchCriteria) throws Exception {
        return nodeRepo.countTotalDocumentsBySearchCriteria(searchCriteria);
    }

    @Override
    public Node update(Long id, Node data) throws Exception {
        return nodeRepo.update(id, data);
    }

    @Override
    public Node delete(Long id) throws Exception {
        return nodeRepo.delete(id);
    }

    @Override
    public Node updateEnabledStatus(Long id) throws Exception {
        return nodeRepo.updateEnabledStatus(id);
    }

    @Override
    public Node updateStatusAsEnabled(Long id) throws Exception {
        return nodeRepo.updateStatusAsEnabled(id);
    }

    @Override
    public Node updateStatusAsDisabled(Long id) throws Exception {
        return nodeRepo.updateStatusAsDisabled(id);
    }

    @Override
    public Node updateNodeAlert(Long id, Node data) throws Exception {
        return nodeRepo.updateNodeAlert(id, data);
    }

    @Override
    public Node updateNodeAlertAsReset(Long id) throws Exception {
        return nodeRepo.updateNodeAlertAsReset(id);
    }

    @Override
    public Node updateNodeAlertToAddNodeAlertInfo(Long id, Node data) throws Exception {
        return nodeRepo.updateNodeAlertToAddNodeAlertInfo(id, data);
    }

    @Override
    public Node updateNodeAlertToRemoveNodeAlertInfo(Long id, NodeAlertInfo data) throws Exception {
        return nodeRepo.updateNodeAlertToRemoveNodeAlertInfo(id, data);
    }

    @Override
    public Node updateNodeCordinates(Long id, Node data) throws Exception {
        return nodeRepo.updateNodeCordinates(id, data);
    }

    @Override
    public int deleteAllNodes() throws Exception {
        return nodeRepo.deleteAllNodes();
    }

    @Override
    public int deleteNodeByLabel(String label) throws Exception {
        return nodeRepo.deleteNodeByLabel(label);
    }

}
