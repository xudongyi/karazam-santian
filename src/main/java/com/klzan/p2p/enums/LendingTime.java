package com.klzan.p2p.enums;

public enum LendingTime implements IEnum {
  AFTER_EACH_INVESTMENT("每次投资后"),

  AFTER_AUDIT("后台审核后");

  private String displayName;

  LendingTime(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

}