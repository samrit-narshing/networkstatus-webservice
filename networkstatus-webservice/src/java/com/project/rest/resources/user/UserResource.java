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
public class UserResource extends ResourceSupport {

    private Long userID = 0L;

    private String firstName = "";
    private String middleName = "";
    private String lastName = "";

    private String username = "";
    private String password = "";
    private Set<RoleResource> roles = new HashSet<>();

    private String address1 = "";
    private String address2 = "";
    private String phoneNo = "";
    private String mobileNo = "";
    private String email = "";
    private String gender = "";

    private Boolean enabled = false;
    private String sessionTimeout = "";
    private String accountExpiration = "";
    private Boolean neverExpire = false;

    private String userType = "";

    private Long lastModifiedUnixTime = 0L;

    private boolean passwordExpire = false;

    private String profileImage = "";

    private String entryByUsername = "";
    private String entryByUserType = "";
    private String lastUpdateByUsername = "";
    private String lastUpdateByUserType = "";

    private String adminUsername = "";
    private String adminOperatorUsername = "";

    private String adminUserType = "";
    private String adminOperatorUserType = "";

    private String syncronizedVersion = "";

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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
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

    public User toUser() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setAccountExpiration(accountExpiration);
        user.setEnabled(enabled);
        user.setNeverExpire(neverExpire);
        user.setSessionTimeout(sessionTimeout);
        user.setAddress1(address1);
        user.setAddress2(address2);
        user.setEmail(email);
        user.setPhoneNo(phoneNo);
        user.setMobileNo(mobileNo);
        user.setGender(gender);

        user.setFirstName(firstName);
        user.setMiddleName(middleName);
        user.setLastName(lastName);

        user.setLastModifiedUnixTime(lastModifiedUnixTime);

        Set<Role> localRoles = new HashSet<>();
        if (roles != null) {
            roles.stream().forEach((roleResource) -> {
                localRoles.add(roleResource.toRole());
            });
        }
        user.setRoles(localRoles);

        user.setPasswordExpire(passwordExpire);
        user.setProfileImage(profileImage);

        user.setLastModifiedUnixTime(getLastModifiedUnixTime());
        user.setEntryByUserType(entryByUserType);
        user.setEntryByUsername(entryByUsername);
        user.setLastUpdateByUserType(lastUpdateByUserType);
        user.setLastUpdateByUsername(lastUpdateByUsername);

        user.setAdminUsername(adminUsername);
        user.setAdminOperatorUsername(adminOperatorUsername);
        user.setAdminUserType(adminUserType);
        user.setAdminOperatorUserType(adminOperatorUserType);
        
        user.setSyncronizedVersion(syncronizedVersion);

        return user;
    }
}
