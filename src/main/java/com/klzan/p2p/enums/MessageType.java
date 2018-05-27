package com.klzan.p2p.enums;

public enum MessageType implements IEnum
{
  BORROWING_APPLY("前台借款申请");

  private String displayName;

  MessageType(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }
}