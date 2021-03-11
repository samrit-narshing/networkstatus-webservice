package com.project.core.repositories.jpa.network;

import com.project.core.models.entities.network.Node;
import com.project.core.models.entities.network.NodeAlert;
import com.project.core.models.entities.network.NodeAlertInfo;
import com.project.core.repositories.network.NodeRepo;
import com.project.rest.util.searchcriteria.network.NodeSearchCriteria;
import java.util.Arrays;
import java.util.HashSet;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Samrit
 */
@Repository
public class JpaNodeRepo implements NodeRepo {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Node create(Node node) {
        em.persist(node);
        em.flush();
        return node;
    }

    @Override
    public Node find(Long id) {
        return em.find(Node.class, id);
    }

    @Override
    public Node findByLabel(String label) {
        Query query = em.createQuery("SELECT u FROM Node u WHERE u.label=?1");
        query.setParameter(1, label);
        List<Node> grades = query.getResultList();
        if (grades.isEmpty()) {
            return null;
        } else {
            return grades.get(0);
        }
    }

    @Override
    public Node findEnabledByLabel(String label) {
        Query query = em.createQuery("SELECT u FROM Node u WHERE u.label=?1 and u.enabled=?2");
        query.setParameter(1, label);
        query.setParameter(2, true);
        List<Node> grades = query.getResultList();
        if (grades.isEmpty()) {
            return null;
        } else {
            return grades.get(0);
        }
    }

    @Override
    public List<Node> findAll() {
        Query query = em.createQuery("SELECT u FROM Node");
        return query.getResultList();
    }

    @Override
    public List<Node> findAllEnabled() {
        Query query = em.createQuery("SELECT u FROM Node u where u.enabled = ?1 and u.type NOT IN ('type-networkgroup') ");
        query.setParameter(1, true);
        return query.getResultList();
    }

    @Override
    public List<Node> findAllEnabledNodesExculdingNodesInNetworkGroup(List<Long> exculdingNodeIdList) {

//        Query query = em.createQuery("SELECT u FROM Node u where u.enabled = ?1 and u.id NOT IN (426,416,427,417,415,452)");
        Query query = em.createQuery("SELECT u FROM Node u where u.enabled = ?1 and u.id NOT IN (?2)");
//        Query query = em.createQuery("SELECT u FROM Node u where u.enabled = ?1 and u.id NOT IN (" + nodeIds.trim() + ")");
        query.setParameter(1, true);
//        query.setParameter(2, exculdingNodeIdList);
        query.setParameter(2, exculdingNodeIdList.size() == 0 ? 0L : exculdingNodeIdList);
        return query.getResultList();
    }

    @Override
    public List<Node> findBySearchCriteria(NodeSearchCriteria searchCriteria) {
        int maxResult = searchCriteria.getLimitResult();
        int adjustedPageN0 = Math.abs(searchCriteria.getPageNo() - 1);
        int startRow = adjustedPageN0 * maxResult;
        Query query;
        if (searchCriteria.getId() != null && searchCriteria.getId() > 0) {
            query = em.createQuery("SELECT u FROM Node u where u.id = ?1 order by u.label ASC");
            query.setParameter(1, searchCriteria.getId());
        } else {
            String headQueryStr = "SELECT u FROM Node u where 1=1 ";
            String bodyQueryStr = "";
            String tailQueryStr = "";
            int index = 0;

            bodyQueryStr += " and (u.label like " + "?" + (++index);
            bodyQueryStr += " or u.label like " + "?" + (++index) + ")";

            bodyQueryStr += " and u.type NOT IN('type-networkgroup')";
//            and u.type NOT IN('type-networkgroup')

            if (searchCriteria.getEnableOrDisableCheck()) {
                bodyQueryStr += " and u.enabled = " + "?" + (++index);
            }

            tailQueryStr = " order by u.label ASC ";

            String wholeQuertStr = headQueryStr + bodyQueryStr + tailQueryStr;

            query = em.createQuery(wholeQuertStr);

            index = 0;

            query.setParameter(++index, searchCriteria.getSearchText() + "%");
            query.setParameter(++index, searchCriteria.getSearchText() + "%");

            if (searchCriteria.getEnableOrDisableCheck()) {
                query.setParameter(++index, searchCriteria.getEnable());
            }

        }

        query.setFirstResult(startRow)
                .setMaxResults(maxResult);

        return query.getResultList();
    }

    @Override
    public Long countTotalDocumentsBySearchCriteria(NodeSearchCriteria searchCriteria) {
        Query query;
        if (searchCriteria.getId() != null && searchCriteria.getId() > 0) {
            query = em.createQuery("SELECT COUNT(u.label) FROM Node u where u.id = ?1 order by u.label ASC");
            query.setParameter(1, searchCriteria.getId());
        } else {
            String headQueryStr = "SELECT COUNT(u.label) FROM Node u where 1=1 ";
            String bodyQueryStr = "";
            String tailQueryStr = "";
            int index = 0;

            bodyQueryStr += " and (u.label like " + "?" + (++index);
            bodyQueryStr += " or u.label like " + "?" + (++index) + ")";

            bodyQueryStr += " and u.type NOT IN('type-networkgroup')";
            if (searchCriteria.getEnableOrDisableCheck()) {
                bodyQueryStr += " and u.enabled = " + "?" + (++index);
            }

            tailQueryStr = " order by u.label ASC ";

            String wholeQuertStr = headQueryStr + bodyQueryStr + tailQueryStr;

            query = em.createQuery(wholeQuertStr);

            index = 0;

            query.setParameter(++index, searchCriteria.getSearchText() + "%");
            query.setParameter(++index, searchCriteria.getSearchText() + "%");

            if (searchCriteria.getEnableOrDisableCheck()) {
                query.setParameter(++index, searchCriteria.getEnable());
            }

        }

        long count = (long) query.getSingleResult();
        return count;
    }

    @Override
    public Node update(Long id, Node data) {
        Node entry = em.find(Node.class, id);
        entry.setDescription(data.getDescription());
        entry.setEnabled(data.isEnabled());
        entry.setHeight(data.getHeight());
        entry.setLabel(data.getLabel());
        entry.setRedirectingURLLink(data.getRedirectingURLLink());
        entry.setType(data.getType());
        entry.setTitle(data.getTitle());
        entry.setNodeValue(data.getNodeValue());
        entry.getFill().setSrc(data.getFill().getSrc());

//        entry.getAlert().setDescription(data.getAlert().getDescription());
//        entry.getAlert().setType(data.getAlert().getType());
        return entry;
    }

    @Override
    public Node updateEnabledStatus(Long id) {
        Node entry = em.find(Node.class, id);
        entry.setEnabled(!entry.isEnabled());
        return entry;
    }

    @Override
    public Node updateStatusAsEnabled(Long id) {
        Node entry = em.find(Node.class, id);
        entry.setEnabled(true);
        return entry;
    }

    @Override
    public Node updateStatusAsDisabled(Long id) {
        Node entry = em.find(Node.class, id);
        entry.setEnabled(false);
        return entry;
    }

    @Override
    public Node delete(Long id) {
        Node entry = em.find(Node.class, id);
        em.remove(entry);
        return entry;
    }

    @Override
    public Node updateNodeAlert(Long id, Node data) {
        Node entry = em.find(Node.class, id);
        entry.getAlert().setType(data.getAlert().getType());
        entry.getAlert().setDescription(data.getAlert().getDescription());
        return entry;
    }

    @Override
    public Node updateNodeAlertAsReset(Long id) {
        Node entry = em.find(Node.class, id);
        entry.getAlert().setType(1);
        entry.getAlert().setDescription("");
        entry.getAlert().getNodeAlertInfos().clear();
        return entry;
    }

    @Override
    public Node updateNodeAlertToAddNodeAlertInfo(Long id, Node data) {
        Node entry = em.find(Node.class, id);
        data.setId(null);
        entry.getAlert().setType(data.getAlert().getType());
        entry.getAlert().setDescription(data.getAlert().getDescription());
        for (NodeAlertInfo nodeAlertInfo : data.getAlert().getNodeAlertInfos()) {
            entry.getAlert().getNodeAlertInfos().add(nodeAlertInfo);
        }
        return entry;
    }

    @Override
    public Node updateNodeAlertToRemoveNodeAlertInfo(Long id, NodeAlertInfo data) {
        Node entry = em.find(Node.class, id);
        NodeAlertInfo tobeRemoved = null;
        for (NodeAlertInfo info : entry.getAlert().getNodeAlertInfos()) {
            if (info.getId().equals(data.getId())) {
                tobeRemoved = info;
            }
        }

        if (tobeRemoved != null) {
            entry.getAlert().getNodeAlertInfos().remove(tobeRemoved);
        }
        return entry;
    }

    @Override
    public Node updateNodeCordinates(Long id, Node data) {
        Node entry = em.find(Node.class, id);
        entry.setCanvasXValue(data.getCanvasXValue());
        entry.setCanvasYValue(data.getCanvasYValue());
        entry.setDomXValue(data.getDomXValue());
        entry.setDomYValue(data.getDomYValue());
        entry.setZoomScale(data.getZoomScale());
        return entry;
    }

    @Override
    public int deleteAllNodes() {
        Query query = em.createNativeQuery("delete from node");
        int result = query.executeUpdate();
        return result;
    }

    @Override
    public int deleteNodeByLabel(String label) {
        Query query = em.createQuery("delete Node u where u.label = ?1");
        query.setParameter(1, label);
        return query.executeUpdate();
    }

}
