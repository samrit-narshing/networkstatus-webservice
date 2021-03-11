package com.project.core.repositories.jpa.user;

import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.project.core.models.entities.user.User;
import com.project.core.repositories.user.UserRepo;
import com.project.core.util.DateConverter;
import com.project.rest.util.Global;
import com.project.rest.util.searchcriteria.user.AnyUserSearchCriteria;
import com.project.rest.util.searchcriteria.user.UserSearchCriteria;
import java.util.Date;

/**
 *
 * @author Samrit
 */
@Repository
public class JpaUserRepo implements UserRepo {

    @PersistenceContext
    private EntityManager em;

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public User createUser(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public User findUser(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public User findUserByUsername(String username) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username=?1");
        query.setParameter(1, username);
        List<User> users = query.getResultList();
        if (users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }

    @Override
    public User findUserByUsernameByLoggedUser(String username, User loggedUser) {
        Query query;
        if (loggedUser.getUserType() != null && loggedUser.getUserType().trim().equalsIgnoreCase("SYSTEM_USER")) {
            query = em.createQuery("SELECT u FROM User u WHERE u.username=?1");
            query.setParameter(1, username);
        } else {
            query = em.createQuery("SELECT u FROM User u WHERE u.username=?1 and u.adminUsername = ?2");
            query.setParameter(1, username);
            query.setParameter(2, loggedUser.getAdminUsername());
        }

//        Query query = em.createQuery("SELECT u FROM User u WHERE u.username=?1 and u.adminUsername = ?2");
//        query.setParameter(1, username);
//        query.setParameter(2, loggedUser.getAdminUsername());
        List<User> users = query.getResultList();
        if (users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }

    @Override
    public User findValidUserByUsername(String username) {
//        Query query = em.createQuery("SELECT u FROM User u WHERE u.username=?1 and u.enabled=?2 and u.neverExpire=?3");
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username=?1 and u.enabled=?2 and (u.neverExpire=?3 or accountExpiration>?4)");
        query.setParameter(1, username);
        query.setParameter(2, true);
        query.setParameter(3, true);
        query.setParameter(4, DateConverter.getCurrentDateFormat2());
        List<User> users = query.getResultList();
        if (users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }

    public int TO_DATE() {
        return 2;
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username=?1 AND u.password=?2");
        query.setParameter(1, username);
        query.setParameter(2, password);
        List<User> users = query.getResultList();
        if (users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }

    @Override
    public List<User> findAllUsers() {
        Query query = em.createQuery("SELECT u FROM User u where u.userType='SYSTEM_USER'");
        return query.getResultList();
    }

    @Override
    public List<User> findUsersBySearchCriteria(UserSearchCriteria searchCriteria) {
        int maxResult = searchCriteria.getLimitResult();
        int adjustedPageN0 = Math.abs(searchCriteria.getPageNo() - 1);
        int startRow = adjustedPageN0 * maxResult;
        Query query;
        if (searchCriteria.getId() != null && searchCriteria.getId() > 0) {
            query = em.createQuery("SELECT u FROM User u where u.id = ?1 and u.userType='SYSTEM_USER' order by u.username ASC");
            query.setParameter(1, searchCriteria.getId());
        } else {

            String headQueryStr = "SELECT u FROM User u where 1=1 and u.userType='SYSTEM_USER'";
            String bodyQueryStr = "";
            String tailQueryStr = "";
            int index = 0;

            bodyQueryStr += " and (u.username like " + "?" + (++index);
            bodyQueryStr += " or u.firstName like " + "?" + (++index);
            bodyQueryStr += " or u.middleName like " + "?" + (++index);
            bodyQueryStr += " or u.lastName like " + "?" + (++index) + ")";

            tailQueryStr = " order by u.username ASC ";

            String wholeQuertStr = headQueryStr + bodyQueryStr + tailQueryStr;

            query = em.createQuery(wholeQuertStr);

            index = 0;

            query.setParameter(++index, searchCriteria.getUsername() + "%");
            query.setParameter(++index, searchCriteria.getUsername() + "%");
            query.setParameter(++index, searchCriteria.getUsername() + "%");
            query.setParameter(++index, searchCriteria.getUsername() + "%");

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
    public Long countTotalDocumentsBySearchCriteria(UserSearchCriteria searchCriteria) {
        Query query;
        if (searchCriteria.getId() != null && searchCriteria.getId() > 0) {
            query = em.createQuery("SELECT COUNT(u.username) FROM User u where u.id = ?1 and u.userType='SYSTEM_USER' order by u.username ASC");
            query.setParameter(1, searchCriteria.getId());
        } else {

            String headQueryStr = "SELECT COUNT(u.username)  FROM User u where 1=1 and u.userType='SYSTEM_USER'";
            String bodyQueryStr = "";
            String tailQueryStr = "";
            int index = 0;

            bodyQueryStr += " and (u.username like " + "?" + (++index);
            bodyQueryStr += " or u.firstName like " + "?" + (++index);
            bodyQueryStr += " or u.middleName like " + "?" + (++index);
            bodyQueryStr += " or u.lastName like " + "?" + (++index) + ")";

            tailQueryStr = " order by u.username ASC ";

            String wholeQuertStr = headQueryStr + bodyQueryStr + tailQueryStr;

            query = em.createQuery(wholeQuertStr);

            index = 0;

            query.setParameter(++index, searchCriteria.getUsername() + "%");
            query.setParameter(++index, searchCriteria.getUsername() + "%");
            query.setParameter(++index, searchCriteria.getUsername() + "%");
            query.setParameter(++index, searchCriteria.getUsername() + "%");

        }
//        else {
//            query = em.createQuery("SELECT COUNT(u.username) FROM User u where u.username like ?1 and u.userType='SYSTEM_USER' order by u.username ASC");
//            query.setParameter(1, "%" + searchCriteria.getUsername() + "%");
//        }

        long count = (long) query.getSingleResult();
        return count;
    }

    @Override
    public User updateUser(Long id, User data) {
        User entry = em.find(User.class, id);
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
        entry.setPasswordExpire(data.isPasswordExpire());

        entry.setLastUpdateByUserType(data.getLastUpdateByUserType());
        entry.setLastUpdateByUsername(data.getLastUpdateByUsername());
        entry.setLastModifiedUnixTime(data.getLastModifiedUnixTime());
        return entry;
    }

    @Override
    public User updateUserDetails(Long id, User data) {
        User entry = em.find(User.class, id);
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
        entry.setPasswordExpire(data.isPasswordExpire());

        entry.setLastUpdateByUserType(data.getLastUpdateByUserType());
        entry.setLastUpdateByUsername(data.getLastUpdateByUsername());
        entry.setLastModifiedUnixTime(data.getLastModifiedUnixTime());
        return entry;
    }

    @Override
    public User updateUserPassword(Long id, User data) {
        User entry = em.find(User.class, id);
        entry.setPassword(data.getPassword());
        entry.setLastUpdateByUserType(data.getLastUpdateByUserType());
        entry.setLastUpdateByUsername(data.getLastUpdateByUsername());
        entry.setLastModifiedUnixTime(data.getLastModifiedUnixTime());
        return entry;
    }

    @Override
    public User updateUserExpiryPassword(Long id, User data) {
        User entry = em.find(User.class, id);
        entry.setPassword(data.getPassword());
        entry.setPasswordExpire(data.isPasswordExpire());
        entry.setLastUpdateByUserType(data.getLastUpdateByUserType());
        entry.setLastUpdateByUsername(data.getLastUpdateByUsername());
        entry.setLastModifiedUnixTime(data.getLastModifiedUnixTime());
        return entry;
    }

    @Override
    public User updateUserProfileImage(Long id, User data) {
        User entry = em.find(User.class, id);
        entry.setProfileImage(data.getProfileImage());
        entry.setLastUpdateByUserType(data.getLastUpdateByUserType());
        entry.setLastUpdateByUsername(data.getLastUpdateByUsername());
        entry.setLastModifiedUnixTime(data.getLastModifiedUnixTime());
        return entry;
    }

    @Override
    public User deleteUser(Long id) {
        User entry = em.find(User.class, id);
        em.remove(entry);
        return entry;
    }

    @Override
    public List<User> findUsersWithPagination(int pageNumber, int pageSize) {
        Query query = em.createQuery("SELECT u FROM User where u.userType='SYSTEM_USER' order by u.username");
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        List<User> users = query.getResultList();
        return users;
    }

    @Override
    public List<User> findUsersWithPagination(String searchText, int pageNumber, int pageSize) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username LIKE ?1  and u.userType='SYSTEM_USER' order by u.username");
        query.setParameter(1, searchText + "%");
//         query.setParameter(1, searchText);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        List<User> users = query.getResultList();
        return users;
    }

    @Override
    public Integer countNoOfPagesForUsersWithPagination(final int pageSize) {
        Integer pages = 0;
        Integer counts = 0;
        Query query = em.createQuery("SELECT count(u) FROM User u  and u.userType='SYSTEM_USER' order by u.username");
        counts = ((Long) query.getSingleResult()).intValue();
        pages = counts / pageSize;
        return pages;
    }

    @Override
    public Integer countNoOfPagesForUsersWithPagination(String searchText, int pageSize) {
        Integer pages = 0;
        Integer counts = 0;
        Query query = em.createQuery("SELECT count(u) FROM User u WHERE u.username  and u.userType='SYSTEM_USER'  LIKE ?1 order by u.username");
        query.setParameter(1, searchText + "%");
        counts = ((Long) query.getSingleResult()).intValue();
        pages = counts / pageSize;
        return pages;
    }

    @Override
    public User updateUserEnabledStatus(Long id, User data) {
        User entry = em.find(User.class, id);
        entry.setEnabled(!entry.isEnabled());
        entry.setLastUpdateByUsername(data.getUsername());
        entry.setLastUpdateByUserType(data.getUserType());
        entry.setLastModifiedUnixTime(data.getLastModifiedUnixTime());
        return entry;
    }

    @Override
    public User updateUserStatusAsEnabled(Long id, User data) {
        User entry = em.find(User.class, id);
        entry.setEnabled(true);
        entry.setLastUpdateByUsername(data.getUsername());
        entry.setLastUpdateByUserType(data.getUserType());
        entry.setLastModifiedUnixTime(data.getLastModifiedUnixTime());
        return entry;
    }

    @Override
    public User updateUserStatusAsDisabled(Long id, User data) {
        User entry = em.find(User.class, id);
        entry.setEnabled(false);
        entry.setLastUpdateByUsername(data.getUsername());
        entry.setLastUpdateByUserType(data.getUserType());
        entry.setLastModifiedUnixTime(data.getLastModifiedUnixTime());
        return entry;
    }

    @Override
    public List<User> findAnyUsersWithPagination(AnyUserSearchCriteria anyUserSearchCriteria, User loggedUser) {

        Query query;

        if (loggedUser.getUserType() != null && loggedUser.getUserType().trim().equalsIgnoreCase("SYSTEM_USER")) {
            query = processToFindAllUsersBySearchCriteria_SYSTEM_USER(anyUserSearchCriteria, loggedUser);
        } else if (loggedUser.getUserType() != null && loggedUser.getUserType().trim().equalsIgnoreCase("SCHOOL_MANGER")) {
            query = processToFindAllUsersBySearchCriteria_SCHOOL_MANGER(anyUserSearchCriteria, loggedUser);
        } else if (loggedUser.getUserType() != null && loggedUser.getUserType().trim().equalsIgnoreCase("TRAVEL_MANGER")) {
            query = processToFindAllUsersBySearchCriteria_TRAVEL_MANGER(anyUserSearchCriteria, loggedUser);
        } else {
            String headQueryStr = "SELECT u FROM User u WHERE 1=1 ";
            String bodyQueryStr = "";
            String tailQueryStr = "";
            int index = 0;

            bodyQueryStr += " and (u.username like " + "?" + (++index);
            bodyQueryStr += " or u.firstName like " + "?" + (++index);
            bodyQueryStr += " or u.middleName like " + "?" + (++index);
            bodyQueryStr += " or u.lastName like " + "?" + (++index);
            bodyQueryStr += " or u.phoneNo like " + "?" + (++index);
            bodyQueryStr += " or u.mobileNo like " + "?" + (++index) + ")";

            bodyQueryStr += " and u.adminUsername =" + "?" + (++index);
            if (anyUserSearchCriteria.getExcludeUsernames() != null && !anyUserSearchCriteria.getExcludeUsernames().isEmpty()) {
                bodyQueryStr += " and u.username NOT IN (";
                String separator = "";  // separator here is your ","

                for (String username : anyUserSearchCriteria.getExcludeUsernames()) {
                    bodyQueryStr += separator + "?" + (++index);
                    separator = ",";
                }
                bodyQueryStr += ")";
            }

            tailQueryStr = " order by u.username ASC ";

            String wholeQuertStr = headQueryStr + bodyQueryStr + tailQueryStr;

            query = em.createQuery(wholeQuertStr);

            index = 0;

            query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
            query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
            query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
            query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
            query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
            query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");

            query.setParameter(++index, loggedUser.getAdminUsername());
            if (anyUserSearchCriteria.getExcludeUsernames() != null && !anyUserSearchCriteria.getExcludeUsernames().isEmpty()) {
                for (String username : anyUserSearchCriteria.getExcludeUsernames()) {
                    query.setParameter(++index, username);
                }
            }

//            query = em.createQuery("SELECT u FROM User u WHERE (u.username LIKE ?1 OR u.firstName LIKE ?2) AND u.adminUsername = ?3 order by u.username");
//            query.setParameter(1, anyUserSearchCriteria.getSearchedText() + "%");
//            query.setParameter(2, anyUserSearchCriteria.getSearchedText() + "%");
//            query.setParameter(3, loggedUser.getAdminUsername());
        }

        query.setFirstResult((anyUserSearchCriteria.getPageNo() - 1) * anyUserSearchCriteria.getLimitResult());
        query.setMaxResults(anyUserSearchCriteria.getLimitResult());
        List<User> users = query.getResultList();
        return users;
    }

    private Query processToFindAllUsersBySearchCriteria_SYSTEM_USER(AnyUserSearchCriteria anyUserSearchCriteria, User loggedUser) {

        Query query;
        String headQueryStr = "SELECT u FROM User u WHERE 1=1 ";
        String bodyQueryStr = "";
        String tailQueryStr = "";
        int index = 0;
        bodyQueryStr += " and (u.username like " + "?" + (++index);
        bodyQueryStr += " or u.firstName like " + "?" + (++index);
        bodyQueryStr += " or u.middleName like " + "?" + (++index);
        bodyQueryStr += " or u.lastName like " + "?" + (++index);
        bodyQueryStr += " or u.phoneNo like " + "?" + (++index);
        bodyQueryStr += " or u.mobileNo like " + "?" + (++index) + ")";

//            bodyQueryStr += " and u.adminUsername !=" + "?" + (++index);
        if (anyUserSearchCriteria.getExcludeUsernames() != null && !anyUserSearchCriteria.getExcludeUsernames().isEmpty()) {
            bodyQueryStr += " and u.username NOT IN (";
            String separator = "";  // separator here is your ","

            for (String username : anyUserSearchCriteria.getExcludeUsernames()) {
                bodyQueryStr += separator + "?" + (++index);
                separator = ",";
            }
            bodyQueryStr += ")";
        }

        if (Global.ONLY_FOR_SIAMSECURE) {
            bodyQueryStr += " and u.userType IN ('SYSTEM_USER','SCHOOL_MANGER','PROFESSOR','PARENT','STUDENT','MOTORIST')";
        }
        tailQueryStr = " order by u.username ASC ";

        String wholeQuertStr = headQueryStr + bodyQueryStr + tailQueryStr;

        query = em.createQuery(wholeQuertStr);

        index = 0;

        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");

//            query.setParameter(++index, loggedUser.getAdminUsername());
        if (anyUserSearchCriteria.getExcludeUsernames() != null && !anyUserSearchCriteria.getExcludeUsernames().isEmpty()) {
            for (String username : anyUserSearchCriteria.getExcludeUsernames()) {
                query.setParameter(++index, username);
            }
        }

//            query = em.createQuery("SELECT u FROM User u WHERE u.username LIKE ?1 OR u.firstName LIKE ?2 order by u.username");
//            query.setParameter(1, anyUserSearchCriteria.getSearchedText() + "%");
//            query.setParameter(2, anyUserSearchCriteria.getSearchedText() + "%");
        return query;
    }

    private Query processToFindAllUsersBySearchCriteria_SCHOOL_MANGER(AnyUserSearchCriteria anyUserSearchCriteria, User loggedUser) {

        Query query;
        String headQueryStr = "SELECT u FROM User u WHERE 1=1 ";
        String bodyQueryStr = "";
        String tailQueryStr = "";
        int index = 0;
        bodyQueryStr += " and (u.username like " + "?" + (++index);
        bodyQueryStr += " or u.firstName like " + "?" + (++index);
        bodyQueryStr += " or u.middleName like " + "?" + (++index);
        bodyQueryStr += " or u.lastName like " + "?" + (++index);
        bodyQueryStr += " or u.phoneNo like " + "?" + (++index);
        bodyQueryStr += " or u.mobileNo like " + "?" + (++index) + ")";

        bodyQueryStr += " and u.adminUsername =" + "?" + (++index);
        if (anyUserSearchCriteria.getExcludeUsernames() != null && !anyUserSearchCriteria.getExcludeUsernames().isEmpty()) {
            bodyQueryStr += " and u.username NOT IN (";
            String separator = "";  // separator here is your ","

            for (String username : anyUserSearchCriteria.getExcludeUsernames()) {
                bodyQueryStr += separator + "?" + (++index);
                separator = ",";
            }
            bodyQueryStr += ")";
        }

        tailQueryStr = " order by u.username ASC ";

        String wholeQuertStr = headQueryStr + bodyQueryStr + tailQueryStr;

        query = em.createQuery(wholeQuertStr);

        index = 0;

        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");

        query.setParameter(++index, loggedUser.getUsername());
        if (anyUserSearchCriteria.getExcludeUsernames() != null && !anyUserSearchCriteria.getExcludeUsernames().isEmpty()) {
            for (String username : anyUserSearchCriteria.getExcludeUsernames()) {
                query.setParameter(++index, username);
            }
        }
        return query;
    }

    private Query processToFindAllUsersBySearchCriteria_TRAVEL_MANGER(AnyUserSearchCriteria anyUserSearchCriteria, User loggedUser) {

        Query query;
        String headQueryStr = "SELECT u FROM User u WHERE 1=1 ";
        String bodyQueryStr = "";
        String tailQueryStr = "";
        int index = 0;
        bodyQueryStr += " and (u.username like " + "?" + (++index);
        bodyQueryStr += " or u.firstName like " + "?" + (++index);
        bodyQueryStr += " or u.middleName like " + "?" + (++index);
        bodyQueryStr += " or u.lastName like " + "?" + (++index);
        bodyQueryStr += " or u.phoneNo like " + "?" + (++index);
        bodyQueryStr += " or u.mobileNo like " + "?" + (++index) + ")";

        bodyQueryStr += " and u.adminUsername =" + "?" + (++index);
        if (anyUserSearchCriteria.getExcludeUsernames() != null && !anyUserSearchCriteria.getExcludeUsernames().isEmpty()) {
            bodyQueryStr += " and u.username NOT IN (";
            String separator = "";  // separator here is your ","

            for (String username : anyUserSearchCriteria.getExcludeUsernames()) {
                bodyQueryStr += separator + "?" + (++index);
                separator = ",";
            }
            bodyQueryStr += ")";
        }

        tailQueryStr = " order by u.username ASC ";

        String wholeQuertStr = headQueryStr + bodyQueryStr + tailQueryStr;

        query = em.createQuery(wholeQuertStr);

        index = 0;

        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");

        query.setParameter(++index, loggedUser.getUsername());
        if (anyUserSearchCriteria.getExcludeUsernames() != null && !anyUserSearchCriteria.getExcludeUsernames().isEmpty()) {
            for (String username : anyUserSearchCriteria.getExcludeUsernames()) {
                query.setParameter(++index, username);
            }
        }
        return query;
    }

    @Override
    public Long countNoOfPagesForAnyUsersWithPagination(AnyUserSearchCriteria anyUserSearchCriteria, User loggedUser) {
        Long pages = 0L;
        Integer counts = 0;

        Query query;
        if (loggedUser.getUserType() != null && loggedUser.getUserType().trim().equalsIgnoreCase("SYSTEM_USER")) {
            query = processToCountTotalDocumentsOfAnyUsersBySearchCriteria_SYSTEM_USER(anyUserSearchCriteria, loggedUser);
        } else if (loggedUser.getUserType() != null && loggedUser.getUserType().trim().equalsIgnoreCase("SCHOOL_MANGER")) {
            query = processToCountTotalDocumentsOfAnyUsersBySearchCriteria_SCHOOL_MANAGER(anyUserSearchCriteria, loggedUser);
        } else if (loggedUser.getUserType() != null && loggedUser.getUserType().trim().equalsIgnoreCase("TRAVEL_MANGER")) {
            query = processToCountTotalDocumentsOfAnyUsersBySearchCriteria_TRAVEL_MANAGER(anyUserSearchCriteria, loggedUser);
        } else {

            String headQueryStr = "SELECT count(u) FROM User u WHERE 1=1 ";
            String bodyQueryStr = "";
            String tailQueryStr = "";
            int index = 0;
            bodyQueryStr += " and (u.username like " + "?" + (++index);
            bodyQueryStr += " or u.firstName like " + "?" + (++index);
            bodyQueryStr += " or u.middleName like " + "?" + (++index);
            bodyQueryStr += " or u.lastName like " + "?" + (++index);
            bodyQueryStr += " or u.phoneNo like " + "?" + (++index);
            bodyQueryStr += " or u.mobileNo like " + "?" + (++index) + ")";

            bodyQueryStr += " and u.adminUsername =" + "?" + (++index);
            if (anyUserSearchCriteria.getExcludeUsernames() != null && !anyUserSearchCriteria.getExcludeUsernames().isEmpty()) {
                bodyQueryStr += " and u.username NOT IN (";
                String separator = "";  // separator here is your ","

                for (String username : anyUserSearchCriteria.getExcludeUsernames()) {
                    bodyQueryStr += separator + "?" + (++index);
                    separator = ",";
                }
                bodyQueryStr += ")";
            }

            tailQueryStr = " order by u.username ASC ";

            String wholeQuertStr = headQueryStr + bodyQueryStr + tailQueryStr;

            query = em.createQuery(wholeQuertStr);

            index = 0;

            query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
            query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
            query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
            query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
            query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
            query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");

            query.setParameter(++index, loggedUser.getAdminUsername());
            if (anyUserSearchCriteria.getExcludeUsernames() != null && !anyUserSearchCriteria.getExcludeUsernames().isEmpty()) {
                for (String username : anyUserSearchCriteria.getExcludeUsernames()) {
                    query.setParameter(++index, username);
                }
            }

//            query = em.createQuery("SELECT count(u) FROM User u WHERE (u.username LIKE ?1 OR u.firstName LIKE ?2) AND u.adminUsername = ?3 order by u.username");
//            query.setParameter(1, anyUserSearchCriteria.getSearchedText() + "%");
//            query.setParameter(2, anyUserSearchCriteria.getSearchedText() + "%");
//            query.setParameter(3, loggedUser.getAdminUsername());
        }

//        Query query = em.createQuery("SELECT count(u) FROM User u WHERE u.username LIKE ?1 OR u.firstName LIKE ?2 order by u.username");
//        query.setParameter(1, anyUserSearchCriteria.getSearchedText() + "%");
//        query.setParameter(2, anyUserSearchCriteria.getSearchedText() + "%");
        counts = ((Long) query.getSingleResult()).intValue();
        pages = (long) (counts / anyUserSearchCriteria.getLimitResult());
        return pages;
    }

    private Query processToCountTotalDocumentsOfAnyUsersBySearchCriteria_SYSTEM_USER(AnyUserSearchCriteria anyUserSearchCriteria, User loggedUser) {
        Query query;
        String headQueryStr = "SELECT count(u) FROM User u WHERE 1=1 ";
        String bodyQueryStr = "";
        String tailQueryStr = "";
        int index = 0;
        bodyQueryStr += " and (u.username like " + "?" + (++index);
        bodyQueryStr += " or u.firstName like " + "?" + (++index);
        bodyQueryStr += " or u.middleName like " + "?" + (++index);
        bodyQueryStr += " or u.lastName like " + "?" + (++index);
        bodyQueryStr += " or u.phoneNo like " + "?" + (++index);
        bodyQueryStr += " or u.mobileNo like " + "?" + (++index) + ")";

//            bodyQueryStr += " and u.adminUsername !=" + "?" + (++index);
        if (anyUserSearchCriteria.getExcludeUsernames() != null && !anyUserSearchCriteria.getExcludeUsernames().isEmpty()) {
            bodyQueryStr += " and u.username NOT IN (";
            String separator = "";  // separator here is your ","

            for (String username : anyUserSearchCriteria.getExcludeUsernames()) {
                bodyQueryStr += separator + "?" + (++index);
                separator = ",";
            }
            bodyQueryStr += ")";
        }

        if (Global.ONLY_FOR_SIAMSECURE) {
            bodyQueryStr += " and u.userType IN ('SYSTEM_USER','SCHOOL_MANGER','PROFESSOR','PARENT','STUDENT','MOTORIST')";
        }
        tailQueryStr = " order by u.username ASC ";

        String wholeQuertStr = headQueryStr + bodyQueryStr + tailQueryStr;

        query = em.createQuery(wholeQuertStr);

        index = 0;

        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");

//            query.setParameter(++index, loggedUser.getAdminUsername());
        if (anyUserSearchCriteria.getExcludeUsernames() != null && !anyUserSearchCriteria.getExcludeUsernames().isEmpty()) {
            for (String username : anyUserSearchCriteria.getExcludeUsernames()) {
                query.setParameter(++index, username);
            }
        }

//            query = em.createQuery("SELECT count(u) FROM User u WHERE u.username LIKE ?1 OR u.firstName LIKE ?2 order by u.username");
//            query.setParameter(1, anyUserSearchCriteria.getSearchedText() + "%");
//            query.setParameter(2, anyUserSearchCriteria.getSearchedText() + "%");
        return query;
    }

    private Query processToCountTotalDocumentsOfAnyUsersBySearchCriteria_SCHOOL_MANAGER(AnyUserSearchCriteria anyUserSearchCriteria, User loggedUser) {
        Query query;
        String headQueryStr = "SELECT count(u) FROM User u WHERE 1=1 ";
        String bodyQueryStr = "";
        String tailQueryStr = "";
        int index = 0;
        bodyQueryStr += " and (u.username like " + "?" + (++index);
        bodyQueryStr += " or u.firstName like " + "?" + (++index);
        bodyQueryStr += " or u.middleName like " + "?" + (++index);
        bodyQueryStr += " or u.lastName like " + "?" + (++index);
        bodyQueryStr += " or u.phoneNo like " + "?" + (++index);
        bodyQueryStr += " or u.mobileNo like " + "?" + (++index) + ")";

        bodyQueryStr += " and u.adminUsername =" + "?" + (++index);
        if (anyUserSearchCriteria.getExcludeUsernames() != null && !anyUserSearchCriteria.getExcludeUsernames().isEmpty()) {
            bodyQueryStr += " and u.username NOT IN (";
            String separator = "";  // separator here is your ","

            for (String username : anyUserSearchCriteria.getExcludeUsernames()) {
                bodyQueryStr += separator + "?" + (++index);
                separator = ",";
            }
            bodyQueryStr += ")";
        }

        tailQueryStr = " order by u.username ASC ";

        String wholeQuertStr = headQueryStr + bodyQueryStr + tailQueryStr;

        query = em.createQuery(wholeQuertStr);

        index = 0;

        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");

        query.setParameter(++index, loggedUser.getUsername());
        if (anyUserSearchCriteria.getExcludeUsernames() != null && !anyUserSearchCriteria.getExcludeUsernames().isEmpty()) {
            for (String username : anyUserSearchCriteria.getExcludeUsernames()) {
                query.setParameter(++index, username);
            }
        }
        return query;
    }

    private Query processToCountTotalDocumentsOfAnyUsersBySearchCriteria_TRAVEL_MANAGER(AnyUserSearchCriteria anyUserSearchCriteria, User loggedUser) {
        Query query;
        String headQueryStr = "SELECT count(u) FROM User u WHERE 1=1 ";
        String bodyQueryStr = "";
        String tailQueryStr = "";
        int index = 0;
        bodyQueryStr += " and (u.username like " + "?" + (++index);
        bodyQueryStr += " or u.firstName like " + "?" + (++index);
        bodyQueryStr += " or u.middleName like " + "?" + (++index);
        bodyQueryStr += " or u.lastName like " + "?" + (++index);
        bodyQueryStr += " or u.phoneNo like " + "?" + (++index);
        bodyQueryStr += " or u.mobileNo like " + "?" + (++index) + ")";

        bodyQueryStr += " and u.adminUsername =" + "?" + (++index);
        if (anyUserSearchCriteria.getExcludeUsernames() != null && !anyUserSearchCriteria.getExcludeUsernames().isEmpty()) {
            bodyQueryStr += " and u.username NOT IN (";
            String separator = "";  // separator here is your ","

            for (String username : anyUserSearchCriteria.getExcludeUsernames()) {
                bodyQueryStr += separator + "?" + (++index);
                separator = ",";
            }
            bodyQueryStr += ")";
        }

        tailQueryStr = " order by u.username ASC ";

        String wholeQuertStr = headQueryStr + bodyQueryStr + tailQueryStr;

        query = em.createQuery(wholeQuertStr);

        index = 0;

        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");
        query.setParameter(++index, anyUserSearchCriteria.getSearchedText() + "%");

        query.setParameter(++index, loggedUser.getUsername());
        if (anyUserSearchCriteria.getExcludeUsernames() != null && !anyUserSearchCriteria.getExcludeUsernames().isEmpty()) {
            for (String username : anyUserSearchCriteria.getExcludeUsernames()) {
                query.setParameter(++index, username);
            }
        }
        return query;
    }
}
