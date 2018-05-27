package com.klzan.p2p.util;

import com.klzan.p2p.enums.IEnumMeta;
import com.klzan.p2p.enums.SignType;
import com.klzan.p2p.enums.UserMetaType;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户元信息类型
 * Created by suhao Date: 2017/5/26 Time: 11:54
 *
 * @version: 1.0
 */
public final class UserMetaTypeUtils {
    public static final IEnumMeta[] METAS = {
            SignType.AUTO_INVESTMENT_SIGN,
            SignType.AUTO_REPAYMENT_SIGN,
            UserMetaType.IPS_OPEN_ACCT
    };
    public static final Map<String, IEnumMeta> MAPS = new HashMap<>();
    static {
        for (IEnumMeta meta : METAS) {
            MAPS.put(meta.getType(), meta);
        }
    }

    public static String getType(IEnumMeta type) {
        return MAPS.get(type.getType()).getType();
    }

    public static String getDes(IEnumMeta type) {
        return MAPS.get(type.getType()).getDisplayName();
    }

    public static IEnumMeta getMetaType(String metaType) {
        if (!MAPS.containsKey(metaType)) {
            return null;
        }
        return MAPS.get(metaType);
    }

}
