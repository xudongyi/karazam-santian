package com.klzan.p2p.editor.ueditor;

import com.baidu.ueditor.define.State;
import com.klzan.p2p.editor.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface UeditorService {

	/**
	 * 获取上传的文件
	 * 
	 * @param filedName
	 *            参数名
	 * @param request
	 * @return
	 */
	MultipartFile getMultipartFile(String filedName, HttpServletRequest request);

	/**
	 * 存储文件
	 * 
	 * @param multipartFile
	 * @param maxSize
	 * @return
	 */
	State saveFileByInputStream(MultipartFile multipartFile, long maxSize);

	/**
	 * 存储文件
	 * 
	 * @param data
	 * @param fileName
	 * @return
	 */
	State saveBinaryFile(byte[] data, String fileName);

	/**
	 * 获取文件列表
	 * 
	 * @param allowFiles
	 *            允许显示的文件
	 * @param start
	 *            起始位置
	 * @param pageSize
	 *            每页显示条数
	 * @return
	 */
	State listFile(String[] allowFiles, int start, int pageSize);
	
}
