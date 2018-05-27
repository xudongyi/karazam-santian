package com.klzan.p2p.controller.admin;

import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.editor.ueditor.UeditorActionEnter;
import com.klzan.p2p.editor.ueditor.UeditorService;
import com.klzan.p2p.util.UnicodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Ueditor后台处理入口
 */
@Controller("UeditorController")
@RequestMapping("ueditor")
public class UeditorController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Resource(name="ueditorServiceFastdfsImpl")
	private UeditorService ueditoreService;
	
	@RequestMapping(value = "execute")
	@ResponseBody
	public Object execute(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
		String rootPath = request.getServletContext().getRealPath("/");
		String resultMsg = new UeditorActionEnter(request, rootPath + "/static", this.ueditoreService).exec();

		logger.error("ueditor execute ... resultMsg:" + UnicodeUtils.fromUnicode(resultMsg));

		return JsonUtils.toObject(resultMsg, Map.class);
	}
}
