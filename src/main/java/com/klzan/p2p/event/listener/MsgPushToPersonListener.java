package com.klzan.p2p.event.listener;

import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.common.push.JpushClientUtil;
import com.klzan.p2p.event.MsgPushToPersonEvent;
import com.klzan.p2p.event.message.MsgPushToPersonMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by suhao Date: 2017/2/15 Time: 14:32
 *
 * @version: 1.0
 */
@Component
public class MsgPushToPersonListener implements ApplicationListener {
    private static final Logger logger = LoggerFactory.getLogger(MsgPushToPersonListener.class);

    @Override
    @Async
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof MsgPushToPersonEvent) {
            MsgPushToPersonMessage message = ((MsgPushToPersonEvent)event).getMessage();
            logger.info("推送用户[{}]消息：{}", message.getRegistrationId(), JsonUtils.toJson(message));
            JpushClientUtil.sendToRegistrationId(message.getRegistrationId(), message.getNotificationTitle(), message.getMsgTitle(), message.getMsgContent(), message.getExtrasparam());
        }
    }
}
