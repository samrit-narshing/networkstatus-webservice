package com.project.core.services.util;

import com.project.core.models.entities.util.SchedulerTask;
import java.util.List;

/**
 *
 * @author Samrit
 */
public interface SchedulerTaskService {

    public SchedulerTask createSchedulerTask(SchedulerTask data) throws Exception;

    public SchedulerTask findSchedulerTask(Long id) throws Exception;

    public SchedulerTask findSchedulerTaskByName(String name) throws Exception;

    public List<SchedulerTask> findAllSchedulerTask() throws Exception;

    public SchedulerTask updateSchedulerTask(Long id, SchedulerTask data) throws Exception;

    public SchedulerTask deleteSchedulerTask(Long id) throws Exception;

    public int deleteSchedulerTaskByName(String name) throws Exception;

}
