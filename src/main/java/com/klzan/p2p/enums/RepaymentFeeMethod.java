package com.klzan.p2p.enums;

/**
 * 还款服务费方式
 */
public enum RepaymentFeeMethod implements IEnum {
  EACH_PERIOD("分期支付"),

  LAST_PERIOD("一次性支付");

  private String displayName;

  RepaymentFeeMethod(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }
}