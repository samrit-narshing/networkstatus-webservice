/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.mvc.web;

/**
 *
 * @author samri_g64pbd3
 */
public class ChartDataTwo {

    private int numId;

    private String id;
    private int height;
    ChartDataTwoFill fill;
    private String link;

    private Boolean alert = false;

    public Boolean getAlert() {
        return alert;
    }

    public void setAlert(Boolean alert) {
        this.alert = alert;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ChartDataTwoFill getFill() {
        return fill;
    }

    public void setFill(ChartDataTwoFill fill) {
        this.fill = fill;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getNumId() {
        return numId;
    }

    public void setNumId(int numId) {
        this.numId = numId;
    }

}
