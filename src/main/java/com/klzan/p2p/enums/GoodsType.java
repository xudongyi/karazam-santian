package com.klzan.p2p.enums;

public enum GoodsType implements IEnum
{

  gift("礼品店"),

  benefit("实惠购"),

  ;

  private String displayName;

  GoodsType(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }
}