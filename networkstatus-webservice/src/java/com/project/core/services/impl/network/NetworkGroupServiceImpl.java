/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.services.impl.network;

import com.project.core.services.network.NetworkGroupService;
import com.project.core.models.entities.network.Edge;
import com.project.core.models.entities.network.EdgeInNetworkGroup;
import com.project.core.models.entities.network.NetworkGroup;
import com.project.core.models.entities.network.Node;
import com.project.core.models.entities.network.NodeInNetworkGroup;
import com.project.core.models.entities.network.RoleInNetworkGroup;
import com.project.core.models.entities.user.User;
import com.project.core.repositories.network.EdgeRepo;
import com.project.core.repositories.network.NetworkGroupRepo;
import com.project.core.repositories.network.NodeRepo;
import com.project.core.services.exceptions.EdgeExistsException;
import com.project.core.services.exceptions.EdgeNotFoundException;
import com.project.core.services.exceptions.NetworkGroupExistsException;
import com.project.core.services.exceptions.NodeExistsException;
import com.project.core.services.exceptions.NodeNotFoundException;
import com.project.core.services.exceptions.UserExistsException;
import com.project.core.services.network.EdgeService;
import com.project.core.services.util.EdgeInNetworkGroupList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.project.core.services.util.EdgeList;
import com.project.core.services.util.NetworkGroupList;
import com.project.core.services.util.NodeInNetworkGroupList;
import com.project.rest.util.searchcriteria.network.EdgeInNetworkGroupSearchCriteria;
import com.project.rest.util.searchcriteria.network.EdgeSearchCriteria;
import com.project.rest.util.searchcriteria.network.NetworkGroupSearchCriteria;
import com.project.rest.util.searchcriteria.network.NodeInNetworkGroupSearchCriteria;
import java.util.List;

/**
 *
 * @author samri_g64pbd3
 */
@Service
@Transactional
public class NetworkGroupServiceImpl implements NetworkGroupService {

    @Autowired
    private NetworkGroupRepo networkGroupRepo;

    @Autowired
    private NodeRepo nodeRepo;

    @Autowired
    private EdgeRepo edgeRepo;

    @Override
    public NetworkGroup createNetworkGroup(NetworkGroup networkGroup) throws Exception {
        NetworkGroup account = networkGroupRepo.findNetworkGroupByCode(networkGroup.getCode());
        if (account != null) {
            throw new UserExistsException();
        }
        return networkGroupRepo.createNetworkGroup(networkGroup);
    }

    @Override
    public NetworkGroup findNetworkGroup(Long id) throws Exception {
        return networkGroupRepo.findNetworkGroup(id);
    }

    @Override
    public NetworkGroupList findAllNetworkGroups() throws Exception {
        NetworkGroupList userList = new NetworkGroupList(networkGroupRepo.findAllNetworkGroups());
        return userList;
    }

    @Override
    public NetworkGroup findNetworkGroupByCode(String code) throws Exception {
        return networkGroupRepo.findNetworkGroupByCode(code);
    }

    @Override
    public NetworkGroup updateNetworkGroup(Long id, NetworkGroup data) throws Exception {
        return networkGroupRepo.updateNetworkGroup(id, data);
    }

    @Override
    public NetworkGroup updateNetworkGroupCode(Long id, NetworkGroup data) throws Exception {
        return networkGroupRepo.updateNetworkGroupCode(id, data);
    }

    @Override
    public NetworkGroupList findNetworkGroupsBySearchCriteria(NetworkGroupSearchCriteria searchCriteria, User user) throws Exception {
        Long totalDocuments = countTotalDocumentsBySearchCriteria(searchCriteria, user);
        Long totalPages = (long) Math.ceil(totalDocuments / searchCriteria.getLimitResult().doubleValue());
        NetworkGroupList userList = new NetworkGroupList(networkGroupRepo.findNetworkGroupsBySearchCriteria(searchCriteria, user), totalDocuments, totalPages);
        return userList;
    }

    @Override
    public Long countTotalDocumentsBySearchCriteria(NetworkGroupSearchCriteria searchCriteria, User user) throws Exception {
        return networkGroupRepo.countTotalDocumentsBySearchCriteria(searchCriteria, user);
    }

    @Override
    public NetworkGroup deleteNetworkGroup(Long id) throws Exception {
        return networkGroupRepo.deleteNetworkGroup(id);
    }

////    @Override
////    public NetworkGroupList findNetworkGroupsBySearchCriteria(NetworkGroupSearchCriteria searchCriteria) throws Exception {
////        Long totalDocuments = countTotalDocumentsBySearchCriteria(searchCriteria);
////        Long totalPages = (long) Math.ceil(totalDocuments / searchCriteria.getLimitResult().doubleValue());
////        NetworkGroupList userList = new NetworkGroupList(networkGroupRepo.findNetworkGroupsBySearchCriteria(searchCriteria), totalDocuments, totalPages);
////        return userList;
////    }
////
////    @Override
////    public Long countTotalDocumentsBySearchCriteria(NetworkGroupSearchCriteria searchCriteria) throws Exception {
////        return networkGroupRepo.countTotalDocumentsBySearchCriteria(searchCriteria);
////    }
    @Override
    public NetworkGroup findNetworkGroupByNode(String code, Node node) {
        return networkGroupRepo.findNetworkGroupByNode(code, node);
    }

    @Override
    public NetworkGroup updateNetworkGroupForAddNodes(Long id, NetworkGroup data, Node node) throws Exception {

        Node readNode = nodeRepo.find(node.getId());
        if (readNode == null) {
            throw new NodeNotFoundException();
        }

        NetworkGroup alreadyExistNode = findNetworkGroupByNode(data.getCode(), node);
        if (alreadyExistNode != null) {
            throw new NodeExistsException();
        }
        return networkGroupRepo.updateNetworkGroupForAddUpdateAndRemoveNodes(id, data);
    }

    @Override
    public NetworkGroup updateNetworkGroupForUpdateAndRemoveNodes(Long id, NetworkGroup data) throws Exception {
        return networkGroupRepo.updateNetworkGroupForAddUpdateAndRemoveNodes(id, data);
    }

    @Override
    public NetworkGroup updateNetworkGroupForAddNodes_NEW(Long id, NetworkGroup data) throws Exception {
        return networkGroupRepo.updateNetworkGroupForAddNodes_NEW(id, data);
    }

    @Override
    public NetworkGroup updateNetworkGroupForRemoveNodes_NEW(Long id, NetworkGroup data) throws Exception {
        return networkGroupRepo.updateNetworkGroupForRemoveNodes_NEW(id, data);
    }

    @Override
    public NetworkGroup updateNetworkGroupForAddNodes_NEW(Long id, List<NodeInNetworkGroup> data) throws Exception {
        return networkGroupRepo.updateNetworkGroupForAddNodes_NEW(id, data);
    }

    @Override
    public NetworkGroup updateNetworkGroupForUpdateNodes_NEW(Long id, List<NodeInNetworkGroup> data) throws Exception {
        return networkGroupRepo.updateNetworkGroupForUpdateNodes_NEW(id, data);
    }

    @Override
    public NetworkGroup updateNetworkGroupForRemoveNodes_NEW(Long id, List<NodeInNetworkGroup> data) throws Exception {
        return networkGroupRepo.updateNetworkGroupForRemoveNodes_NEW(id, data);
    }

    @Override
    public NodeInNetworkGroup findNodeInNetworkGroup(Long id) throws Exception {
        return networkGroupRepo.findNodeInNetworkGroup(id);
    }

    @Override
    public RoleInNetworkGroup findRoleInNetworkGroup(Long id) throws Exception {
        return networkGroupRepo.findRoleInNetworkGroup(id);
    }

    @Override
    public NetworkGroup findNetworkGroupByNodeInNetworkGroup(String networkGroupCode, NodeInNetworkGroup nodeInNetworkGroup) throws Exception {
        return networkGroupRepo.findNetworkGroupByNodeInNetworkGroup(networkGroupCode, nodeInNetworkGroup);
    }

    @Override
    public NetworkGroup updateNetworkGroupForAddEdges_NEW(Long networkGroupCode, List<EdgeInNetworkGroup> edgesInNetworkGroup) throws Exception {
        return networkGroupRepo.updateNetworkGroupForAddEdges_NEW(networkGroupCode, edgesInNetworkGroup);
    }

    @Override
    public NetworkGroup updateNetworkGroupForRemoveEdges_NEW(Long networkGroupCode, List<EdgeInNetworkGroup> edgesInNetworkGroup) throws Exception {
        return networkGroupRepo.updateNetworkGroupForRemoveEdges_NEW(networkGroupCode, edgesInNetworkGroup);
    }

    @Override
    public NetworkGroup updateNetworkGroupForUpdateEdges_NEW(Long networkGroupCode, List<EdgeInNetworkGroup> edgesInNetworkGroup) throws Exception {
        return networkGroupRepo.updateNetworkGroupForUpdateEdges_NEW(networkGroupCode, edgesInNetworkGroup);
    }

    @Override
    public EdgeInNetworkGroup findEdgeInNetworkGroup(Long id) throws Exception {
        return networkGroupRepo.findEdgeInNetworkGroup(id);
    }

    @Override
    public NetworkGroup findNetworkGroupByEdgeInNetworkGroup(String networkGroupCode, EdgeInNetworkGroup edgeInNetworkGroup) throws Exception {
        return networkGroupRepo.findNetworkGroupByEdgeInNetworkGroup(networkGroupCode, edgeInNetworkGroup);
    }

    @Override
    public EdgeInNetworkGroup updateEdgeInNetworkGroup(Long id, EdgeInNetworkGroup data) throws Exception {
        return networkGroupRepo.updateEdgeInNetworkGroup(id, data);
    }

    @Override
    public NetworkGroup findNetworkGroupIfNodeInNetworkGroupsAreAlreadyExists(Long networkGroupID, Long nodeInNetworkGroupId1, Long nodeInNetworkGroupId2) throws Exception {
        return networkGroupRepo.findNetworkGroupIfNodeInNetworkGroupsAreAlreadyExists(networkGroupID, nodeInNetworkGroupId1, nodeInNetworkGroupId2);
    }

    @Override
    public List<Long> findAllEnabledNetworkGroupsIds() throws Exception {
        return networkGroupRepo.findAllEnabledNetworkGroupsIds();
    }

    @Override
    public List<String> findAllEnabledNetworkGroupsIdsWithNames() throws Exception {
        return networkGroupRepo.findAllEnabledNetworkGroupsIdsWithNames();
    }

    @Override
    public NodeInNetworkGroup updateNodeInNetworkGroupCordinates(Long id, NodeInNetworkGroup data) throws Exception {
        return networkGroupRepo.updateNodeInNetworkGroupCordinates(id, data);
    }

    public NetworkGroup clearAndUpdateNetworkGroupForAddEdges_NEW(Long networkGroupCode, List<EdgeInNetworkGroup> edgesInNetworkGroup) throws Exception {
        return networkGroupRepo.clearAndUpdateNetworkGroupForAddEdges_NEW(networkGroupCode, edgesInNetworkGroup);
    }

    @Override
    public int deleteAllNetworkGroup() throws Exception {
        return networkGroupRepo.deleteAllNetworkGroup();
    }

    @Override
    public int deleteAllNodeInNetworkGroup() throws Exception {
        return networkGroupRepo.deleteAllNodeInNetworkGroup();
    }

    @Override
    public int deleteAllEdgeInNetworkGroup() throws Exception {
        return networkGroupRepo.deleteAllEdgeInNetworkGroup();
    }

    @Override
    public NetworkGroup findNetworkGroupByNodeInNetworkGroupId(Long nodeInNetworkGroupId) throws Exception {
        return networkGroupRepo.findNetworkGroupByNodeInNetworkGroupId(nodeInNetworkGroupId);
    }

    public NetworkGroupList findAllNetworkGroupsByNodeId(Long nodeId) throws Exception {
        NetworkGroupList userList = new NetworkGroupList(networkGroupRepo.findAllNetworkGroupsByNodeId(nodeId), 1L, 1L);
        return userList;
    }

//    @Override
//    public NetworkGroup updateNetworkGroupWithDetails(Long id, NetworkGroup data) throws Exception {
//        return networkGroupRepo.updateNetworkGroupWithDetails(id, data);
//    }
//
//   
//    @Override
//    public NetworkGroup findNetworkGroupByEdge(String name, Edge edge) {
//        return networkGroupRepo.findNetworkGroupByEdge(name, edge);
//    }
//
//    @Override
//    public NetworkGroup updateNetworkGroupForAddEdge(Long id, NetworkGroup data, Edge edge) throws Exception {
//
//        Edge readNode = edgeRepo.find(edge.getId());
//        if (readNode == null) {
//            throw new EdgeNotFoundException();
//        }
//
//        NetworkGroup alreadyExistEdge = findNetworkGroupByEdge(data.getName(), edge);
//        if (alreadyExistEdge != null) {
//            throw new EdgeExistsException();
//        }
//        return networkGroupRepo.updateNetworkGroupForAddUpdateAndRemoveEdge(id, data);
//    }
//
//    @Override
//    public NetworkGroup updateNetworkGroupForUpdateAndRemoveEdge(Long id, NetworkGroup data) throws Exception {
//        return networkGroupRepo.updateNetworkGroupForAddUpdateAndRemoveEdge(id, data);
//    }
//    @Override
//
//    @Override
//    public NodeInNetworkGroupList findNodeInNetworkGroupsBySearchCriteria_JDBC(NodeInNetworkGroupSearchCriteria searchCriteria, List<Long> networkGroupIDs) throws Exception {
//
//        Long totalDocuments = countTotalDocumentsOfNodeInNetworkGroupsBySearchCriteria_JDBC(searchCriteria, networkGroupIDs);
//        Long totalPages = (long) Math.ceil(totalDocuments / searchCriteria.getLimitResult().doubleValue());
//        NodeInNetworkGroupList userList = new NodeInNetworkGroupList(networkGroupRepo.findNodeInNetworkGroupsBySearchCriteria_JDBC(searchCriteria, networkGroupIDs), totalDocuments, totalPages);
//        return userList;
//
////        NodeInNetworkGroupList userList = new NodeInNetworkGroupList(networkGroupRepo.findNodeInNetworkGroupsBySearchCriteria_JDBC(geoLocationSearchCriteria, networkGroupIDs), 1L, 1L);
////        return userList;
//    }
//
//    @Override
//    public Long countTotalDocumentsOfNodeInNetworkGroupsBySearchCriteria_JDBC(NodeInNetworkGroupSearchCriteria searchCriteria, List<Long> networkGroupIDs) throws Exception {
//        return networkGroupRepo.countTotalDocumentsOfNodeInNetworkGroupsBySearchCriteria_JDBC(searchCriteria, networkGroupIDs);
//    }
//
//
//    @Override
//    public EdgeInNetworkGroupList findEdgeInNetworkGroupsBySearchCriteria_JDBC(EdgeInNetworkGroupSearchCriteria searchCriteria, List<Long> networkGroupIDs) throws Exception {
//        Long totalDocuments = countTotalDocumentsOfEdgeInNetworkGroupsBySearchCriteria_JDBC(searchCriteria, networkGroupIDs);
//        Long totalPages = (long) Math.ceil(totalDocuments / searchCriteria.getLimitResult().doubleValue());
//        EdgeInNetworkGroupList userList = new EdgeInNetworkGroupList(networkGroupRepo.findEdgeInNetworkGroupsBySearchCriteria_JDBC(searchCriteria, networkGroupIDs), totalDocuments, totalPages);
//        return userList;
//    }
//
//    @Override
//    public Long countTotalDocumentsOfEdgeInNetworkGroupsBySearchCriteria_JDBC(EdgeInNetworkGroupSearchCriteria searchCriteria, List<Long> networkGroupIDs) throws Exception {
//        return networkGroupRepo.countTotalDocumentsOfEdgeInNetworkGroupsBySearchCriteria_JDBC(searchCriteria, networkGroupIDs);
//    }
//
//
//
//    @Override
//    public EdgeInNetworkGroup findEdgeInNetworkGroup(Long id) throws Exception {
//        return networkGroupRepo.findEdgeInNetworkGroup(id);
//    }
//
//
////        @Override
////    public NetworkGroupIDList findAllNetworkGroupsIDAssociatedWithUser(Long id) throws Exception {
////        NetworkGroupIDList userList = new NetworkGroupIDList(networkGroupRepo.findAllNetworkGroupsIDAssociatedWithUser(id));
////        return userList;
////    }
////
////    @Override
////    public NetworkGroupIDList findAllValidNetworkGroupsIDAssociatedWithNode(Long id, Long currentDate, String networkGroupCode) throws Exception {
////        NetworkGroupIDList userList = new NetworkGroupIDList(networkGroupRepo.findAllValidNetworkGroupsIDAssociatedWithNode(id, currentDate, networkGroupCode));
////        return userList;
////    }
////
////    @Override
////    public NetworkGroupIDList findAllValidNetworkGroupsIDAssociatedWithEdge(Long id, Long currentDate, String networkGroupCode) throws Exception {
////        NetworkGroupIDList userList = new NetworkGroupIDList(networkGroupRepo.findAllValidNetworkGroupsIDAssociatedWithEdge(id, currentDate, networkGroupCode));
////        return userList;
////    }
//
//    @Override
//    public NetworkGroupList findAllNetworkGroupsForUser(User user) throws Exception {
////        Long totalDocuments = 1L;
////        Long totalPages = 1L;
//        NetworkGroupList userList = new NetworkGroupList(networkGroupRepo.findAllNetworkGroupsForUser(user));
//        return userList;
//    }
//
//    @Override
//    public NetworkGroupList findAllValidNetworkGroupsForUser(User loggedUser, Long currentDate) throws Exception {
//        NetworkGroupList userList = new NetworkGroupList(networkGroupRepo.findAllValidNetworkGroupsForUser(loggedUser, currentDate));
//        return userList;
//    }
}
