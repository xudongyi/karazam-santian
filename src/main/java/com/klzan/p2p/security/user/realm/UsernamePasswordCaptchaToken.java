package com.klzan.p2p.security.user.realm;

import com.klzan.p2p.enums.ClientType;

import javax.servlet.http.HttpSession;

public class UsernamePasswordCaptchaToken extends org.apache.shiro.authc.UsernamePasswordToken {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7368760471388159436L;

    private String captcha;

    private ClientType clientType = ClientType.PC;

    private String clientId;

    private HttpSession session;

    /**
     * APP token
     * @param username
     * @param password
     * @param rememberMe
     * @param host
     * @param clientType
     * @param session
     */
    public UsernamePasswordCaptchaToken(String username, char[] password, boolean rememberMe, String host, ClientType clientType, String clientId, HttpSession session) {
        super(username, password, rememberMe, host);
        this.session = session;
        this.clientType = clientType;
        this.clientId = clientId;
    }

    /**
     * pc,mobile_web token
     * @param username
     * @param password
     * @param rememberMe
     * @param host
     * @param captcha
     * @param clientType
     * @param session
     */
    public UsernamePasswordCaptchaToken(String username, char[] password, boolean rememberMe, String host, String captcha, ClientType clientType, HttpSession session) {
        super(username, password, rememberMe, host);
        this.captcha = captcha;
        this.session = session;
        this.clientType = clientType;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

}