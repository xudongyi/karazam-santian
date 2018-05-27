package com.klzan.p2p.enums;

public enum InvestmentMethod implements IEnum {
//  BETWEEN_MINIMUM_AND_MAXIMUM("最低投资与最高投资之间"),

  MULTIPLE_OF_MINIMUM("最低投资倍数");

  private String displayName;

  InvestmentMethod(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

}