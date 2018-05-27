package com.klzan.p2p.dao.message;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.DaoSupport;
import com.klzan.p2p.enums.MessagePushType;
import com.klzan.p2p.model.MessagePush;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suhao Date: 2017/1/10 Time: 17:22
 *
 * @version: 1.0
 */
@Repository
public class MessagePushDao extends DaoSupport<MessagePush> {
    public MessagePush findByType(MessagePushType type) {
        String hql = "FROM MessagePush WHERE deleted=0 and type=?0 ORDER BY id DESC ";
        List<MessagePush> messagePushes = find(hql, type);
        if (messagePushes.isEmpty()) {
            return null;
        }
        return messagePushes.get(0);
    }

    public MessagePush findByType(MessagePushType type, Integer userId) {
        String hql = "SELECT a FROM MessagePush a RIGHT JOIN MessagePushUser b ON b.messageId=a.id WHERE a.deleted=0 and a.type=?0 AND b.userId=?1 ORDER BY a.id DESC ";
        List<MessagePush> messagePushes = find(hql, type, userId);
        if (messagePushes.isEmpty()) {
            return null;
        }
        return messagePushes.get(0);
    }

    public PageResult<MessagePush> findPageByType(PageCriteria criteria, MessagePushType type) {
        String hql = "FROM MessagePush WHERE deleted=0 and type=?0 ORDER BY id DESC ";
        return findPage(hql, criteria, type);
    }

    public PageResult<MessagePush> findPageByType(PageCriteria criteria, Integer userId, MessagePushType... types) {
        Map param = new HashMap();
        StringBuffer hql = new StringBuffer ("select a FROM MessagePush a left JOIN MessagePushUser b ON b.messageId=a.id   WHERE a.deleted=0 ");
       if (userId!=null){
           hql.append(" AND b.userId=:userId ");
           param.put("userId", userId);
       }
        if(types!=null && types.length > 0){
           hql.append(" AND a.type in (:types) ");
            List _types = new ArrayList();
            for (MessagePushType type : types) {
                _types.add(type);
            }
           param.put("types", _types);
       }else {
           hql.append(" AND a.type=:type ) ");
           param.put("type", MessagePushType.userown);
       }
       hql.append(" ORDER BY a.createDate DESC ");
        return findPage(hql.toString(), criteria,param);
    }
}
