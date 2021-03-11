package com.project.core.repositories.jpa.network;

import com.project.core.models.entities.network.Edge;
import com.project.core.repositories.network.EdgeRepo;
import com.project.rest.util.searchcriteria.network.EdgeSearchCriteria;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 *
 * @author Samrit
 */
@Repository
public class JpaEdgeRepo implements EdgeRepo {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Edge create(Edge edge) {
        em.persist(edge);
        em.flush();
        return edge;
    }

    @Override
    public Edge find(Long id) {
        return em.find(Edge.class, id);
    }

    @Override
    public Edge findByLabel(String label) {
        Query query = em.createQuery("SELECT u FROM Edge u WHERE u.fromNode.label=?1");
        query.setParameter(1, label);
        List<Edge> edges = query.getResultList();
        if (edges.isEmpty()) {
            return null;
        } else {
            return edges.get(0);
        }
    }

    @Override
    public Edge findEnabledByLabel(String label) {
        Query query = em.createQuery("SELECT u FROM Edge u WHERE u.fromNode.label=?1 and u.enabled=?2");
        query.setParameter(1, label);
        query.setParameter(2, true);
        List<Edge> edges = query.getResultList();
        if (edges.isEmpty()) {
            return null;
        } else {
            return edges.get(0);
        }
    }

    @Override
    public List<Edge> findAll() {
        Query query = em.createQuery("SELECT u FROM Edge");
        return query.getResultList();
    }

    @Override
    public List<Edge> findAllEnabled() {
        Query query = em.createQuery("SELECT u FROM Edge u where u.enabled = ?1");
        query.setParameter(1, true);
        return query.getResultList();
    }

    @Override
    public List<Edge> findBySearchCriteria(EdgeSearchCriteria searchCriteria) {
        int maxResult = searchCriteria.getLimitResult();
        int adjustedPageN0 = Math.abs(searchCriteria.getPageNo() - 1);
        int startRow = adjustedPageN0 * maxResult;
        Query query;
        if (searchCriteria.getId() != null && searchCriteria.getId() > 0) {
            query = em.createQuery("SELECT u FROM Edge u where u.id = ?1 order by u.fromNode.label ASC");
            query.setParameter(1, searchCriteria.getId());
        } else {
            String headQueryStr = "SELECT u FROM Edge u where 1=1 ";
            String bodyQueryStr = "";
            String tailQueryStr = "";
            int index = 0;

            bodyQueryStr += " and (u.fromNode.label like " + "?" + (++index);
            bodyQueryStr += " or u.fromNode.label like " + "?" + (++index) + ")";

            if (searchCriteria.getEnableOrDisableCheck()) {
                bodyQueryStr += " and u.enabled = " + "?" + (++index);
            }

            tailQueryStr = " order by u.fromNode.label ASC ";

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
    public Long countTotalDocumentsBySearchCriteria(EdgeSearchCriteria searchCriteria) {
        Query query;
        if (searchCriteria.getId() != null && searchCriteria.getId() > 0) {
            query = em.createQuery("SELECT COUNT(u.fromNode.label) FROM Edge u where u.id = ?1 order by u.fromNode.label ASC");
            query.setParameter(1, searchCriteria.getId());
        } else {
            String headQueryStr = "SELECT COUNT(u.fromNode.label) FROM Edge u where 1=1 ";
            String bodyQueryStr = "";
            String tailQueryStr = "";
            int index = 0;

            bodyQueryStr += " and (u.fromNode.label like " + "?" + (++index);
            bodyQueryStr += " or u.fromNode.label like " + "?" + (++index) + ")";
            if (searchCriteria.getEnableOrDisableCheck()) {
                bodyQueryStr += " and u.enabled = " + "?" + (++index);
            }

            tailQueryStr = " order by u.fromNode.label ASC ";

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
    public Edge update(Long id, Edge data) {
        Edge entry = em.find(Edge.class, id);
        entry.setArrows(data.getArrows());
        entry.setDashes(data.isDashes());
        entry.setEdge_length(data.getEdge_length());
        entry.setEdge_value(data.getEdge_value());
        entry.setEnabled(data.isEnabled());
        entry.setLabel(data.getLabel());
        entry.setToNode(data.getToNode());
        entry.setFromNode(data.getFromNode());
        entry.setTitle(data.getLabel());
        return entry;
    }

    @Override
    public Edge updateEnabledStatus(Long id) {
        Edge entry = em.find(Edge.class, id);
        entry.setEnabled(!entry.isEnabled());
        return entry;
    }

    @Override
    public Edge updateStatusAsEnabled(Long id) {
        Edge entry = em.find(Edge.class, id);
        entry.setEnabled(true);
        return entry;
    }

    @Override
    public Edge updateStatusAsDisabled(Long id) {
        Edge entry = em.find(Edge.class, id);
        entry.setEnabled(false);
        return entry;
    }

    @Override
    public Edge delete(Long id) {
        Edge entry = em.find(Edge.class, id);
        em.remove(entry);
        return entry;
    }

    @Override
    public Edge findEnabledByNodeIDs(Long nodeID1, Long nodeID2) {
        Query query = em.createQuery("SELECT u FROM Edge u WHERE (u.fromNode.id=?1 and u.toNode.id=?2) or (u.fromNode.id=?3 and u.toNode.id=?4) and u.enabled=?5");
        query.setParameter(1, nodeID1);
        query.setParameter(2, nodeID2);
        query.setParameter(3, nodeID2);
        query.setParameter(4, nodeID1);
        query.setParameter(5, true);

        List<Edge> edges = query.getResultList();

        if (edges.isEmpty()) {
            return null;
        } else {
            return edges.get(0);
        }
    }

    @Override
    public int deleteAllEdges() {
        Query query = em.createNativeQuery("delete from edge");
        int result = query.executeUpdate();
        return result;
    }
}
