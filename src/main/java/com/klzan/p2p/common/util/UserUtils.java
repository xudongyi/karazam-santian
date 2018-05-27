package com.klzan.p2p.common.util;

import com.klzan.core.util.WebUtils;
import com.klzan.p2p.model.User;
import com.klzan.p2p.model.sys.SysUser;
import com.klzan.p2p.security.sysuser.ShiroSysUser;
import com.klzan.p2p.security.user.UserPrincipal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import javax.servlet.http.HttpSession;

public class UserUtils {

	/**
	 * 获取当前系统用户对象shiro
	 * @return
	 */
	public static ShiroSysUser getCurrentShiroSysUser(){
		ShiroSysUser user=(ShiroSysUser) SecurityUtils.getSubject().getPrincipal();
		return (ShiroSysUser) SecurityUtils.getSubject().getPrincipal();
	}
	
	/**
	 * 获取当前用户session
	 * @return session
	 */
	public static HttpSession getSession(){
		return WebUtils.getHttpRequest().getSession();
	}
	
	/**
	 * 获取当前用户对象
	 * @return user
	 */
	public static User getCurrentUser(){
		return (User) getSession().getAttribute("user");
	}

	public static UserPrincipal getCurrentUserPrincipal(){
		return (UserPrincipal) getSession().getAttribute(User.PRINCIPAL_ATTR_NAME);
	}

	/**
	 * 获取当前系统用户对象
	 * @return user
	 */
	public static SysUser getCurrentSysUser(){
		Session session = SecurityUtils.getSubject().getSession();
		if(null!=session){
			return (SysUser) session.getAttribute("sysuser");
		}else{
			return null;
		}
	}
}
 