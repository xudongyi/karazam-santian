package com.klzan.p2p.security.user.realm;

import com.klzan.p2p.common.PasswordHelper;
import com.klzan.p2p.common.util.ConstantUtils;
import com.klzan.p2p.enums.CaptchaType;
import com.klzan.p2p.enums.ClientType;
import com.klzan.p2p.enums.UserType;
import com.klzan.p2p.event.LoginEvent;
import com.klzan.p2p.event.LoginUpdateEvent;
import com.klzan.p2p.model.User;
import com.klzan.p2p.security.user.LoginInfo;
import com.klzan.p2p.security.user.LoginUpdate;
import com.klzan.p2p.security.user.UserPrincipal;
import com.klzan.p2p.service.captcha.CaptchaService;
import com.klzan.p2p.service.user.UserService;
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

public class UserRealm extends AuthorizingRealm {

    @Inject
    private UserService userService;

    @Inject
    private CaptchaService captchaService;

    @Inject
    private ApplicationContext applicationContext;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordCaptchaToken usernamePasswordCaptchaToken = (UsernamePasswordCaptchaToken) token;
        // 获取登录信息
        String username = usernamePasswordCaptchaToken.getUsername();
        String password = new String(usernamePasswordCaptchaToken.getPassword());
        String captcha = usernamePasswordCaptchaToken.getCaptcha();
        ClientType clientType = usernamePasswordCaptchaToken.getClientType();
        String ip = usernamePasswordCaptchaToken.getHost();
        HttpSession session = usernamePasswordCaptchaToken.getSession();

        User user = userService.getUserByLoginName(username, UserType.GENERAL);

        // 启用管理员登录验证码时，验证验证码
        if (!captchaService.verify(CaptchaType.LOGIN, captcha, session) && (clientType == ClientType.PC || clientType == ClientType.MOBILEWEB)) {
            // 异常：无效令牌
            throw new UnsupportedTokenException();
        }

        if (user != null) {
            String salt = user.getCredentialsSalt();
            UserPrincipal shiroUser = new UserPrincipal(user.getId(), user.getLoginName(), user.getType(), user.getName(), user.getMobile());
            // 密码错误时
            if (!PasswordHelper.verifyPassword(user, password)) {
                // 异常：认证错误
                throw new IncorrectCredentialsException();
            }
            session.setAttribute(ConstantUtils.CURRENT_USER, user);
//            mqClient.sendMessageOneway(ConsumeTopic.USER_TOPIC, ConsumeTagExpression.LOGIN_TAG, String.valueOf(shiroUser.getId()), new UserLoginMessage(shiroUser.getId(), user.getMobile(), shiroUser.getLoginName()));
            applicationContext.publishEvent(new LoginUpdateEvent(applicationContext, new LoginUpdate(user.getId(), password)));
            applicationContext.publishEvent(new LoginEvent(applicationContext, new LoginInfo(user.getId(), shiroUser.getLoginName(), usernamePasswordCaptchaToken.getHost(), clientType.getDisplayName())));
            return new SimpleAuthenticationInfo(shiroUser, user.getPassword(), ByteSource.Util.bytes(salt), getName());
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
