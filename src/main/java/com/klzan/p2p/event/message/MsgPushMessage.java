package com.klzan.p2p.event.message;

import com.klzan.p2p.enums.MessageReciveType;

import java.io.Serializable;

/**
 * Created by suhao Date: 2017/2/15 Time: 14:23
 *
 * @version:
 */
public class MsgPushMessage implements Serializable {
    private MessageReciveType reciveType;
    private String notificationTitle;
    private String msgTitle;
    private String msgContent;
    private String extrasparam;

    public MsgPushMessage(MessageReciveType reciveType, String notificationTitle, String msgTitle, String msgContent, String extrasparam) {
        this.reciveType = reciveType;
        this.notificationTitle = notificationTitle;
        this.msgTitle = msgTitle;
        this.msgContent = msgContent;
        this.extrasparam = extrasparam;
    }

    public MessageReciveType getReciveType() {
        return reciveType;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public String getExtrasparam() {
        return extrasparam;
    }
}
