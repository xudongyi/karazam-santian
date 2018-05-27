package com.klzan.plugin.pay.ips.querybank.vo;

import java.io.Serializable;

/**
 * Created by zhutao on 2017/3/15.
 */
public class IpsBank implements Serializable {
    private String bankCode;
    private String bankName;
    private String cardType;
    private String bizId;

    public String getBankCode() {
        return bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public String getCardType() {
        return cardType;
    }

    public String getBizId() {
        return bizId;
    }
}
