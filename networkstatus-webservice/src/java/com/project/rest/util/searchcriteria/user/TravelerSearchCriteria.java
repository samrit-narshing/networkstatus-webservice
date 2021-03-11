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
public class TravelerSearchCriteria {

    private Long id = 0L;
    private String username = "";
    private Integer pageNo = 1;
    private Integer limitResult = 0;

    private Long travelGroupID = 0L;
    private String travelGroupCode = "";
    private Integer sortBy = 0;

    public Integer getSortBy() {
        return sortBy;
    }

    public void setSortBy(Integer sortBy) {
        this.sortBy = sortBy;
    }

    public Long getTravelGroupID() {
        return travelGroupID;
    }

    public void setTravelGroupID(Long travelGroupID) {
        this.travelGroupID = travelGroupID;
    }

    public String getTravelGroupCode() {
        return travelGroupCode;
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
