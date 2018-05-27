package com.klzan.p2p.enums;

/**
 * 企业放贷资质状态
 */
public enum EnterpriseBorrowingAbility implements IEnum {
  NOT_APPLY("未申请"),

  APPLING("申请中"),

  SUCCESS("成功"),

  FAILURE("失败");

  private String displayName;

  EnterpriseBorrowingAbility(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }
}