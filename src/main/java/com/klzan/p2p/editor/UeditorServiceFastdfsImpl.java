package com.klzan.p2p.editor;

import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.MultiState;
import com.baidu.ueditor.define.State;
import com.jcraft.jsch.SftpException;
import com.klzan.core.util.DateUtils;
import com.klzan.core.util.SftpUtils;
import com.klzan.p2p.editor.ueditor.UeditorService;
import com.klzan.p2p.setting.SettingUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * UeditorService实现 - Fastdfs
 */
@Component("ueditorServiceFastdfsImpl")
public class UeditorServiceFastdfsImpl implements UeditorService {
	@Inject
	private SettingUtils setting;

	@Override
	public com.klzan.p2p.editor.MultipartFile getMultipartFile(String filedName, HttpServletRequest request) {
		com.klzan.p2p.editor.MultipartFile resultFile = null;
		try {
			MultipartHttpServletRequest multipartHttpservletRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartHttpservletRequest.getFile(filedName);
			if (!multipartFile.isEmpty()) {
				resultFile = new com.klzan.p2p.editor.StandardMultipartFile(filedName, multipartFile.getInputStream(), multipartFile.getOriginalFilename(), multipartFile.getSize());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultFile;
	}

	@Override
	public State saveFileByInputStream(com.klzan.p2p.editor.MultipartFile multipartFile, long maxSize) {
		State state = null;
		SftpUtils sftp = new SftpUtils();
		try {
			if (multipartFile.getSize() > maxSize) {
				return new BaseState(false, AppInfo.MAX_SIZE);
			}

			String filename = "article_" + DateUtils.getTime() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
			sftp.connect().upload("article", filename, multipartFile.getInputStream());
			state = new BaseState(true);
			state.putInfo("size", multipartFile.getSize());
			state.putInfo("title", multipartFile.getOriginalFilename());
			state.putInfo("url", setting.getDfsUrl() + "/article/" + filename);

			// 把上传的文件信息记入数据库
			// ---自行处理---
			return state;
		} catch (IOException e) {

		} catch (SftpException e) {
			e.printStackTrace();
		} finally {
			sftp.disconnect();
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	@Override
	public State saveBinaryFile(byte[] data, String fileName) {
		State state = null;

		Map<String, Object> uploadResult = null;
		try {
			new SftpUtils().connect().upload("", "123.txt", data);
		} catch (SftpException e) {
			e.printStackTrace();
		}
		if ((Boolean) uploadResult.get("status")) {
			state = new BaseState(true);
			state.putInfo("size", uploadResult.get("length").toString());
			state.putInfo("title", uploadResult.get("fileName").toString());
			state.putInfo("url", setting.getDfsUrl() + "/" + uploadResult.get("link").toString());

			// 把上传的文件信息记入数据库
			// ---自行处理---
			return state;
		}

		return new BaseState(false, AppInfo.IO_ERROR);
	}

	@Override
	public State listFile(String[] allowFiles, int start, int pageSize) {
		// 把计入数据库中的文件信息读取出来，返回即可

		// 下面的代码，仅作示例
		State state = new MultiState(true);
		state.putInfo("start", start);
		state.putInfo("total", 0);
		return state;
	}

}
