/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.repositories.util;

import com.project.core.models.entities.util.ApplicationLogMessage;
import com.project.rest.util.searchcriteria.util.ApplicationLogMessageSearchCriteria;
import java.util.List;

/**
 *
 * @author Samrit
 */
public interface ApplicationLogMessageRepo {

    public ApplicationLogMessage createApplicationLogMessage(ApplicationLogMessage data);

    public ApplicationLogMessage findApplicationLogMessage(Long id);

    public List<ApplicationLogMessage> findAllApplicationLogMessage();

    public List<ApplicationLogMessage> findApplicationLogMessageBySearchCriteria(ApplicationLogMessageSearchCriteria searchCriteria);

    public Long countTotalDocumentsOfApplicationLogMessageBySearchCriteria(ApplicationLogMessageSearchCriteria searchCriteria);

    public ApplicationLogMessage deleteApplicationLogMessage(Long id);

    public int deleteAllApplicationLogMessage();

}
