package com.project.rest.resources.user;

import com.project.core.models.entities.user.DepartmentUser;
import com.project.core.models.entities.user.Role;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Samrit
 */
public class DepartmentUserResource extends UserResource {

    private String socialID;
    private String departmentUserField;

    public String getSocialID() {
        return socialID;
    }

    public void setSocialID(String socialID) {
        this.socialID = socialID;
    }

    public String getDepartmentUserField() {
        return departmentUserField;
    }

    public void setDepartmentUserField(String departmentUserField) {
        this.departmentUserField = departmentUserField;
    }



    public DepartmentUser toDepartmentUser() {
        DepartmentUser departmentUser = new DepartmentUser();
        departmentUser.setId(getUserID());
        departmentUser.setUsername(getUsername());
        departmentUser.setPassword(getPassword());
        departmentUser.setAccountExpiration(getAccountExpiration());
        departmentUser.setEnabled(getEnabled());
        departmentUser.setNeverExpire(getNeverExpire());
        departmentUser.setSessionTimeout(getSessionTimeout());
        departmentUser.setAddress1(getAddress1());
        departmentUser.setAddress2(getAddress2());
        departmentUser.setEmail(getEmail());
        departmentUser.setPhoneNo(getPhoneNo());
        departmentUser.setMobileNo(getMobileNo());
        departmentUser.setGender(getGender());
        departmentUser.setFirstName(getFirstName());
        departmentUser.setMiddleName(getMiddleName());
        departmentUser.setLastName(getLastName());

        departmentUser.setSocialID(getSocialID());
        departmentUser.setDepartmentUserField(getDepartmentUserField());
        Set<Role> localRoles = new HashSet<>();
        if (getRoles() != null) {
            getRoles().stream().forEach((roleResource) -> {
                localRoles.add(roleResource.toRole());
            });
        }
        departmentUser.setRoles(localRoles);

        departmentUser.setPasswordExpire(isPasswordExpire());

        departmentUser.setProfileImage(getProfileImage());

        departmentUser.setEntryByUserType(getEntryByUserType());
        departmentUser.setEntryByUsername(getEntryByUsername());

        departmentUser.setLastUpdateByUserType(getLastUpdateByUserType());
        departmentUser.setLastUpdateByUsername(getLastUpdateByUsername());
        departmentUser.setLastModifiedUnixTime(getLastModifiedUnixTime());

        departmentUser.setAdminUsername(getAdminUsername());
        departmentUser.setAdminOperatorUsername(getAdminOperatorUsername());
        departmentUser.setAdminUserType(getAdminUserType());
        departmentUser.setAdminOperatorUserType(getAdminOperatorUserType());

//        Set<Student> localStudents = new HashSet<>();
//        if (getStudents() != null) {
//            getStudents().stream().forEach((studentResource) -> {
//                localStudents.add(studentResource.toStudent());
//            });
//        }
//        parent.setStudents(localStudents);
        return departmentUser;
    }
}
