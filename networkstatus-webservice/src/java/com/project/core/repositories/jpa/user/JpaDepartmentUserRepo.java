package com.project.core.repositories.jpa.user;

import com.project.core.models.entities.user.DepartmentUser;
import com.project.core.models.entities.user.User;
import com.project.core.repositories.user.DepartmentUserRepo;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import com.project.core.util.DateConverter;
import com.project.rest.util.searchcriteria.user.DepartmentUserSearchCriteria;

/**
 *
 * @author Samrit
 */
@Repository
public class JpaDepartmentUserRepo implements DepartmentUserRepo {

    @PersistenceContext
    private EntityManager em;

    @Override
    public DepartmentUser createDepartmentUser(DepartmentUser departmentUser) {
        em.persist(departmentUser);
        em.flush();
        return departmentUser;
    }

    @Override
    public DepartmentUser findDepartmentUser(Long id) {
        return em.find(DepartmentUser.class, id);
    }

    @Override
    public DepartmentUser findDepartmentUserByUsername(String username) {
        Query query = em.createQuery("SELECT u FROM DepartmentUser u WHERE u.username=?1");
        query.setParameter(1, username);
        List<DepartmentUser> departmentUsers = query.getResultList();
        if (departmentUsers.isEmpty()) {
            return null;
        } else {
            return departmentUsers.get(0);
        }
    }

    @Override
    public DepartmentUser findValidDepartmentUserByUsername(String username) {
//        Query query = em.createQuery("SELECT u FROM User u WHERE u.username=?1 and u.enabled=?2 and u.neverExpire=?3");
        Query query = em.createQuery("SELECT u FROM DepartmentUser u WHERE u.username=?1 and u.enabled=?2 and (u.neverExpire=?3 or accountExpiration>?4)");
        query.setParameter(1, username);
        query.setParameter(2, true);
        query.setParameter(3, true);
        query.setParameter(4, DateConverter.getCurrentDateFormat2());
        List<DepartmentUser> departmentUsers = query.getResultList();
        if (departmentUsers.isEmpty()) {
            return null;
        } else {
            return departmentUsers.get(0);
        }
    }

    @Override
    public DepartmentUser findDepartmentUserByUsernameAndPassword(String username, String password) {
        Query query = em.createQuery("SELECT u FROM DepartmentUser u WHERE u.username=?1 AND u.password=?2");
        query.setParameter(1, username);
        query.setParameter(2, password);
        List<DepartmentUser> departmentUsers = query.getResultList();
        if (departmentUsers.isEmpty()) {
            return null;
        } else {
            return departmentUsers.get(0);
        }
    }

    @Override
    public List<DepartmentUser> findAllDepartmentUsers() {
        Query query = em.createQuery("SELECT u FROM DepartmentUser u");
        return query.getResultList();
    }

    @Override
    public List<DepartmentUser> findAllValidDepartmentUsers(User loggedUser) {
        Query query = em.createQuery("SELECT u FROM DepartmentUser u where u.adminUsername = ?1 and u.enabled = ?2 ");
        query.setParameter(1, loggedUser.getAdminUsername());
        query.setParameter(2, true);
        return query.getResultList();
    }

    @Override
    public List<DepartmentUser> findDepartmentUsersBySearchCriteria(DepartmentUserSearchCriteria searchCriteria, User loggedUser) {
        int maxResult = searchCriteria.getLimitResult();
        int adjustedPageN0 = Math.abs(searchCriteria.getPageNo() - 1);
        int startRow = adjustedPageN0 * maxResult;
        Query query;
        if (searchCriteria.getId() != null && searchCriteria.getId() > 0) {
            query = em.createQuery("SELECT u FROM DepartmentUser u where u.id = ?1 order by u.username ASC");
            query.setParameter(1, searchCriteria.getId());
        } else if (loggedUser.getUserType() != null && loggedUser.getUserType().trim().equalsIgnoreCase("SYSTEM_USER")) {

            String headQueryStr = "SELECT u FROM DepartmentUser u where 1=1 ";
            String bodyQueryStr = "";
            String tailQueryStr = "";
            int index = 0;

            bodyQueryStr += " and (u.username like " + "?" + (++index);
            bodyQueryStr += " or u.firstName like " + "?" + (++index);
            bodyQueryStr += " or u.middleName like " + "?" + (++index);
            bodyQueryStr += " or u.lastName like " + "?" + (++index) + ")";

//            tailQueryStr = " order by u.username ASC ";
            tailQueryStr = " order by u.firstName ";

            if (searchCriteria.getSortBy() == 0) {
                tailQueryStr += "DESC";
            } else {
                tailQueryStr += "ASC";
            }

            String wholeQuertStr = headQueryStr + bodyQueryStr + tailQueryStr;

            query = em.createQuery(wholeQuertStr);

            index = 0;

            query.setParameter(++index, searchCriteria.getUsername() + "%");
            query.setParameter(++index, searchCriteria.getUsername() + "%");
            query.setParameter(++index, searchCriteria.getUsername() + "%");
            query.setParameter(++index, searchCriteria.getUsername() + "%");

        } else if (loggedUser.getUserType() != null && loggedUser.getUserType().trim().equalsIgnoreCase("SCHOOL_MANGER")) {
            String headQueryStr = "SELECT u FROM DepartmentUser u where 1=1 ";
            String bodyQueryStr = "";
            String tailQueryStr = "";
            int index = 0;

            bodyQueryStr += " and (u.username like " + "?" + (++index);
            bodyQueryStr += " or u.firstName like " + "?" + (++index);
            bodyQueryStr += " or u.middleName like " + "?" + (++index);
            bodyQueryStr += " or u.lastName like " + "?" + (++index) + ")";
            bodyQueryStr += " and u.adminUsername = " + "?" + (++index);
//            tailQueryStr = " order by u.username ASC ";
            tailQueryStr = " order by u.firstName ";

            if (searchCriteria.getSortBy() == 0) {
                tailQueryStr += "DESC";
            } else {
                tailQueryStr += "ASC";
            }

            String wholeQuertStr = headQueryStr + bodyQueryStr + tailQueryStr;

            query = em.createQuery(wholeQuertStr);

            index = 0;

            query.setParameter(++index, searchCriteria.getUsername() + "%");
            query.setParameter(++index, searchCriteria.getUsername() + "%");
            query.setParameter(++index, searchCriteria.getUsername() + "%");
            query.setParameter(++index, searchCriteria.getUsername() + "%");
            query.setParameter(++index, loggedUser.getUsername());
        } else {
            query = em.createQuery("SELECT u FROM DepartmentUser u where 1=2");
        }
        query.setFirstResult(startRow)
                .setMaxResults(maxResult);

        return query.getResultList();
    }

    @Override
    public Long countTotalDocumentsBySearchCriteria(DepartmentUserSearchCriteria searchCriteria, User loggedUser) {
        Query query;
        if (searchCriteria.getId() != null && searchCriteria.getId() > 0) {
            query = em.createQuery("SELECT COUNT(u.username) FROM DepartmentUser u where u.id = ?1 order by u.username ASC");
            query.setParameter(1, searchCriteria.getId());
        } else if (loggedUser.getUserType() != null && loggedUser.getUserType().trim().equalsIgnoreCase("SYSTEM_USER")) {

            String headQueryStr = "SELECT COUNT(u.username) FROM DepartmentUser u where 1=1 ";
            String bodyQueryStr = "";
            String tailQueryStr = "";
            int index = 0;

            bodyQueryStr += " and (u.username like " + "?" + (++index);
            bodyQueryStr += " or u.firstName like " + "?" + (++index);
            bodyQueryStr += " or u.middleName like " + "?" + (++index);
            bodyQueryStr += " or u.lastName like " + "?" + (++index) + ")";

//            tailQueryStr = " order by u.username ASC ";
            tailQueryStr = " order by u.firstName ";

            if (searchCriteria.getSortBy() == 0) {
                tailQueryStr += "DESC";
            } else {
                tailQueryStr += "ASC";
            }

            String wholeQuertStr = headQueryStr + bodyQueryStr + tailQueryStr;

            query = em.createQuery(wholeQuertStr);

            index = 0;

            query.setParameter(++index, searchCriteria.getUsername() + "%");
            query.setParameter(++index, searchCriteria.getUsername() + "%");
            query.setParameter(++index, searchCriteria.getUsername() + "%");
            query.setParameter(++index, searchCriteria.getUsername() + "%");

        } else if (loggedUser.getUserType() != null && loggedUser.getUserType().trim().equalsIgnoreCase("SCHOOL_MANGER")) {
            String headQueryStr = "SELECT COUNT(u.username) FROM DepartmentUser u where 1=1 ";
            String bodyQueryStr = "";
            String tailQueryStr = "";
            int index = 0;

            bodyQueryStr += " and (u.username like " + "?" + (++index);
            bodyQueryStr += " or u.firstName like " + "?" + (++index);
            bodyQueryStr += " or u.middleName like " + "?" + (++index);
            bodyQueryStr += " or u.lastName like " + "?" + (++index) + ")";
            bodyQueryStr += " and u.adminUsername = " + "?" + (++index);

//            tailQueryStr = " order by u.username ASC ";
            tailQueryStr = " order by u.firstName ";

            if (searchCriteria.getSortBy() == 0) {
                tailQueryStr += "DESC";
            } else {
                tailQueryStr += "ASC";
            }

            String wholeQuertStr = headQueryStr + bodyQueryStr + tailQueryStr;

            query = em.createQuery(wholeQuertStr);

            index = 0;

            query.setParameter(++index, searchCriteria.getUsername() + "%");
            query.setParameter(++index, searchCriteria.getUsername() + "%");
            query.setParameter(++index, searchCriteria.getUsername() + "%");
            query.setParameter(++index, searchCriteria.getUsername() + "%");
            query.setParameter(++index, loggedUser.getUsername());
        } else {
            query = em.createQuery("SELECT COUNT(u.username) FROM DepartmentUser u where 1=2");
        }
        long count = (long) query.getSingleResult();
        return count;
    }

    @Override
    public DepartmentUser updateDepartmentUser(Long id,DepartmentUser data) {
       DepartmentUser entry = em.find(DepartmentUser.class, id);
        entry.setPassword(data.getPassword());
        entry.setRoles(data.getRoles());
        entry.setEnabled(data.isEnabled());
        entry.setNeverExpire(data.isNeverExpire());
        entry.setAccountExpiration(data.getAccountExpiration());
        entry.setSessionTimeout(data.getSessionTimeout());
        entry.setAddress1(data.getAddress1());
        entry.setAddress2(data.getAddress2());
        entry.setPhoneNo(data.getPhoneNo());
        entry.setMobileNo(data.getMobileNo());
        entry.setEmail(data.getEmail());
        entry.setGender(data.getGender());
        entry.setFirstName(data.getFirstName());
        entry.setMiddleName(data.getMiddleName());
        entry.setLastName(data.getLastName());

        entry.setLastModifiedUnixTime(data.getLastModifiedUnixTime());
        entry.setDepartmentUserField(data.getDepartmentUserField());
        entry.setSocialID(data.getSocialID());
        entry.setLastUpdateByUserType(data.getLastUpdateByUserType());
        entry.setLastUpdateByUsername(data.getLastUpdateByUsername());
        entry.setLastModifiedUnixTime(data.getLastModifiedUnixTime());
        return entry;
    }

    @Override
    public DepartmentUser updateDepartmentUserDetails(Long id,DepartmentUser data) {
       DepartmentUser entry = em.find(DepartmentUser.class, id);
        entry.setEnabled(data.isEnabled());
        entry.setNeverExpire(data.isNeverExpire());
        entry.setAccountExpiration(data.getAccountExpiration());
        entry.setSessionTimeout(data.getSessionTimeout());
        entry.setRoles(data.getRoles());
        entry.setAddress1(data.getAddress1());
        entry.setAddress2(data.getAddress2());
        entry.setPhoneNo(data.getPhoneNo());
        entry.setMobileNo(data.getMobileNo());
        entry.setEmail(data.getEmail());
        entry.setGender(data.getGender());
        entry.setFirstName(data.getFirstName());
        entry.setMiddleName(data.getMiddleName());
        entry.setLastName(data.getLastName());

        entry.setLastModifiedUnixTime(data.getLastModifiedUnixTime());
        entry.setDepartmentUserField(data.getDepartmentUserField());
        entry.setSocialID(data.getSocialID());
        entry.setLastUpdateByUserType(data.getLastUpdateByUserType());
        entry.setLastUpdateByUsername(data.getLastUpdateByUsername());
        entry.setLastModifiedUnixTime(data.getLastModifiedUnixTime());
        return entry;
    }

    @Override
    public DepartmentUser updateDepartmentUserPassword(Long id,DepartmentUser data) {
       DepartmentUser entry = em.find(DepartmentUser.class, id);
        entry.setLastUpdateByUserType(data.getLastUpdateByUserType());
        entry.setLastUpdateByUsername(data.getLastUpdateByUsername());
        entry.setLastModifiedUnixTime(data.getLastModifiedUnixTime());
        entry.setPassword(data.getPassword());
        return entry;
    }

    @Override
    public DepartmentUser updateDepartmentUserUsername(Long id,DepartmentUser data) {
       DepartmentUser entry = em.find(DepartmentUser.class, id);
        entry.setLastUpdateByUserType(data.getLastUpdateByUserType());
        entry.setLastUpdateByUsername(data.getLastUpdateByUsername());
        entry.setLastModifiedUnixTime(data.getLastModifiedUnixTime());
        entry.setUsername(data.getUsername());
        em.flush();
        return entry;
    }

    @Override
    public DepartmentUser updateDepartmentUserProfileImage(Long id,DepartmentUser data) {
       DepartmentUser entry = em.find(DepartmentUser.class, id);
        entry.setLastUpdateByUserType(data.getLastUpdateByUserType());
        entry.setLastUpdateByUsername(data.getLastUpdateByUsername());
        entry.setLastModifiedUnixTime(data.getLastModifiedUnixTime());
        entry.setProfileImage(data.getProfileImage());
        return entry;
    }

    @Override
    public DepartmentUser deleteDepartmentUser(Long id) {
       DepartmentUser entry = em.find(DepartmentUser.class, id);
        em.remove(entry);
        return entry;
    }

    @Override
    public DepartmentUser updateDepartmentUserEnabledStatus(Long id, User data) {
       DepartmentUser entry = em.find(DepartmentUser.class, id);
        entry.setEnabled(!entry.isEnabled());
        entry.setLastUpdateByUsername(data.getUsername());
        entry.setLastUpdateByUserType(data.getUserType());
        entry.setLastModifiedUnixTime(DateConverter.getCurrentConvertedDateAndTimeInUnixDate());
        return entry;
    }

    @Override
    public DepartmentUser updateDepartmentUserStatusAsEnabled(Long id, User data) {
       DepartmentUser entry = em.find(DepartmentUser.class, id);
        entry.setEnabled(true);
        entry.setLastUpdateByUsername(data.getUsername());
        entry.setLastUpdateByUserType(data.getUserType());
        entry.setLastModifiedUnixTime(DateConverter.getCurrentConvertedDateAndTimeInUnixDate());
        return entry;
    }

    @Override
    public DepartmentUser updateDepartmentUserStatusAsDisabled(Long id, User data) {
       DepartmentUser entry = em.find(DepartmentUser.class, id);
        entry.setEnabled(false);
        entry.setLastUpdateByUsername(data.getUsername());
        entry.setLastUpdateByUserType(data.getUserType());
        entry.setLastModifiedUnixTime(DateConverter.getCurrentConvertedDateAndTimeInUnixDate());
        return entry;
    }
}
