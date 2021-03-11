/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.models.entities.util;

/**
 *
 * @author Samrit
 */
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "application_log_message")
public class ApplicationLogMessage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "application-log-message-seqid-gen")
    @SequenceGenerator(name = "application-log-message-seqid-gen", sequenceName = "hibernate_application_log_message_sequence")
    private Long id;

    @Column(name = "sender_username")
    private String senderUsername;

    @Column(name = "logged_username")
    private String loggedUsername;

    @Column(name = "device_type")
    private String deviceType;

    @Lob
    @Column(length = 1000, name = "device_model_name")
    private String deviceModelName;

    @Column(name = "log_message_blop")
    @Lob
    private byte[] logMessageBlop;

    @Column(name = "entry_date")
    private Long entryDate;

    @Column(name = "admin_username")
    private String adminUsername;

    @Column(name = "admin_operator_username")
    private String adminOperatorUsername;

    @Column(name = "admin_user_type")
    private String adminUserType;

    @Column(name = "admin_operator_user_type")
    private String adminOperatorUserType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getLoggedUsername() {
        return loggedUsername;
    }

    public void setLoggedUsername(String loggedUsername) {
        this.loggedUsername = loggedUsername;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceModelName() {
        return deviceModelName;
    }

    public void setDeviceModelName(String deviceModelName) {
        this.deviceModelName = deviceModelName;
    }

    public byte[] getLogMessageBlop() {
        return logMessageBlop;
    }

    public void setLogMessageBlop(byte[] logMessageBlop) {
        this.logMessageBlop = logMessageBlop;
    }

    public Long getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Long entryDate) {
        this.entryDate = entryDate;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public String getAdminOperatorUsername() {
        return adminOperatorUsername;
    }

    public void setAdminOperatorUsername(String adminOperatorUsername) {
        this.adminOperatorUsername = adminOperatorUsername;
    }

    public String getAdminUserType() {
        return adminUserType;
    }

    public void setAdminUserType(String adminUserType) {
        this.adminUserType = adminUserType;
    }

    public String getAdminOperatorUserType() {
        return adminOperatorUserType;
    }

    public void setAdminOperatorUserType(String adminOperatorUserType) {
        this.adminOperatorUserType = adminOperatorUserType;
    }

}
