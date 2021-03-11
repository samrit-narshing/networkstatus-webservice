package com.project.rest.resources.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.core.models.entities.user.Role;
import org.springframework.hateoas.ResourceSupport;
import com.project.core.models.entities.user.User;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Samrit
 */
public class LoggedUserResource extends ResourceSupport {

    private Long userID;
    private String username;
    private String password;
    private Boolean enabled;
    private String sessionTimeout;
    private String accountExpiration;
    private Boolean neverExpire;

    private Set<RoleResource> roles;

    public Set<RoleResource> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleResource> roles) {
        this.roles = roles;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    @JsonIgnore
    public String getPassword() {
        return password;
    }

//    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(String sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public String getAccountExpiration() {
        return accountExpiration;
    }

    public void setAccountExpiration(String accountExpiration) {
        this.accountExpiration = accountExpiration;
    }

    public Boolean getNeverExpire() {
        return neverExpire;
    }

    public void setNeverExpire(Boolean neverExpire) {
        this.neverExpire = neverExpire;
    }

    public User toUser() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setAccountExpiration(accountExpiration);
        user.setEnabled(enabled);
        user.setNeverExpire(neverExpire);
        user.setSessionTimeout(sessionTimeout);

        Set<Role> localRoles = new HashSet<>();
        if (roles != null) {
            roles.stream().forEach((roleResource) -> {
                localRoles.add(roleResource.toRole());
            });
        }
        user.setRoles(localRoles);
        return user;
    }
}
