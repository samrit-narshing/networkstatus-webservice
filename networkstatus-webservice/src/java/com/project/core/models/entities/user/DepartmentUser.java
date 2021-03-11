/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.models.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author samri
 */
@Entity
@Table(name = "department_user")
@PrimaryKeyJoinColumn(name = "user_id")
@DiscriminatorValue("DEPARTMENT_USER")
public class DepartmentUser extends User implements Serializable {

    @Column(name = "social_id")
    private String socialID;

    @Column(name = "department_user_field")
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

}
