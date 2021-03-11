package com.project.core.services.util;

import com.project.core.models.entities.util.ApplicationLogMessage;
import com.project.rest.util.searchcriteria.util.ApplicationLogMessageSearchCriteria;
import java.util.List;

/**
 *
 * @author Samrit
 */
public interface ApplicationLogMessageService {

    public ApplicationLogMessage createApplicationLogMessage(ApplicationLogMessage data) throws Exception;

    public ApplicationLogMessage findApplicationLogMessage(Long id) throws Exception;

    public ApplicationLogMessageList findAllApplicationLogMessage() throws Exception;

    public ApplicationLogMessageList findApplicationLogMessageBySearchCriteria(ApplicationLogMessageSearchCriteria searchCriteria) throws Exception;

    public Long countTotalDocumentsOfApplicationLogMessageBySearchCriteria(ApplicationLogMessageSearchCriteria searchCriteria) throws Exception;

    public ApplicationLogMessage deleteApplicationLogMessage(Long id) throws Exception;

    public int deleteAllApplicationLogMessage() throws Exception;

}
