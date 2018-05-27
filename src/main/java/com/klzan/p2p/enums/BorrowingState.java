package com.klzan.p2p.enums;

/**
 * 借款状态
 */
public enum BorrowingState implements IEnum {
  WAIT("等待"),

  SUCCESS("成功"),

  FAILURE("失败"),

  EXPIRY("过期");

  private String displayName;

  BorrowingState(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }
}