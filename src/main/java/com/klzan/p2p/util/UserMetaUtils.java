package com.klzan.p2p.util;

import com.klzan.core.util.ObjectUtils;
import com.klzan.p2p.model.UserMeta;
import com.klzan.p2p.vo.user.AbstractUserMeta;
import com.klzan.p2p.vo.user.UserMetaVo;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户元信息工具类
 * Created by suhao Date: 2017/5/26 Time: 16:25
 *
 * @version: 1.0
 */
public class UserMetaUtils {
    public static <M> M convert(List<UserMetaVo> metas, Class clazz) {
        try {
            Object obj = ObjectUtils.newInstance(clazz.getPackage().getName() + "." +clazz.getSimpleName());
            for (UserMetaVo meta : metas) {
                BeanUtils.setProperty(obj, meta.getMetaKey(), meta.getMetaVal());
            }
            return (M) obj;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<UserMeta> convertBeanToMeta(Integer userId, String type, AbstractUserMeta autoSign) {
        List<UserMeta> metas = new ArrayList<>();
        Map<String, Object> fieldMap = ObjectUtils.getFieldMap(autoSign);
        for (Map.Entry<String, Object> entry : fieldMap.entrySet()) {
            String metaKey = entry.getKey().toString();
            Object metaVal = entry.getValue();
            if (null == metaVal) {
                continue;
            }
            UserMeta meta = new UserMeta(userId, type, metaKey, metaVal.toString());
            metas.add(meta);
        }
        return metas;
    }
}
