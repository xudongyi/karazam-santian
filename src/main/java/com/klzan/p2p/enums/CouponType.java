package com.klzan.p2p.enums;

/**
 * 优惠券类型
 */
public enum CouponType implements IEnum {

  CASH("现金券");

  private String displayName;

  CouponType(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

}