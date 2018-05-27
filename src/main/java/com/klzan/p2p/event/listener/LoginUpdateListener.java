package com.klzan.p2p.event.listener;

import com.klzan.core.util.DateUtils;
import com.klzan.p2p.event.LoginUpdateEvent;
import com.klzan.p2p.model.User;
import com.klzan.p2p.security.user.LoginUpdate;
import com.klzan.p2p.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.sql.Timestamp;

/**
 * Created by suhao Date: 2017/2/15 Time: 14:32
 *
 * @version: 1.0
 */
@Component
public class LoginUpdateListener implements ApplicationListener {
    private static final Logger logger = LoggerFactory.getLogger(LoginUpdateListener.class);
    @Inject
    private UserService userService;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof LoginUpdateEvent) {
            LoginUpdate loginUpdate = ((LoginUpdateEvent)event).getLoginUpdate();
            logger.info("用户ID[{}]登录信息更新", loginUpdate.getUserId());
            User user = userService.get(loginUpdate.getUserId());
            Integer loginCount = (user.getLoginCount() == null ? 0 : user.getLoginCount()) + 1;
            Timestamp previousVisit = user.getLastVisit();
            Timestamp lastVisit = DateUtils.getTimestamp();
            user.updateLoginInfo(loginCount, previousVisit, lastVisit);
            userService.update(user);
        }
    }
}
