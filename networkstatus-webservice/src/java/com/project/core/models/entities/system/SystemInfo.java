/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.models.entities.system;

import java.io.Serializable;

/**
 *
 * @author Samrit
 */
public class SystemInfo implements Serializable {

    private OS os = new OS();
    private Memory physicalMemory = new Memory();
    private Memory swapMemory = new Memory();
    private CPU cpu = new CPU();
    private DateTime dateTime = new DateTime();

    public OS getOs() {
        return os;
    }

    public void setOs(String name, String architecture, String version) {
        os.setName(name);
        os.setArchitecture(architecture);
        os.setVersion(version);
    }

    public Memory getPhysicalMemory() {
        return physicalMemory;
    }

    public void setPhysicalMemory(String totalMemory, String usedMemory, String availableMemory) {
        physicalMemory.setTotalMemory(totalMemory);
        physicalMemory.setUsedMemory(usedMemory);
        physicalMemory.setAvailableMemory(availableMemory);
    }

    public Memory getSwapMemory() {
        return swapMemory;
    }

    public void setSwapMemory(String totalMemory, String usedMemory, String availableMemory) {
        swapMemory = new Memory();
        swapMemory.setTotalMemory(totalMemory);
        swapMemory.setUsedMemory(usedMemory);
        swapMemory.setAvailableMemory(availableMemory);
    }

    public CPU getCpu() {
        return cpu;
    }

    public void setCpu(String cpuUsage) {
        cpu.setCpuUsage(cpuUsage);
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(String timeZone, String localdateTime, String utcDateTime) {
        dateTime.setTimeZone(timeZone);
        dateTime.setLocaldateTime(localdateTime);
        dateTime.setUtcDateTime(utcDateTime);
    }

    private class OS {

        private String name = "N/A";
        private String architecture = "N/A";
        private String version = "N/A";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getArchitecture() {
            return architecture;
        }

        public void setArchitecture(String architecture) {
            this.architecture = architecture;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

    }

    private class Memory {

        private String totalMemory = "N/A";
        private String usedMemory = "N/A";
        private String availableMemory = "N/A";

        public String getTotalMemory() {
            return totalMemory;
        }

        public void setTotalMemory(String totalMemory) {
            this.totalMemory = totalMemory;
        }

        public String getUsedMemory() {
            return usedMemory;
        }

        public void setUsedMemory(String usedMemory) {
            this.usedMemory = usedMemory;
        }

        public String getAvailableMemory() {
            return availableMemory;
        }

        public void setAvailableMemory(String availableMemory) {
            this.availableMemory = availableMemory;
        }

    }

    private class CPU {

        private String cpuUsage = "N/A";

        public String getCpuUsage() {
            return cpuUsage;
        }

        public void setCpuUsage(String cpuUsage) {
            this.cpuUsage = cpuUsage;
        }

    }

    private class DateTime {

        private String timeZone = "N/A";
        private String localdateTime = "N/A";
        private String utcDateTime = "N/A";

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }

        public String getLocaldateTime() {
            return localdateTime;
        }

        public void setLocaldateTime(String localdateTime) {
            this.localdateTime = localdateTime;
        }

        public String getUtcDateTime() {
            return utcDateTime;
        }

        public void setUtcDateTime(String utcDateTime) {
            this.utcDateTime = utcDateTime;
        }

    }

}
