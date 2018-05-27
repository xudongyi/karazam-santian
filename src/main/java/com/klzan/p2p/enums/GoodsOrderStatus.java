package com.klzan.p2p.enums;

/**
 * 商品订单状态
 */
public enum GoodsOrderStatus implements IEnum{

//	unpaid("未支付"),

	paid("已支付"),

	sending("待发货"),

	send("已发货"),

	complet("已完成"),

	failure("已失败"),

	cancel("已撤销");

	private String displayName;

	GoodsOrderStatus(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

}
