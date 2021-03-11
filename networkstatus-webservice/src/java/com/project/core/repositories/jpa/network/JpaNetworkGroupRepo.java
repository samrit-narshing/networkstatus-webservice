/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.repositories.jpa.network;

import com.project.core.models.entities.network.Edge;
import com.project.core.models.entities.network.EdgeInNetworkGroup;
import com.project.core.models.entities.network.NetworkGroup;
import com.project.core.models.entities.network.Node;
import com.project.core.models.entities.network.NodeInNetworkGroup;
import com.project.core.models.entities.network.RoleInNetworkGroup;
import com.project.core.repositories.network.NetworkGroupRepo;
import com.project.core.models.entities.user.User;
import com.project.core.util.DateConverter;
import com.project.rest.util.searchcriteria.network.EdgeInNetworkGroupSearchCriteria;
import com.project.rest.util.searchcriteria.network.NetworkGroupSearchCriteria;
import com.project.rest.util.searchcriteria.network.NodeInNetworkGroupSearchCriteria;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

/**
 *
 * @author samri_g64pbd3
 */
@Repository
public class JpaNetworkGroupRepo implements NetworkGroupRepo {
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public NetworkGroup createNetworkGroup(NetworkGroup networkGroup) {
        networkGroup.setEntryDate(DateConverter.getCurrentConvertedDateAndTimeInUnixDate());
        networkGroup.setLastModifiedUnixTime(DateConverter.getCurrentConvertedDateAndTimeInUnixDate());
        em.persist(networkGroup);
        em.flush();
        return networkGroup;
    }
    
    @Override
    public NetworkGroup findNetworkGroup(Long id) {
        NetworkGroup networkGroup = em.find(NetworkGroup.class, id);
//        networkGroup.getNodesInNetworkGroup().size();
        return networkGroup;
    }
    
    @Override
    public List<NetworkGroup> findAllNetworkGroups() {
        Query query = em.createQuery("SELECT u FROM NetworkGroup u");
        return query.getResultList();
    }
    
    @Override
    public NetworkGroup updateNetworkGroup(Long id, NetworkGroup data) {
        NetworkGroup entry = em.find(NetworkGroup.class, id);
//        javax.swing.JOptionPane.showMessageDialog(null, data.getRolesInNetworkGroup().size());
//        if (entry != null) {
//            em.merge(data);
//        } else {
        entry.setDescription(data.getDescription());
        entry.setEnabled(data.isEnabled());
        entry.setName(data.getName());
        entry.setRoles(data.getRoles());
        entry.setStatusCode(data.getStatusCode());
        entry.setLastUpdateByUserType(data.getLastUpdateByUserType());
        entry.setLastUpdateByUsername(data.getLastUpdateByUsername());
        entry.setLastModifiedUnixTime(DateConverter.getCurrentConvertedDateAndTimeInUnixDate());
//        entry.setRolesInNetworkGroup(data.getRolesInNetworkGroup());
//        entry.getRolesInNetworkGroup().clear();
//        entry.getRolesInNetworkGroup().addAll(data.getRolesInNetworkGroup());
// Other way  It will not increment the ids in table
        List<RoleInNetworkGroup> toDeletedRoles = new ArrayList<>();
        boolean toDelete = false;
        for (RoleInNetworkGroup mainRole : entry.getRolesInNetworkGroup()) {
            for (RoleInNetworkGroup group : data.getRolesInNetworkGroup()) {
                if (Objects.equals(mainRole.getRole().getId(), group.getRole().getId())) {
                    toDelete = false;
                    break;
                } else {
                    toDelete = true;
                }
            }
            if (toDelete) {
                toDeletedRoles.add(mainRole);
            }
        }

        toDeletedRoles.forEach((ng) -> {
            entry.getRolesInNetworkGroup().remove(ng);
        });
//        javax.swing.JOptionPane.showMessageDialog(null, toDeletedRoles.size());
        List<RoleInNetworkGroup> toAddRoles = new ArrayList<>();
        boolean toAdd = true;
        for (RoleInNetworkGroup group : data.getRolesInNetworkGroup()) {
            for (RoleInNetworkGroup mainRole : entry.getRolesInNetworkGroup()) {
                if (Objects.equals(mainRole.getRole().getId(), group.getRole().getId())) {
                    toAdd = false;
                    break;
                } else {
                    toAdd = true;
                }
            }
            if (toAdd) {
                toAddRoles.add(group);
            }
        }
//        javax.swing.JOptionPane.showMessageDialog(null, toAddRoles.size());

        toAddRoles.forEach((ng) -> {
            entry.getRolesInNetworkGroup().add(ng);
        });
// End of Other Way
        return entry;
    }
    
    @Override
    public NetworkGroup updateNetworkGroupCode(Long id, NetworkGroup data) {
        NetworkGroup entry = em.find(NetworkGroup.class, id);
        entry.setCode(data.getCode());
        entry.setLastModifiedUnixTime(DateConverter.getCurrentConvertedDateAndTimeInUnixDate());
        em.flush();
        return entry;
    }
    
    @Override
    public NetworkGroup updateNetworkGroupWithDetails(Long id, NetworkGroup data) {
        NetworkGroup entry = em.find(NetworkGroup.class, id);
        
        if (entry != null) {
            em.merge(data);
        } else {
            entry.setDescription(data.getDescription());
            entry.setEntryDate(data.getEntryDate());
            entry.setName(data.getName());
            entry.setStatusCode(data.getStatusCode());
            entry.setNodesInNetworkGroup(data.getNodesInNetworkGroup());
            entry.setLastModifiedUnixTime(data.getLastModifiedUnixTime());
        }
        
        return entry;
    }
    
    @Override
    public NetworkGroup updateNetworkGroupForAddUpdateAndRemoveNodes(Long id, NetworkGroup data) {
        NetworkGroup entry = em.find(NetworkGroup.class, id);
        
        if (entry != null) {
            data.setLastModifiedUnixTime(DateConverter.getCurrentConvertedDateAndTimeInUnixDate());
            em.merge(data);
        } else {
            entry.setLastModifiedUnixTime(DateConverter.getCurrentConvertedDateAndTimeInUnixDate());
            entry.setNodesInNetworkGroup(data.getNodesInNetworkGroup());
        }
        return entry;
    }
    
    @Override
    public NetworkGroup updateNetworkGroupForAddNodes_NEW(Long id, NetworkGroup data) {
        NetworkGroup entry = em.find(NetworkGroup.class, id);
        // FOR ADD
        List<NodeInNetworkGroup> newNodesInNG = new ArrayList<>();
        for (NodeInNetworkGroup group : data.getNodesInNetworkGroup()) {
            boolean isNew = true;
            for (NodeInNetworkGroup mainEntry : entry.getNodesInNetworkGroup()) {
                if (Objects.equals(mainEntry.getNode().getId(), group.getNode().getId())) {
                    isNew = false;
                    break;
                }
            }
            if (isNew) {
                newNodesInNG.add(group);
                isNew = true;
            }
        }
        
        for (NodeInNetworkGroup ng : newNodesInNG) {
            entry.getNodesInNetworkGroup().add(ng);
        }
        //End For ADD
        return entry;
    }
    
    @Override
    public NetworkGroup updateNetworkGroupForAddNodes_NEW(Long id, List<NodeInNetworkGroup> data) {
        NetworkGroup entry = em.find(NetworkGroup.class, id);
        for (NodeInNetworkGroup ng : data) {
            ng.setId(null);
            entry.getNodesInNetworkGroup().add(ng);
        }
        return entry;
    }
    
    @Override
    public NetworkGroup updateNetworkGroupForUpdateNodes_NEW(Long id, List<NodeInNetworkGroup> data) {
        NetworkGroup entry = em.find(NetworkGroup.class, id);
        for (NodeInNetworkGroup mainNodes : entry.getNodesInNetworkGroup()) {
            for (NodeInNetworkGroup nodeInNetworkGroup : data) {
                if (Objects.equals(mainNodes.getNode().getId(), nodeInNetworkGroup.getNode().getId())) {
                    mainNodes.setCanvasXValue(nodeInNetworkGroup.getCanvasXValue());
                    mainNodes.setCanvasYValue(nodeInNetworkGroup.getCanvasYValue());
                    mainNodes.setDescription(nodeInNetworkGroup.getDescription());
                    mainNodes.setDomXValue(nodeInNetworkGroup.getDomXValue());
                    mainNodes.setDomYValue(nodeInNetworkGroup.getDomYValue());
                    mainNodes.setZoomScale(nodeInNetworkGroup.getZoomScale());
                    break;
                }
            }
        }
        return entry;
    }
    
    @Override
    public NetworkGroup updateNetworkGroupForRemoveNodes_NEW(Long id, List<NodeInNetworkGroup> data) {
        NetworkGroup entry = em.find(NetworkGroup.class, id);
        
        List<NodeInNetworkGroup> toDeletedNodes = new ArrayList<>();
        for (NodeInNetworkGroup group : data) {
            for (NodeInNetworkGroup mainNodes : entry.getNodesInNetworkGroup()) {
                if (Objects.equals(mainNodes.getId(), group.getId())) {
                    toDeletedNodes.add(mainNodes);
                    break;
                }
            }
        }
        
        toDeletedNodes.forEach((ng) -> {
            entry.getNodesInNetworkGroup().remove(ng);
        });
        
        return entry;
    }
    
    @Override
    public NetworkGroup updateNetworkGroupForAddEdges_NEW(Long id, List<EdgeInNetworkGroup> data) {
        NetworkGroup entry = em.find(NetworkGroup.class, id);
        for (EdgeInNetworkGroup ng : data) {
            ng.setId(null);
            entry.getEdgesInNetworkGroup().add(ng);
        }
        return entry;
    }
    
    @Override
    public NetworkGroup clearAndUpdateNetworkGroupForAddEdges_NEW(Long id, List<EdgeInNetworkGroup> data) {
        NetworkGroup entry = em.find(NetworkGroup.class, id);
        entry.getEdgesInNetworkGroup().clear();
        for (EdgeInNetworkGroup ng : data) {
            ng.setId(null);
            entry.getEdgesInNetworkGroup().add(ng);
        }
        return entry;
    }
    
    @Override
    public NetworkGroup updateNetworkGroupForRemoveEdges_NEW(Long id, List<EdgeInNetworkGroup> data) {
        NetworkGroup entry = em.find(NetworkGroup.class, id);
        
        List<EdgeInNetworkGroup> toDeletedEdges = new ArrayList<>();
        for (EdgeInNetworkGroup mainEdges : entry.getEdgesInNetworkGroup()) {
            for (EdgeInNetworkGroup group : data) {
                if (Objects.equals(mainEdges.getId(), group.getId())) {
                    toDeletedEdges.add(mainEdges);
                    break;
                }
            }
        }
        
        toDeletedEdges.forEach((ng) -> {
            entry.getEdgesInNetworkGroup().remove(ng);
        });
        
        return entry;
    }
    
    @Override
    public NetworkGroup updateNetworkGroupForUpdateEdges_NEW(Long id, List<EdgeInNetworkGroup> data) {
        NetworkGroup entry = em.find(NetworkGroup.class, id);
        for (EdgeInNetworkGroup edgeInNetworkGroup : data) {
            for (EdgeInNetworkGroup mainNodes : entry.getEdgesInNetworkGroup()) {
                if (Objects.equals(mainNodes.getId(), edgeInNetworkGroup.getId())) {
                    mainNodes.setArrows(edgeInNetworkGroup.getArrows());
                    mainNodes.setDashes(edgeInNetworkGroup.isDashes());
                    mainNodes.setDescription(edgeInNetworkGroup.getDescription());
                    mainNodes.setEdge_length(edgeInNetworkGroup.getEdge_length());
                    mainNodes.setEdge_value(edgeInNetworkGroup.getEdge_value());
                    break;
                }
            }
        }
        return entry;
    }
    
    @Override
    public EdgeInNetworkGroup updateEdgeInNetworkGroup(Long id, EdgeInNetworkGroup data) {
        
        EdgeInNetworkGroup entry = em.find(EdgeInNetworkGroup.class, id);
        entry.setArrows(data.getArrows());
        entry.setDashes(data.isDashes());
        entry.setEdge_length(data.getEdge_length());
        entry.setEdge_value(data.getEdge_value());
        entry.setEnabled(data.isEnabled());
        entry.setLabel(data.getLabel());
        entry.setTitle(data.getLabel());
        
        return entry;
        
    }
    
    @Override
    public List<Long> findAllEnabledNetworkGroupsIds() {
        Query query;
        query = em.createQuery("SELECT s.id from NetworkGroup s where s.enabled= ?1 order by s.id ASC");
        query.setParameter(1, true);
        return query.getResultList();
    }
    
    @Override
    public List<String> findAllEnabledNetworkGroupsIdsWithNames() {
        Query query;
        query = em.createQuery("SELECT concat(s.id, ':', s.name, ':', s.roles) as fullname  from NetworkGroup s where s.enabled= ?1 order by s.id ASC");
        query.setParameter(1, true);
        return query.getResultList();
    }
    
    @Override
    public NetworkGroup findNetworkGroupByNodeInNetworkGroupId(Long nodeInNetworkGroupId) {
//        javax.swing.JOptionPane.showMessageDialog(null, nodeInNetworkGroupId);
        Query query = em.createQuery("SELECT ng FROM NetworkGroup ng inner join ng.nodesInNetworkGroup n where n.id = ?1");
        query.setParameter(1, nodeInNetworkGroupId);
        List<NetworkGroup> networkGroups = query.getResultList();
        if (networkGroups.isEmpty()) {
            return null;
        } else {
            return networkGroups.get(0);
        }
    }

//    @Override
//    public NetworkGroup updateNetworkGroupForAddNodes_NEW(Long id, NetworkGroup data) {
//        NetworkGroup entry = em.find(NetworkGroup.class, id);
//        // FOR ADD
//        List<NodeInNetworkGroup> newNodesInNG = new ArrayList<>();
//        for (NodeInNetworkGroup group : data.getNodesInNetworkGroup()) {
//            boolean isNew = true;
//            for (NodeInNetworkGroup mainEntry : entry.getNodesInNetworkGroup()) {
//                System.out.println("CHECK INTERNATL  ----" + mainEntry.getNode().getId() + " AND " + group.getNode().getId());
//                if (Objects.equals(mainEntry.getNode().getId(), group.getNode().getId())) {
//                    isNew = false;
//                    break;
//                }
//            }
//            if (isNew) {
//                newNodesInNG.add(group);
//                isNew = true;
//            }
//        }
//
//        for (NodeInNetworkGroup ng : newNodesInNG) {
//            entry.getNodesInNetworkGroup().add(ng);
//        }
//        //End For ADD
//        return entry;
//    }
    @Override
    public NetworkGroup updateNetworkGroupForRemoveNodes_NEW(Long id, NetworkGroup data) {
        NetworkGroup entry = em.find(NetworkGroup.class, id);

        //FOR REMOVE  
        List<NodeInNetworkGroup> deetedNodesInNG = new ArrayList<>();
        
        for (NodeInNetworkGroup mainEntry : entry.getNodesInNetworkGroup()) {
            System.out.println("MOxMO----" + mainEntry.getId());
            boolean isDeleted = true;
            
            for (NodeInNetworkGroup group : data.getNodesInNetworkGroup()) {
                if (Objects.equals(mainEntry.getId(), group.getId())) {
                    isDeleted = false;
                }
            }
            if (isDeleted) {
                deetedNodesInNG.add(mainEntry);
            }
        }
        
        for (NodeInNetworkGroup ng : deetedNodesInNG) {
            entry.getNodesInNetworkGroup().remove(ng);
        }

//END For Remove
        return entry;
    }
    
    @Override
    public NetworkGroup deleteNetworkGroup(Long id
    ) {
        NetworkGroup entry = em.find(NetworkGroup.class, id);
        em.remove(entry);
        return entry;
    }
    
    @Override
    public NetworkGroup findNetworkGroupByCode(String code
    ) {
        Query query = em.createQuery("SELECT u FROM NetworkGroup u WHERE u.code=?1");
        query.setParameter(1, code);
        List<NetworkGroup> networkGroups = query.getResultList();
        if (networkGroups.isEmpty()) {
            return null;
        } else {
            return networkGroups.get(0);
        }
    }
    
    @Override
    public NetworkGroup findNetworkGroupByNode(String code, Node traveler
    ) {
        Query query = em.createQuery("SELECT ng FROM NetworkGroup ng inner join ng.nodesInNetworkGroup n where ng.code=?1 and n.node.id = ?2");
        query.setParameter(1, code);
        query.setParameter(2, traveler.getId());
        List<NetworkGroup> networkGroups = query.getResultList();
        if (networkGroups.isEmpty()) {
            return null;
        } else {
            return networkGroups.get(0);
        }
    }
    
    @Override
    public NetworkGroup findNetworkGroupByNodeInNetworkGroup(String networkGroupCode, NodeInNetworkGroup nodeInNetworkGroup
    ) {
        Query query = em.createQuery("SELECT ng FROM NetworkGroup ng inner join ng.nodesInNetworkGroup n where ng.code=?1 and n.id = ?2");
        query.setParameter(1, networkGroupCode);
        query.setParameter(2, nodeInNetworkGroup.getId());
        List<NetworkGroup> networkGroups = query.getResultList();
        if (networkGroups.isEmpty()) {
            return null;
        } else {
            return networkGroups.get(0);
        }
    }
    
    @Override
    public NetworkGroup findNetworkGroupByEdgeInNetworkGroup(String networkGroupCode, EdgeInNetworkGroup edgeInNetworkGroup
    ) {
        Query query = em.createQuery("SELECT ng FROM NetworkGroup ng inner join ng.edgesInNetworkGroup n where ng.code=?1 and n.id = ?2");
        query.setParameter(1, networkGroupCode);
        query.setParameter(2, edgeInNetworkGroup.getId());
        List<NetworkGroup> networkGroups = query.getResultList();
        if (networkGroups.isEmpty()) {
            return null;
        } else {
            return networkGroups.get(0);
        }
    }
    
    @Override
    public NetworkGroup findNetworkGroupIfNodeInNetworkGroupsAreAlreadyExists(Long networkGroupID, Long nodeInNetworkGroupId1, Long nodeInNetworkGroupId2) {
//   javax.swing.JOptionPane.showMessageDialog(null, networkGroupID + "   ---     " + nodeID1 + "---- " + nodeID2);
//        Query query = em.createQuery("SELECT ng FROM NetworkGroup ng where ng.id=?1 and (ng.edgesInNetworkGroup.fromNodeInNetworkGroup.id = ?2 and  ng.edgesInNetworkGroup.toNodeInNetworkGroup.id = ?3) or (ng.edgesInNetworkGroup.fromNodeInNetworkGroup.id = ?4 and  ng.edgesInNetworkGroup.toNodeInNetworkGroup.id = ?5) and ng.edgesInNetworkGroup.enabled = ?6");
//        Query query = em.createQuery("SELECT ng FROM NetworkGroup ng inner join ng.edgesInNetworkGroup e where ng.id=?1 and e.enabled = ?2 and e.fromNodeInNetworkGroup.node.id=418");
//        Query query = em.createQuery("SELECT ng FROM NetworkGroup ng inner join ng.edgesInNetworkGroup e where ng.id=?1 and e.enabled = ?2 and (e.fromNodeInNetworkGroup.node.id = ?3 and  e.toNodeInNetworkGroup.node.id = ?4) or (e.fromNodeInNetworkGroup.node.id = ?5 and  e.toNodeInNetworkGroup.node.id = ?6)");
        Query query = em.createQuery("SELECT ng FROM NetworkGroup ng inner join ng.edgesInNetworkGroup e where ng.id=?1 and e.enabled = ?2 and (e.fromNodeInNetworkGroup.id = ?3 and  e.toNodeInNetworkGroup.id = ?4) or (e.fromNodeInNetworkGroup.id = ?5 and  e.toNodeInNetworkGroup.id = ?6)");
        
        query.setParameter(1, networkGroupID);
        query.setParameter(2, true);
        query.setParameter(3, nodeInNetworkGroupId1);
        query.setParameter(4, nodeInNetworkGroupId2);
        query.setParameter(5, nodeInNetworkGroupId2);
        query.setParameter(6, nodeInNetworkGroupId1);
//        query.setParameter(6, true);

        List<NetworkGroup> networkGroups = query.getResultList();
        if (networkGroups.isEmpty()) {
            return null;
        } else {
            return networkGroups.get(0);
        }
    }
    
    @Override
    public NetworkGroup findNetworkGroupByEdge(String name, Edge edge) {
        Query query = em.createQuery("SELECT tg FROM NetworkGroup tg inner join tg.edgesInNetworkGroup tr where tg.name=?1 and tr.edge.id = ?2");
        query.setParameter(1, name);
        query.setParameter(2, edge.getId());
        List<NetworkGroup> networkGroups = query.getResultList();
        if (networkGroups.isEmpty()) {
            return null;
        } else {
            return networkGroups.get(0);
        }
    }
    
    @Override
    public NetworkGroup updateNetworkGroupForAddUpdateAndRemoveEdge(Long id, NetworkGroup data
    ) {
        NetworkGroup entry = em.find(NetworkGroup.class, id);
        
        if (entry != null) {
            em.merge(data);
        } else {
            entry.setLastModifiedUnixTime(data.getLastModifiedUnixTime());
            entry.setEdgesInNetworkGroup(data.getEdgesInNetworkGroup());
        }
        
        return entry;
    }
    
    @Override
    public List<NetworkGroup> findNetworkGroupsBySearchCriteria(NetworkGroupSearchCriteria searchCriteria, User user
    ) {
        int maxResult = searchCriteria.getLimitResult();
        int adjustedPageN0 = Math.abs(searchCriteria.getPageNo() - 1);
        int startRow = adjustedPageN0 * maxResult;
        Query query;
        if (searchCriteria.getId() != null && searchCriteria.getId() > 0) {
            query = em.createQuery("SELECT u FROM NetworkGroup u where u.id = ?1 order by u.name ASC");
            query.setParameter(1, searchCriteria.getId());
        } else {
            
            String headQueryStr = "SELECT u FROM NetworkGroup u where 1=1 ";
            String bodyQueryStr = "";
            String tailQueryStr = "";
            int index = 0;
            
            bodyQueryStr += " and (u.name like " + "?" + (++index);
            bodyQueryStr += " or u.code like " + "?" + (++index);
            bodyQueryStr += ")";
            
            if (searchCriteria.getEnableCreatedDateSearch()) {
                bodyQueryStr += " and u.entryDate between " + "?" + (++index) + " and " + "?" + (++index);
            }
            tailQueryStr = " order by u.entryDate ";
            
            if (searchCriteria.getSortBy() == 0) {
                tailQueryStr += "DESC";
            } else {
                tailQueryStr += "ASC";
            }
            
            String wholeQuertStr = headQueryStr + bodyQueryStr + tailQueryStr;
            
            query = em.createQuery(wholeQuertStr);
            
            index = 0;
            
            query.setParameter(++index, searchCriteria.getSearchedText() + "%");
            query.setParameter(++index, searchCriteria.getSearchedText() + "%");
            if (searchCriteria.getEnableCreatedDateSearch()) {
                query.setParameter(++index, (long) searchCriteria.getFromDateTime());
                query.setParameter(++index, (long) searchCriteria.getToDateTime());
            }
            
        }
        query.setFirstResult(startRow)
                .setMaxResults(maxResult);
        
        return query.getResultList();
    }
    
    @Override
    public Long countTotalDocumentsBySearchCriteria(NetworkGroupSearchCriteria searchCriteria, User user
    ) {
        Query query;
        if (searchCriteria.getId() != null && searchCriteria.getId() > 0) {
            query = em.createQuery("SELECT COUNT(u.code) FROM NetworkGroup u where u.id = ?1 order by u.name ASC");
            query.setParameter(1, searchCriteria.getId());
        } else {
            String headQueryStr = "SELECT COUNT(u.code) FROM NetworkGroup u where 1=1 ";
            String bodyQueryStr = "";
            String tailQueryStr = "";
            int index = 0;
            
            bodyQueryStr += " and (u.name like " + "?" + (++index);
            bodyQueryStr += " or u.code like " + "?" + (++index);
            bodyQueryStr += ")";
            
            if (searchCriteria.getEnableCreatedDateSearch()) {
                bodyQueryStr += " and u.entryDate between " + "?" + (++index) + " and " + "?" + (++index);
            }
            
            tailQueryStr = " order by u.entryDate ";
            
            if (searchCriteria.getSortBy() == 0) {
                tailQueryStr += "DESC";
            } else {
                tailQueryStr += "ASC";
            }
            String wholeQuertStr = headQueryStr + bodyQueryStr + tailQueryStr;
            
            query = em.createQuery(wholeQuertStr);
            
            index = 0;
            
            query.setParameter(++index, searchCriteria.getSearchedText() + "%");
            query.setParameter(++index, searchCriteria.getSearchedText() + "%");
            if (searchCriteria.getEnableCreatedDateSearch()) {
                query.setParameter(++index, (long) searchCriteria.getFromDateTime());
                query.setParameter(++index, (long) searchCriteria.getToDateTime());
            }
        }
        
        long count = (long) query.getSingleResult();
        return count;
    }
    
    @Override
    public List<NetworkGroup> findAllNetworkGroupsByNodeId(Long nodeId) {
        Query query;
        query = em.createQuery("SELECT s from NetworkGroup s join s.nodesInNetworkGroup p where p.node.id= ?1 order by s.name ASC");
        query.setParameter(1, nodeId);
        return query.getResultList();
    }
    
    @Override
    public List<Long> findAllNetworkGroupsIDAssociatedWithUser(Long id
    ) {
        Query query;
        query = em.createQuery("SELECT s.id from NetworkGroup s join s.nodesInNetworkGroup p where p.node.id= ?1 order by s.name ASC");
        query.setParameter(1, id);
        return query.getResultList();
    }
    
    @Override
    public List<Long> findAllValidNetworkGroupsIDAssociatedWithNode(Long id, Long currentDate,
            String networkGroupCode
    ) {
        Query query;
        
        String headQueryStr = "SELECT s.id from NetworkGroup s join s.nodesInNetworkGroup p where 1=1 ";
        String bodyQueryStr = "";
        String tailQueryStr = "";
        int index = 0;
        
        bodyQueryStr += " and p.node.id= " + "?" + (++index);
//        bodyQueryStr += " and s.expiryDate >= " + "?" + (++index);
        if (networkGroupCode != null && !networkGroupCode.trim().equals("") && !networkGroupCode.trim().equalsIgnoreCase("All")) {
            bodyQueryStr += " and s.code = " + "?" + (++index);
        }
        
        tailQueryStr = " order by s.name ASC ";
        
        String wholeQuertStr = headQueryStr + bodyQueryStr + tailQueryStr;
        
        query = em.createQuery(wholeQuertStr);
        
        index = 0;
        
        query.setParameter(++index, id);
//        query.setParameter(++index, currentDate);
        if (networkGroupCode != null && !networkGroupCode.trim().equals("") && !networkGroupCode.trim().equalsIgnoreCase("All")) {
            query.setParameter(++index, networkGroupCode);
        }
        
        return query.getResultList();
        
    }
    
    @Override
    public List<Long> findAllValidNetworkGroupsIDAssociatedWithEdge(Long id, Long currentDate,
            String networkGroupCode
    ) {
        Query query;
        
        String headQueryStr = "SELECT s.id from NetworkGroup s join s.edgeInNetworkGroups p where 1=1 ";
        String bodyQueryStr = "";
        String tailQueryStr = "";
        int index = 0;
        
        bodyQueryStr += " and p.edge.id= " + "?" + (++index);
//        bodyQueryStr += " and s.expiryDate >= " + "?" + (++index);
        if (networkGroupCode != null && !networkGroupCode.trim().equals("") && !networkGroupCode.trim().equalsIgnoreCase("All")) {
            bodyQueryStr += " and s.code = " + "?" + (++index);
        }
        
        tailQueryStr = " order by s.name ASC ";
        
        String wholeQuertStr = headQueryStr + bodyQueryStr + tailQueryStr;
        
        query = em.createQuery(wholeQuertStr);
        
        index = 0;
        
        query.setParameter(++index, id);
//        query.setParameter(++index, currentDate);
        if (networkGroupCode != null && !networkGroupCode.trim().equals("") && !networkGroupCode.trim().equalsIgnoreCase("All")) {
            query.setParameter(++index, networkGroupCode);
        }
        
        return query.getResultList();
        
    }

//SELECT
//tg.id tg_id,tg.code tg_code,
//ttg.description ttg_description,
//t.social_id,
//u.username
//FROM travel_group tg
//INNER JOIN traveler_travelgroup ttg ON tg.id=ttg.travel_group_id
//INNER JOIN traveler t ON ttg.traveler_user_id = t.user_id
//INNER JOIN user u ON t.user_id=u.id
//-- where u.id=2102
// WHERE tg.id IN (3052,3053)
//ORDER BY tg.id DESC,u.username ASC
//LIMIT 0,10
    @Override
    public List<NodeInNetworkGroup> findNodeInNetworkGroupsBySearchCriteria_JDBC(NodeInNetworkGroupSearchCriteria searchCriteria, List<Long> networkGroupIDs
    ) {
        List<NodeInNetworkGroup> travelerInNetworkGroups = new ArrayList<>();

//        String queryString = "SELECT "
//                + " tg.id tg_id, tg.code tg_code, "
//                + " ttg.description ttg_description, "
//                + " t.social_id t_social_id, "
//                + " u.username u_username, u.first_name u_first_name, u.middle_name u_middle_name, u.last_name u_last_name, u.phone_number u_phone_number, u.mobile_number u_mobile_number, u.profile_image u_profile_image, u.gender u_gender , u.address1 u_address1"
//                + " FROM travel_group tg "
//                + " INNER JOIN traveler_travelgroup ttg ON tg.id=ttg.travel_group_id "
//                + " INNER JOIN traveler t ON ttg.traveler_user_id = t.user_id "
//                + " INNER JOIN user u ON t.user_id=u.id "
//                + " WHERE tg.id IN (3052,3053) "
//                + " ORDER BY tg.id DESC,u.username ASC "
//                //                + " LIMIT 0,10";
//                + " LIMIT ?1 , ?2 ";
        String headQueryStr = "SELECT "
                + " tg.id tg_id, tg.code tg_code, tg.expiry_date tg_expiry_date, "
                + " ttg.id ttg_id, ttg.description ttg_description, "
                + " t.social_id t_social_id, "
                + " u.id, u.username u_username, u.first_name u_first_name, u.middle_name u_middle_name, u.last_name u_last_name, u.phone_number u_phone_number, u.mobile_number u_mobile_number, u.profile_image u_profile_image, u.gender u_gender , u.address1 u_address1"
                + " FROM travel_group tg "
                + " INNER JOIN traveler_travelgroup ttg ON tg.id=ttg.travel_group_id "
                + " INNER JOIN traveler t ON ttg.traveler_user_id = t.user_id "
                + " INNER JOIN user u ON t.user_id=u.id "
                + " WHERE 1=1 ";
        
        String bodyQueryStr = "";
        String tailQueryStr = "";
        
        int index = 0;
        bodyQueryStr += " and tg.id IN (";
        String separator = "";  // separator here is your ","
        if (networkGroupIDs != null && !networkGroupIDs.isEmpty()) {
            for (Long p : networkGroupIDs) {
                bodyQueryStr += separator + "?" + (++index);
                separator = ",";
            }
        } else {
            bodyQueryStr += "?" + (++index);
        }
        bodyQueryStr += ")";
        
        bodyQueryStr += " and (u.username like " + "?" + (++index);
        bodyQueryStr += " or u.first_name like " + "?" + (++index);
        bodyQueryStr += " or u.middle_name like " + "?" + (++index);
        bodyQueryStr += " or u.last_name like " + "?" + (++index) + ")";
        
        bodyQueryStr += " and u.enabled = true ";

//        tailQueryStr = " ORDER BY tg.id DESC"
//                + ",u.username ASC "
//                + " LIMIT " + "?" + (++index) + " , " + "?" + (++index);
        tailQueryStr = " order by tg.id ";
        
        if (searchCriteria.getSortBy() == 0) {
            tailQueryStr += "DESC";
        } else {
            tailQueryStr += "ASC";
        }
        
        tailQueryStr += " ,u.username ASC "
                + " LIMIT " + "?" + (++index) + " , " + "?" + (++index);
        
        String wholeQuertStr = headQueryStr + bodyQueryStr + tailQueryStr;
        System.out.println(wholeQuertStr);
        
        index = 0;
        Query query = em.createNativeQuery(wholeQuertStr);
        int maxResult = searchCriteria.getLimitResult();
        int adjustedPageN0 = Math.abs(searchCriteria.getPageNo() - 1);
        int startRow = adjustedPageN0 * maxResult;
        
        if (networkGroupIDs != null && !networkGroupIDs.isEmpty()) {
            for (Long p : networkGroupIDs) {
                query.setParameter(++index, p);
            }
        } else {
            query.setParameter(++index, -1);
        }
        
        query.setParameter(++index, searchCriteria.getUsername() + "%");
        query.setParameter(++index, searchCriteria.getUsername() + "%");
        query.setParameter(++index, searchCriteria.getUsername() + "%");
        query.setParameter(++index, searchCriteria.getUsername() + "%");
        
        query.setParameter(++index, startRow);
        query.setParameter(++index, searchCriteria.getLimitResult());
        
        List<Object[]> results = query.getResultList();
        
        results.stream().forEach((record) -> {
            Long networkGroupID = Long.parseLong(record[0].toString());
            String networkGroupCode = (String) record[1];
            
            Long networkGroupExpiryDate = Long.parseLong(record[2].toString());
            
            Long travelerNetworkGroupID = Long.parseLong(record[3].toString());
            String travelerNetworkGroupDescription = (String) record[4];
            
            String travelerSocialID = (String) record[5];
            
            Long travelerID = Long.parseLong(record[6].toString());
            String userUserName = (String) record[7];
            String userFirstName = (String) record[8];
            String userMiddleName = (String) record[9];
            String userLastName = (String) record[10];
            String userPhoneNumber = (String) record[11];
            String userMobileNumber = (String) record[12];
            String userProfileImage = (String) record[13];
            String userGender = (String) record[14];
            String address1 = (String) record[15];
            
            NodeInNetworkGroup travelerInNetworkGroup = new NodeInNetworkGroup();
            travelerInNetworkGroup.setId(travelerNetworkGroupID);
            travelerInNetworkGroup.setDescription(travelerNetworkGroupDescription);
            travelerInNetworkGroup.setNetworkGroupCode(networkGroupCode);
            travelerInNetworkGroup.setNetworkGroupId(networkGroupID);
//            travelerInNetworkGroup.setNetworkGroupExpiryDate(networkGroupExpiryDate);
            Node traveler = new Node();
            traveler.setId(travelerID);
//            traveler.setUsername(userUserName);
//            traveler.setFirstName(userFirstName);
//            traveler.setMiddleName(userMiddleName);
//            traveler.setLastName(userLastName);
//            traveler.setSocialID(travelerSocialID);
//            traveler.setPhoneNo(userPhoneNumber);
//            traveler.setMobileNo(userMobileNumber);
//            traveler.setProfileImage(userProfileImage);
//            traveler.setGender(userGender);
//            traveler.setAddress1(address1);
//
//            traveler.setRoles(new HashSet<>());

            travelerInNetworkGroup.setNode(traveler);
            
            travelerInNetworkGroups.add(travelerInNetworkGroup);
        });
        return travelerInNetworkGroups;
    }
    
    @Override
    public Long countTotalDocumentsOfNodeInNetworkGroupsBySearchCriteria_JDBC(NodeInNetworkGroupSearchCriteria searchCriteria, List<Long> networkGroupIDs
    ) {
        Long count = 0L;
        String headQueryStr = "SELECT "
                + " count(*) "
                + " FROM travel_group tg "
                + " INNER JOIN traveler_travelgroup ttg ON tg.id=ttg.travel_group_id "
                + " INNER JOIN traveler t ON ttg.traveler_user_id = t.user_id "
                + " INNER JOIN user u ON t.user_id=u.id "
                + " WHERE 1=1 ";
        
        String bodyQueryStr = "";
        String tailQueryStr = "";
        
        int index = 0;
        bodyQueryStr += " and tg.id IN (";
        String separator = "";  // separator here is your ","
        if (networkGroupIDs != null && !networkGroupIDs.isEmpty()) {
            for (Long p : networkGroupIDs) {
                bodyQueryStr += separator + "?" + (++index);
                separator = ",";
            }
        } else {
            bodyQueryStr += "?" + (++index);
        }
        bodyQueryStr += ")";
        
        bodyQueryStr += " and (u.username like " + "?" + (++index);
        bodyQueryStr += " or u.first_name like " + "?" + (++index);
        bodyQueryStr += " or u.middle_name like " + "?" + (++index);
        bodyQueryStr += " or u.last_name like " + "?" + (++index) + ")";
        bodyQueryStr += " and u.enabled = true ";
        
        tailQueryStr = " ORDER BY tg.id DESC,u.username ASC ";
        
        String wholeQuertStr = headQueryStr + bodyQueryStr + tailQueryStr;
        System.out.println(wholeQuertStr);
        
        index = 0;
        Query query = em.createNativeQuery(wholeQuertStr);
        int maxResult = searchCriteria.getLimitResult();
        int adjustedPageN0 = Math.abs(searchCriteria.getPageNo() - 1);
        int startRow = adjustedPageN0 * maxResult;
        
        if (networkGroupIDs != null && !networkGroupIDs.isEmpty()) {
            for (Long p : networkGroupIDs) {
                query.setParameter(++index, p);
            }
        } else {
            query.setParameter(++index, -1);
        }
        
        query.setParameter(++index, searchCriteria.getUsername() + "%");
        query.setParameter(++index, searchCriteria.getUsername() + "%");
        query.setParameter(++index, searchCriteria.getUsername() + "%");
        query.setParameter(++index, searchCriteria.getUsername() + "%");

//        query.setParameter(++index, startRow);
//        query.setParameter(++index, geoLocationSearchCriteria.getLimitResult());
        count = Long.parseLong(query.getSingleResult().toString());
        
        return count;
    }
    
    @Override
    public List<EdgeInNetworkGroup> findEdgeInNetworkGroupsBySearchCriteria_JDBC(EdgeInNetworkGroupSearchCriteria searchCriteria, List<Long> networkGroupIDs
    ) {
        List<EdgeInNetworkGroup> travelerInNetworkGroups = new ArrayList<>();
        
        String headQueryStr = "SELECT "
                + " tg.id tg_id, tg.code tg_code, tg.expiry_date tg_expiry_date, "
                + " ttg.id ttg_id, ttg.description ttg_description, "
                + " t.social_id t_social_id, "
                + " u.id, u.username u_username, u.first_name u_first_name, u.middle_name u_middle_name, u.last_name u_last_name, u.phone_number u_phone_number, u.mobile_number u_mobile_number, u.profile_image u_profile_image, u.gender u_gender , u.address1 u_address1"
                + " FROM travel_group tg "
                + " INNER JOIN travelguide_travelgroup ttg ON tg.id=ttg.travelguide_group_id "
                + " INNER JOIN travel_guide t ON ttg.travelguide_user_id = t.user_id "
                + " INNER JOIN user u ON t.user_id=u.id"
                + " WHERE 1=1 ";
        
        String bodyQueryStr = "";
        String tailQueryStr = "";
        
        int index = 0;
        bodyQueryStr += " and tg.id IN (";
        String separator = "";  // separator here is your ","
        if (networkGroupIDs != null && !networkGroupIDs.isEmpty()) {
            for (Long p : networkGroupIDs) {
                bodyQueryStr += separator + "?" + (++index);
                separator = ",";
            }
        } else {
            bodyQueryStr += "?" + (++index);
        }
        bodyQueryStr += ")";
        
        bodyQueryStr += " and (u.username like " + "?" + (++index);
        bodyQueryStr += " or u.first_name like " + "?" + (++index);
        bodyQueryStr += " or u.middle_name like " + "?" + (++index);
        bodyQueryStr += " or u.last_name like " + "?" + (++index) + ")";
        
        bodyQueryStr += " and u.enabled = true ";
        
        tailQueryStr = " ORDER BY tg.id DESC,u.username ASC "
                + " LIMIT " + "?" + (++index) + " , " + "?" + (++index);
        
        String wholeQuertStr = headQueryStr + bodyQueryStr + tailQueryStr;
        System.out.println(wholeQuertStr);
        
        index = 0;
        Query query = em.createNativeQuery(wholeQuertStr);
        int maxResult = searchCriteria.getLimitResult();
        int adjustedPageN0 = Math.abs(searchCriteria.getPageNo() - 1);
        int startRow = adjustedPageN0 * maxResult;
        
        if (networkGroupIDs != null && !networkGroupIDs.isEmpty()) {
            for (Long p : networkGroupIDs) {
                query.setParameter(++index, p);
            }
        } else {
            query.setParameter(++index, -1);
        }
        
        query.setParameter(++index, searchCriteria.getUsername() + "%");
        query.setParameter(++index, searchCriteria.getUsername() + "%");
        query.setParameter(++index, searchCriteria.getUsername() + "%");
        query.setParameter(++index, searchCriteria.getUsername() + "%");
        
        query.setParameter(++index, startRow);
        query.setParameter(++index, searchCriteria.getLimitResult());
        
        List<Object[]> results = query.getResultList();
        
        results.stream().forEach((record) -> {
            Long networkGroupID = Long.parseLong(record[0].toString());
            String networkGroupCode = (String) record[1];
            Long networkGroupExpiryDate = Long.parseLong(record[2].toString());
            
            Long travelerNetworkGroupID = Long.parseLong(record[3].toString());
            String travelerNetworkGroupDescription = (String) record[4];
            
            String travelerSocialID = (String) record[5];
            
            Long travelerID = Long.parseLong(record[6].toString());
            String userUserName = (String) record[7];
            String userFirstName = (String) record[8];
            String userMiddleName = (String) record[9];
            String userLastName = (String) record[10];
            String userPhoneNumber = (String) record[11];
            String userMobileNumber = (String) record[12];
            String userProfileImage = (String) record[13];
            String userGender = (String) record[14];
            String address1 = (String) record[15];
            
            EdgeInNetworkGroup travelerInNetworkGroup = new EdgeInNetworkGroup();
            travelerInNetworkGroup.setId(travelerNetworkGroupID);
            travelerInNetworkGroup.setNetworkGroupCode(networkGroupCode);
            travelerInNetworkGroup.setNetworkGroupId(networkGroupID);
//            travelerInNetworkGroup.setNetworkGroupExpiryDate(networkGroupExpiryDate);
            travelerInNetworkGroup.setDescription(travelerNetworkGroupDescription);
            Edge edge = new Edge();
            edge.setId(travelerID);
//            edge.setUsername(userUserName);
//            edge.setFirstName(userFirstName);
//            edge.setMiddleName(userMiddleName);
//            edge.setLastName(userLastName);
//            edge.setSocialID(travelerSocialID);
//            edge.setPhoneNo(userPhoneNumber);
//            edge.setMobileNo(userMobileNumber);
//            edge.setProfileImage(userProfileImage);
//            edge.setGender(userGender);
//            edge.setAddress1(address1);
//            edge.setRoles(new HashSet<>());
            travelerInNetworkGroup.setEdge(edge);
            
            travelerInNetworkGroups.add(travelerInNetworkGroup);
        });
        return travelerInNetworkGroups;
    }
    
    @Override
    public Long countTotalDocumentsOfEdgeInNetworkGroupsBySearchCriteria_JDBC(EdgeInNetworkGroupSearchCriteria searchCriteria, List<Long> networkGroupIDs
    ) {
        Long count = 0L;
        
        String headQueryStr = "SELECT "
                + " count(*) "
                + " FROM travel_group tg "
                + " INNER JOIN travelguide_travelgroup ttg ON tg.id=ttg.travelguide_group_id "
                + " INNER JOIN travel_guide t ON ttg.travelguide_user_id = t.user_id "
                + " INNER JOIN user u ON t.user_id=u.id"
                + " WHERE 1=1 ";
        
        String bodyQueryStr = "";
        String tailQueryStr = "";
        
        int index = 0;
        bodyQueryStr += " and tg.id IN (";
        String separator = "";  // separator here is your ","
        if (networkGroupIDs != null && !networkGroupIDs.isEmpty()) {
            for (Long p : networkGroupIDs) {
                bodyQueryStr += separator + "?" + (++index);
                separator = ",";
            }
        } else {
            bodyQueryStr += "?" + (++index);
        }
        bodyQueryStr += ")";
        
        bodyQueryStr += " and (u.username like " + "?" + (++index);
        bodyQueryStr += " or u.first_name like " + "?" + (++index);
        bodyQueryStr += " or u.middle_name like " + "?" + (++index);
        bodyQueryStr += " or u.last_name like " + "?" + (++index) + ")";
        
        bodyQueryStr += " and u.enabled = true ";
        
        tailQueryStr = " ORDER BY tg.id DESC,u.username ASC ";
        
        String wholeQuertStr = headQueryStr + bodyQueryStr + tailQueryStr;
        System.out.println(wholeQuertStr);
        
        index = 0;
        Query query = em.createNativeQuery(wholeQuertStr);
        int maxResult = searchCriteria.getLimitResult();
        int adjustedPageN0 = Math.abs(searchCriteria.getPageNo() - 1);
        int startRow = adjustedPageN0 * maxResult;
        
        if (networkGroupIDs != null && !networkGroupIDs.isEmpty()) {
            for (Long p : networkGroupIDs) {
                query.setParameter(++index, p);
            }
        } else {
            query.setParameter(++index, -1);
        }
        
        query.setParameter(++index, searchCriteria.getUsername() + "%");
        query.setParameter(++index, searchCriteria.getUsername() + "%");
        query.setParameter(++index, searchCriteria.getUsername() + "%");
        query.setParameter(++index, searchCriteria.getUsername() + "%");

//        query.setParameter(++index, startRow);
//        query.setParameter(++index, geoLocationSearchCriteria.getLimitResult());
        count = Long.parseLong(query.getSingleResult().toString());
        
        return count;
    }

//SELECT
//tg.id tg_id,tg.code tg_code,
//gtg.id gtg_id,gtg.journey_date gtg_journey_date, gtg.description gtg_description
//,g.name g_name, g.latitude g_latitue,g.longitude g_longitude, g.phoneNo g_phone_number, g.mobileNo g_mobile_number, g.profileImage g_profile_image, g.address1 g_address
//FROM travel_group tg
//INNER JOIN geolocation_travelgroup gtg ON tg.id=gtg.geolocation_group_id
//INNER JOIN geolocation g ON gtg.geoLocation_id = g.id
// WHERE tg.id IN (3052,3053)
//-- AND gtg.journey_date between 3 and 1356997501
//ORDER BY tg.id DESC,gtg.journey_date ASC , g.name ASC
//LIMIT 0,10
    @Override
    public EdgeInNetworkGroup findEdgeInNetworkGroup(Long id
    ) {
        return em.find(EdgeInNetworkGroup.class, id);
    }
    
    @Override
    public NodeInNetworkGroup findNodeInNetworkGroup(Long id
    ) {
        return em.find(NodeInNetworkGroup.class, id);
    }
    
    @Override
    public RoleInNetworkGroup findRoleInNetworkGroup(Long id
    ) {
        return em.find(RoleInNetworkGroup.class, id);
    }
    
    @Override
    public List<NetworkGroup> findAllNetworkGroupsForUser(User user
    ) {
        
        Query query;
        
        String headQueryStr = "SELECT u FROM NetworkGroup u where 1=1 ";
        String bodyQueryStr = "";
        String tailQueryStr = "";
        
        int index = 0;
        
        tailQueryStr = " order by u.name ASC ";
        
        String wholeQuertStr = headQueryStr + bodyQueryStr + tailQueryStr;
        
        query = em.createQuery(wholeQuertStr);
        
        index = 0;
        
        return query.getResultList();
    }
    
    @Override
    public List<NetworkGroup> findAllValidNetworkGroupsForUser(User loggedUser, Long currentDate
    ) {
        
        Query query;
        
        String headQueryStr = "SELECT u FROM NetworkGroup u where 1=1 ";
        String bodyQueryStr = "";
        String tailQueryStr = "";
        
        int index = 0;
        
        tailQueryStr = " order by u.name ASC ";
        
        String wholeQuertStr = headQueryStr + bodyQueryStr + tailQueryStr;
        
        query = em.createQuery(wholeQuertStr);
        
        index = 0;
        
        return query.getResultList();
    }
    
    @Override
    public NodeInNetworkGroup updateNodeInNetworkGroupCordinates(Long id, NodeInNetworkGroup data) {
        NodeInNetworkGroup entry = em.find(NodeInNetworkGroup.class, id);
        entry.setCanvasXValue(data.getCanvasXValue());
        entry.setCanvasYValue(data.getCanvasYValue());
        entry.setDomXValue(data.getDomXValue());
        entry.setDomYValue(data.getDomYValue());
        entry.setZoomScale(data.getZoomScale());
        return entry;
    }
    
    @Override
    public int deleteAllNetworkGroup() {
        Query query = em.createNativeQuery("delete from network_group");
        int result = query.executeUpdate();
        return result;
    }
    
    @Override
    public int deleteAllNodeInNetworkGroup() {
        Query query = em.createNativeQuery("delete from node_networkgroup");
        int result = query.executeUpdate();
        return result;
    }
    
    @Override
    public int deleteAllEdgeInNetworkGroup() {
        Query query = em.createNativeQuery("delete from edge_networkgroup");
        int result = query.executeUpdate();
        return result;
    }
    
}
