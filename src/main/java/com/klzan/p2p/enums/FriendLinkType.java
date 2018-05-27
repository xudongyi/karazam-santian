package com.klzan.p2p.enums;

/**
 * 链接类型
 */
public enum FriendLinkType implements IEnum {
  TEXT("文本链接"),

  IMAGE("图片链接");

  private String displayName;

  FriendLinkType(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }
}