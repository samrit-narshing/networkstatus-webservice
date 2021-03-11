package com.project.core.services.user;

import com.project.core.models.entities.user.User;
import com.project.core.services.util.UserList;
import com.project.rest.util.searchcriteria.user.AnyUserSearchCriteria;
import com.project.rest.util.searchcriteria.user.UserSearchCriteria;

/**
 *
 * @author Samrit
 */
public interface UserService {

    public User createUser(User user) throws Exception;

    public User findUser(Long id) throws Exception;

    public User findUserByUsername(String username) throws Exception;

    public User findUserByUsernameByLoggedUser(String username, User loggedUser) throws Exception;

    public User findValidUserByUsername(String username) throws Exception;

    public User findUserByUsernameAndPassword(String username, String password) throws Exception;

    public UserList findUserListWithAllUsers() throws Exception;

    public UserList findUserListWithSearchedUsersBySearchCriteria(UserSearchCriteria searchCriteria) throws Exception;

    public Long countTotalDocumentsBySearchCriteria(UserSearchCriteria searchCriteria) throws Exception;

    public User updateUser(Long id, User data) throws Exception;

    public User updateUserDetails(Long id, User data) throws Exception;

    public User updateUserPassword(Long id, User data) throws Exception;

    public User updateUserExpiryPassword(Long id, User data) throws Exception;

    public User updateUserProfileImage(Long id, User data) throws Exception;

    public User updateUserEnabledStatus(Long id, User data) throws Exception;

    public User updateUserStatusAsEnabled(Long id, User data) throws Exception;

    public User updateUserStatusAsDisabled(Long id, User data) throws Exception;

    public User deleteUser(Long id) throws Exception;

    public UserList findUsersWithPagination(final int pageNo, final int pageSize) throws Exception;

    public UserList findUsersWithPagination(final String searchText, final int pageNo, final int pageSize) throws Exception;

    public Integer countNoOfPagesForUsersWithPagination(final int pageSize) throws Exception;

    public Integer countNoOfPagesForUsersWithPagination(final String searchText, final int pageSize) throws Exception;

    public UserList findAnyUsersWithPagination(AnyUserSearchCriteria searchCriteria, User loggedUser) throws Exception;

    public Long countNoOfPagesForAnyUsersWithPagination(AnyUserSearchCriteria searchCriteria, User loggedUser) throws Exception;

}
