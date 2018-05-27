package com.klzan.core.freemarker.function;

public enum FunctionName {
	
	URL_FUNCTION("url"),
	STATIC_FUNCTION("static");
	
	private String functionName;
	
	FunctionName(String functionName) {
		this.functionName = functionName;
	}
	
	public String getFunctionName() {
		return functionName;
	}
}
