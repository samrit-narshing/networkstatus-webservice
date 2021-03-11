/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.models.entities.user;

/**
 *
 * @author Samrit
 */
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "device_user")
public class DeviceUser implements Serializable {

//        @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deviceusers-seqid-gen")
    @SequenceGenerator(name = "deviceusers-seqid-gen", sequenceName = "hibernate_deviceusers_sequence")
    private Long id;
    private String username;

    @Column(name = "token_name")
    private String tokenName;
    private Boolean active;

    @Column(name = "entry_date")
    private Long entryDate;

    @Column(name = "entryby_username")
    private String entryByUsername;

    @Column(name = "entryby_user_type")
    private String entryByUserType;

    @Column(name = "last_modified_date")
    private Long lastModifiedDate;

    @Column(name = "admin_username")
    private String adminUsername;

    @Column(name = "admin_operator_username")
    private String adminOperatorUsername;

    @Column(name = "admin_user_type")
    private String adminUserType;

    @Column(name = "admin_operator_user_type")
    private String adminOperatorUserType;

    public String getAdminUserType() {
        return adminUserType;
    }

    public void setAdminUserType(String adminUserType) {
        this.adminUserType = adminUserType;
    }

    public String getAdminOperatorUserType() {
        return adminOperatorUserType;
    }

    public void setAdminOperatorUserType(String adminOperatorUserType) {
        this.adminOperatorUserType = adminOperatorUserType;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public String getAdminOperatorUsername() {
        return adminOperatorUsername;
    }

    public void setAdminOperatorUsername(String adminOperatorUsername) {
        this.adminOperatorUsername = adminOperatorUsername;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Long entryDate) {
        this.entryDate = entryDate;
    }

    public Long getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Long lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
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

}
