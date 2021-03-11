/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.repositories.jpa.user;

import com.project.core.models.entities.user.DeviceUser;
import com.project.core.models.entities.user.User;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import com.project.core.repositories.user.DeviceUserRepo;
import com.project.rest.util.Global;


/**
 *
 * @author Samrit
 */
@Repository
public class JpaDeviceUserRepo implements DeviceUserRepo {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public DeviceUser createDeviceUser(DeviceUser data) {
        em.persist(data);
        return data;
    }

    @Override
    public DeviceUser findDeviceUser(Long id) {
        return em.find(DeviceUser.class, id);
    }

    @Override
    public DeviceUser findDeviceUserByUsername(String username) {
        Query query = em.createQuery("SELECT s FROM DeviceUser s where s.username=?1");
        query.setParameter(1, username);
        return (DeviceUser) query.getSingleResult();
    }

//        @Override
//    public DeviceUser findDeviceUserByUsernameAndTokenName(String username, String tokenName) {
//        Query query = em.createQuery("SELECT s FROM DeviceUser s where s.username=?1 and s.tokenName=?2");
//        query.setParameter(1, username);
//        query.setParameter(2, tokenName);
//        return (DeviceUser) query.getSingleResult();
//    }
//
//    @Override
//    public List<DeviceUser> findDeviceUsersByUsername(String username) {
//        Query query = em.createQuery("SELECT s FROM DeviceUser s where s.username=?1");
//        query.setParameter(1, username);
//        return query.getResultList();
//    }
//    @Override
//    public List<DeviceUser> findAllDeviceUser() {
//        Query query = em.createQuery("SELECT s FROM DeviceUser s");
//        return query.getResultList();
//    }
    @Override
    public List<DeviceUser> findAllDeviceUser(User loggedUser) {
//        Query query = em.createQuery("SELECT s FROM DeviceUser s");
//        return query.getResultList();

        Query query;
        if (loggedUser.getUserType() != null && (loggedUser.getUserType().trim().equalsIgnoreCase("SYSTEM_USER"))) {
            query = em.createQuery("SELECT u FROM DeviceUser u");
        } else {
            query = em.createQuery("SELECT u FROM DeviceUser u where u.adminUsername = ?1");
            query.setParameter(1, loggedUser.getUsername());
        }
        return query.getResultList();
    }

    @Override
    public DeviceUser updateDeviceUser(Long id, DeviceUser data) {
        DeviceUser entry = em.find(DeviceUser.class, id);
        entry.setUsername(data.getUsername());
        entry.setTokenName(data.getTokenName());
        entry.setEntryDate(data.getEntryDate());
        entry.setLastModifiedDate(data.getLastModifiedDate());
        entry.setActive(data.getActive());
        return entry;
    }

    @Override
    public DeviceUser deleteDeviceUser(Long id) {
        DeviceUser entry = em.find(DeviceUser.class, id);
        em.remove(entry);
        return entry;
    }

    @Override
    public int deleteDeviceUserByUsername(String username) {
        Query query = em.createQuery("Delete From DeviceUser where username=?1");
        query.setParameter(1, username);
        int result = query.executeUpdate();
        return result;
    }

    @Override
    public int deleteDuplicateDeviceUserOfUser(String username, String tokenName) {
        Query query = em.createQuery("Delete From DeviceUser where username!=?1 and tokenName=?2");
        query.setParameter(1, username);
        query.setParameter(2, tokenName);
        int result = query.executeUpdate();
        return result;
    }

    @Override
    public int deleteExpiredDeviceUser(Long fromDate) {
        Query query = em.createQuery("Delete From DeviceUser where lastModifiedDate<=?1");
        query.setParameter(1, fromDate);
        int result = query.executeUpdate();
        return result;
    }

}
