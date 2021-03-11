/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.scheduler;
//import org.springframework.scheduling.annotation.Scheduled;

import com.project.core.models.entities.util.SchedulerTask;
import com.project.core.security.PropertiesConfig;
import com.project.core.services.user.DeviceUserService;
import com.project.core.services.util.SchedulerTaskService;
import com.project.core.util.DateConverter;
import com.project.core.util.FCM_RealtimeDBUtil;
import com.project.rest.util.Global;
import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author Samrit
 */
@Component
public class SchedulerConfig {


    @Autowired
    SchedulerTaskService schedulerTaskService;

    @Autowired
    DeviceUserService deviceUserService;

    @Autowired
    @Resource(name = "propertiesConfig")
    private PropertiesConfig propertiesConfig;

//        @Scheduled(cron = "*/30 * * * * ?")
    @Scheduled(cron = "0 */15 * * * ?")
    public void syncGeolocationToIndexScheduler() {
        System.out.println("Initiating Job Schedule For Node Update. MOMO");
        try {
            try {
                SchedulerTask task = schedulerTaskService.findSchedulerTaskByName(Global.SCHEDULE_NODE_SYNC);
                if (task.getActive()) {
                    System.out.println("Syncing....");
//                    geoLocationService.syncWithElasticSearchGeoLocationWithAdvanceBulkProcessing();
                    System.out.println("Syncing Completed");
                } else {
                    System.out.println("Job Schedule For Syncing Is Not Activated. ");
                }
            } catch (NonUniqueResultException uniqueResultException) {
                System.out.println("Cleaning ALL");
                schedulerTaskService.deleteSchedulerTaskByName(Global.SCHEDULE_NODE_SYNC);
                schedulerTaskService.createSchedulerTask(Global.getInActiveSchedulerTaskObject_NODE_SYNC());
            } catch (NoResultException noResultException) {
                System.out.println("Inserting");
                schedulerTaskService.createSchedulerTask(Global.getInActiveSchedulerTaskObject_NODE_SYNC());
            } catch (Exception e) {
                schedulerTaskService.deleteSchedulerTaskByName(Global.SCHEDULE_NODE_SYNC);
                schedulerTaskService.createSchedulerTask(Global.getInActiveSchedulerTaskObject_NODE_SYNC());
            }
        } catch (Exception e) {

        }
    }

   

}
