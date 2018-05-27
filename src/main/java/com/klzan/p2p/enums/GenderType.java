package com.klzan.p2p.enums;

/**
 * 性别
 */
public enum GenderType implements IEnum {

	MALE("男"),
	FEMALE("女"),
	UNKNOWN("-");

	private String displayName;

	GenderType(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
