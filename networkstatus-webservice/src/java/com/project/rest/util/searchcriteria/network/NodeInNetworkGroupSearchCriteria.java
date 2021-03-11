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
public class NodeInNetworkGroupSearchCriteria {

    private Long id = 0L;
    private String username = "";
    private Integer pageNo = 1;
    private Integer limitResult = 0;

    private Long networkGroupID = 0L;
    private String networkGroupCode = "";

    private Integer statusCode = 0;
    private int sortBy;

    private String dateFrom = "";
    private String dateTo = "";
    private int hourFrom;
    private int hourTo;
    private int minuteFrom;
    private int minuteTo;
    private int fromDateTime;
    private int toDateTime;

    public Long getNetworkGroupID() {
        return networkGroupID;
    }

    public void setNetworkGroupID(Long networkGroupID) {
        this.networkGroupID = networkGroupID;
    }

    public String getNetworkGroupCode() {
        return networkGroupCode;
    }

    public void setNetworkGroupCode(String networkGroupCode) {
        this.networkGroupCode = networkGroupCode;
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

    public int getHourFrom() {
        return hourFrom;
    }

    public void setHourFrom(int hourFrom) {
        this.hourFrom = hourFrom;
    }

    public int getHourTo() {
        return hourTo;
    }

    public void setHourTo(int hourTo) {
        this.hourTo = hourTo;
    }

    public int getMinuteFrom() {
        return minuteFrom;
    }

    public void setMinuteFrom(int minuteFrom) {
        this.minuteFrom = minuteFrom;
    }

    public int getMinuteTo() {
        return minuteTo;
    }

    public void setMinuteTo(int minuteTo) {
        this.minuteTo = minuteTo;
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

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public int getSortBy() {
        return sortBy;
    }

    public void setSortBy(int sortBy) {
        this.sortBy = sortBy;
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
