package com.klzan.p2p.enums;

public enum CreditRating implements IEnum {
  HR("HR"), E("E"), D("D"), C("C"), B("B"), A("A"), AA("AA");

  private String displayName;

  CreditRating(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

}