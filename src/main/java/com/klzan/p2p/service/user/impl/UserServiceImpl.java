/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.user.impl;

import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.lock.DistributeLock;
import com.klzan.core.lock.LockStack;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.DateUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.core.util.WebUtils;
import com.klzan.p2p.common.PasswordHelper;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.common.util.ConstantUtils;
import com.klzan.p2p.dao.user.UserDao;
import com.klzan.p2p.dao.user.UserFinanceDao;
import com.klzan.p2p.dao.user.UserInfoDao;
import com.klzan.p2p.dao.user.UserPointDao;
import com.klzan.p2p.enums.CouponSource;
import com.klzan.p2p.enums.GenderType;
import com.klzan.p2p.enums.UserLogType;
import com.klzan.p2p.enums.UserType;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.coupon.UserCouponService;
import com.klzan.p2p.service.token.AccessTokenService;
import com.klzan.p2p.service.user.*;
import com.klzan.p2p.setting.SettingUtils;
import com.klzan.p2p.vo.user.UserIdentityVo;
import com.klzan.p2p.vo.user.UserRegisterVo;
import com.klzan.p2p.vo.user.UserThirdAccountVo;
import com.klzan.p2p.vo.user.UserVo;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends BaseService<User> implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private UserFinanceDao userFinanceDao;
    @Autowired
    private UserPointDao userPointDao;
    @Autowired
    private UserThirdPlatfromService userThirdPlatfromService;
    @Autowired
    private UserLogService userLogService;
    @Autowired
    private CorporationService corporationService;
    @Autowired
    private CorporationLegalService corporationLegalService;
    @Autowired
    private ReferralService referralService;
    @Autowired
    private SettingUtils settingUtils;
    @Autowired
    private DistributeLock distributeLock;
    @Autowired
    private AccessTokenService accessTokenService;
    @Autowired
    private UserPointService userPointService;
    @Resource
    private UserCouponService userCouponService;

    @Override
    public User getCurrentUserOfApp(Integer userId, String token) {
        try {
            AccessToken accessToken = accessTokenService.findAppToken(userId, token);
            if(accessToken != null){
                return this.get(accessToken.getUserId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User createUser(UserVo userVo) {
        User user = new User(userVo.getType(), userVo.getLoginName(), userVo.getName(), userVo.getPassword(), userVo.getBirthday(), userVo.getGender(), userVo.getMobile(), userVo.getDescription());
        //加密密码
        PasswordHelper.encryptPassword(user, userVo.getPassword());
        return userDao.persist(user);
    }

    @Override
    public User createUser(User user, String inviteCode, String referrer) throws Exception {
        User inviteUser = null;
        if (StringUtils.isNotBlank(inviteCode)) {
            inviteUser = getUserByInviteCode(inviteCode);
        }
        user = userDao.createUser(user);
        //初始化一条对应UserInfo数据(业务需求)
        //初始化一条对应UserFinance数据(业务需求)
        generateInfoAfterRegist(user);

        // 注册送积分
        userPointService.regist(user.getId());
        //注册送红包
        userCouponService.createUserCoupon(CouponSource.REGISTER,user.getId());

        //1.如果在关系表中存在推荐关系，维护推荐关系到 referral
//        Relationship relationship = relationshipService.findByMobile(user.getMobile());
//        if (relationship != null && inviteUser == null) {
//            inviteUser = userService.findOne(relationship.getUserId());
//            if (inviteUser == null || inviteUser.getDeleted()) {
//                logger.error("推荐人不存在");
//                throw new Exception("推荐人不存在");
//            }
//            if (!inviteUser.getMobile().equals(referrer)){
//                Referral referral = new Referral();
//                referral.setUserId(inviteUser.getId());
//                referral.setReUserId(user.getId());
//                referral.setAvailable(true);
//                referral.setReferralFeeRate(settingUtils.getReferral().getReferralRate());
//                referralService.createReferral(referral);
//            }
//        }
        //2.如果在注册页面输入了推荐人，维护推荐关系到 referral
        if (StringUtils.isNotBlank(inviteCode) && inviteUser != null) {
            // 维护推荐关系
            Referral referral = new Referral();
            referral.setUserId(inviteUser.getId());
            referral.setReUserId(user.getId());
            referral.setAvailable(true);
            referral.setReferralFeeRate(settingUtils.getReferral().getReferralRate());
            referralService.createReferral(referral);
            // 推荐送积分
            userPointService.referral(user.getId(), inviteUser.getId());
            //推荐送红包
            userCouponService.createUserCoupon(CouponSource.REFERRAL,inviteUser.getId());
        }
        //3.如果在注册页面填写了推荐人
        if (StringUtils.isNotBlank(referrer) && StringUtils.isBlank(inviteCode)) {
            User u = getUserByMobile(referrer, UserType.GENERAL);
            if (u == null || u.getDeleted()) {
                logger.error("推荐人不存在");
                throw new Exception("推荐人不存在");
            }
            Referral referral = new Referral();
            referral.setUserId(u.getId());
            referral.setReUserId(user.getId());
            referral.setAvailable(true);
            referral.setReferralFeeRate(settingUtils.getReferral().getReferralRate());
            referralService.createReferral(referral);
            // 推荐送积分
            userPointService.referral(user.getId(), u.getId());
            //推荐送红包
            userCouponService.createUserCoupon(CouponSource.REFERRAL,u.getId());
        }
        return user;
    }

    @Override
    public User updateUser(UserVo userVo) {
        User user = get(userVo.getId());
        userVo.setPassword(user.getPassword());
        user.update(userVo.getLoginName(), userVo.getName(), userVo.getPassword(), userVo.getBirthday(), userVo.getGender(), userVo.getMobile(), userVo.getDescription());
        return userDao.update(user);
    }

    @Override
    public void changePassword(Integer userId, String newPassword) {
        User user = userDao.get(userId);
        PasswordHelper.encryptPassword(user, newPassword);
        userDao.update(user);
    }

    public User getUserByLoginName(String loginName, UserType userType) {
        return userDao.findByLoginName(loginName, userType);
    }

    @Override
    public PageResult<UserVo> findUsers(PageCriteria pageCriteria) {
        return userDao.findUsers(pageCriteria);
    }

    @Override
    public void updateLoginUser(User user) {
        try {
            distributeLock.lock(LockStack.USER_LOCK, "updateUserLoginInfo" + user.getId());
            Integer loginCount = (user.getLoginCount() == null ? 0 : user.getLoginCount()) + 1;
            Timestamp previousVisit = user.getLastVisit();
            Timestamp lastVisit = DateUtils.getTimestamp();
            user.updateLoginInfo(loginCount, previousVisit, lastVisit);
            userDao.merge(user);
        } finally {
            distributeLock.unLock(LockStack.USER_LOCK, "updateUserLoginInfo" + user.getId());
        }
    }

    @Override
    public User register(UserRegisterVo userRegisterVo) {
        User user = new User(userRegisterVo.getMobile(), userRegisterVo.getPassword(), userRegisterVo.getType());
        //加密密码
        PasswordHelper.encryptPassword(user, userRegisterVo.getPassword());
        return userDao.persist(user);
    }

    @Override
    public User registerByThirdPlatform(UserThirdAccountVo thirdAccountVo) {
        String defaultLoginName = DateUtils.format(new Date(), DateUtils.DATE_PATTERN_NUMBER_yyyyMMdd) + RandomStringUtils.random(5, false, true);
        Boolean isExist = userDao.isExistLoginName(defaultLoginName, thirdAccountVo.getType());
        while (isExist) {
            defaultLoginName = DateUtils.format(new Date(), DateUtils.DATE_PATTERN_NUMBER_yyyyMMdd) + RandomStringUtils.random(5, false, true);
            isExist = userDao.isExistLoginName(defaultLoginName, thirdAccountVo.getType());
        }
        String defaultPassword = ConstantUtils.DEFAULT_PWD;
        User user = new User(thirdAccountVo.getNickname(), defaultLoginName, defaultPassword, thirdAccountVo.getType());
        //加密密码
        PasswordHelper.encryptPassword(user, defaultPassword);
        userDao.persist(user);
        userThirdPlatfromService.create(thirdAccountVo);
        return user;
    }

    @Override
    public User getUserByMobile(String mobile, UserType userType) {
        return userDao.getUserByMobile(mobile, userType);
    }
    @Override
    public List<User> getUserListByMobile(String mobile, UserType userType) {
        return userDao.getUserListByMobile(mobile, userType);
    }
    @Override
    public Boolean isExistMobile(String mobile, UserType userType) {
        List<User> user = getUserListByMobile(mobile, userType);
        return user.size()==0 ? false : true;
    }

    @Override
    public Boolean isExistLoginName(String loginName, UserType userType) {
        User user = getUserByLoginName(loginName, userType);
        return null == user ? false : true;
    }

    @Override
    public PageResult<UserVo> findUserDetailPage(PageCriteria criteria) {
        return myDaoSupport.findPage("com.klzan.p2p.mapper.UserMapper.findUserDetailPage", criteria.getParams(), criteria);
    }

    @Override
    public List<UserVo> findHasOpenAcctUsers() {
        Map map = new HashMap();
        map.put("openAcct", true);
        return myDaoSupport.findList("com.klzan.p2p.mapper.UserMapper.findAllUser", map);
    }

    @Override
    public List<UserVo> findHasOpenAcctUsersByNameOrMobile(String nameOrMobile) {
        Map map = new HashMap();
        map.put("openAcct", true);
        map.put("nameOrMobile", nameOrMobile);
        return myDaoSupport.findList("com.klzan.p2p.mapper.UserMapper.findAllUser", map);
    }

    @Override
    public PageResult<UserVo> findAllUserByPage(PageCriteria criteria, Map map) {
        return myDaoSupport.findPage("com.klzan.p2p.mapper.UserMapper.findAllUser", map, criteria);
    }

    /**
     * 通过mybatis查询企业或个人用户列表
     */
    @Override
    public PageResult<UserVo> findListByMybatis(String userType, PageCriteria pageCriteria) {
        Map<String, Object> map = new HashedMap();
        map.put("type", userType);
        PageResult<UserVo> usersPageResult = myDaoSupport.findPage("com.klzan.p2p.mapper.UserMapper.findUserDetailPage", map, pageCriteria);
        return usersPageResult;
    }

    @Override
    public UserVo getUserById(Integer userId) {
        User user = get(userId);
        if (user == null) {
            return null;
        }
        Map map = new HashMap();
        map.put("id", userId);
        return (UserVo) myDaoSupport.findUnique("com.klzan.p2p.mapper.UserMapper.findUserDetailPage", map);
    }

    @Override
    public User getUserByInviteCode(String inviteCode) {
        return userDao.getUserByInviteCode(inviteCode);
    }

    @Override
    public void addIdentityInfo(UserIdentityVo userIdentityVo) {
        try {
            User user = get(userIdentityVo.getUserId());
            UserInfo userInfo = userInfoDao.getUserInfoByUserId(user.getId());
            String idNo = userIdentityVo.getIdNo();
            String realName = userIdentityVo.getRealName();
            Date birth = null;
            GenderType gender = null;
            // 18位身份证号码
            if (StringUtils.length(idNo) == 18) {
                //获取出生日期
                birth = DateUtils.parseDate(StringUtils.substring(idNo, 6, 14), "yyyyMMdd");
                // 获取性别
                gender = Integer.parseInt(StringUtils.left(StringUtils.right(idNo, 2), 1)) % 2 == 0 ? GenderType.FEMALE : GenderType.MALE;
            } else if (StringUtils.length(idNo) == 15) { // 15位身份证号码
                // 获取出生日期
                birth = DateUtils.parseDate("19" + StringUtils.substring(idNo, 6, 12), "yyyyMMdd");
                // 获取性别
                gender = Integer.parseInt(StringUtils.right(idNo, 1)) % 2 == 0 ? GenderType.FEMALE : GenderType.MALE;
            }
            // 更新user表性别、出生日期,
            user.setGender(gender == null ? GenderType.UNKNOWN : gender);
            user.setBirthday(birth);
            user.setType(userIdentityVo.getType());
            user.setLegalMobile(userIdentityVo.getIpsMobile());
            this.merge(user);
            // 更新user_info表身份信息
            userInfo.setRealName(realName);
            userInfo.setIdNo(org.apache.commons.lang3.StringUtils.upperCase(idNo));
            //更新User表
            userInfoDao.merge(userInfo);
            if (user.getType() == UserType.ENTERPRISE) {
                CorporationLegal corporationLegal = new CorporationLegal();
                corporationLegal.setUserId(user.getId());
                corporationLegal.setCorporationName(realName);
                corporationLegal.setCorporationMobile(user.getMobile());
                corporationLegal.setCorporationIdCard(StringUtils.upperCase(idNo));
                corporationLegalService.persist(corporationLegal);

                Corporation corporation = new Corporation();
                corporation.setLegalId(corporationLegal.getId());
                corporation.setCorpName(userIdentityVo.getCorpName());
                corporation.setCorpLicenseNo(userIdentityVo.getCorpLicenseNo());
                corporationService.persist(corporation);
            }

            UserLog userLog = new UserLog(UserLogType.MODIFY, new String("PC端用户中心实名认证"), user.getLoginName(), WebUtils.getRemoteIp(WebUtils.getHttpRequest()), user.getId());
            userLogService.persist(userLog);
        } catch (ParseException e) {
            throw new BusinessProcessException("身份认证错误");
        }
    }

    @Override
    public Boolean isExistIdNo(String idNo) {
        UserInfo userInfo = userInfoDao.getUserInfoByIdNo(idNo);
        if(userInfo!=null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<UserVo> findAllUser(String loginName, String mobile, UserType userType, String realName) {
        Map<String, Object> map = new HashedMap();
        map.put("loginName", loginName);
        map.put("mobile", mobile);
        if (null != userType) {
            map.put("type", userType.name());
        }
        map.put("realName", realName);
        List<UserVo> result = myDaoSupport.findList("com.klzan.p2p.mapper.UserMapper.findAllUser",map);
        return result;
    }

    private void generateInfoAfterRegist(User user) {
        UserFinance userFinance = new UserFinance();
        userFinance.setUserId(user.getId());
        userFinance.setBalance(BigDecimal.ZERO);
        userFinanceDao.persist(userFinance);

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfoDao.persist(userInfo);

        UserPoint userPoint = new UserPoint();
        userPoint.setUserId(user.getId());
        userPoint = userPointDao.persist(userPoint);
        userPointDao.flush();
    }

}
