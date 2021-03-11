/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.util;

import com.project.core.models.entities.util.SchedulerTask;

/**
 *
 * @author Samrit
 */
public class Global {

    final public static boolean ONLY_FOR_SIAMSECURE = true;

    final public static String SCHEDULE_NODE_SYNC = "SCHEDULE_NODE_STATUS_SYNC";
    final public static String SCHEDULE_EDGE_SYNC = "SCHEDULE_EDGE_STATUS_SYNC";

    //    final public static String SYSTEM_DEFAULT_ADMIN_USER = "SYSTEM_ADMIN_USER";
    final public static String SYSTEM_DEFAULT_ADMIN_USER_TYPE = "SYSTEM_USER";
    final public static String SYSTEM_DEFAULT_NO_OPERATOR_USER = "NO_OPERATOR_USER";
    final public static String SYSTEM_DEFAULT_NO_OPERATOR_USER_TYPE = "NO_OPERATOR_USER_TYPE";

    public static SchedulerTask getInActiveSchedulerTaskObject_NODE_SYNC() {
        SchedulerTask schedulerTask = new SchedulerTask();
        schedulerTask.setActive(Boolean.FALSE);
        schedulerTask.setName(Global.SCHEDULE_NODE_SYNC);
        return schedulerTask;
    }

}
