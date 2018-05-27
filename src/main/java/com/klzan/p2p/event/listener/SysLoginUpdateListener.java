package com.klzan.p2p.event.listener;

import com.klzan.p2p.event.SysLoginUpdateEvent;
import com.klzan.p2p.model.sys.SysUser;
import com.klzan.p2p.service.sysuser.SysUserService;
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
public class SysLoginUpdateListener implements ApplicationListener {
    private static final Logger logger = LoggerFactory.getLogger(SysLoginUpdateListener.class);
    @Inject
    private SysUserService sysUserService;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof SysLoginUpdateEvent) {
            Integer sysUserId = ((SysLoginUpdateEvent)event).getSysUserId();
            logger.info("系统用户ID[{}]登录信息更新", sysUserId);
            SysUser sysUser = sysUserService.get(sysUserId);
            sysUserService.updateLoginUser(sysUser);
        }
    }
}
