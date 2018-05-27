package com.klzan.p2p.security.sysuser.realm;

import javax.servlet.http.HttpSession;

public class SysUserCaptchaToken extends org.apache.shiro.authc.UsernamePasswordToken {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7368760471388159436L;

    private String captcha;

    private HttpSession session;

    public SysUserCaptchaToken(String username, String password, String captcha, boolean rememberMe, String host,
                               HttpSession session) {
        super(username, password, rememberMe, host);
        this.captcha = captcha;
        this.session = session;
    }

    public SysUserCaptchaToken(String username, char[] password, boolean rememberMe, String host, String captcha, HttpSession session) {
        super(username, password, rememberMe, host);
        this.captcha = captcha;
        this.session = session;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

}