package com.klzan.p2p.model;
import com.klzan.p2p.model.base.BaseModel;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by suhao Date: 2017/1/10 Time: 16:47
 * 消息接收人记录
 * @version: 1.0
 */
@Entity
@Table(name = "karazam_message_push_user")
public class MessagePushUser extends BaseModel {
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 消息ID
     */
    private Integer messageId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }
}
