/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.util.searchcriteria.user;

/**
 *
 * @author Samrit
 */
public class ProfessorSearchCriteria {

    private Long id = 0L;
    private String username = "";
    private Integer pageNo = 1;
    private Integer limitResult = 0;

    private Long classGroupID = 0L;
    private String classGroupCode = "";

    private Long courseID = 0L;
    private String courseCode = "";

    private Boolean enable = false;
    private Boolean enableOrDisableCheck = false;

    private Integer sortBy = 0;

    public Integer getSortBy() {
        return sortBy;
    }

    public void setSortBy(Integer sortBy) {
        this.sortBy = sortBy;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getEnableOrDisableCheck() {
        return enableOrDisableCheck;
    }

    public void setEnableOrDisableCheck(Boolean enableOrDisableCheck) {
        this.enableOrDisableCheck = enableOrDisableCheck;
    }

    public Long getClassGroupID() {
        return classGroupID;
    }

    public void setClassGroupID(Long classGroupID) {
        this.classGroupID = classGroupID;
    }

    public String getClassGroupCode() {
        return classGroupCode;
    }

    public void setClassGroupCode(String classGroupCode) {
        this.classGroupCode = classGroupCode;
    }

    public Long getCourseID() {
        return courseID;
    }

    public void setCourseID(Long courseID) {
        this.courseID = courseID;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
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

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getLimitResult() {
        return limitResult;
    }

    public void setLimitResult(Integer limitResult) {
        this.limitResult = limitResult;
    }

}
