package com.project.core.services.impl.user;

import com.project.core.models.entities.user.DepartmentUser;
import com.project.core.models.entities.user.User;
import com.project.core.repositories.user.DepartmentUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.project.core.services.exceptions.UserExistsException;
import com.project.core.services.user.DepartmentUserService;
import com.project.core.services.util.DepartmentUserList;
import com.project.rest.util.searchcriteria.user.DepartmentUserSearchCriteria;

/**
 *
 * @author Samrit
 */
@Service
@Transactional
public class DepartmentUserServiceImpl implements DepartmentUserService {

    @Autowired
    private DepartmentUserRepo departmentUserRepo;

    @Override
    public DepartmentUser createDepartmentUser(DepartmentUser departmentUser) throws Exception {
        DepartmentUser account = departmentUserRepo.findDepartmentUserByUsername(departmentUser.getUsername());
        if (account != null) {
            throw new UserExistsException();
        }
        return departmentUserRepo.createDepartmentUser(departmentUser);
    }

    @Override
    public DepartmentUser findDepartmentUser(Long id) throws Exception {
        return departmentUserRepo.findDepartmentUser(id);
    }

    @Override
    public DepartmentUser findDepartmentUserByUsername(String username) throws Exception {
        return departmentUserRepo.findDepartmentUserByUsername(username);
    }

    @Override
    public DepartmentUser findValidDepartmentUserByUsername(String username) throws Exception {
        return departmentUserRepo.findValidDepartmentUserByUsername(username);
    }

    @Override
    public DepartmentUser findDepartmentUserByUsernameAndPassword(String username, String password) throws Exception {
        return departmentUserRepo.findDepartmentUserByUsernameAndPassword(username, password);
    }

    @Override
    public DepartmentUserList findDepartmentUserListWithAllDepartmentUsers() throws Exception {
        return new DepartmentUserList(departmentUserRepo.findAllDepartmentUsers());
    }

    @Override
    public DepartmentUserList findAllValidDepartmentUsers(User loggedUser) throws Exception {
        return new DepartmentUserList(departmentUserRepo.findAllValidDepartmentUsers(loggedUser));
    }

    @Override
    public DepartmentUserList findDepartmentUserListBySearchCriteria(DepartmentUserSearchCriteria searchCriteria, User loggedUser) throws Exception {
        Long totalDocuments = countTotalDocumentsBySearchCriteria(searchCriteria, loggedUser);
        Long totalPages = (long) Math.ceil(totalDocuments / searchCriteria.getLimitResult().doubleValue());
        DepartmentUserList userList = new DepartmentUserList(departmentUserRepo.findDepartmentUsersBySearchCriteria(searchCriteria, loggedUser), totalDocuments, totalPages);
        return userList;
    }

    @Override
    public Long countTotalDocumentsBySearchCriteria(DepartmentUserSearchCriteria searchCriteria, User loggedUser) throws Exception {
        return departmentUserRepo.countTotalDocumentsBySearchCriteria(searchCriteria, loggedUser);
    }

    @Override
    public DepartmentUser updateDepartmentUser(Long id, DepartmentUser data) throws Exception {
        return departmentUserRepo.updateDepartmentUser(id, data);
    }

    @Override
    public DepartmentUser updateDepartmentUserDetails(Long id, DepartmentUser data) throws Exception {
        return departmentUserRepo.updateDepartmentUserDetails(id, data);
    }

    @Override
    public DepartmentUser updateDepartmentUserPassword(Long id, DepartmentUser data) throws Exception {
        return departmentUserRepo.updateDepartmentUserPassword(id, data);
    }

    @Override
    public DepartmentUser updateDepartmentUserUsername(Long id, DepartmentUser data) throws Exception {
        return departmentUserRepo.updateDepartmentUserUsername(id, data);
    }

    @Override
    public DepartmentUser updateDepartmentUserProfileImage(Long id, DepartmentUser data) throws Exception {
        return departmentUserRepo.updateDepartmentUserProfileImage(id, data);
    }

    @Override
    public DepartmentUser deleteDepartmentUser(Long id) throws Exception {
        return departmentUserRepo.deleteDepartmentUser(id);
    }

    @Override
    public DepartmentUser updateDepartmentUserEnabledStatus(Long id, User data) throws Exception {
        return departmentUserRepo.updateDepartmentUserEnabledStatus(id, data);
    }

    @Override
    public DepartmentUser updateDepartmentUserStatusAsEnabled(Long id, User data) throws Exception {
        return departmentUserRepo.updateDepartmentUserStatusAsEnabled(id, data);
    }

    @Override
    public DepartmentUser updateDepartmentUserStatusAsDisabled(Long id, User data) throws Exception {
        return departmentUserRepo.updateDepartmentUserStatusAsDisabled(id, data);
    }
}
