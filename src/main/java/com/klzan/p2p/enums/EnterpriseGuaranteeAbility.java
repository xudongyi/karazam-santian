package com.klzan.p2p.enums;

/**
 * 企业担保资质状态
 */
public enum EnterpriseGuaranteeAbility implements IEnum {
  NOT_APPLY("未申请"),

  APPLING("申请中"),

  SUCCESS("成功"),

  FAILURE("失败");

  private String displayName;

  EnterpriseGuaranteeAbility(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

}