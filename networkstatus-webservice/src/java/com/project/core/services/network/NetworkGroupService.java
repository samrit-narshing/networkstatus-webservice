/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.services.network;

import com.project.core.models.entities.network.Edge;
import com.project.core.models.entities.network.EdgeInNetworkGroup;
import com.project.core.models.entities.network.NetworkGroup;
import com.project.core.models.entities.network.Node;
import com.project.core.models.entities.network.NodeInNetworkGroup;
import com.project.core.models.entities.network.RoleInNetworkGroup;
import com.project.core.models.entities.user.User;
import com.project.core.services.util.EdgeInNetworkGroupList;
import com.project.core.services.util.NetworkGroupList;
import com.project.core.services.util.NodeInNetworkGroupList;
import com.project.rest.util.searchcriteria.network.EdgeInNetworkGroupSearchCriteria;
import com.project.rest.util.searchcriteria.network.NetworkGroupSearchCriteria;
import com.project.rest.util.searchcriteria.network.NodeInNetworkGroupSearchCriteria;
import java.util.List;

/**
 *
 * @author samri_g64pbd3
 */
public interface NetworkGroupService {

    public NetworkGroup createNetworkGroup(NetworkGroup networkGroup) throws Exception;

    public NetworkGroup findNetworkGroup(Long id) throws Exception;

    public NetworkGroup findNetworkGroupByCode(String code) throws Exception;

    public NetworkGroupList findAllNetworkGroups() throws Exception;

    public NetworkGroup updateNetworkGroup(Long id, NetworkGroup data) throws Exception;

    public NetworkGroup updateNetworkGroupCode(Long id, NetworkGroup data) throws Exception;

    public NetworkGroupList findNetworkGroupsBySearchCriteria(NetworkGroupSearchCriteria searchCriteria, User user) throws Exception;

    public Long countTotalDocumentsBySearchCriteria(NetworkGroupSearchCriteria searchCriteria, User user) throws Exception;

    public NetworkGroup deleteNetworkGroup(Long id) throws Exception;

//    public NetworkGroup updateNetworkGroupWithDetails(Long id, NetworkGroup data) throws Exception;
    public NetworkGroup updateNetworkGroupForAddNodes(Long id, NetworkGroup data, Node node) throws Exception;

    public NetworkGroup updateNetworkGroupForUpdateAndRemoveNodes(Long id, NetworkGroup data) throws Exception;

    public NetworkGroup updateNetworkGroupForAddNodes_NEW(Long id, NetworkGroup data) throws Exception;

    public NetworkGroup updateNetworkGroupForRemoveNodes_NEW(Long id, NetworkGroup data) throws Exception;

    public NetworkGroup updateNetworkGroupForAddNodes_NEW(Long id, List<NodeInNetworkGroup> data) throws Exception;

    public NetworkGroup updateNetworkGroupForRemoveNodes_NEW(Long id, List<NodeInNetworkGroup> data) throws Exception;

    public NetworkGroup updateNetworkGroupForUpdateNodes_NEW(Long id, List<NodeInNetworkGroup> data) throws Exception;

    public NetworkGroup findNetworkGroupByNode(String name, Node node) throws Exception;

    public NetworkGroup findNetworkGroupByNodeInNetworkGroup(String networkGroupCode, NodeInNetworkGroup nodeInNetworkGroup) throws Exception;

    public NetworkGroup updateNetworkGroupForAddEdges_NEW(Long id, List<EdgeInNetworkGroup> data) throws Exception;

    public NetworkGroup updateNetworkGroupForRemoveEdges_NEW(Long id, List<EdgeInNetworkGroup> data) throws Exception;

    public NetworkGroup updateNetworkGroupForUpdateEdges_NEW(Long id, List<EdgeInNetworkGroup> data) throws Exception;

    public NetworkGroup findNetworkGroupByEdgeInNetworkGroup(String networkGroupCode, EdgeInNetworkGroup edgeInNetworkGroup) throws Exception;

    public NodeInNetworkGroup findNodeInNetworkGroup(Long id) throws Exception;

    public EdgeInNetworkGroup findEdgeInNetworkGroup(Long id) throws Exception;

    public RoleInNetworkGroup findRoleInNetworkGroup(Long id) throws Exception;

    public EdgeInNetworkGroup updateEdgeInNetworkGroup(Long id, EdgeInNetworkGroup data) throws Exception;

    public NetworkGroup findNetworkGroupIfNodeInNetworkGroupsAreAlreadyExists(Long networkGroupID, Long nodeInNetworkGroupId1, Long nodeInNetworkGroupId2) throws Exception;

    public List<Long> findAllEnabledNetworkGroupsIds() throws Exception;

    public List<String> findAllEnabledNetworkGroupsIdsWithNames() throws Exception;

    public NodeInNetworkGroup updateNodeInNetworkGroupCordinates(Long id, NodeInNetworkGroup data) throws Exception;

    public NetworkGroup clearAndUpdateNetworkGroupForAddEdges_NEW(Long id, List<EdgeInNetworkGroup> data) throws Exception;

    public int deleteAllNetworkGroup() throws Exception;

    public int deleteAllNodeInNetworkGroup() throws Exception;

    public int deleteAllEdgeInNetworkGroup() throws Exception;

    public NetworkGroup findNetworkGroupByNodeInNetworkGroupId(Long nodeInNetworkGroupId) throws Exception;

    public NetworkGroupList findAllNetworkGroupsByNodeId(Long nodeId) throws Exception;
//
//    public NetworkGroup findNetworkGroupByEdge(String name, Edge edge) throws Exception;
//
//    public NetworkGroup updateNetworkGroupForAddEdge(Long id, NetworkGroup data, Edge edge) throws Exception;
//
//    public NetworkGroup updateNetworkGroupForUpdateAndRemoveEdge(Long id, NetworkGroup data) throws Exception;
//    public NetworkGroupList findAllValidNetworkGroupsForNode(Long id) throws Exception;
//    public NetworkGroupIDList findAllNetworkGroupsIDAssociatedWithUser(Long id) throws Exception;
//
//    public NetworkGroupIDList findAllValidNetworkGroupsIDAssociatedWithNode(Long id, Long currentDate, String networkGroupCode) throws Exception;
//
//    public NetworkGroupIDList findAllValidNetworkGroupsIDAssociatedWithEdge(Long id, Long currentDate, String networkGroupCode) throws Exception;
//
//    public NodeInNetworkGroupList findNodeInNetworkGroupsBySearchCriteria_JDBC(NodeInNetworkGroupSearchCriteria searchCriteria, List<Long> networkGroupIDs) throws Exception;
//
//    public Long countTotalDocumentsOfNodeInNetworkGroupsBySearchCriteria_JDBC(NodeInNetworkGroupSearchCriteria searchCriteria, List<Long> networkGroupIDs) throws Exception;
//
//    public EdgeInNetworkGroupList findEdgeInNetworkGroupsBySearchCriteria_JDBC(EdgeInNetworkGroupSearchCriteria searchCriteria, List<Long> networkGroupIDs) throws Exception;
//
//    public Long countTotalDocumentsOfEdgeInNetworkGroupsBySearchCriteria_JDBC(EdgeInNetworkGroupSearchCriteria searchCriteria, List<Long> networkGroupIDs) throws Exception;
//
//    public NetworkGroupList findAllNetworkGroupsForUser(User user) throws Exception;
//
//    public NetworkGroupList findAllValidNetworkGroupsForUser(User loggedUser, Long currentDate) throws Exception;
}
