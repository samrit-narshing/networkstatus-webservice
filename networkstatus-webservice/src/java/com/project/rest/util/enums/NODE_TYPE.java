/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.util.enums;

/**
 *
 * @author samri
 */
public enum NODE_TYPE {

    TYPE_ROUTER("type-router"),
    TYPE_SERVER("type-server"),
    TYPE_NETWORKGROUP("type-networkgroup"),
    TYPE_DATABASE("type-database"),
    TYPE_DATABASE_2("Database"),
    TYPE_HUB("type-hub"),
    TYPE_PRINTER("type-printer"),
    TYPE_SWITCH("type-switch"),
    TYPE_SWITCH_2("Switch"),
    TYPE_COMPUTER("type-computer");

    private final String type;

    NODE_TYPE(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }
//NODE_TYPE.TYPE_ROUTER.message()
}
