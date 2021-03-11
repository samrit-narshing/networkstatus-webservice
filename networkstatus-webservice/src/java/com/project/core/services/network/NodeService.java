package com.project.core.services.network;

import com.project.core.models.entities.network.Node;
import com.project.core.models.entities.network.NodeAlert;
import com.project.core.models.entities.network.NodeAlertInfo;
import com.project.core.services.util.NodeList;
import com.project.rest.util.searchcriteria.network.NodeSearchCriteria;
import java.util.List;

/**
 *
 * @author Samrit
 */
public interface NodeService {

    public Node create(Node node) throws Exception;

    public Node find(Long id) throws Exception;

    public Node findByLabel(String code) throws Exception;

    public Node findEnabledByLabel(String code) throws Exception;

    public NodeList findAll() throws Exception;

    public NodeList findAllEnabled() throws Exception;

    public NodeList findAllEnabledNodesExculdingNodesInNetworkGroup(List<Long> exculdingNodeIdList) throws Exception;

    public NodeList findBySearchCriteria(NodeSearchCriteria searchCriteria) throws Exception;

    public Long countTotalDocumentsBySearchCriteria(NodeSearchCriteria searchCriteria) throws Exception;

    public Node update(Long id, Node data) throws Exception;

    public Node updateEnabledStatus(Long id) throws Exception;

    public Node updateStatusAsEnabled(Long id) throws Exception;

    public Node updateStatusAsDisabled(Long id) throws Exception;

    public Node updateNodeAlert(Long id, Node data) throws Exception;

    public Node updateNodeAlertAsReset(Long id) throws Exception;

    public Node updateNodeAlertToAddNodeAlertInfo(Long id, Node data) throws Exception;

    public Node updateNodeAlertToRemoveNodeAlertInfo(Long id, NodeAlertInfo data) throws Exception;

    public Node updateNodeCordinates(Long id, Node data) throws Exception;

    public Node delete(Long id) throws Exception;

    public int deleteAllNodes() throws Exception;

    public int deleteNodeByLabel(String label) throws Exception;

}
