package com.project.rest.resources.user;

import com.project.core.models.entities.user.Role;
import org.springframework.hateoas.ResourceSupport;

/**
 *
 * @author Samrit
 */
public class RoleResource extends ResourceSupport {

    private Long roleID;
    private String name;
    private String description;
    private Boolean enabled = false;

    private Boolean selected;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getRoleID() {
        return roleID;
    }

    public void setRoleID(Long roleID) {
        this.roleID = roleID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Role toRole() {
        Role role = new Role();
        role.setId(roleID);
        role.setName(name);
        role.setDescription(description);
        role.setEnabled(enabled);
        return role;
    }
}
