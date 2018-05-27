package com.klzan.core.lock;

public enum DistributeLockProviders {
	
	LOCAL("local"), REDIS("redis");
	
	private String providerName;
	
	DistributeLockProviders(String providerName) {
		this.providerName = providerName;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
}
