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
public class StudentSearchCriteria {

    private String parentUsername = "";
    private Long id = 0L;
    private String username = "";
    private Integer pageNo = 1;
    private Integer limitResult = 0;

    private Integer sortBy = 0;

    private Long classGroupID = 0L;
    private String classGroupCode = "";

    public Integer getSortBy() {
        return sortBy;
    }

    public void setSortBy(Integer sortBy) {
        this.sortBy = sortBy;
    }

    public String getParentUsername() {
        return parentUsername;
    }

    public void setParentUsername(String parentUsername) {
        this.parentUsername = parentUsername;
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

}
