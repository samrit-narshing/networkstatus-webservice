package com.project.core.services.user;

import com.project.core.models.entities.user.DepartmentUser;
import com.project.core.models.entities.user.User;
import com.project.core.services.util.DepartmentUserList;
import com.project.rest.util.searchcriteria.user.DepartmentUserSearchCriteria;

/**
 *
 * @author Samrit
 */
public interface DepartmentUserService {

    public DepartmentUser createDepartmentUser(DepartmentUser departmentUser) throws Exception;

    public DepartmentUser findDepartmentUser(Long id) throws Exception;

    public DepartmentUser findDepartmentUserByUsername(String username) throws Exception;

    public DepartmentUser findValidDepartmentUserByUsername(String username) throws Exception;

    public DepartmentUser findDepartmentUserByUsernameAndPassword(String username, String password) throws Exception;

    public DepartmentUserList findDepartmentUserListWithAllDepartmentUsers() throws Exception;

    public DepartmentUserList findAllValidDepartmentUsers(User loggedUser) throws Exception;

    public DepartmentUserList findDepartmentUserListBySearchCriteria(DepartmentUserSearchCriteria searchCriteria, User loggedUser) throws Exception;

    public Long countTotalDocumentsBySearchCriteria(DepartmentUserSearchCriteria searchCriteria, User loggedUser) throws Exception;

    public DepartmentUser updateDepartmentUser(Long id, DepartmentUser data) throws Exception;

    public DepartmentUser updateDepartmentUserDetails(Long id, DepartmentUser data) throws Exception;

    public DepartmentUser updateDepartmentUserPassword(Long id, DepartmentUser data) throws Exception;

    public DepartmentUser updateDepartmentUserUsername(Long id, DepartmentUser data) throws Exception;

    public DepartmentUser updateDepartmentUserProfileImage(Long id, DepartmentUser data) throws Exception;

    public DepartmentUser deleteDepartmentUser(Long id) throws Exception;

    public DepartmentUser updateDepartmentUserEnabledStatus(Long id, User data) throws Exception;

    public DepartmentUser updateDepartmentUserStatusAsEnabled(Long id, User data) throws Exception;

    public DepartmentUser updateDepartmentUserStatusAsDisabled(Long id, User data) throws Exception;

}
