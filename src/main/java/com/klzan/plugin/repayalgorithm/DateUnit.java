package com.klzan.plugin.repayalgorithm;

import com.klzan.core.util.DateUtils;

import java.util.Date;

public enum DateUnit {
    DAY(365, "天"),
    MONTH(12, "个月"),
    YEAR(1, "年");

    DateUnit(int lengthInYear, String displayName) {
        this.lengthInYear = lengthInYear;
        this.displayName = displayName;
    }

    public int lengthInYear;

    private int leapYearDay = 366;

    private String displayName;

    public String getDisplayName() {
        return this.displayName;
    }

    public int getLengthInYear(Date date) {
        if (DAY.equals(this)) {
            if (DateUtils.isLeapYear(date)) {
                return leapYearDay;
            }
        }
        return lengthInYear;
    }
}
