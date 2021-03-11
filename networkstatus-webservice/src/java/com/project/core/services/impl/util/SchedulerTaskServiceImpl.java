/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.services.impl.util;

import com.project.core.models.entities.util.SchedulerTask;
import com.project.core.repositories.util.SchedulerTaskRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.project.core.services.util.SchedulerTaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Samrit
 */
@Service
@Transactional
public class SchedulerTaskServiceImpl implements SchedulerTaskService {

    @Autowired
    private SchedulerTaskRepo schedulerTaskRepo;

    @Override
    public SchedulerTask createSchedulerTask(SchedulerTask data) throws Exception {
        return schedulerTaskRepo.createSchedulerTask(data);
    }

    @Override
    public SchedulerTask findSchedulerTask(Long id) throws Exception {
        return schedulerTaskRepo.findSchedulerTask(id);
    }

    @Override
    public SchedulerTask findSchedulerTaskByName(String name) throws Exception {
        return schedulerTaskRepo.findSchedulerTaskByName(name);
    }

    @Override
    public List<SchedulerTask> findAllSchedulerTask() throws Exception {
        return schedulerTaskRepo.findAllSchedulerTask();
    }

    @Override
    public SchedulerTask updateSchedulerTask(Long id, SchedulerTask data) throws Exception {
        return schedulerTaskRepo.updateSchedulerTask(id, data);
    }

    @Override
    public SchedulerTask deleteSchedulerTask(Long id) throws Exception {
        return schedulerTaskRepo.deleteSchedulerTask(id);
    }

    @Override
    public int deleteSchedulerTaskByName(String name) throws Exception {
        return schedulerTaskRepo.deleteSchedulerTaskByName(name);
    }

}
