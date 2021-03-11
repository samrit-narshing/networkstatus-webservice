/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.services.impl.system;

import com.project.core.models.entities.system.ControlPanel;
import com.project.core.models.entities.system.NetworkInfo;
import com.project.core.models.entities.system.OperatingSystem;
import com.project.core.models.entities.system.OperatingSystem_CPUStatus;
import com.project.core.models.entities.system.OperatingSystem_JVM;
import com.project.core.models.entities.system.OperatingSystem_PhysicalMemory;
import com.project.core.models.entities.system.OperatingSystem_SwapMemory;
import com.project.core.models.entities.system.SystemInfo;
import com.project.core.models.entities.system.WebServerInfo;
import com.project.core.models.entities.user.User;
import com.project.core.repositories.user.UserRepo;
import com.project.core.security.PropertiesConfig;
import com.project.core.services.system.SystemManagementService;
import com.project.core.util.DateConverter;
import java.text.DecimalFormat;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import license.LicenseManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Samrit
 */
@Service
@Transactional
public class SystemManagementServiceImpl implements SystemManagementService {

    @Autowired
    @Resource(name = "propertiesConfig")
    private PropertiesConfig propertiesConfig;

    @Autowired
    private UserRepo userRepo;

    @Override
    public SystemInfo readSystemInfo() throws Exception {
        SystemInfo systemInfo = new SystemInfo();
//        systemInfo.setPhysicalMemory("100S MB", "200S MB", "300S MB");
//        systemInfo.setSwapMemory("400S MB", "500S MB", "600S MB");
//        systemInfo.setCpu("20S%");

        OperatingSystem information = new OperatingSystem();
        String osName = OperatingSystem.getOsName();
        String osArchitecture = OperatingSystem.getOsArchitecture();
        String osVersion = OperatingSystem.getOsVersion();
        systemInfo.setOs(osName, osArchitecture, osVersion);

        OperatingSystem_PhysicalMemory physicalMemoryStatsModel = information.getPhysicalRAMStatusInMB();
        DecimalFormat df = new DecimalFormat("#.##");

        String physcialUsedMemory = df.format((physicalMemoryStatsModel.getUsed() / 1024)) + "GB";
        String physcialTotalMemory = df.format((physicalMemoryStatsModel.getTotal() / 1024)) + "GB";
        String physcialAvailableMemory = df.format(((physicalMemoryStatsModel.getTotal() - physicalMemoryStatsModel.getUsed()) / 1024)) + "GB";
        systemInfo.setPhysicalMemory(physcialTotalMemory, physcialUsedMemory, physcialAvailableMemory);

        OperatingSystem_SwapMemory swapMemoryStatsModel = information.getSWAPRAMStatusInMB();
        String swapAvailableMemory = df.format((swapMemoryStatsModel.getFree() / 1024)) + "GB";
        String swapTotalMemory = df.format((swapMemoryStatsModel.getTotal() / 1024)) + "GB";
        String swapUsedMemory = df.format(((swapMemoryStatsModel.getTotal() - swapMemoryStatsModel.getFree()) / 1024)) + "GB";
        systemInfo.setSwapMemory(swapTotalMemory, swapUsedMemory, swapAvailableMemory);
//
        OperatingSystem_CPUStatus cPUStatsModel = information.getCPUUsageStatus();
        systemInfo.setCpu(df.format(cPUStatsModel.getUsage()) + "%");

        String serverUTCDateTime = DateConverter.getCurrentUTCDateTimeFormat2();
        String serverLocalDateTime = DateConverter.getCurrentDateTimeFormat2();
        String serverServerTimeZone = DateConverter.getLocalTimeZoneID();
        systemInfo.setDateTime(serverServerTimeZone, serverLocalDateTime, serverUTCDateTime);
        return systemInfo;
    }

    @Override
    public NetworkInfo readNetworkInfo() throws Exception {
        NetworkInfo networkInfo = new NetworkInfo();
        networkInfo.setMac(LicenseManagement.getAnyInterfaceMacAddressExceptBridge());
        networkInfo.setHostID(LicenseManagement.getHostId());
        return networkInfo;
    }

    @Override
    public WebServerInfo readWebServerInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        WebServerInfo webServerInfo = new WebServerInfo();

        OperatingSystem information = new OperatingSystem();
        OperatingSystem_JVM jVMModel = information.getJVMInformationModel();

        webServerInfo.setJava(jVMModel.getJavaVersion());

        String jvmUsedMemory = String.valueOf(jVMModel.getUsedMemory().intValue()) + " MB";
        String jvmFreeMemory = String.valueOf(jVMModel.getFreeMemory().intValue()) + " MB";
        String jvmTotalMemory = String.valueOf(jVMModel.getTotalMemory().intValue()) + " MB";
        String jvmMaxMemory = jVMModel.getMaxMemory() + " MB";
        webServerInfo.setJvmMemory(jvmMaxMemory, jvmTotalMemory, jvmUsedMemory, jvmFreeMemory);

        String webServerApplicationVersion = propertiesConfig.getProperty("app_version");
        String webServerApplicationVersionExpiryDate = LicenseManagement.getExpireyDate(request);
        webServerInfo.setSimCLogger(webServerApplicationVersion, webServerApplicationVersionExpiryDate);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("PRINCI >>>>>>>>> " + principal);
        if (principal instanceof UserDetails) {
            UserDetails details = (UserDetails) principal;
            System.out.println(details.getUsername() + "USER DETAILLLLLLLLLLL");
            User user = userRepo.findUserByUsername(details.getUsername());
            webServerInfo.setUserLicenseDetail(details.getUsername(), user.isNeverExpire() ? "Never Expires" : user.getAccountExpiration());
        }

        return webServerInfo;
    }

    @Override
    public String getCurrentPhysicalMemorystatus() throws Exception {
        OperatingSystem information = new OperatingSystem();
        OperatingSystem_PhysicalMemory physicalMemoryStatsModel = information.getPhysicalRAMStatusInMB();

//        For Testing        
//        OperatingSystem_PhysicalMemory physicalMemoryStatsModel = new OperatingSystem_PhysicalMemory();
//        physicalMemoryStatsModel.setFree(10000 * Math.random());
//        physicalMemoryStatsModel.setTotal(new Double(10000));
        DecimalFormat df = new DecimalFormat("#.##");
        long percent = Math.round((physicalMemoryStatsModel.getUsed() / physicalMemoryStatsModel.getTotal()) * 100);
        System.out.println(percent);

        String physicalRamStatus = percent + ";" + df.format((physicalMemoryStatsModel.getUsed() / 1024)) + "GB" + ";" + df.format((physicalMemoryStatsModel.getTotal() / 1024)) + "GB";
        return physicalRamStatus;
    }

    @Override
    public String getCurrentSwapMemorystatus() throws Exception {
        OperatingSystem information = new OperatingSystem();
        OperatingSystem_SwapMemory swapMemoryStatsModel = information.getSWAPRAMStatusInMB();

//        OperatingSystem_SwapMemory swapMemoryStatsModel = new OperatingSystem_SwapMemory();
//        swapMemoryStatsModel.setFree(10000 * Math.random());
//        swapMemoryStatsModel.setTotal(new Double(10000));
        DecimalFormat df = new DecimalFormat("#.##");
        long percent = Math.round((swapMemoryStatsModel.getUsed() / swapMemoryStatsModel.getTotal()) * 100);
        System.out.println(percent);
        return (percent + ";" + df.format((swapMemoryStatsModel.getUsed() / 1024)) + "GB" + ";" + df.format((swapMemoryStatsModel.getTotal() / 1024)) + "GB");

    }

    @Override
    public String getCurrentJVMStatus() throws Exception {
        OperatingSystem information = new OperatingSystem();
        OperatingSystem_JVM jVMModel = information.getJVMInformationModel();
        String jvmStatus = jVMModel.getUsedMemory().intValue() + ";" + jVMModel.getFreeMemory().intValue() + ";" + jVMModel.getTotalMemory().intValue() + ";" + jVMModel.getMaxMemory();
        return jvmStatus;
    }

    @Override
    public ControlPanel readControlPanelInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ControlPanel controlPanel = new ControlPanel();
        String webServerApplicationVersion = propertiesConfig.getProperty("app_version");
        String hostID = LicenseManagement.getHostId();
        String webServerApplicationVersionExpiryDate = LicenseManagement.getExpireyDate(request);
        String macAddress = LicenseManagement.getAnyInterfaceMacAddressExceptBridge();

        controlPanel.setAppVersion(webServerApplicationVersion);
        controlPanel.setHostId(hostID);
        controlPanel.setLicenseExpiryDate(webServerApplicationVersionExpiryDate);
        controlPanel.setMacAddress(macAddress);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("PRINCI >>>>>>>>> " + principal);
        if (principal instanceof UserDetails) {
            UserDetails details = (UserDetails) principal;
            User user = userRepo.findUserByUsername(details.getUsername());
            controlPanel.setUsername(details.getUsername());
            controlPanel.setUserLicenseExpiryDate(user.isNeverExpire() ? "Never Expires" : user.getAccountExpiration());
        }
        return controlPanel;
    }

    @Override
    public Integer forceGarbageCollectionServiceForWebServer() throws Exception {
        int status = 1;
        Runtime.getRuntime().gc();
        System.gc();
        System.runFinalization();
        return status;
    }

}
