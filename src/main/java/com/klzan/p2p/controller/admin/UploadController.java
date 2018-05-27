/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.controller.admin;

import com.klzan.core.Result;
import com.klzan.core.util.DateUtils;
import com.klzan.core.util.SftpUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by suhao Date: 2016/11/14 Time: 17:20
 *
 * @version: 1.0
 */
@Controller
@RequestMapping("admin/upload")
public class UploadController extends BaseAdminController {

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request) {
        return template("upload/index-single");
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Object upload(MultipartFile attachFile, HttpServletRequest request) {
        SftpUtils sftp = new SftpUtils();

        try {
            String filename = "attach_" + DateUtils.getTime() + "." + FilenameUtils.getExtension(attachFile.getOriginalFilename());
            sftp.connect().upload("file", filename, attachFile.getInputStream());
            Map map = new HashMap();
            map.put("attachUrl", "/file/" + filename);
            return Result.success("上传成功", map);
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            return Result.success("上传失败");
        } finally {
            sftp.disconnect();
        }
    }
}
