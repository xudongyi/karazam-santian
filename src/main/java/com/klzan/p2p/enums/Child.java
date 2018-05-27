package com.klzan.p2p.enums;

/**
 * 子女状态
 */
public enum Child implements IEnum {
  NONE("无"),

  ONE("一个"),

  TWO("两个"),

  THREE("三个"),

  MORE("更多");

  private String displayName;

  Child(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }
}