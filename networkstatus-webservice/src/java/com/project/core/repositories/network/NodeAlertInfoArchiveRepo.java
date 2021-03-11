/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.repositories.network;

import com.project.core.models.entities.network.NodeAlertInfoArchive;
import com.project.rest.util.searchcriteria.network.NodeAlertInfoArchiveSearchCriteria;
import java.util.List;

/**
 *
 * @author Samrit
 */
public interface NodeAlertInfoArchiveRepo {

    public NodeAlertInfoArchive createNodeAlertInfoArchive(NodeAlertInfoArchive data);

    public NodeAlertInfoArchive findNodeAlertInfoArchive(Long id);

    public List<NodeAlertInfoArchive> findAllNodeAlertInfoArchive();

    public List<NodeAlertInfoArchive> findNodeAlertInfoArchiveBySearchCriteria(NodeAlertInfoArchiveSearchCriteria searchCriteria);

    public Long countTotalDocumentsOfNodeAlertInfoArchiveBySearchCriteria(NodeAlertInfoArchiveSearchCriteria searchCriteria);

    public NodeAlertInfoArchive deleteNodeAlertInfoArchive(Long id);

    public int deleteAllNodeAlertInfoArchive();

}
