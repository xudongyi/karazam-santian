package com.klzan.p2p.enums;

/**
 * 借款申请类型
 */
public enum BorrowingApplyType implements IEnum {

  CAR_PLEDGE("车抵贷"),

  FAST_BORROW("秒速贷"),

  SALE_BORROW("拍易融"),

  HOUSE_BORROW("房拖贷"),

  TRAVEL_BORROW("出行派"),

  CAR_BY_STAGES("车分期");

  private String displayName;

  BorrowingApplyType(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

}