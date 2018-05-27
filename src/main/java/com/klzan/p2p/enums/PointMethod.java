package com.klzan.p2p.enums;

public enum PointMethod implements IEnum
{
  /**  来源   */
  regist("注册"),

  referral("被推荐"),

  referrer("推荐"),

  sign_in("签到"),

  invest("投资"),

  repayment("回款"),

  exchange_cancel("商品订单撤销"),

  system_in("后台奖励(未启用)"),

  activity_in("活动奖励(未启用)"),

  invest_king("标王赠送(未启用)"),

  other("其他来源"),

  /**  使用   */
  exchange("商品兑换"),

  system_out("后台扣除(未启用)"),

  activity_out("活动扣除(未启用)"),

  ;

  private String displayName;

  PointMethod(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }
}