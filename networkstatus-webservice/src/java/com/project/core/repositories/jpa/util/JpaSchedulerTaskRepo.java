/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.repositories.jpa.util;

import com.project.core.models.entities.util.SchedulerTask;
import java.util.List;
import com.project.core.repositories.util.SchedulerTaskRepo;
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
public class JpaSchedulerTaskRepo implements SchedulerTaskRepo {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public SchedulerTask createSchedulerTask(SchedulerTask data) {
        em.persist(data);
        return data;
    }

    @Override
    public SchedulerTask findSchedulerTask(Long id) {
        return em.find(SchedulerTask.class, id);
    }

    @Override
    public SchedulerTask findSchedulerTaskByName(String name) {
        Query query = em.createQuery("SELECT s FROM SchedulerTask s where s.name=?1");
        query.setParameter(1, name);
        return (SchedulerTask) query.getSingleResult();

//          CriteriaBuilder builder= em.getCriteriaBuilder();
//          CriteriaQuery criteriaQuery = builder.createQuery(SchedulerTask.class);
//          Root<SchedulerTask> from = criteriaQuery.from(SchedulerTask.class);
//          em.
//          criteriaQuery.add(Restrictions.like("firstName", "zara%"))
    }

    @Override
    public List<SchedulerTask> findAllSchedulerTask() {
        Query query = em.createQuery("SELECT s FROM SchedulerTask s");
        return query.getResultList();
    }

    @Override
    public SchedulerTask updateSchedulerTask(Long id, SchedulerTask data) {
        SchedulerTask entry = em.find(SchedulerTask.class, id);
        entry.setName(data.getName());
        entry.setActive(data.getActive());
        return entry;
    }

    @Override
    public SchedulerTask deleteSchedulerTask(Long id) {
        SchedulerTask entry = em.find(SchedulerTask.class, id);
        em.remove(entry);
        return entry;
    }

    @Override
    public int deleteSchedulerTaskByName(String name) {
        Query query = em.createQuery("Delete From SchedulerTask where name=?1");
        query.setParameter(1, name);
        int result = query.executeUpdate();
        return result;
    }

}
