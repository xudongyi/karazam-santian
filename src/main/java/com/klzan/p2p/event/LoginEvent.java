package com.klzan.p2p.event;

import com.klzan.p2p.security.user.LoginInfo;
import org.springframework.context.ApplicationEvent;

/**
 * Created by suhao Date: 2017/2/15 Time: 14:21
 *
 * @version:
 */
public class LoginEvent extends ApplicationEvent {
    private LoginInfo loginInfo;

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    /**
     * Create a new ApplicationEvent.
     *
     * @param loginInfo the object on which the event initially occurred (never {@code null})
     */
    public LoginEvent(Object source, LoginInfo loginInfo) {
        super(source);
        this.loginInfo = loginInfo;
    }
}
