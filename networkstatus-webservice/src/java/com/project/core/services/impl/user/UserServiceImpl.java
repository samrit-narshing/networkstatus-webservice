package com.project.core.services.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.project.core.models.entities.user.User;
import com.project.core.repositories.user.UserRepo;
import com.project.core.services.exceptions.UserExistsException;
import com.project.core.services.user.UserService;
import com.project.core.services.util.UserList;
import com.project.rest.util.searchcriteria.user.AnyUserSearchCriteria;
import com.project.rest.util.searchcriteria.user.UserSearchCriteria;

/**
 *
 * @author Samrit
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public User createUser(User data) throws Exception {
        User account = userRepo.findUserByUsername(data.getUsername());
        if (account != null) {
            throw new UserExistsException();
        }
        return userRepo.createUser(data);
    }

    @Override
    public User findUser(Long id) throws Exception {
        return userRepo.findUser(id);
    }

    @Override
    public User findUserByUsername(String username) throws Exception {
        return userRepo.findUserByUsername(username);
    }

    @Override
    public User findUserByUsernameByLoggedUser(String username, User loggedUser) throws Exception {
        return userRepo.findUserByUsernameByLoggedUser(username, loggedUser);
    }

    @Override
    public User findValidUserByUsername(String username) throws Exception {
        return userRepo.findValidUserByUsername(username);
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) throws Exception {
        return userRepo.findUserByUsernameAndPassword(username, password);
    }

    @Override
    public UserList findUserListWithAllUsers() throws Exception {
        return new UserList(userRepo.findAllUsers());
    }

    @Override
    public UserList findUserListWithSearchedUsersBySearchCriteria(UserSearchCriteria searchCriteria) throws Exception {
        Long totalDocuments = countTotalDocumentsBySearchCriteria(searchCriteria);
        Long totalPages = (long) Math.ceil(totalDocuments / searchCriteria.getLimitResult().doubleValue());
        UserList userList = new UserList(userRepo.findUsersBySearchCriteria(searchCriteria), totalDocuments, totalPages);
        return userList;
    }

    @Override
    public Long countTotalDocumentsBySearchCriteria(UserSearchCriteria searchCriteria) throws Exception {
        return userRepo.countTotalDocumentsBySearchCriteria(searchCriteria);
    }

    @Override
    public User updateUser(Long id, User data) throws Exception {
        return userRepo.updateUser(id, data);
    }

    @Override
    public User updateUserDetails(Long id, User data) throws Exception {
        return userRepo.updateUserDetails(id, data);
    }

    @Override
    public User updateUserPassword(Long id, User data) throws Exception {
        return userRepo.updateUserPassword(id, data);
    }

    @Override
    public User updateUserExpiryPassword(Long id, User data) throws Exception {
        return userRepo.updateUserExpiryPassword(id, data);
    }

    @Override
    public User updateUserProfileImage(Long id, User data) throws Exception {
        return userRepo.updateUserProfileImage(id, data);
    }

    @Override
    public User deleteUser(Long id) throws Exception {
        return userRepo.deleteUser(id);
    }

    @Override
    public UserList findUsersWithPagination(int pageNo, int pageSize) throws Exception {
        return new UserList(userRepo.findUsersWithPagination(pageNo, pageSize));
    }

    @Override
    public UserList findUsersWithPagination(String searchText, int pageNo, int pageSize) throws Exception {
        return new UserList(userRepo.findUsersWithPagination(searchText, pageNo, pageSize));
    }

    @Override
    public Integer countNoOfPagesForUsersWithPagination(int pageSize) throws Exception {
        return userRepo.countNoOfPagesForUsersWithPagination(pageSize);
    }

    @Override
    public Integer countNoOfPagesForUsersWithPagination(String searchText, int pageSize) throws Exception {
        return userRepo.countNoOfPagesForUsersWithPagination(searchText, pageSize);
    }

    @Override
    public User updateUserEnabledStatus(Long id, User data) throws Exception {
        return userRepo.updateUserEnabledStatus(id, data);
    }

    @Override
    public User updateUserStatusAsEnabled(Long id, User data) throws Exception {
        return userRepo.updateUserEnabledStatus(id, data);
    }

    @Override
    public User updateUserStatusAsDisabled(Long id, User data) throws Exception {
        return userRepo.updateUserEnabledStatus(id, data);
    }

    @Override
    public UserList findAnyUsersWithPagination(AnyUserSearchCriteria searchCriteria, User loggedUser) throws Exception {
        Long totalDocuments = countNoOfPagesForAnyUsersWithPagination(searchCriteria, loggedUser);
        Long totalPages = (long) Math.ceil(totalDocuments / searchCriteria.getLimitResult().doubleValue());
        UserList userList = new UserList(userRepo.findAnyUsersWithPagination(searchCriteria, loggedUser), totalDocuments, totalPages);
        return userList;
    }

    @Override
    public Long countNoOfPagesForAnyUsersWithPagination(AnyUserSearchCriteria searchCriteria, User loggedUser) throws Exception {
        return userRepo.countNoOfPagesForAnyUsersWithPagination(searchCriteria, loggedUser);
    }

}
