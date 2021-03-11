package com.project.core.repositories.network;

import com.project.core.models.entities.network.Edge;
import com.project.core.models.entities.network.EdgeInNetworkGroup;
import com.project.core.models.entities.network.NetworkGroup;
import com.project.core.models.entities.network.Node;
import com.project.core.models.entities.network.NodeInNetworkGroup;
import com.project.core.models.entities.network.RoleInNetworkGroup;
import com.project.core.models.entities.user.User;
import com.project.rest.util.searchcriteria.network.EdgeInNetworkGroupSearchCriteria;
import com.project.rest.util.searchcriteria.network.NetworkGroupSearchCriteria;
import com.project.rest.util.searchcriteria.network.NodeInNetworkGroupSearchCriteria;
import java.util.List;

/**
 *
 * @author Samrit
 */
public interface NetworkGroupRepo {

    public NetworkGroup createNetworkGroup(NetworkGroup networkGroup);

    public NetworkGroup findNetworkGroup(Long id);

    public NetworkGroup findNetworkGroupByCode(String code);

    public List<NetworkGroup> findAllNetworkGroups();

    public NetworkGroup updateNetworkGroup(Long id, NetworkGroup data);

    public NetworkGroup updateNetworkGroupCode(Long id, NetworkGroup data);

    public NetworkGroup updateNetworkGroupWithDetails(Long id, NetworkGroup data);

    public NetworkGroup updateNetworkGroupForAddUpdateAndRemoveNodes(Long id, NetworkGroup data);

    public NetworkGroup updateNetworkGroupForAddNodes_NEW(Long id, NetworkGroup data);

    public NetworkGroup updateNetworkGroupForRemoveNodes_NEW(Long id, NetworkGroup data);

    public NetworkGroup updateNetworkGroupForAddNodes_NEW(Long id, List<NodeInNetworkGroup> data);

    public NetworkGroup updateNetworkGroupForRemoveNodes_NEW(Long id, List<NodeInNetworkGroup> data);

    public NetworkGroup updateNetworkGroupForUpdateNodes_NEW(Long id, List<NodeInNetworkGroup> data);

    public NetworkGroup updateNetworkGroupForAddEdges_NEW(Long id, List<EdgeInNetworkGroup> data);

    public NetworkGroup clearAndUpdateNetworkGroupForAddEdges_NEW(Long id, List<EdgeInNetworkGroup> data);

    public NetworkGroup updateNetworkGroupForRemoveEdges_NEW(Long id, List<EdgeInNetworkGroup> data);

    public NetworkGroup updateNetworkGroupForUpdateEdges_NEW(Long id, List<EdgeInNetworkGroup> data);

    public EdgeInNetworkGroup updateEdgeInNetworkGroup(Long id, EdgeInNetworkGroup data);

    public NetworkGroup updateNetworkGroupForAddUpdateAndRemoveEdge(Long id, NetworkGroup data);

    public int deleteAllNetworkGroup();

    public int deleteAllNodeInNetworkGroup();

    public int deleteAllEdgeInNetworkGroup();

    public List<Long> findAllEnabledNetworkGroupsIds();

    public List<String> findAllEnabledNetworkGroupsIdsWithNames();

    public NetworkGroup findNetworkGroupIfNodeInNetworkGroupsAreAlreadyExists(Long networkGroupID, Long nodeInNetworkGroupId1, Long nodeInNetworkGroupId2);

    public NetworkGroup deleteNetworkGroup(Long id);

    public NodeInNetworkGroup updateNodeInNetworkGroupCordinates(Long id, NodeInNetworkGroup data);

    public NetworkGroup findNetworkGroupByNode(String name, Node node);

    public NetworkGroup findNetworkGroupByNodeInNetworkGroupId(Long nodeInNetworkGroupId);

    public NetworkGroup findNetworkGroupByNodeInNetworkGroup(String networkGroupCode, NodeInNetworkGroup nodeInNetworkGroup);

    public NetworkGroup findNetworkGroupByEdgeInNetworkGroup(String networkGroupCode, EdgeInNetworkGroup edgeInNetworkGroup);

    public NetworkGroup findNetworkGroupByEdge(String name, Edge edge);

    public List<NetworkGroup> findNetworkGroupsBySearchCriteria(NetworkGroupSearchCriteria searchCriteria, User user);

    public Long countTotalDocumentsBySearchCriteria(NetworkGroupSearchCriteria searchCriteria, User user);

    public List<NetworkGroup> findAllNetworkGroupsByNodeId(Long id);

    public List<Long> findAllNetworkGroupsIDAssociatedWithUser(Long id);

    public List<Long> findAllValidNetworkGroupsIDAssociatedWithNode(Long id, Long currentDate, String networkGroupCode);

    public List<Long> findAllValidNetworkGroupsIDAssociatedWithEdge(Long id, Long currentDate, String networkGroupCode);

    public List<NodeInNetworkGroup> findNodeInNetworkGroupsBySearchCriteria_JDBC(NodeInNetworkGroupSearchCriteria searchCriteria, List<Long> networkGroupIDs);

    public Long countTotalDocumentsOfNodeInNetworkGroupsBySearchCriteria_JDBC(NodeInNetworkGroupSearchCriteria searchCriteria, List<Long> networkGroupIDs);

    public List<EdgeInNetworkGroup> findEdgeInNetworkGroupsBySearchCriteria_JDBC(EdgeInNetworkGroupSearchCriteria searchCriteria, List<Long> networkGroupIDs);

    public Long countTotalDocumentsOfEdgeInNetworkGroupsBySearchCriteria_JDBC(EdgeInNetworkGroupSearchCriteria searchCriteria, List<Long> networkGroupIDs);

    public EdgeInNetworkGroup findEdgeInNetworkGroup(Long id);

    public NodeInNetworkGroup findNodeInNetworkGroup(Long id);

    public RoleInNetworkGroup findRoleInNetworkGroup(Long id);

    public List<NetworkGroup> findAllNetworkGroupsForUser(User user);

    public List<NetworkGroup> findAllValidNetworkGroupsForUser(User loggedUser, Long currentDate);

}
