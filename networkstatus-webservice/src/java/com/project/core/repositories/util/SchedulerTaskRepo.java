/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.repositories.util;

import com.project.core.models.entities.util.SchedulerTask;
import java.util.List;

/**
 *
 * @author Samrit
 */
public interface SchedulerTaskRepo {

    public SchedulerTask createSchedulerTask(SchedulerTask data);

    public SchedulerTask findSchedulerTask(Long id);

    public SchedulerTask findSchedulerTaskByName(String name);

    public List<SchedulerTask> findAllSchedulerTask();

    public SchedulerTask updateSchedulerTask(Long id, SchedulerTask data);

    public SchedulerTask deleteSchedulerTask(Long id);

    public int deleteSchedulerTaskByName(String name);

}
