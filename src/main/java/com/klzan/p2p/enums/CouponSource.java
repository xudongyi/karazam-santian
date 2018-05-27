package com.klzan.p2p.enums;

/**
 * 优惠券 来源
 */
public enum CouponSource implements IEnum {

  REGISTER("注册送红包"),
  INVESTMENT("投资送红包"),
  REFERRAL("推荐送红包"),
  ACTIVITY("活动赠送红包"),
  SIGN_IN("签到赠送红包"),
  BACK_CREATE("平台新增红包");

  private String displayName;

  CouponSource(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

}