package com.project.core.repositories.user;

import com.project.core.models.entities.user.User;
import com.project.rest.util.searchcriteria.user.AnyUserSearchCriteria;
import com.project.rest.util.searchcriteria.user.UserSearchCriteria;
import java.util.List;

/**
 *
 * @author Samrit
 */
public interface UserRepo {

    public User createUser(User user);

    public User findUser(Long id);

    public User findUserByUsername(String username);

    public User findUserByUsernameByLoggedUser(String username, User loggedUser);

    public User findValidUserByUsername(String username);

    public User findUserByUsernameAndPassword(String username, String password);

    public List<User> findAllUsers();

    public List<User> findUsersBySearchCriteria(UserSearchCriteria searchCriteria);

    public Long countTotalDocumentsBySearchCriteria(UserSearchCriteria searchCriteria);

    public User updateUser(Long id, User data);

    public User updateUserDetails(Long id, User data);

    public User updateUserPassword(Long id, User data);

    public User updateUserExpiryPassword(Long id, User data);

    public User updateUserProfileImage(Long id, User data);

    public User deleteUser(Long id);

    public List<User> findUsersWithPagination(final int pageNo, final int pageSize);

    public List<User> findUsersWithPagination(final String searchText, final int pageNo, final int pageSize);

    public Integer countNoOfPagesForUsersWithPagination(final int pageSize);

    public Integer countNoOfPagesForUsersWithPagination(final String searchText, final int pageSize);

    public User updateUserEnabledStatus(Long id, User data);

    public User updateUserStatusAsEnabled(Long id, User data);

    public User updateUserStatusAsDisabled(Long id, User data);

    public List<User> findAnyUsersWithPagination(AnyUserSearchCriteria anyUserSearchCriteria, User loggedUser);

    public Long countNoOfPagesForAnyUsersWithPagination(AnyUserSearchCriteria anyUserSearchCriteria, User loggedUser);
}
