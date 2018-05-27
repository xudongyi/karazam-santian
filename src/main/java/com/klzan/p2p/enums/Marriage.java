package com.klzan.p2p.enums;

/**
 * 婚姻状态
 */
public enum Marriage implements IEnum {
  UNMARRIED("未婚"),

  MARRIED("已婚"),

  DIVORCED("离异"),

  WIDOWED("丧偶");

  private String displayName;

  Marriage(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }
}