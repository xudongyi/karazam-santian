package com.klzan.core.freemarker.tags;

public enum TagName {
	
    TAG_MASTER("insert"),
    TAG_JS("js"),
    TAG_CSS("css"),
    TAG_BODY("body"),
    TAG_IMAGE("img"),
    METAS("metas"),
    TITLE("title"), 
    NESTED_STYLESHEET("nestedstyle"), 
    NESTED_SCRIPT("nestedscript"), 
    SCRIPT("script"), 
    STYLESHEET("stylesheet"),
    AUTHORIZE("authorize");
    private String tagName;
    
    TagName(String tagName) {
    	this.tagName = tagName;
    }

	public String getTagName() {
		return tagName;
	}
}
