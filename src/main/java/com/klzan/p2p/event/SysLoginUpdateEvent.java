package com.klzan.p2p.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by suhao Date: 2017/2/15 Time: 14:21
 *
 * @version: 1.0
 */
public class SysLoginUpdateEvent extends ApplicationEvent {
    private Integer sysUserId;

    public Integer getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public SysLoginUpdateEvent(Object source, Integer sysUserId) {
        super(source);
        this.sysUserId = sysUserId;
    }
}
