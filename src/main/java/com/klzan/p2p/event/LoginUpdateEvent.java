package com.klzan.p2p.event;

import com.klzan.p2p.security.user.LoginUpdate;
import org.springframework.context.ApplicationEvent;

/**
 * Created by suhao Date: 2017/2/15 Time: 14:21
 *
 * @version:
 */
public class LoginUpdateEvent extends ApplicationEvent {
    private LoginUpdate loginUpdate;

    public LoginUpdate getLoginUpdate() {
        return loginUpdate;
    }

    public void setLoginUpdate(LoginUpdate loginUpdate) {
        this.loginUpdate = loginUpdate;
    }

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public LoginUpdateEvent(Object source, LoginUpdate loginUpdate) {
        super(source);
        this.loginUpdate = loginUpdate;
    }
}
