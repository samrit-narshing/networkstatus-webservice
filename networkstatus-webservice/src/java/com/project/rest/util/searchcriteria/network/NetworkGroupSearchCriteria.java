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
public class NetworkGroupSearchCriteria {

    private Long id = 0L;
    private String searchedText = "";
    private Integer pageNo = 1;
    private Integer limitResult = 0;
    private int sortBy = 0;
    private String dateFrom = "";
    private String dateTo = "";
    private int fromDateTime = 0;
    private int toDateTime = 0;
    private String errSearchedText = "";
    private String networkGroupCode = "";
    private Boolean enableCreatedDateSearch = false;

    public Boolean getEnableCreatedDateSearch() {
        return enableCreatedDateSearch;
    }

    public void setEnableCreatedDateSearch(Boolean enableCreatedDateSearch) {
        this.enableCreatedDateSearch = enableCreatedDateSearch;
    }

    public String getTravelGroupCode() {
        return networkGroupCode;
    }

    public void setTravelGroupCode(String networkGroupCode) {
        this.networkGroupCode = networkGroupCode;
    }

    public int getSortBy() {
        return sortBy;
    }

    public void setSortBy(int sortBy) {
        this.sortBy = sortBy;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public int getFromDateTime() {
        return fromDateTime;
    }

    public void setFromDateTime(int fromDateTime) {
        this.fromDateTime = fromDateTime;
    }

    public int getToDateTime() {
        return toDateTime;
    }

    public void setToDateTime(int toDateTime) {
        this.toDateTime = toDateTime;
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

    public String getErrSearchedText() {
        return errSearchedText;
    }

    public void setErrSearchedText(String errSearchedText) {
        this.errSearchedText = errSearchedText;
    }

}
