package com.klzan.p2p.enums;

/**
 * 物流状态
 */
public enum GoodsShippingStatus implements IEnum {

	unshipped("未发货"),

	shipped("已发货"),

	take_over("已收货");

	private String displayName;

	GoodsShippingStatus(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}
}
