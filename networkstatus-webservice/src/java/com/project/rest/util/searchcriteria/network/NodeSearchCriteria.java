/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.util.searchcriteria.network;

/**
 *
 * @author Samrit
 */
public class NodeSearchCriteria {

    private Long id = 0L;
    private String searchText = "";
    private Integer pageNo = 1;
    private Integer limitResult = 0;
    private Boolean enable = false;
    private Boolean enableOrDisableCheck = false;

    private Integer statusCode = 0;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
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
    
    

}
