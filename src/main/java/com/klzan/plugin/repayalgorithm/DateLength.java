package com.klzan.plugin.repayalgorithm;

import com.klzan.p2p.enums.InterestMethod;

public final class DateLength {

    public static DateLength days(int days, InterestMethod interestMethod) {
        return new DateLength(days, DateUnit.DAY, interestMethod);
    }

    public static DateLength month(int month, InterestMethod interestMethod) {
        return new DateLength(month, DateUnit.MONTH, interestMethod);
    }

    public static DateLength year(int year, InterestMethod interestMethod) {
        return new DateLength(year, DateUnit.YEAR, interestMethod);
    }

    private final int length;
    private final DateUnit unit;
    private final InterestMethod interestMethod;

    public DateLength(int length, DateUnit unit, InterestMethod interestMethod) {
        this.length = length;
        this.unit = unit;
        this.interestMethod = interestMethod;
    }

    public int length() {
        return length;
    }

    public DateUnit unit() {
        return unit;
    }

    public InterestMethod interestMethod() {
        return interestMethod;
    }


    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof DateLength)) return false;

        DateLength that = (DateLength) other;

        return this.length == that.length && this.unit == that.unit;
    }

    @Override
    public int hashCode() {
        int result = 31 * this.length + this.unit.hashCode();
        return result;
    }

    public boolean isMonth() {
        return this.unit == DateUnit.MONTH;
    }

    public boolean isDay() {
        return this.unit == DateUnit.DAY;
    }

    public boolean isYear() {
        return this.unit == DateUnit.YEAR;
    }

    //是否是1个月或者30天
    public boolean isOneMonth() {
        return isMonth() && length == 1 || isDay() && length / 30 == 1 && length % 30 == 0;
    }
}
