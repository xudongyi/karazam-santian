package com.klzan.p2p.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by suhao Date: 2017/2/15 Time: 14:21
 *
 * @version: 1.0
 */
public class ProjectPublishEvent extends ApplicationEvent {
    private Integer borrowingId;

    public Integer getBorrowingId() {
        return borrowingId;
    }

    public void setBorrowingId(Integer borrowingId) {
        this.borrowingId = borrowingId;
    }

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public ProjectPublishEvent(Object source, Integer borrowingId) {
        super(source);
        this.borrowingId = borrowingId;
    }
}
