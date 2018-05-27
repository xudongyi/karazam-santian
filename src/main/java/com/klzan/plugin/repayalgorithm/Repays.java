/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.plugin.repayalgorithm;

import com.klzan.p2p.model.Repayment;
import com.klzan.p2p.model.RepaymentPlan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by suhao Date: 2016/11/16 Time: 21:24
 *
 * @version: 1.0
 */
public class Repays implements Serializable {
    private List<Repayment> repayments = new ArrayList<>();
    private List<RepaymentPlan> repaymentPlen = new ArrayList<>();

    public List<Repayment> getRepayments() {
        return repayments;
    }

    public List<RepaymentPlan> getRepaymentPlen() {
        return repaymentPlen;
    }

    public void addRepaymentPlan(List<RepaymentPlan> repaymentPlen) {
        this.repaymentPlen.addAll(repaymentPlen);
    }

    public void addRepayment(List<Repayment> repayments) {
        this.repayments.addAll(repayments);
    }
}
