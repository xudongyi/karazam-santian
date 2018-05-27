package com.klzan.p2p.enums;

/**
 * 转让状态 (还款计划)
 */
public enum TransferState implements IEnum {
  GENERAL("原始"),

  RECOVER("转让恢复"),

  TRANSFERING("转让中"),

  TRANSFER_OUT("已转出"),

  TRANSFER_IN("已转入");

  private String displayName;

  TransferState(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

}