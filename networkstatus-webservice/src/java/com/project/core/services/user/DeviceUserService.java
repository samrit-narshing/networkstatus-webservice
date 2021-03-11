package com.project.core.services.user;

import com.project.core.models.entities.user.DeviceUser;
import com.project.core.models.entities.user.User;
import com.project.core.services.util.DeviceUserList;


/**
 *
 * @author Samrit
 */
public interface DeviceUserService {

    public DeviceUser createDeviceUser(DeviceUser data) throws Exception;

    public DeviceUser findDeviceUser(Long id) throws Exception;

    public DeviceUser findDeviceUserByUsername(String username) throws Exception;

//    public DeviceUser findDeviceUserByUsernameAndTokenName(String username, String tokenName);
//
//    public DeviceUserList findDeviceUsersByUsername(String name) throws Exception;
//    public DeviceUserList findAllDeviceUser() throws Exception;
    public DeviceUserList findAllDeviceUser(User loggedUser) throws Exception;



    public DeviceUser updateDeviceUser(Long id, DeviceUser data) throws Exception;

    public DeviceUser deleteDeviceUser(Long id) throws Exception;

    public int deleteDeviceUserByUsername(String username) throws Exception;

    public int deleteDuplicateDeviceUserOfUser(String username, String tokenName) throws Exception;

    public int deleteExpiredDeviceUser(Long fromDate) throws Exception;

}
