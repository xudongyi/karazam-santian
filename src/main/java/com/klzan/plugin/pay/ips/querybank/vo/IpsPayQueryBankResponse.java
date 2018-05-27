package com.klzan.plugin.pay.ips.querybank.vo;

import com.klzan.plugin.pay.IDetailResponse;

import java.util.List;

/**
 * Created by zhutao on 2017/3/15.
 */
public class IpsPayQueryBankResponse implements IDetailResponse {

    private List<IpsBank> banks;

    public List<IpsBank> getBanks() {
        return banks;
    }
}
