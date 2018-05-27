package com.klzan.p2p.security.sysuser.realm;

import com.klzan.p2p.common.PasswordHelper;
import com.klzan.p2p.common.util.ConstantUtils;
import com.klzan.p2p.enums.CaptchaType;
import com.klzan.p2p.enums.UserStatus;
import com.klzan.p2p.event.SysLoginUpdateEvent;
import com.klzan.p2p.model.sys.SysUser;
import com.klzan.p2p.security.sysuser.ShiroSysUser;
import com.klzan.p2p.service.captcha.CaptchaService;
import com.klzan.p2p.service.sysuser.SysResourceService;
import com.klzan.p2p.service.sysuser.SysRoleService;
import com.klzan.p2p.service.sysuser.SysUserRoleService;
import com.klzan.p2p.service.sysuser.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.context.ApplicationContext;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

public class SysUserRealm extends AuthorizingRealm {

    @Inject
    private SysUserService sysUserService;

    @Inject
    private SysRoleService sysRoleService;

    @Inject
    private SysUserRoleService sysUserRoleService;

    @Inject
    private SysResourceService sysResourceService;

    @Inject
    private CaptchaService captchaService;

    @Inject
    private ApplicationContext applicationContext;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ShiroSysUser shiroUser = (ShiroSysUser) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(shiroUser.getRoles());
        authorizationInfo.setStringPermissions(shiroUser.getPermissions());
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        SysUserCaptchaToken usernamePasswordCaptchaToken = (SysUserCaptchaToken) token;
        // 获取登录信息
        String loginName = usernamePasswordCaptchaToken.getUsername();
        String password = new String(usernamePasswordCaptchaToken.getPassword());
        String captcha = usernamePasswordCaptchaToken.getCaptcha();
        String ip = usernamePasswordCaptchaToken.getHost();
        HttpSession session = usernamePasswordCaptchaToken.getSession();

        // 启用管理员登录验证码时，验证验证码
        if (!captchaService.verify(CaptchaType.ADMIN_LOGIN, captcha, session)) {
            // 异常：无效令牌
            throw new UnsupportedTokenException();
        }

        SysUser sysUser = sysUserService.findByLoginName(loginName);
        if (sysUser != null) {
            if (sysUser.getStatus() != UserStatus.ENABLE) {
                if (sysUser.getStatus() == UserStatus.DISABLE) {
                    throw new DisabledAccountException();
                }
                if (sysUser.getStatus() == UserStatus.LOCKED) {
                    throw new LockedAccountException();
                }
            }
            String salt = sysUser.getCredentialsSalt();
            // 密码错误时
            if (!PasswordHelper.verifyPassword(sysUser, password)) {
                // 异常：认证错误
                throw new IncorrectCredentialsException();
            }
            List<Integer> roleList = sysUserRoleService.findRoleIdList(sysUser.getId());
            Integer[] roleIds = new Integer[roleList.size()];
            roleIds = roleList.toArray(roleIds);
            Set<String> roles = sysRoleService.findRoles(roleIds);
            Set<String> permissions = sysRoleService.findPermissions(roleIds);
            ShiroSysUser shiroUser = new ShiroSysUser(sysUser.getId(), sysUser.getLoginName(), sysUser.getName(), sysUser.getMobile(), permissions, roles);
            session.setAttribute(ConstantUtils.CURRENT_SYS_USER, sysUser);
            applicationContext.publishEvent(new SysLoginUpdateEvent(applicationContext, sysUser.getId()));
            return new SimpleAuthenticationInfo(shiroUser, sysUser.getPassword(), ByteSource.Util.bytes(salt), getName());
        } else {
            // 异常：账户不存在
            throw new UnknownAccountException();
        }
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
