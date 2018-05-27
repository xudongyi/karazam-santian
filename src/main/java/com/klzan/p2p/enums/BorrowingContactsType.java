/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.enums;

/**
 * 标的联系人类型
 */
public enum BorrowingContactsType implements IEnum {
  LAUNCH("标的发起人"),

  PRINCIPAL("标的负责人"),

  SERVICE("客服"),

  RISK_CONTROL_PERSONNEL("风控"),

  OTHER("其他");

  private String displayName;

  BorrowingContactsType(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }
}