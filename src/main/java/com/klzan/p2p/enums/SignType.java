package com.klzan.p2p.enums;

import com.klzan.p2p.vo.user.UserAutoInvestVo;
import com.klzan.p2p.vo.user.UserAutoRepayVo;

public enum SignType implements IEnumMeta {
    AUTO_INVESTMENT_SIGN("自动投标签约", UserAutoInvestVo.class),

    AUTO_REPAYMENT_SIGN("自动还款签约", UserAutoRepayVo.class),

    ;

    private String displayName;
    private Class infoClazz;

    SignType(String displayName, Class infoClazz) {
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