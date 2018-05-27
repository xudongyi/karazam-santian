package com.klzan.p2p.event.listener;

import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.common.push.JpushClientUtil;
import com.klzan.p2p.event.MsgPushEvent;
import com.klzan.p2p.event.message.MsgPushMessage;
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
public class MsgPushListener implements ApplicationListener {
    private static final Logger logger = LoggerFactory.getLogger(MsgPushListener.class);

    @Override
    @Async
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof MsgPushEvent) {
            MsgPushMessage message = ((MsgPushEvent)event).getMessage();
            logger.info("推送[{}]消息：{}", message.getReciveType(), JsonUtils.toJson(message));
            switch (message.getReciveType()) {
                case all:
                    JpushClientUtil.sendToAll(message.getNotificationTitle(), message.getMsgTitle(), message.getMsgContent(), message.getExtrasparam());
                    break;
                case android:
                    JpushClientUtil.sendToAllAndroid(message.getNotificationTitle(), message.getMsgTitle(), message.getMsgContent(), message.getExtrasparam());
                    break;
                case ios:
                    JpushClientUtil.sendToAllIos(message.getNotificationTitle(), message.getMsgTitle(), message.getMsgContent(), message.getExtrasparam());
                    break;
                default:
                    logger.error("消息推送失败");
            }
        }
    }
}
