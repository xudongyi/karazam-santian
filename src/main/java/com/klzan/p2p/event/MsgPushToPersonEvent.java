package com.klzan.p2p.event;

import com.klzan.p2p.event.message.MsgPushToPersonMessage;
import org.springframework.context.ApplicationEvent;

/**
 * Created by suhao Date: 2017/2/15 Time: 14:21
 *
 * @version:
 */
public class MsgPushToPersonEvent extends ApplicationEvent {
    private MsgPushToPersonMessage message;

    public MsgPushToPersonMessage getMessage() {
        return message;
    }

    public void setMessage(MsgPushToPersonMessage message) {
        this.message = message;
    }

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public MsgPushToPersonEvent(Object source, MsgPushToPersonMessage message) {
        super(source);
        this.message = message;
    }
}
