package com.project.core.services.impl.user;

import com.project.core.models.entities.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.project.core.repositories.user.RoleRepo;
import com.project.core.services.user.RoleService;
import com.project.core.services.util.RoleList;
import com.project.rest.util.searchcriteria.user.RoleSearchCriteria;

/**
 *
 * @author Samrit
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public Role createRole(Role role) throws Exception {
        return roleRepo.createRole(role);
    }

    @Override
    public Role createRoleByObject(Role role) throws Exception {
        return roleRepo.createRoleByObject(role);
    }

    @Override
    public Role updateRole(Long id, Role data) throws Exception {
        return roleRepo.updateRole(id, data);
    }

    @Override
    public List<String> findRoleByUsername(String username) throws Exception {
        return roleRepo.findRoleByUsername(username);
    }

    @Override
    public Role findRoleByName(String name) throws Exception {
        return roleRepo.findRoleByName(name);
    }

    @Override
    public Role findRole(Long id) throws Exception {
        return roleRepo.findRole(id);
    }

    @Override
    public RoleList findAllRoles() throws Exception {
        return new RoleList(roleRepo.findAllRoles());
    }

    @Override
    public RoleList findAllRolesForUser() throws Exception {
        return new RoleList(roleRepo.findAllRolesForUser());
    }

    @Override
    public RoleList findAllRolesForStudent() throws Exception {
        return new RoleList(roleRepo.findAllRolesForStudent());
    }

    @Override
    public RoleList findAllRolesForParent() throws Exception {
        return new RoleList(roleRepo.findAllRolesForParent());
    }

    @Override
    public RoleList findAllRolesForProfessor() throws Exception {
        return new RoleList(roleRepo.findAllRolesForProfessor());
    }

    @Override
    public RoleList findAllRolesForMotorist() throws Exception {
        return new RoleList(roleRepo.findAllRolesForMotorist());
    }

    @Override
    public RoleList findAllRolesForFriend() throws Exception {
        return new RoleList(roleRepo.findAllRolesForFriend());
    }

    @Override
    public RoleList findAllRolesForTraveler() throws Exception {
        return new RoleList(roleRepo.findAllRolesForTraveler());
    }

    @Override
    public RoleList findAllRolesForTravelManager() throws Exception {
        return new RoleList(roleRepo.findAllRolesForTravelManager());
    }

    @Override
    public RoleList findAllRolesForTravelGuide() throws Exception {
        return new RoleList(roleRepo.findAllRolesForTravelGuide());
    }

    @Override
    public RoleList findAllRolesForSchoolManager() throws Exception {
        return new RoleList(roleRepo.findAllRolesForSchoolManager());
    }

    @Override
    public RoleList findAllRolesForDepartmentUser() throws Exception {
        return new RoleList(roleRepo.findAllRolesForDepartmentUser());
    }

    @Override
    public Role updateEnabledStatus(Long id) throws Exception {
        return roleRepo.updateEnabledStatus(id);
    }

    @Override
    public Role updateStatusAsEnabled(Long id) throws Exception {
        return roleRepo.updateStatusAsEnabled(id);
    }

    @Override
    public Role updateStatusAsDisabled(Long id) throws Exception {
        return roleRepo.updateStatusAsDisabled(id);
    }

    @Override
    public RoleList findBySearchCriteriaForNetwork(RoleSearchCriteria searchCriteria) throws Exception {
        Long totalDocuments = countTotalDocumentsBySearchCriteriaForNetwork(searchCriteria);
        Long totalPages = (long) Math.ceil(totalDocuments / searchCriteria.getLimitResult().doubleValue());
        RoleList userList = new RoleList(roleRepo.findBySearchCriteriaForNetwork(searchCriteria), totalDocuments, totalPages);
        return userList;
    }

    @Override
    public Long countTotalDocumentsBySearchCriteriaForNetwork(RoleSearchCriteria searchCriteria) throws Exception {
        return roleRepo.countTotalDocumentsBySearchCriteriaForNetwork(searchCriteria);
    }

    @Override
    public int initializeHibernateCounterForRole() throws Exception {
        return roleRepo.initializeHibernateCounterForRole();
    }

}
