package com.klzan.p2p.enums;

public enum CpcnRepaymentStatus implements IEnum
{
  unpaid("未还款"),

  paying("还款中"),

  success("成功"),

  failure("失败");

  private String displayName;

  CpcnRepaymentStatus(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }
}
