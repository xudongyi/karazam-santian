package com.klzan.p2p.enums;

/**
 * 借款进度
 */
public enum BorrowingProgress implements IEnum {
  INQUIRING("调查中"), /*调查失败*/

  APPROVAL("审批中"), /*审批失败*/

  PREVIEWING("预告中"),

  AUTOINVESTING("自动投资中"),

  INVESTING("投资中"),

  EXPIRE("已流标"),

  REFUND("已退款"),

  LENDING("出借中"),

  RESCIND("撤销"),

  REPAYING("还款中"),

  COMPLETED("已完成");

  private String displayName;

  BorrowingProgress(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

}