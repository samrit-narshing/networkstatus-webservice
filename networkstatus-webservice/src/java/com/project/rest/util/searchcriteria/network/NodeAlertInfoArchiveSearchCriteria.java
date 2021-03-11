/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.util.searchcriteria.network;

import com.project.rest.util.searchcriteria.util.*;

/**
 *
 * @author Samrit
 */
public class NodeAlertInfoArchiveSearchCriteria {

    private Long id = 0L;

    private String searchText = "";

    private Integer pageNo = 1;
    private Integer limitResult = 0;
    private Integer statusCode = 0;

    private String errSearchText = "";

    private String dateFrom = "";
    private String dateTo = "";
    private int hourFrom;
    private int hourTo;
    private int minuteFrom;
    private int minuteTo;
    private int fromDateTime;
    private int toDateTime;
    private int sortBy;

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

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

    public int getSortBy() {
        return sortBy;
    }

    public void setSortBy(int sortBy) {
        this.sortBy = sortBy;
    }

    public String getErrSearchText() {
        return errSearchText;
    }

    public void setErrSearchText(String errSearchText) {
        this.errSearchText = errSearchText;
    }

}
