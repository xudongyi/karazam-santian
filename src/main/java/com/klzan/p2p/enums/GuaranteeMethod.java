package com.klzan.p2p.enums;

/**
 * 担保类型
 */
public enum GuaranteeMethod implements IEnum {
  CAPITAL("本金担保"),

  CAPITAL_PLUS_INTEREST("本息担保");

  private String displayName;

  GuaranteeMethod(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

}