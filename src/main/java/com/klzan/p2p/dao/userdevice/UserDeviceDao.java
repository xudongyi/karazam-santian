package com.klzan.p2p.dao.userdevice;

import com.klzan.core.persist.DaoSupport;
import com.klzan.p2p.model.UserDevice;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suhao Date: 2017/1/9 Time: 15:29
 *
 * @version: 1.0
 */
@Repository
public class UserDeviceDao extends DaoSupport<UserDevice> {
    public List<UserDevice> findListByRegistrationId(String registrationId) {
        String hql = "FROM UserDevice WHERE deleted=0 AND registrationId=?0 ";
        return find(hql, registrationId);
    }

    public UserDevice findByRegistrationId(String registrationId) {
        String hql = "FROM UserDevice WHERE deleted=0 AND registrationId=?0 ";
        return findUnique(hql, registrationId);
    }

    public List<UserDevice> findByOsType(String osType) {
        String hql = "FROM UserDevice WHERE deleted=0 AND osType=?0 ";
        return find(hql, osType);
    }

    public List<Integer> findUserByOsType(String osType) {
        List<UserDevice> devices = findByOsType(osType);
        List<Integer> userIds = new ArrayList<>();
        for (UserDevice device : devices) {
            userIds.add(device.getUserId());
        }
        return userIds;
    }

    public List<Integer> findAllUser() {
        String hql = "FROM UserDevice WHERE deleted=0 ";
        List<UserDevice> userDevices = find(hql);
        List<Integer> userIds = new ArrayList<>();
        for (UserDevice device : userDevices) {
            userIds.add(device.getUserId());
        }
        return userIds;
    }

    public UserDevice findByUserId(Integer userId) {
        String hql = "FROM UserDevice WHERE deleted=0 AND userId=?0 ";
        return findUnique(hql, userId);
    }
}
