package com.klzan.p2p.enums;

/**
 * 转让状态 (转让标)
 */
public enum TransferLoanState implements IEnum {

  TRANSFERING("转让中"),

  TRANSFERPART("部分转让"),

  TRANSFERED("已转让"),

  CANCEL("已撤销");

  private String displayName;

  TransferLoanState(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

}