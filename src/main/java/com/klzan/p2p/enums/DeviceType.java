package com.klzan.p2p.enums;

/**
 * 终端类型
 */
public enum DeviceType {
	//PC 手机WEB、iosWEB、android、ios
    ANDROID("ANDROID端"),
    IOS("IOS端"),
    WAP("M站"),
    WECHAT("微信"),
    PC("PC端"),
	ANDROID_WEB("ANDROID端WEB"),
	IOS_WEB("IOS端WEB"),
	SYSTEM("系统"),
	UNKNOWN("未知");
	DeviceType(String displayName) {
		this.displayName = displayName;
	}

	private String displayName;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String toString() {
		return this.name();
	}

	public static DeviceType resolve(String value) {
		for (DeviceType type : values()) {
			if (type.name().equals(value)) {
				return type;
			}
		}
		return UNKNOWN;
	}
}
