package com.project.core.services.util;

import com.project.core.models.entities.util.ApplicationLogMessage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samrit
 */
public class ApplicationLogMessageList {

    private Long totalPages = new Long(0);
    private Long totalDocuments = new Long(0);

    private List<ApplicationLogMessage> applicationLogMessages = new ArrayList<>();

    public ApplicationLogMessageList(List<ApplicationLogMessage> applicationLogMessages) {
        this.applicationLogMessages = applicationLogMessages;
    }

    public ApplicationLogMessageList(List<ApplicationLogMessage> applicationLogMessages, Long totalDocuments, Long totalPages) {
        this.applicationLogMessages = applicationLogMessages;
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

    public List<ApplicationLogMessage> getApplicationLogMessages() {
        return applicationLogMessages;
    }

    public void setApplicationLogMessages(List<ApplicationLogMessage> pushMessages) {
        this.applicationLogMessages = pushMessages;
    }

}
