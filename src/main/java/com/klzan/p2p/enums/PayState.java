package com.klzan.p2p.enums;

/**
 * 支付状态
 */
public enum PayState implements IEnum {
  NOTPAY("未支付"),

  PAYING("支付中"),

  PAID("已支付");

  private String displayName;

  PayState(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }
}