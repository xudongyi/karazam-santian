package com.klzan.p2p.vo.message;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.MessagePushType;

/**
 * Created by suhao Date: 2017/1/16 Time: 15:37
 *
 * @version: 1.0
 */
public class MessagePushVo extends BaseVo {
    /** title */
    private String title;
    private String notificationTitle;
    /** 内容 */
    private String content;
    /** 类型 */
    private MessagePushType type;
    private String typeStr;

    public String getTitle() {
        return title;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessagePushType getType() {
        if (StringUtils.isNotBlank(typeStr)) {
            return MessagePushType.valueOf(typeStr);
        }
        return type;
    }

    public void setType(MessagePushType type) {
        this.type = type;
    }

    public String getTypeStr() {
        if (StringUtils.isNotBlank(typeStr)) {
            return MessagePushType.valueOf(typeStr).getDisplayName();
        }
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }
}
