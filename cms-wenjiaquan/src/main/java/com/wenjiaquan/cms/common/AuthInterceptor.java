package com.wenjiaquan.cms.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

/**   
* @Title: AuthInterceptor.java 
* @Package com.wenjiaquan.cms.common 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2019年12月16日 下午2:18:53 
* @version V1.0   
*/
public class AuthInterceptor implements HandlerInterceptor{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Object userInfo = request.getSession().getAttribute(CmsConstant.UserAdminSessionKey);
		if(userInfo!=null) {
			return true;
		}
	    response.sendRedirect("/admin/");
		return false;
	}
}
