package com.klzan.p2p.enums;

import com.klzan.p2p.vo.user.UserOpenIpsAcctVo;

/**
 * 用户元信息类型
 */
public enum UserMetaType implements IEnumMeta {
    IPS_OPEN_ACCT("IPS开户", UserOpenIpsAcctVo.class),

    ;

    private String displayName;
    private Class infoClazz;

    UserMetaType(String displayName, Class infoClazz) {
        this.displayName = displayName;
        this.infoClazz = infoClazz;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getType() {
        return this.name();
    }

    @Override
    public Class getInfoBean() {
        return infoClazz;
    }
}
