package com.project.core.services.system;

import com.project.core.models.entities.system.ControlPanel;
import com.project.core.models.entities.system.NetworkInfo;
import com.project.core.models.entities.system.SystemInfo;
import com.project.core.models.entities.system.WebServerInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Samrit
 */
public interface SystemManagementService {

    public SystemInfo readSystemInfo() throws Exception;

    public NetworkInfo readNetworkInfo() throws Exception;

    public WebServerInfo readWebServerInfo(HttpServletRequest request, HttpServletResponse response) throws Exception;

    public String getCurrentPhysicalMemorystatus() throws Exception;

    public String getCurrentSwapMemorystatus() throws Exception;

    public String getCurrentJVMStatus() throws Exception;

    public ControlPanel readControlPanelInfo(HttpServletRequest request, HttpServletResponse response) throws Exception;

    public Integer forceGarbageCollectionServiceForWebServer() throws Exception;

}
