package com.klzan.p2p.enums;

import com.klzan.core.exception.BusinessProcessException;

/**
 * Created by suhao Date: 2017/5/3 Time: 9:42
 *
 * @version: 1.0
 */
public enum FeeDeductionType implements IEnum {
    IN("内扣", 1),

    OUT("外扣", 2);

    private String displayName;
    private Integer deduction;

    FeeDeductionType(String displayName, Integer deduction) {
        this.displayName = displayName;
        this.deduction = deduction;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public Integer getDeduction() {
        return deduction;
    }

    public static FeeDeductionType convert(Integer deduction) {
        FeeDeductionType[] deductionTypes = values();
        for (FeeDeductionType deductionType : deductionTypes) {
            if (deductionType.getDeduction().equals(deduction)) {
                return deductionType;
            }
        }
        throw new BusinessProcessException("FeeDeductionType convert error ");
    }
}