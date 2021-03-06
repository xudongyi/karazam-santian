package com.klzan.p2p.editor.ueditor;

import com.baidu.ueditor.define.State;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class UeditorUploader {

	private HttpServletRequest request = null;
	private Map<String, Object> conf = null;

	private UeditorService ueditorService = null;

	public UeditorUploader(HttpServletRequest request, Map<String, Object> conf, UeditorService ueditorService) {
		this.request = request;
		this.conf = conf;
		
		this.ueditorService = ueditorService;
	}

	public final State doExec() {
		String filedName = (String) this.conf.get("fieldName");
		State state = null;
		
		if ("true".equals(this.conf.get("isBase64"))) {
			state = UeditorBase64Uploader.save(this.request.getParameter(filedName), this.conf, this.ueditorService);
		} else {
			state = UeditorBinaryUploader.save(this.request, this.conf, this.ueditorService);
		}
		
		return state;
	}

}
