/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.repositories.jpa.network;

import com.project.core.models.entities.network.NodeAlertInfoArchive;
import com.project.core.repositories.jpa.util.*;
import com.project.core.repositories.network.NodeAlertInfoArchiveRepo;
import com.project.rest.util.searchcriteria.network.NodeAlertInfoArchiveSearchCriteria;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Samrit
 */
@Repository
public class JpaNodeAlertInfoArchiveRepo implements NodeAlertInfoArchiveRepo {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public NodeAlertInfoArchive createNodeAlertInfoArchive(NodeAlertInfoArchive data) {
        em.persist(data);
        return data;
    }

    @Override
    public NodeAlertInfoArchive findNodeAlertInfoArchive(Long id) {
        return em.find(NodeAlertInfoArchive.class, id);
    }

    @Override
    public List<NodeAlertInfoArchive> findAllNodeAlertInfoArchive() {
        Query query = em.createQuery("SELECT s FROM NodeAlertInfoArchive s");
        return query.getResultList();
    }

    @Override
    public NodeAlertInfoArchive deleteNodeAlertInfoArchive(Long id) {
        NodeAlertInfoArchive entry = em.find(NodeAlertInfoArchive.class, id);
        em.remove(entry);
        return entry;
    }

    @Override
    public int deleteAllNodeAlertInfoArchive() {
        Query query = em.createNativeQuery("TRUNCATE  TABLE  nodealertinfo_archive");
        int result = query.executeUpdate();
        return result;
    }

    @Override
    public List<NodeAlertInfoArchive> findNodeAlertInfoArchiveBySearchCriteria(NodeAlertInfoArchiveSearchCriteria searchCriteria) {
        int maxResult = searchCriteria.getLimitResult();
        int adjustedPageN0 = Math.abs(searchCriteria.getPageNo() - 1);
        int startRow = adjustedPageN0 * maxResult;
        Query query;
        ///////////////
        if (searchCriteria.getId() != null && searchCriteria.getId() > 0) {
            query = em.createQuery("SELECT u FROM NodeAlertInfoArchive u where u.id = ?1 order by u.entryDate DESC");
            query.setParameter(1, searchCriteria.getId());
        } else {

            String headQueryStr = "SELECT u FROM NodeAlertInfoArchive u where 1=1 ";
            String bodyQueryStr = "";
            String tailQueryStr = "";
            int index = 0;

            bodyQueryStr += " and (u.nodeName like " + "?" + (++index);
            bodyQueryStr += " OR u.loggedUsername like " + "?" + (++index);
            bodyQueryStr += " )";
            bodyQueryStr += " and u.entryDate between " + "?" + (++index) + " and " + "?" + (++index);

            tailQueryStr = " order by u.entryDate ";

            if (searchCriteria.getSortBy() == 0) {
                tailQueryStr += "DESC";
            } else {
                tailQueryStr += "ASC";
            }

            String wholeQuertStr = headQueryStr + bodyQueryStr + tailQueryStr;
            query = em.createQuery(wholeQuertStr);

            index = 0;

            query.setParameter(++index, searchCriteria.getSearchText() + "%");
            query.setParameter(++index, searchCriteria.getSearchText() + "%");
            query.setParameter(++index, (long) searchCriteria.getFromDateTime());
            query.setParameter(++index, (long) searchCriteria.getToDateTime());

        }
        query.setFirstResult(startRow)
                .setMaxResults(maxResult);

        return query.getResultList();

    }

    @Override
    public Long countTotalDocumentsOfNodeAlertInfoArchiveBySearchCriteria(NodeAlertInfoArchiveSearchCriteria searchCriteria) {
        Query query;
        if (searchCriteria.getId() != null && searchCriteria.getId() > 0) {
            query = em.createQuery("SELECT COUNT(u.id) FROM NodeAlertInfoArchive u where u.id = ?1 order by u.entryDate DESC");
            query.setParameter(1, searchCriteria.getId());
        } else {

            String headQueryStr = "SELECT COUNT(u.id) FROM NodeAlertInfoArchive u where 1=1 ";
            String bodyQueryStr = "";
            String tailQueryStr = "";
            int index = 0;

            bodyQueryStr += " and (u.nodeName like " + "?" + (++index);
            bodyQueryStr += " OR u.loggedUsername like " + "?" + (++index);
            bodyQueryStr += " )";

            bodyQueryStr += " and u.entryDate between " + "?" + (++index) + " and " + "?" + (++index);
            tailQueryStr = " order by u.entryDate ";

            if (searchCriteria.getSortBy() == 0) {
                tailQueryStr += "DESC";
            } else {
                tailQueryStr += "ASC";
            }

            String wholeQuertStr = headQueryStr + bodyQueryStr + tailQueryStr;
            query = em.createQuery(wholeQuertStr);

            index = 0;

            query.setParameter(++index, searchCriteria.getSearchText() + "%");
            query.setParameter(++index, searchCriteria.getSearchText() + "%");
            query.setParameter(++index, (long) searchCriteria.getFromDateTime());
            query.setParameter(++index, (long) searchCriteria.getToDateTime());

        }

        long count = (long) query.getSingleResult();
        return count;
    }

}
