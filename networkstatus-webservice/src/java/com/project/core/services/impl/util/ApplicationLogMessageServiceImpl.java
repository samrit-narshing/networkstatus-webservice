/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.services.impl.util;

import com.project.core.models.entities.util.ApplicationLogMessage;
import com.project.core.repositories.util.ApplicationLogMessageRepo;
import com.project.core.services.util.ApplicationLogMessageList;
import com.project.core.services.util.ApplicationLogMessageService;
import com.project.rest.util.searchcriteria.util.ApplicationLogMessageSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Samrit
 */
@Service
@Transactional
public class ApplicationLogMessageServiceImpl implements ApplicationLogMessageService {

    @Autowired
    private ApplicationLogMessageRepo applicationLogMessageRepo;

    @Override
    public ApplicationLogMessage createApplicationLogMessage(ApplicationLogMessage data) throws Exception {
        return applicationLogMessageRepo.createApplicationLogMessage(data);
    }

    @Override
    public ApplicationLogMessage findApplicationLogMessage(Long id) throws Exception {
        return applicationLogMessageRepo.findApplicationLogMessage(id);
    }

    @Override
    public ApplicationLogMessageList findAllApplicationLogMessage() throws Exception {
        return new ApplicationLogMessageList(applicationLogMessageRepo.findAllApplicationLogMessage());
    }

    @Override
    public ApplicationLogMessageList findApplicationLogMessageBySearchCriteria(ApplicationLogMessageSearchCriteria searchCriteria) throws Exception {
        Long totalDocuments = countTotalDocumentsOfApplicationLogMessageBySearchCriteria(searchCriteria);
        Long totalPages = (long) Math.ceil(totalDocuments / searchCriteria.getLimitResult().doubleValue());
        ApplicationLogMessageList applicationLogMessageList = new ApplicationLogMessageList(applicationLogMessageRepo.findApplicationLogMessageBySearchCriteria(searchCriteria), totalDocuments, totalPages);
        return applicationLogMessageList;
    }

    @Override
    public Long countTotalDocumentsOfApplicationLogMessageBySearchCriteria(ApplicationLogMessageSearchCriteria searchCriteria) throws Exception {
        return applicationLogMessageRepo.countTotalDocumentsOfApplicationLogMessageBySearchCriteria(searchCriteria);
    }

    @Override
    public ApplicationLogMessage deleteApplicationLogMessage(Long id) throws Exception {
        return applicationLogMessageRepo.deleteApplicationLogMessage(id);
    }

    @Override
    public int deleteAllApplicationLogMessage() throws Exception {
        return applicationLogMessageRepo.deleteAllApplicationLogMessage();
    }

}
