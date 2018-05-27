package com.klzan.p2p.model;
import com.klzan.p2p.enums.MessagePushType;
import com.klzan.p2p.enums.MessageReciveType;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.*;

/**
 * Created by suhao Date: 2017/1/10 Time: 16:47
 * 消息推送记录
 * @version: 1.0
 */
@Entity
@Table(name = "karazam_message_push")
public class MessagePush extends BaseModel {
    /** 标题 */
    private String title;
    /** 内容 */
    private String content;
    /** 类型 */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessagePushType type;
    /** 通知title */
    String notificationTitle;
    /** 扩展参数 */
    String ext;
    /** 接收对象 */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageReciveType reciveType;

    public MessagePush() {
    }

    public MessagePush(String title, String content, MessagePushType type, String notificationTitle, String ext, MessageReciveType reciveType) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.notificationTitle = notificationTitle;
        this.ext = ext;
        this.reciveType = reciveType;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public MessagePushType getType() {
        return type;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public String getExt() {
        return ext;
    }

    public MessageReciveType getReciveType() {
        return reciveType;
    }
}
