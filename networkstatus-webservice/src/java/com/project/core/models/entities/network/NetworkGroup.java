/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.models.entities.network;

import java.io.Serializable;
//import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
//import javax.persistence.Version;

/**
 *
 * @author samri
 */
@Entity
@Table(name = "network_group")
public class NetworkGroup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "networkgroup-seqid-gen")
    @SequenceGenerator(name = "networkgroup-seqid-gen", sequenceName = "hibernate_networkgroup_sequence")
    private Long id;

    private String name;

    private String code;

    private String description;

    private String roles;

    @Column(name = "entry_date")
    private Long entryDate;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "node_networkgroup_id")
    private Set<NodeInNetworkGroup> nodesInNetworkGroup = new HashSet<>();

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "edge_networkgroup_id")
    private Set<EdgeInNetworkGroup> edgesInNetworkGroup = new HashSet<>();

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "role_networkgroup_id")
    private Set<RoleInNetworkGroup> rolesInNetworkGroup = new HashSet<>();

    @Column(name = "status_code")
    private Integer statusCode;

    @Column(name = "entryby_username")
    private String entryByUsername;

    @Column(name = "entryby_user_type")
    private String entryByUserType;

    @Column(name = "last_updateby_username")
    private String lastUpdateByUsername;

    @Column(name = "last_updateby_user_type")
    private String lastUpdateByUserType;

    @Column(name = "last_modified_unix_time")
    private Long lastModifiedUnixTime;

    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Long getLastModifiedUnixTime() {
        return lastModifiedUnixTime;
    }

    public void setLastModifiedUnixTime(Long lastModifiedUnixTime) {
        this.lastModifiedUnixTime = lastModifiedUnixTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Long entryDate) {
        this.entryDate = entryDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<NodeInNetworkGroup> getNodesInNetworkGroup() {
        return nodesInNetworkGroup;
    }

//    public void setNodesInNetworkGroup(Set<NodeInNetworkGroup> nodesInNetworkGroup) {
//        this.nodesInNetworkGroup.clear();
//        if (nodesInNetworkGroup != null) {
//            this.nodesInNetworkGroup.addAll(nodesInNetworkGroup);
//        }
//
//    }
    public void setNodesInNetworkGroup(Set<NodeInNetworkGroup> nodesInNetworkGroup) {
        this.nodesInNetworkGroup = nodesInNetworkGroup;
    }

    public Set<EdgeInNetworkGroup> getEdgesInNetworkGroup() {
        return edgesInNetworkGroup;
    }

    public void setEdgesInNetworkGroup(Set<EdgeInNetworkGroup> edgesInNetworkGroup) {
        this.edgesInNetworkGroup = edgesInNetworkGroup;
    }

    public String getEntryByUsername() {
        return entryByUsername;
    }

    public void setEntryByUsername(String entryByUsername) {
        this.entryByUsername = entryByUsername;
    }

    public String getEntryByUserType() {
        return entryByUserType;
    }

    public void setEntryByUserType(String entryByUserType) {
        this.entryByUserType = entryByUserType;
    }

    public String getLastUpdateByUsername() {
        return lastUpdateByUsername;
    }

    public void setLastUpdateByUsername(String lastUpdateByUsername) {
        this.lastUpdateByUsername = lastUpdateByUsername;
    }

    public String getLastUpdateByUserType() {
        return lastUpdateByUserType;
    }

    public void setLastUpdateByUserType(String lastUpdateByUserType) {
        this.lastUpdateByUserType = lastUpdateByUserType;
    }

    public Set<RoleInNetworkGroup> getRolesInNetworkGroup() {
        return rolesInNetworkGroup;
    }

    public void setRolesInNetworkGroup(Set<RoleInNetworkGroup> rolesInNetworkGroup) {
        this.rolesInNetworkGroup = rolesInNetworkGroup;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

}
