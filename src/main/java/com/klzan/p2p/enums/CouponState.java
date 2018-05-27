package com.klzan.p2p.enums;

/**
 * 优惠券状态
 */
public enum CouponState implements IEnum {

  UNUSED("未使用"),

  USED("已使用"),

  OVERDUE("已过期");

  private String displayName;

  CouponState(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

}