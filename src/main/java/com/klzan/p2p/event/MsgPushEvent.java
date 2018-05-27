package com.klzan.p2p.event;

import com.klzan.p2p.event.message.MsgPushMessage;
import org.springframework.context.ApplicationEvent;

/**
 * Created by suhao Date: 2017/2/15 Time: 14:21
 *
 * @version:
 */
public class MsgPushEvent extends ApplicationEvent {
    private MsgPushMessage message;

    public MsgPushMessage getMessage() {
        return message;
    }

    public void setMessage(MsgPushMessage message) {
        this.message = message;
    }

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public MsgPushEvent(Object source, MsgPushMessage message) {
        super(source);
        this.message = message;
    }
}
