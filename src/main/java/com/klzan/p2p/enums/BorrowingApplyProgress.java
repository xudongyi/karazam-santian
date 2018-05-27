package com.klzan.p2p.enums;

/**
 * 借款申请进度
 */
public enum BorrowingApplyProgress implements IEnum {

  APPROVAL("申请中"),

  CONTACTED("已联系"),

  REJECTED("驳回"),

  COMPLETED("已通过");

  private String displayName;

  BorrowingApplyProgress(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

}