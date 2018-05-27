package com.klzan.core;

import java.util.Map;

public class RequestContextHolder {
	
	private final ThreadLocal<Map<String, Object>> requestConextModel = new ThreadLocal<>();
	
	public Map<String, Object> getModel() {
		return requestConextModel.get();
	}
	
	public void bindModel(Map<String, Object> model) {
		requestConextModel.set(model);
	}
	
	public void releaseModel() {
		requestConextModel.remove();
	}
}
