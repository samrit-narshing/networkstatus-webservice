package com.project.core.services.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samrit
 */
public class StringList {

    private Long totalPages = new Long(0);
    private Long totalDocuments = new Long(0);

    private List<String> list = new ArrayList<>();

    public StringList(List<String> list) {
        this.list = list;
    }

    public StringList(List<String> list, Long totalDocuments, Long totalPages) {
        this.list = list;
        this.totalPages = totalPages;
        this.totalDocuments = totalDocuments;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalDocuments() {
        return totalDocuments;
    }

    public void setTotalDocuments(Long totalDocuments) {
        this.totalDocuments = totalDocuments;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

}
