package com.klzan.p2p.enums;

/**
 * 还款状态
 */
public enum RepaymentState implements IEnum {
  REPAYING("还款中"),

  REPAID("已还款");

  private String displayName;

  RepaymentState(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }
}