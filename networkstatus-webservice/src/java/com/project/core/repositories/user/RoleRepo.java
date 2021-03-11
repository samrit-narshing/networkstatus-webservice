package com.project.core.repositories.user;

import com.project.core.models.entities.user.Role;
import com.project.rest.util.searchcriteria.user.RoleSearchCriteria;
import java.util.List;

/**
 *
 * @author Samrit
 */
public interface RoleRepo {

    public Role createRole(Role role);

    public Role createRoleByObject(Role user);

    public Role updateRole(Long id, Role data);

    public List<String> findRoleByUsername(String username);

    public Role findRoleByName(String name);

    public Role findRole(Long id);

    public List<Role> findAllRoles();

    public List<Role> findAllRolesForUser();

    public List<Role> findAllRolesForDepartmentUser();

    public List<Role> findAllRolesForStudent();

    public List<Role> findAllRolesForParent();

    public List<Role> findAllRolesForProfessor();

    public List<Role> findAllRolesForMotorist();

    public List<Role> findAllRolesForFriend();

    public List<Role> findAllRolesForTraveler();

    public List<Role> findAllRolesForTravelManager();

    public List<Role> findAllRolesForTravelGuide();

    public List<Role> findAllRolesForSchoolManager();

    public Role updateEnabledStatus(Long id);

    public Role updateStatusAsEnabled(Long id);

    public Role updateStatusAsDisabled(Long id);

    public List<Role> findBySearchCriteriaForNetwork(RoleSearchCriteria searchCriteria);

    public Long countTotalDocumentsBySearchCriteriaForNetwork(RoleSearchCriteria searchCriteria);
    
    public int initializeHibernateCounterForRole() ;

}
