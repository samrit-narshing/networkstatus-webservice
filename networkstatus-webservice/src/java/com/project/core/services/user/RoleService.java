package com.project.core.services.user;

import com.project.core.models.entities.user.Role;
import java.util.List;
import com.project.core.services.util.RoleList;
import com.project.rest.util.searchcriteria.user.RoleSearchCriteria;

/**
 *
 * @author Samrit
 */
public interface RoleService {

    public Role findRole(Long id) throws Exception;

    public Role createRole(Role role) throws Exception;

    public Role createRoleByObject(Role user) throws Exception;

    public Role updateRole(Long id, Role data) throws Exception;

    public List<String> findRoleByUsername(String username) throws Exception;

    public Role findRoleByName(String name) throws Exception;

    public RoleList findAllRoles() throws Exception;

    public RoleList findAllRolesForUser() throws Exception;

    public RoleList findAllRolesForDepartmentUser() throws Exception;

    public RoleList findAllRolesForStudent() throws Exception;

    public RoleList findAllRolesForParent() throws Exception;

    public RoleList findAllRolesForProfessor() throws Exception;

    public RoleList findAllRolesForMotorist() throws Exception;

    public RoleList findAllRolesForFriend() throws Exception;

    public RoleList findAllRolesForTraveler() throws Exception;

    public RoleList findAllRolesForTravelManager() throws Exception;

    public RoleList findAllRolesForTravelGuide() throws Exception;

    public RoleList findAllRolesForSchoolManager() throws Exception;

    public Role updateEnabledStatus(Long id) throws Exception;

    public Role updateStatusAsEnabled(Long id) throws Exception;

    public Role updateStatusAsDisabled(Long id) throws Exception;

    public RoleList findBySearchCriteriaForNetwork(RoleSearchCriteria searchCriteria) throws Exception;

    public Long countTotalDocumentsBySearchCriteriaForNetwork(RoleSearchCriteria searchCriteria) throws Exception;

    public int initializeHibernateCounterForRole() throws Exception;

}
