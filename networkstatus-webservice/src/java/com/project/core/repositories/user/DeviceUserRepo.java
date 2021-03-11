/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.repositories.user;

import com.project.core.models.entities.user.DeviceUser;
import com.project.core.models.entities.user.User;
import java.util.List;

/**
 *
 * @author Samrit
 */
public interface DeviceUserRepo {

    public DeviceUser createDeviceUser(DeviceUser data);

    public DeviceUser findDeviceUser(Long id);

    public DeviceUser findDeviceUserByUsername(String username);

//    public List<DeviceUser> findDeviceUsersByUsername(String username);
//
//    public DeviceUser findDeviceUserByUsernameAndTokenName(String username, String tokenName);

//    public List<DeviceUser> findAllDeviceUser();
    
    public List<DeviceUser> findAllDeviceUser(User loggedUser);

    public DeviceUser updateDeviceUser(Long id, DeviceUser data);

    public DeviceUser deleteDeviceUser(Long id);

    public int deleteDeviceUserByUsername(String username);

    public int deleteDuplicateDeviceUserOfUser(String username, String tokenName);

    public int deleteExpiredDeviceUser(Long fromDate);

}
