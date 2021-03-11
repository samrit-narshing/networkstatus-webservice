/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.models.entities.system;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Samrit
 */
public class OperatingSystem_ValueHolder {

    private static final HashMap<String, Process> processTable = new HashMap<String, Process>();
    private static final HashMap<String, List<String>> outputStatusTable = new HashMap<String, List<String>>();
    

    public static HashMap<String, Process> getProcessTable() {
        return processTable;
    }

    public static HashMap<String, List<String>> getOutputStatusTable() {
        return outputStatusTable;
    }

}
