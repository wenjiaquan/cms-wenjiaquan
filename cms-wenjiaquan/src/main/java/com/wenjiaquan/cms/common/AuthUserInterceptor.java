package com.wenjiaquan.cms.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.wenjiaquan.cms.pojo.User;
import com.wenjiaquan.cms.service.UserService;

/**   
* @Title: AuthInterceptor.java 
* @Package com.wenjiaquan.cms.common 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2019年12月16日 下午2:18:53 
* @version V1.0   
*/
public class AuthUserInterceptor implements HandlerInterceptor{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Object userInfo = request.getSession().getAttribute(CmsConstant.UserSessionKey);
		if(userInfo!=null) {
			return true;
		}
		//记住登录
		String username = CookieUtil.getCookieByName(request,"username");
		if(username!=null) {
			UserService userService = SpringBeanUtils.getBean(UserService.class);
			userInfo = userService.getByUsername(username);
			request.getSession().setAttribute(CmsConstant.UserSessionKey, userInfo);
		}
	    response.sendRedirect("/user/login");
		return false;
	}
}
