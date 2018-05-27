package com.klzan.p2p.service.user.impl;

import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.user.UserInfoDao;
import com.klzan.p2p.enums.UserType;
import com.klzan.p2p.model.UserInfo;
import com.klzan.p2p.service.user.UserInfoService;
import com.klzan.p2p.vo.user.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by suhao Date: 2017/4/5 Time: 14:17
 *
 * @version: 1.0
 */
@Service
public class UserInfoServiceImpl extends BaseService<UserInfo> implements UserInfoService {
    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public List<UserInfo> findByIdNo(String idNo) {
        return userInfoDao.findByIdNo(idNo);
    }

    @Override
    public List<UserInfo> findByIdNo(UserType type, String idNo) {
        return userInfoDao.findByIdNo(type, idNo);
    }

    @Override
    public UserInfo getUserInfo(Integer userId) {
        return userInfoDao.getUserInfoByUserId(userId);
    }

    @Override
    public void addRealInfoIdentify(UserVo userVo) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userVo.getId());
        userInfo.setRealName(userVo.getRealName());
        userInfo.setIdNo(userVo.getIdNo());
        userInfo.setIdIssueDate(userVo.getIdIssueDate());
        userInfo.setIdExpiryDate(userVo.getIdExpiryDate());
        userInfo.setQq(userVo.getQq());
        userInfo.setEduc(userVo.getEduc());
        userInfo.setUniv(userVo.getUniv());
        userInfo.setMarriage(userVo.getMarriage());
        userInfo.setChild(userVo.getChild());
        userInfo.setBirthplace(userVo.getBirthplace());
        userInfo.setDomicilePlace(userVo.getDomicilePlace());
        userInfo.setAbodePlace(userVo.getAbodePlace());
        userInfo.setAddr(userVo.getAddr());
        userInfo.setZipcode(userVo.getZipcode());
        userInfo.setOccup(userVo.getOccup());
        userInfo.setWorkEmail(userVo.getWorkEmail());
        userInfo.setWorkMobile(userVo.getWorkMobile());
        userInfo.setWorkPhone(userVo.getWorkPhone());
        userInfo.setWorkQq(userVo.getWorkQq());
        userInfo.setOwnHouse(userVo.getOwnHouse());
        userInfo.setWithHouseLoan(userVo.getWithHouseLoan());
        userInfo.setOwnCar(userVo.getOwnCar());
        userInfo.setWithCarLoan(userVo.getWithCarLoan());
        userInfo.setMonthlyCreditCardStatement(userVo.getMonthlyCreditCardStatement());
        userInfo.setEducId(userVo.getEducId());
        userInfo.setPost(userVo.getPost());
        userInfo.setWorkYears(userVo.getWorkYears());
        userInfo.setIncome(userVo.getIncome());
        userInfo.setIntro(userVo.getIntro());
        userInfoDao.persist(userInfo);
    }

    @Override
    public boolean hasCertification(Integer userId) {
        return userInfoDao.getUserInfoByUserId(userId).hasIdentity();
    }
}
