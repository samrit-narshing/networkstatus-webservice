/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.models.entities.system;

import java.util.List;
import com.project.core.util.*;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 *
 * @author Samrit
 */
public class OperatingSystem {

    final static double gb = 1024 * 1014 * 1024;
    final int mb = 1024 * 1024;

    private static String OS = null;

    public static String getOsName() {
        if (OS == null) {
            OS = System.getProperty("os.name");
        }
        return OS;
    }

    public static String getOsArchitecture() {
        return System.getProperty("os.arch");
    }

    public static String getOsVersion() {
        return System.getProperty("os.version");
    }

    public static boolean isWindows() {
        return getOsName().startsWith("Windows");
    }

    public static boolean isLinux() {
        return getOsName().equalsIgnoreCase("Linux");
    }

    public OperatingSystem_JVM getJVMInformationModel() {
        //Getting the runtime reference from system
        Runtime runtime = Runtime.getRuntime();
        OperatingSystem_JVM operatingSystem_JVM = new OperatingSystem_JVM();
        String javaVersion = Runtime.class.getPackage().getImplementationVersion();
//        String javaVersion = System.getProperty("java.version");
        operatingSystem_JVM.setJavaVersion(javaVersion);
        //Print used memory
        operatingSystem_JVM.setFreeMemory((double) (runtime.freeMemory() / mb));
        operatingSystem_JVM.setMaxMemory(runtime.maxMemory() == Long.MAX_VALUE ? "no limit" : String.valueOf((runtime.maxMemory() / mb)));
        operatingSystem_JVM.setTotalMemory((double) (runtime.totalMemory() / mb));
        operatingSystem_JVM.setUsedMemory((double) ((runtime.totalMemory() - runtime.freeMemory()) / mb));

        return operatingSystem_JVM;
    }

    public OperatingSystem_PhysicalMemory getPhysicalRAMStatusInMB() {
        OperatingSystem_PhysicalMemory memoryStats = new OperatingSystem_PhysicalMemory();
        Double totalRAM = 0d;
        Double freeRAM = 0d;
        if (isWindows()) {
            OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
            for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
                method.setAccessible(true);
                if (method.getName().startsWith("get")
                        && Modifier.isPublic(method.getModifiers())) {
                    Object value;
                    try {
                        value = method.invoke(operatingSystemMXBean);
                    } catch (Exception e) {
                        value = e;
                    } // try
                    if (method.getName().equalsIgnoreCase("getTotalPhysicalMemorySize")) {
                        totalRAM = (double) ((long) value / (1024 * 1024));
                    }

                    if (method.getName().equalsIgnoreCase("getFreePhysicalMemorySize")) {
                        freeRAM = (double) ((long) value / (1024 * 1024));
                    }
                    System.out.println(method.getName() + " = " + value);
                } // if
            } // for
        } else if (isLinux()) {
            System.out.println("FOR CENT OS ***************");

//            String command = "top -n 1";
            String command = "free -h | awk '/Mem\\:/'";

//            String regx = "(?i)Swap:\\s+(\\d+)(.*)\\s+(\\d*)(.*?)\\s.*";
            String regx = "(?i)Mem:\\s+(\\d+(\\.\\d{1,2})?)(.*?)\\s+(\\d+(\\.\\d{1,2})?)(.*?)\\s+(\\d+(\\.\\d{1,2})?)(.*?)\\s+(.*)";
            List<String> resultList = new CmdExec().run_with_shell_output(command);

            Double totalRAMSize = new Double(0.0);
            String totalRAMSizeUnit = "";
            Double freeRAMSize = new Double(0.0);
            String freeRAMSizeUnit = "";

            try {
                for (String str : resultList) {
                    if (str.matches(regx)) {
                        totalRAMSize = Double.parseDouble(new RegEx_Tool().capture(regx, str, 1));
                        totalRAMSizeUnit = new RegEx_Tool().capture(regx, str, 3);
                        freeRAMSize = Double.parseDouble(new RegEx_Tool().capture(regx, str, 7));
                        freeRAMSizeUnit = new RegEx_Tool().capture(regx, str, 9);
                        break;
                    }
                }

                if (totalRAMSizeUnit.trim().equalsIgnoreCase("M") || totalRAMSizeUnit.trim().equalsIgnoreCase("MB")) {
                    totalRAM = totalRAMSize;
                } else if (totalRAMSizeUnit.trim().equalsIgnoreCase("K") || totalRAMSizeUnit.trim().equalsIgnoreCase("KB")) {
                    totalRAM = (totalRAMSize / 1024);
                } else if (totalRAMSizeUnit.trim().equalsIgnoreCase("G") || totalRAMSizeUnit.trim().equalsIgnoreCase("GB")) {
                    totalRAM = (totalRAMSize * 1024);
                }

                if (freeRAMSizeUnit.trim().equalsIgnoreCase("M") || freeRAMSizeUnit.trim().equalsIgnoreCase("MB")) {
                    freeRAM = freeRAMSize;
                } else if (freeRAMSizeUnit.trim().equalsIgnoreCase("K") || freeRAMSizeUnit.trim().equalsIgnoreCase("KB")) {
                    freeRAM = (freeRAMSize / 1024);
                } else if (freeRAMSizeUnit.trim().equalsIgnoreCase("G") || freeRAMSizeUnit.trim().equalsIgnoreCase("GB")) {
                    freeRAM = (freeRAMSize * 1024);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            totalRAM = getTotalMemoryRAM();
            freeRAM = getFreeMemoryRAM();
        }

        memoryStats.setTotal(totalRAM);
        memoryStats.setFree(freeRAM);
        return memoryStats;

    }

    public Double getTotalMemoryRAM() {

        String regx = "hw.physmem:.*?(\\d*)$";
        String command = "sysctl hw.physmem";
        //List<String> resultList = getDummyResultTotalRAM();
        List<String> resultList = new CmdExec().run_with_shell_output(command);

        Double ramSize = new Double(0.0);
        try {
            for (String str : resultList) {
                if (str.matches(regx)) {
                    System.out.println(str);
                    ramSize = Double.parseDouble(new RegEx_Tool().capture(regx, str, 1));
                    ramSize = ramSize / (1024 * 1024);
                    System.out.println(ramSize);
                    // System.out.println(capture(regx, str, 2));
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ramSize;
    }

    public Double getFreeMemoryRAM() {

        Double freeRAM = new Double(0);
        // String regx = "(?i)Mem:.*\\s(\\d+)(.*)FRee";
        String regx = "(?i)Mem:.*\\s+(\\d+)(.*)\\s+Free.*";

        String command = "top -d1 0";

//         List<String> resultList = getDummyResult();
//        resultList = getDummyResultII();
        List<String> resultList = new CmdExec().run_with_shell_output(command);

        String sizeUnit = "";
        try {
            for (String str : resultList) {

                if (str.matches(regx)) {
                    System.out.println(str);
                    System.out.println(new RegEx_Tool().capture(regx, str, 1));
                    System.out.println(new RegEx_Tool().capture(regx, str, 2));
                    freeRAM = Double.parseDouble(new RegEx_Tool().capture(regx, str, 1));
                    sizeUnit = new RegEx_Tool().capture(regx, str, 2);
                    break;
                }
            }

            if (sizeUnit.trim().equalsIgnoreCase("M") || sizeUnit.trim().equalsIgnoreCase("MB")) {
                return freeRAM;
            } else if (sizeUnit.trim().equalsIgnoreCase("K") || sizeUnit.trim().equalsIgnoreCase("KB")) {
                return (freeRAM / 1024);
            } else if (sizeUnit.trim().equalsIgnoreCase("G") || sizeUnit.trim().equalsIgnoreCase("GB")) {
                return (freeRAM * 1024);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return freeRAM;
    }

    public OperatingSystem_SwapMemory getSWAPRAMStatusInMB() {

        OperatingSystem_SwapMemory memoryStats = new OperatingSystem_SwapMemory();
        Double totalSWAPRAM = (double) 0;
        Double freeSWAPRAM = (double) 0;
        if (isWindows()) {
            OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
            for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
                method.setAccessible(true);
                if (method.getName().startsWith("get")
                        && Modifier.isPublic(method.getModifiers())) {
                    Object value;
                    try {
                        value = method.invoke(operatingSystemMXBean);
                    } catch (Exception e) {
                        value = e;
                    } // try
                    if (method.getName().equalsIgnoreCase("getTotalSwapSpaceSize")) {
                        totalSWAPRAM = (double) ((long) value / (1024 * 1024));
                    }

                    if (method.getName().equalsIgnoreCase("getFreeSwapSpaceSize")) {
                        freeSWAPRAM = (double) ((long) value / (1024 * 1024));
                    }
                    System.out.println(method.getName() + " = " + value);
                } // if
            } // for
        } else if (isLinux()) {
            System.out.println("FOR CENT OS ***************");

//            String command = "top -n 1";
            String command = "free -h | awk '/Swap\\:/'";

            String regx = "(?i)Swap:\\s+(\\d+(\\.\\d{1,2})?)(.*?)\\s+(\\d+(\\.\\d{1,2})?)(.*?)\\s+(\\d+(\\.\\d{1,2})?)(.*?)";

            List<String> resultList = new CmdExec().run_with_shell_output(command);

            Double totalSwapSize = new Double(0.0);
            String totalSwapSizeUnit = "";
            Double freeSwapSize = new Double(0.0);
            String freeSwapSizeUnit = "";

            try {
                for (String str : resultList) {
                    if (str.matches(regx)) {
                        totalSwapSize = Double.parseDouble(new RegEx_Tool().capture(regx, str, 1));
                        totalSwapSizeUnit = new RegEx_Tool().capture(regx, str, 3);
                        freeSwapSize = Double.parseDouble(new RegEx_Tool().capture(regx, str, 7));
                        freeSwapSizeUnit = new RegEx_Tool().capture(regx, str, 9);
                        break;
                    }
                }

                if (totalSwapSizeUnit.trim().equalsIgnoreCase("M") || totalSwapSizeUnit.trim().equalsIgnoreCase("MB")) {
                    totalSWAPRAM = totalSwapSize;
                } else if (totalSwapSizeUnit.trim().equalsIgnoreCase("K") || totalSwapSizeUnit.trim().equalsIgnoreCase("KB")) {
                    totalSWAPRAM = (totalSwapSize / 1024);
                } else if (totalSwapSizeUnit.trim().equalsIgnoreCase("G") || totalSwapSizeUnit.trim().equalsIgnoreCase("GB")) {
                    totalSWAPRAM = (totalSwapSize * 1024);
                }

                if (freeSwapSizeUnit.trim().equalsIgnoreCase("M") || freeSwapSizeUnit.trim().equalsIgnoreCase("MB")) {
                    freeSWAPRAM = freeSwapSize;
                } else if (freeSwapSizeUnit.trim().equalsIgnoreCase("K") || freeSwapSizeUnit.trim().equalsIgnoreCase("KB")) {
                    freeSWAPRAM = (freeSwapSize / 1024);
                } else if (freeSwapSizeUnit.trim().equalsIgnoreCase("G") || freeSwapSizeUnit.trim().equalsIgnoreCase("GB")) {
                    freeSWAPRAM = (freeSwapSize * 1024);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            String regx = "(?i)Swap:\\s+(\\d+)(.*)\\s+Total.*\\s+(\\d*)(.*?)\\sFree.*";
            String command = "top -d1 0";

            List<String> resultList = new CmdExec().run_with_shell_output(command);

            Double totalSwapSize = new Double(0.0);
            String totalSwapSizeUnit = "";
            Double freeSwapSize = new Double(0.0);
            String freeSwapSizeUnit = "";

            try {
                for (String str : resultList) {
                    if (str.matches(regx)) {
                        System.out.println(str);
                        totalSwapSize = Double.parseDouble(new RegEx_Tool().capture(regx, str, 1));
                        totalSwapSizeUnit = new RegEx_Tool().capture(regx, str, 2);
                        freeSwapSize = Double.parseDouble(new RegEx_Tool().capture(regx, str, 3));
                        freeSwapSizeUnit = new RegEx_Tool().capture(regx, str, 4);
                        break;
                    }
                }

                if (totalSwapSizeUnit.trim().equalsIgnoreCase("M") || totalSwapSizeUnit.trim().equalsIgnoreCase("MB")) {
                    totalSWAPRAM = totalSwapSize;
                } else if (totalSwapSizeUnit.trim().equalsIgnoreCase("K") || totalSwapSizeUnit.trim().equalsIgnoreCase("KB")) {
                    totalSWAPRAM = (totalSwapSize / 1024);
                } else if (totalSwapSizeUnit.trim().equalsIgnoreCase("G") || totalSwapSizeUnit.trim().equalsIgnoreCase("GB")) {
                    totalSWAPRAM = (totalSwapSize * 1024);
                }

                if (freeSwapSizeUnit.trim().equalsIgnoreCase("M") || freeSwapSizeUnit.trim().equalsIgnoreCase("MB")) {
                    freeSWAPRAM = freeSwapSize;
                } else if (freeSwapSizeUnit.trim().equalsIgnoreCase("K") || freeSwapSizeUnit.trim().equalsIgnoreCase("KB")) {
                    freeSWAPRAM = (freeSwapSize / 1024);
                } else if (freeSwapSizeUnit.trim().equalsIgnoreCase("G") || freeSwapSizeUnit.trim().equalsIgnoreCase("GB")) {
                    freeSWAPRAM = (freeSwapSize * 1024);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        memoryStats.setTotal(totalSWAPRAM);
        memoryStats.setFree(freeSWAPRAM);
        return memoryStats;
    }

    public OperatingSystem_CPUStatus getCPUUsageStatus() {
        OperatingSystem_CPUStatus cPUStatsModel = new OperatingSystem_CPUStatus();
        Double idle = (double) 0;

        if (isWindows()) {
            cPUStatsModel.setIdle(0d);
        } else {
            String command = "iostat -C -w1 -c2";
            try {

                List<String> commandLinesResult = new CmdExec().run_with_shell_output(command);
                if (commandLinesResult != null) {
                    String[] cpusUtils = commandLinesResult.get(commandLinesResult.size() - 1).split(" ");
                    System.out.println(cpusUtils[cpusUtils.length - 1]);
                    idle = Double.parseDouble(cpusUtils[cpusUtils.length - 1]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            cPUStatsModel.setIdle(idle);
        }

        return cPUStatsModel;
    }

    public static void main(String[] args) {

//        Integer totalSwap = new Integer(0);
        OperatingSystem systemUtil = new OperatingSystem();

        OperatingSystem_JVM operatingSystem_JVM = systemUtil.getJVMInformationModel();
        System.out.println(operatingSystem_JVM.getFreeMemory() + ":" + operatingSystem_JVM.getTotalMemory() + ":" + operatingSystem_JVM.getMaxMemory());

        System.out.println("");
        OperatingSystem_SwapMemory memoryStatsSWAP = systemUtil.getSWAPRAMStatusInMB();
        System.out.println("Total SWAP :" + memoryStatsSWAP.getTotal());
        System.out.println("Free SWAP :" + memoryStatsSWAP.getFree());
        System.out.println("Used SWAP :" + memoryStatsSWAP.getUsed());

        System.out.println("");
        OperatingSystem_PhysicalMemory memoryStatsRAM = systemUtil.getPhysicalRAMStatusInMB();
        System.out.println("Total RAM :" + memoryStatsRAM.getTotal());
        System.out.println("Free RAM :" + memoryStatsRAM.getFree());
        System.out.println("Used RAM :" + memoryStatsRAM.getUsed());
    }

}
