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
public class NetworkInfo implements Serializable {

    private MAC mac = new MAC();
    private HostID hostID = new HostID();

    public MAC getMac() {
        return mac;
    }

    public void setMac(String name) {
        mac = new MAC();
        mac.setName(name);
    }

    public HostID getHostID() {
        return hostID;
    }

    public void setHostID(String name) {
        hostID = new HostID();
        hostID.setName(name);
    }

    private class MAC {

        private String name = "N/A";

        ;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private class HostID {

        private String name = "N/A";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
