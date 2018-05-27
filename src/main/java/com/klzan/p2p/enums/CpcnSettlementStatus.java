package com.klzan.p2p.enums;

public enum CpcnSettlementStatus implements IEnum
{
  unsettled("未结算"),

  settling("结算中"),

  success("已结算"),

  failure("结算失败");

  private String displayName;

  CpcnSettlementStatus(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }
}