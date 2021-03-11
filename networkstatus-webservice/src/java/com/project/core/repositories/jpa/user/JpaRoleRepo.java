package com.project.core.repositories.jpa.user;

import com.project.core.models.entities.user.Role;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import com.project.core.repositories.user.RoleRepo;
import com.project.rest.util.searchcriteria.user.RoleSearchCriteria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

/**
 *
 * @author Samrit
 */
@Repository
public class JpaRoleRepo implements RoleRepo {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Role createRole(Role role) {

        String query = "Insert into role (id,name,description,enabled) values (?,?,?,?)";
        jdbcTemplate.update((Connection paramConnection) -> {
            PreparedStatement localPreparedStatement = paramConnection.prepareStatement(query);
            localPreparedStatement.setLong(1, role.getId());
            localPreparedStatement.setString(2, role.getName());
            localPreparedStatement.setString(3, role.getDescription());
            localPreparedStatement.setBoolean(4, true);
            return localPreparedStatement;
        });

        Role fineRole = findRole(role.getId());
        return fineRole;
    }

    @Override
    public Role createRoleByObject(Role user) {
        em.persist(user);
        return user;
    }

    @Override
    public Role updateRole(Long id, Role data) {
        Role entry = em.find(Role.class, id);
        entry.setDescription(data.getDescription());
        entry.setName(data.getName());
        entry.setEnabled(data.isEnabled());
        em.flush();
        return entry;
    }

    @Override
    public List<String> findRoleByUsername(String username) {
        Query query = em.createQuery("select a.role from UserRole a, User b where b.userName=?1 and a.userid=b.userId");
        query.setParameter(1, username);
        List<String> roles = query.getResultList();
        if (roles.size() == 0) {
            return null;
        } else {
            return roles;
        }
    }

    @Override
    public Role findRoleByName(String name) {
        Query query = em.createQuery("SELECT r FROM Role r where r.name=?1");
        query.setParameter(1, name);
        List<Role> users = query.getResultList();
        if (users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }

    @Override
    public Role findRole(Long id) {
        return em.find(Role.class, id);
    }

    @Override
    public List<Role> findAllRoles() {
        Query query = em.createQuery("SELECT r FROM Role r");
        return query.getResultList();
    }

    @Override
    public List<Role> findAllRolesForUser() {
        Query query = em.createQuery("SELECT r FROM Role r where r.id in (1,2)");
//           Query query = em.createQuery("SELECT r FROM Role r");
        return query.getResultList();
    }

    @Override
    public List<Role> findAllRolesForStudent() {
//        Query query = em.createQuery("SELECT r FROM Role r where r.id in (3,4)");
        Query query = em.createQuery("SELECT r FROM Role r where r.id in (3)");
        return query.getResultList();
    }

    @Override
    public List<Role> findAllRolesForParent() {
//        Query query = em.createQuery("SELECT r FROM Role r where r.id in (3,5)");
        Query query = em.createQuery("SELECT r FROM Role r where r.id in (3)");
        return query.getResultList();
    }

    @Override
    public List<Role> findAllRolesForProfessor() {
//        Query query = em.createQuery("SELECT r FROM Role r where r.id in (3,6)");
        Query query = em.createQuery("SELECT r FROM Role r where r.id in (3)");
        return query.getResultList();
    }

    @Override
    public List<Role> findAllRolesForMotorist() {
//        Query query = em.createQuery("SELECT r FROM Role r where r.id in (3,7)");
        Query query = em.createQuery("SELECT r FROM Role r where r.id in (3)");
        return query.getResultList();
    }

    @Override
    public List<Role> findAllRolesForFriend() {
//        Query query = em.createQuery("SELECT r FROM Role r where r.id in (3,7)");
        Query query = em.createQuery("SELECT r FROM Role r where r.id in (3)");
        return query.getResultList();
    }

    @Override
    public List<Role> findAllRolesForTraveler() {
//        Query query = em.createQuery("SELECT r FROM Role r where r.id in (3,8)");
        Query query = em.createQuery("SELECT r FROM Role r where r.id in (3)");
        return query.getResultList();
    }

    @Override
    public List<Role> findAllRolesForTravelManager() {
//        Query query = em.createQuery("SELECT r FROM Role r where r.id in (3,8)");
        Query query = em.createQuery("SELECT r FROM Role r where r.id in (3)");
        return query.getResultList();
    }

    @Override
    public List<Role> findAllRolesForTravelGuide() {
//        Query query = em.createQuery("SELECT r FROM Role r where r.id in (3,8)");
        Query query = em.createQuery("SELECT r FROM Role r where r.id in (3)");
        return query.getResultList();
    }

    @Override
    public List<Role> findAllRolesForSchoolManager() {
//        Query query = em.createQuery("SELECT r FROM Role r where r.id in (3,8)");
        Query query = em.createQuery("SELECT r FROM Role r where r.id in (3)");
        return query.getResultList();
    }

    @Override
    public List<Role> findAllRolesForDepartmentUser() {
//        Query query = em.createQuery("SELECT r FROM Role r where r.id in (3,8)");
        Query query = em.createQuery("SELECT r FROM Role r where r.id >=100 and r.enabled=true");
        return query.getResultList();
    }

    @Override
    public Role updateEnabledStatus(Long id) {
        Role entry = em.find(Role.class, id);
        entry.setEnabled(!entry.isEnabled());
        return entry;
    }

    @Override
    public Role updateStatusAsEnabled(Long id) {
        Role entry = em.find(Role.class, id);
        entry.setEnabled(true);
        return entry;
    }

    @Override
    public Role updateStatusAsDisabled(Long id) {
        Role entry = em.find(Role.class, id);
        entry.setEnabled(false);
        return entry;
    }

    @Override
    public List<Role> findBySearchCriteriaForNetwork(RoleSearchCriteria searchCriteria) {
        int maxResult = searchCriteria.getLimitResult();
        int adjustedPageN0 = Math.abs(searchCriteria.getPageNo() - 1);
        int startRow = adjustedPageN0 * maxResult;
        Query query;
        if (searchCriteria.getId() != null && searchCriteria.getId() > 0) {
            query = em.createQuery("SELECT u FROM Role u where u.id = ?1 and  u.id >=100 order by u.name ASC");
            query.setParameter(1, searchCriteria.getId());
        } else {

            String headQueryStr = "SELECT u FROM Role u where 1=1 and u.id >=100";
            String bodyQueryStr = "";
            String tailQueryStr = "";
            int index = 0;

            bodyQueryStr += " and (u.name like " + "?" + (++index);
            bodyQueryStr += " or u.description like " + "?" + (++index) + ")";

            tailQueryStr = " order by u.name ASC ";

            String wholeQuertStr = headQueryStr + bodyQueryStr + tailQueryStr;

            query = em.createQuery(wholeQuertStr);

            index = 0;

            query.setParameter(++index, searchCriteria.getSearchText() + "%");
            query.setParameter(++index, searchCriteria.getSearchText() + "%");

        }
//        else {
//            query = em.createQuery("SELECT u FROM User u where u.username like ?1 and u.userType='SYSTEM_USER' order by u.username ASC");
//            query.setParameter(1, "%" + searchCriteria.getUsername() + "%");
//        }
        query.setFirstResult(startRow)
                .setMaxResults(maxResult);

        return query.getResultList();
    }

    @Override
    public Long countTotalDocumentsBySearchCriteriaForNetwork(RoleSearchCriteria searchCriteria) {
        Query query;
        if (searchCriteria.getId() != null && searchCriteria.getId() > 0) {
            query = em.createQuery("SELECT COUNT(u.name) FROM Role u where u.id = ?1 and  u.id >=100 order by u.name ASC");
            query.setParameter(1, searchCriteria.getId());
        } else {

            String headQueryStr = "SELECT COUNT(u.name) FROM Role u where 1=1 and u.id >=100";
            String bodyQueryStr = "";
            String tailQueryStr = "";
            int index = 0;

            bodyQueryStr += " and (u.name like " + "?" + (++index);
            bodyQueryStr += " or u.description like " + "?" + (++index) + ")";

            tailQueryStr = " order by u.name ASC ";

            String wholeQuertStr = headQueryStr + bodyQueryStr + tailQueryStr;

            query = em.createQuery(wholeQuertStr);

            index = 0;

            query.setParameter(++index, searchCriteria.getSearchText() + "%");
            query.setParameter(++index, searchCriteria.getSearchText() + "%");

        }
//        else {
//            query = em.createQuery("SELECT COUNT(u.username) FROM User u where u.username like ?1 and u.userType='SYSTEM_USER' order by u.username ASC");
//            query.setParameter(1, "%" + searchCriteria.getUsername() + "%");
//        }

        long count = (long) query.getSingleResult();
        return count;
    }

    @Override
    public int initializeHibernateCounterForRole() {
        String query = "update hibernate_roles_sequence set next_val=?";
        int status = jdbcTemplate.update((Connection paramConnection) -> {
            PreparedStatement localPreparedStatement = paramConnection.prepareStatement(query);
            localPreparedStatement.setLong(1, 1000);
            return localPreparedStatement;
        });

        return status;
    }
}
