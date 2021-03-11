/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.models.entities.network;

import com.project.core.models.entities.user.Role;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Samrit
 */
@Entity
@Table(name = "role_networkgroup")
public class RoleInNetworkGroup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role-networkgroup-seqid-gen")
    @SequenceGenerator(name = "role-networkgroup-seqid-gen", sequenceName = "hibernate_role_networkgroup_sequence")
    private Long id;

    @OneToOne
    private Role role;

    @Transient
    private String networkGroupCode;

    @Transient
    private Long networkGroupId;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getNetworkGroupCode() {
        return networkGroupCode;
    }

    public void setNetworkGroupCode(String networkGroupCode) {
        this.networkGroupCode = networkGroupCode;
    }

    public Long getNetworkGroupId() {
        return networkGroupId;
    }

    public void setNetworkGroupId(Long networkGroupId) {
        this.networkGroupId = networkGroupId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
