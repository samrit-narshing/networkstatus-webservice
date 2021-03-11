/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.resources.network;

import com.project.core.models.entities.network.RoleInNetworkGroup;
import com.project.rest.resources.user.RoleResource;
import org.springframework.hateoas.ResourceSupport;

/**
 *
 * @author samri
 */
public class RoleInNetworkGroupResource extends ResourceSupport {

    private Long roleInNetworkGroupID = 0L;

    private RoleResource roleResource = new RoleResource();

    public Long getRoleInNetworkGroupID() {
        return roleInNetworkGroupID;
    }

    public void setRoleInNetworkGroupID(Long roleInNetworkGroupID) {
        this.roleInNetworkGroupID = roleInNetworkGroupID;
    }

    public RoleResource getRoleResource() {
        return roleResource;
    }

    public void setRoleResource(RoleResource roleResource) {
        this.roleResource = roleResource;
    }

    public RoleInNetworkGroup toRoleInNetworkGroup() {
        RoleInNetworkGroup roleInNetworkGroup = new RoleInNetworkGroup();
        roleInNetworkGroup.setId(getRoleInNetworkGroupID());
        roleInNetworkGroup.setRole(getRoleResource().toRole());
        return roleInNetworkGroup;
    }
}
