package com.project.core.repositories.user;

import com.project.core.models.entities.user.DepartmentUser;
import com.project.core.models.entities.user.User;
import com.project.rest.util.searchcriteria.user.DepartmentUserSearchCriteria;
import java.util.List;

/**
 *
 * @author Samrit
 */
public interface DepartmentUserRepo {

    public DepartmentUser createDepartmentUser(DepartmentUser departmentUser);

    public DepartmentUser findDepartmentUser(Long id);

    public DepartmentUser findDepartmentUserByUsername(String username);

    public DepartmentUser findValidDepartmentUserByUsername(String username);

    public DepartmentUser findDepartmentUserByUsernameAndPassword(String username, String password);

    public List<DepartmentUser> findAllDepartmentUsers();

    public List<DepartmentUser> findAllValidDepartmentUsers(User loggedUser);

    public List<DepartmentUser> findDepartmentUsersBySearchCriteria(DepartmentUserSearchCriteria searchCriteria, User loggedUser);

    public Long countTotalDocumentsBySearchCriteria(DepartmentUserSearchCriteria searchCriteria, User loggedUser);

    public DepartmentUser updateDepartmentUser(Long id, DepartmentUser data);

    public DepartmentUser updateDepartmentUserDetails(Long id, DepartmentUser data);

    public DepartmentUser updateDepartmentUserPassword(Long id, DepartmentUser data);

    public DepartmentUser updateDepartmentUserUsername(Long id, DepartmentUser data);

    public DepartmentUser updateDepartmentUserProfileImage(Long id, DepartmentUser data);

    public DepartmentUser deleteDepartmentUser(Long id);

    public DepartmentUser updateDepartmentUserEnabledStatus(Long id, User data);

    public DepartmentUser updateDepartmentUserStatusAsEnabled(Long id, User data);

    public DepartmentUser updateDepartmentUserStatusAsDisabled(Long id, User data);
}
