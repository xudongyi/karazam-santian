package com.klzan.p2p.enums;

public enum PointType implements IEnum
{
//  frozen("冻结"),

  credit("收入"),

  debit("支出");

  private String displayName;

  PointType(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }
}