package com.klzan.p2p.util;

import com.klzan.core.util.StringUtils;
import com.klzan.core.util.WebUtils;
import com.klzan.p2p.common.util.UserUtils;
import org.apache.shiro.SecurityUtils;

/**
 * Created by suhao Date: 2017/2/16 Time: 19:29
 *
 * @version: 1.0
 */
public class CommonUtils {

    public static String getRemoteIp() {
        try {
            String remoteIp = WebUtils.getRemoteIp(WebUtils.getHttpRequest());
            remoteIp = remoteIp == null ? "-" : remoteIp;
            return remoteIp;
        } catch (Exception e) {
            return "-";
        }
    }

    public static String getLoginName() {
        try {
            String loginName = SecurityUtils.getSubject().getPrincipal().toString();
            if (StringUtils.isBlank(loginName)) {
                loginName = UserUtils.getCurrentUser().getLoginName();
            }
            loginName = loginName == null ? "-" : loginName;
            return loginName;
        } catch (Exception e) {
            return "-";
        }
    }

}
