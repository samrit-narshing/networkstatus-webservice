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
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
//import java.sql.Timestamp;
import javax.persistence.*;
import java.util.Set;

/**
 *
 * @author samri
 */
@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING, length = 30)
@DiscriminatorValue("SYSTEM_USER")
public class User implements Serializable {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users-seqid-gen")
    @SequenceGenerator(name = "users-seqid-gen", sequenceName = "hibernate_users_sequence")
    private Long id;
    private String username;
    private String password;
    @Transient
    private String passwordConfirm;
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    private boolean enabled;

    @Column(name = "session_timeout")
    private String sessionTimeout;

    @Column(name = "account_expiration")
    private String accountExpiration;

    @Column(name = "never_expire")
    private boolean neverExpire;

    private String address1;
    private String address2;

    @Column(name = "phone_number")
    private String phoneNo;
    @Column(name = "mobile_number")
    private String mobileNo;

    private String email;
    private String gender;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_type", insertable = false, updatable = false)
    private String userType;

    @Column(name = "password_expire")
    private boolean passwordExpire;

//    @Version
//    @Column(name = "last_updated_time")
//    private Timestamp updatedTime;
    @Column(name = "last_modified_unix_time")
    private Long lastModifiedUnixTime;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "entryby_username")
    private String entryByUsername;

    @Column(name = "entryby_user_type")
    private String entryByUserType;

    @Column(name = "last_updateby_username")
    private String lastUpdateByUsername;

    @Column(name = "last_updateby_user_type")
    private String lastUpdateByUserType;

    @Column(name = "admin_username")
    private String adminUsername;

    @Column(name = "admin_operator_username")
    private String adminOperatorUsername;

    @Column(name = "admin_user_type")
    private String adminUserType;

    @Column(name = "admin_operator_user_type")
    private String adminOperatorUserType;

    @Transient
    private String syncronizedVersion;

    public String getSyncronizedVersion() {
        return syncronizedVersion;
    }

    public void setSyncronizedVersion(String syncronizedVersion) {
        this.syncronizedVersion = syncronizedVersion;
    }

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

    public Long getLastModifiedUnixTime() {
        return lastModifiedUnixTime;
    }

    public void setLastModifiedUnixTime(Long lastModifiedUnixTime) {
        this.lastModifiedUnixTime = lastModifiedUnixTime;
    }

//    public Timestamp getUpdatedTime() {
//        return updatedTime;
//    }
//
//    public void setUpdatedTime(Timestamp updatedTime) {
//        this.updatedTime = updatedTime;
//    }
    public String getUserType() {
        return userType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
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

    public boolean isNeverExpire() {
        return neverExpire;
    }

    public void setNeverExpire(boolean neverExpire) {
        this.neverExpire = neverExpire;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isPasswordExpire() {
        return passwordExpire;
    }

    public void setPasswordExpire(boolean passwordExpire) {
        this.passwordExpire = passwordExpire;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

}
