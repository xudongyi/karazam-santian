package com.klzan.p2p.enums;

/**
 * 借款审核状态
 */
public enum BorrowingCheckState implements IEnum {
  WAIT("等待"),

  SUCCESS("通过"),

  FAILURE("失败");

  private String displayName;

  BorrowingCheckState(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }
}