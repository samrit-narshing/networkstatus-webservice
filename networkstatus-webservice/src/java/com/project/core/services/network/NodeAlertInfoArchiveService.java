package com.project.core.services.network;

import com.project.core.models.entities.network.NodeAlertInfoArchive;
import com.project.core.services.util.*;
import com.project.rest.util.searchcriteria.network.NodeAlertInfoArchiveSearchCriteria;

import java.util.List;

/**
 *
 * @author Samrit
 */
public interface NodeAlertInfoArchiveService {

    public NodeAlertInfoArchive createNodeAlertInfoArchive(NodeAlertInfoArchive data) throws Exception;

    public NodeAlertInfoArchive findNodeAlertInfoArchive(Long id) throws Exception;

    public NodeAlertInfoArchiveList findAllNodeAlertInfoArchive() throws Exception;

    public NodeAlertInfoArchiveList findNodeAlertInfoArchiveBySearchCriteria(NodeAlertInfoArchiveSearchCriteria searchCriteria) throws Exception;

    public Long countTotalDocumentsOfNodeAlertInfoArchiveBySearchCriteria(NodeAlertInfoArchiveSearchCriteria searchCriteria) throws Exception;

    public NodeAlertInfoArchive deleteNodeAlertInfoArchive(Long id) throws Exception;

    public int deleteAllNodeAlertInfoArchive() throws Exception;

}
