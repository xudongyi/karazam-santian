/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.user;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.UserType;
import com.klzan.p2p.model.User;
import com.klzan.p2p.vo.user.UserIdentityVo;
import com.klzan.p2p.vo.user.UserRegisterVo;
import com.klzan.p2p.vo.user.UserThirdAccountVo;
import com.klzan.p2p.vo.user.UserVo;

import java.util.List;
import java.util.Map;

public interface UserService extends IBaseService<User> {

    /**
     * 获取移动端当前用户
     * @param token
     * @param userId
     * @return
     */
    User getCurrentUserOfApp(Integer userId, String token);

    /**
     * 创建用户
     *
     * @param userVo
     */
    User createUser(UserVo userVo);

    /**
     * 注册时创建用户并维护推荐关系
     *
     * @param user
     * @param inviteCode
     */
    User createUser(User user, String inviteCode, String referrer) throws Exception;

    /**
     * 更新
     *
     * @param userVo
     * @return
     */
    User updateUser(UserVo userVo);

    /**
     * 修改密码
     *
     * @param userId
     * @param newPassword
     */
    void changePassword(Integer userId, String newPassword);

    /**
     * 根据用户名查找用户
     *
     * @param loginName
     * @return
     */
    User getUserByLoginName(String loginName, UserType userType);

    /**
     * 查询用户分页
     *
     * @param pageCriteria
     * @return
     */
    PageResult<UserVo> findUsers(PageCriteria pageCriteria);

    /**
     * 修改用户登录
     *
     * @param user
     */
    void updateLoginUser(User user);

    /**
     * 用户注册
     *
     * @param userRegisterVo
     * @return
     */
    User register(UserRegisterVo userRegisterVo);

    /**
     * 第三方平台注册
     *
     * @param thirdAccountVo
     * @return
     */
    User registerByThirdPlatform(UserThirdAccountVo thirdAccountVo);

    /**
     * 根据手机号查找
     *
     * @param mobile
     * @return
     */
    User getUserByMobile(String mobile, UserType userType);

    List<User> getUserListByMobile(String mobile, UserType userType);

    /**
     * 手机号是否存在
     * @param mobile
     * @return
     */
    Boolean isExistMobile(String mobile, UserType userType);

    /**
     * 登录名是否存在
     *
     * @param loginName
     * @return
     */
    Boolean isExistLoginName(String loginName, UserType userType);

    /**
     * 按条件查询所有用户（不包含已删除）
     *
     * @return
     */
    PageResult<UserVo> findUserDetailPage(PageCriteria criteria);

    /**
     * 已开户用户列表
     * @return
     */
    List<UserVo> findHasOpenAcctUsers();

    /**
     * 已开户用户列表
     * @param nameOrMobile
     * @return
     */
    List<UserVo> findHasOpenAcctUsersByNameOrMobile(String nameOrMobile);

    /**
     * 按条件查询所有用户（包含已删除）
     *
     * @param criteria
     * @param map
     * @return
     */
    PageResult<UserVo> findAllUserByPage(PageCriteria criteria, Map map);

    /**
     * @param userType
     * @param pageCriteria
     * @return
     */
    PageResult<UserVo> findListByMybatis(String userType, PageCriteria pageCriteria);

    /**
     * 根据会员ID查询
     *
     * @param userId
     * @return
     */
    UserVo getUserById(Integer userId);

    /**
     * 根据邀请码查询
     *
     * @param inviteCode
     * @return
     */
    User getUserByInviteCode(String inviteCode);

    /**
     * 实名认证
     * @param userIdentityVo
     */
    void addIdentityInfo(UserIdentityVo userIdentityVo);

    /**
     * 判断身份证号是否存在
     * @return
     */
    Boolean isExistIdNo(String idNo);

    List<UserVo> findAllUser(String loginName, String mobile, UserType userType, String realName);
}
