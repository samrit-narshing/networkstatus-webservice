/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.services.impl.user;

import com.project.core.models.entities.user.DeviceUser;
import com.project.core.models.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.project.core.repositories.user.DeviceUserRepo;
import com.project.core.services.user.DeviceUserService;
import com.project.core.services.util.DeviceUserList;


/**
 *
 * @author Samrit
 */
@Service
@Transactional
public class DeviceUserServiceImpl implements DeviceUserService {

    @Autowired
    private DeviceUserRepo deviceUserRepo;

    @Override
    public DeviceUser createDeviceUser(DeviceUser data) throws Exception {
        return deviceUserRepo.createDeviceUser(data);
    }

    @Override
    public DeviceUser findDeviceUser(Long id) throws Exception {
        return deviceUserRepo.findDeviceUser(id);
    }

    @Override
    public DeviceUser findDeviceUserByUsername(String name) throws Exception {
        return deviceUserRepo.findDeviceUserByUsername(name);
    }

//        public DeviceUser findDeviceUserByUsernameAndTokenName(String username, String tokenName) {
//        return deviceUserRepo.findDeviceUserByUsernameAndTokenName(username, tokenName);
//    }
//
//    @Override
//    public DeviceUserList findDeviceUserByUsername(String name) throws Exception {
////        return deviceUserRepo.findDeviceUserByUsername(name);
//        DeviceUserList deviceUserList = new DeviceUserList(deviceUserRepo.findDeviceUserByUsername(name), (long) deviceUserRepo.findDeviceUserByUsername(name).size(), 1L);
//        return deviceUserList;
//    }
//    @Override
//    public DeviceUserList findAllDeviceUser() throws Exception {
//        return new DeviceUserList(deviceUserRepo.findAllDeviceUser());
//    }
    @Override
    public DeviceUserList findAllDeviceUser(User loggedUser) throws Exception {
        return new DeviceUserList(deviceUserRepo.findAllDeviceUser(loggedUser));
//        return deviceUserRepo.findAllDeviceUser();
    }

    @Override
    public DeviceUser updateDeviceUser(Long id, DeviceUser data) throws Exception {
        return deviceUserRepo.updateDeviceUser(id, data);
    }

    @Override
    public DeviceUser deleteDeviceUser(Long id) throws Exception {
        return deviceUserRepo.deleteDeviceUser(id);
    }

    @Override
    public int deleteDeviceUserByUsername(String username) throws Exception {
        return deviceUserRepo.deleteDeviceUserByUsername(username);
    }

    @Override
    public int deleteDuplicateDeviceUserOfUser(String username, String tokenName) throws Exception {
        return deviceUserRepo.deleteDuplicateDeviceUserOfUser(username, tokenName);
    }

    @Override
    public int deleteExpiredDeviceUser(Long fromDate) throws Exception {
        return deviceUserRepo.deleteExpiredDeviceUser(fromDate);
    }

}
