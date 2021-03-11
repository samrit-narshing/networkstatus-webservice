/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.util.searchcriteria.user;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samrit
 */
public class AnyUserSearchCriteria {

    private Long id = 0L;
    private String searchedText = "";
    private Integer pageNo = 1;
    private Integer limitResult = 0;
    private String userType = "";

    private List<String> excludeUsernames = new ArrayList<>();

    public List<String> getExcludeUsernames() {
        return excludeUsernames;
    }

    public void setExcludeUsernames(List<String> excludeUsernames) {
        this.excludeUsernames = excludeUsernames;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSearchedText() {
        return searchedText;
    }

    public void setSearchedText(String searchedText) {
        this.searchedText = searchedText;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}
