package com.project.core.services.util;

import com.project.core.models.entities.user.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samrit
 */
public class UserList {

    private Long totalPages = new Long(0);
    private Long totalDocuments = new Long(0);

    private List<User> users = new ArrayList<>();

    public UserList(List<User> list) {
        this.users = list;
    }

    public UserList(List<User> list, Long totalDocuments, Long totalPages) {
        this.users = list;
        this.totalPages = totalPages;
        this.totalDocuments = totalDocuments;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalDocuments() {
        return totalDocuments;
    }

    public void setTotalDocuments(Long totalDocuments) {
        this.totalDocuments = totalDocuments;
    }

}
