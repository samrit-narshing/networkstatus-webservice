package com.project.core.repositories.network;

import com.project.core.models.entities.network.Node;
import com.project.core.models.entities.network.NodeAlert;
import com.project.core.models.entities.network.NodeAlertInfo;
import com.project.rest.util.searchcriteria.network.NodeSearchCriteria;
import java.util.List;

/**
 *
 * @author Samrit
 */
public interface NodeRepo {

    public Node create(Node node);

    public Node find(Long id);

    public Node findByLabel(String label);

    public Node findEnabledByLabel(String label);

    public List<Node> findAll();

    public List<Node> findAllEnabled();

    public List<Node> findAllEnabledNodesExculdingNodesInNetworkGroup(List<Long> exculdingNodeIdList);

    public List<Node> findBySearchCriteria(NodeSearchCriteria searchCriteria);

    public Long countTotalDocumentsBySearchCriteria(NodeSearchCriteria searchCriteria);

    public Node update(Long id, Node data);

    public Node updateEnabledStatus(Long id);

    public Node updateStatusAsEnabled(Long id);

    public Node updateStatusAsDisabled(Long id);

    public Node updateNodeAlert(Long id, Node data);

    public Node updateNodeCordinates(Long id, Node data);

    public Node updateNodeAlertToAddNodeAlertInfo(Long id, Node data);

    public Node updateNodeAlertToRemoveNodeAlertInfo(Long id, NodeAlertInfo data);

    public Node updateNodeAlertAsReset(Long id);

    public Node delete(Long id);

    public int deleteAllNodes();

    public int deleteNodeByLabel(String label);
}
