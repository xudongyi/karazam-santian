package com.klzan.p2p.event.listener;

import com.klzan.p2p.enums.UserLogType;
import com.klzan.p2p.event.LoginEvent;
import com.klzan.p2p.model.UserLog;
import com.klzan.p2p.security.user.LoginInfo;
import com.klzan.p2p.service.user.UserLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by suhao Date: 2017/2/15 Time: 14:32
 *
 * @version: 1.0
 */
@Component
public class LoginListener implements ApplicationListener {
    private static final Logger logger = LoggerFactory.getLogger(LoginListener.class);
    @Inject
    private UserLogService userLogService;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof LoginEvent) {
            LoginInfo loginInfo = ((LoginEvent)event).getLoginInfo();
            logger.info("用户ID[{}]通过{}登录", loginInfo.getUserId(), loginInfo.getLoginSource());
            UserLog userLog = new UserLog(UserLogType.LOGIN, loginInfo.getLoginSource() + "登录", loginInfo.getLoginName(), loginInfo.getLoginIp(), loginInfo.getUserId());
            userLogService.persist(userLog);
        }
    }
}
