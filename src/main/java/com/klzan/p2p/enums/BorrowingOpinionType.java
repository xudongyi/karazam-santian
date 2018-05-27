package com.klzan.p2p.enums;

public enum BorrowingOpinionType implements IEnum {
  APPLY("筹备意见"),

  RESCIND_APPLY("撤销申请意见"),

  INQUIRY("调查意见"),

  RESCIND_INQUIRY("撤销调查意见"),

  APPROVAL("审批意见"),

  RESCIND_APPROVAL("撤销审批意见"),

  INVEST("投资意见"),

  RESCIND_INVEST("撤销投资意见"),

  LEND("出借意见"),

  REPAY("还款意见"),

  MODIFY("修改意见");

  private String displayName;

  BorrowingOpinionType(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

}