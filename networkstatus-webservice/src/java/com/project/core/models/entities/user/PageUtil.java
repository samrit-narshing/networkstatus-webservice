/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.models.entities.user;

/**
 *
 * @author Samrit
 */
import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;

public class PageUtil implements Serializable {

    private Long pageCount;

    public Long getPageCount() {
        return pageCount;
    }

    public void setPageCount(Long pageCount) {
        this.pageCount = pageCount;
    }

}
