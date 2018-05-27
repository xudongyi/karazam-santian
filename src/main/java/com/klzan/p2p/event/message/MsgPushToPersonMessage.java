package com.klzan.p2p.event.message;

import com.klzan.p2p.enums.MessagePushType;

import java.io.Serializable;

/**
 * Created by suhao Date: 2017/2/15 Time: 14:23
 *
 * @version:
 */
public class MsgPushToPersonMessage implements Serializable {
    private MessagePushType type;
    private String registrationId;
    private String notificationTitle;
    private String msgTitle;
    private String msgContent;
    private String extrasparam;

    public MsgPushToPersonMessage(MessagePushType type, String registrationId, String notificationTitle, String msgTitle, String msgContent, String extrasparam) {
        this.type = type;
        this.registrationId = registrationId;
        this.notificationTitle = notificationTitle;
        this.msgTitle = msgTitle;
        this.msgContent = msgContent;
        this.extrasparam = extrasparam;
    }

    public MessagePushType getType() {
        return type;
    }

    public String getRegistrationId() {
        return registrationId;
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
