package com.wenjiaquan.cms.controller.admin;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wenjiaquan.cms.common.CmsConstant;
import com.wenjiaquan.cms.common.CmsMd5Util;
import com.wenjiaquan.cms.common.JsonResult;
import com.wenjiaquan.cms.pojo.User;
import com.wenjiaquan.cms.service.UserService;
import com.wenjiaquan.utils.StringUtil;

/**   
* @Title: AdminUserController.java 
* @Package com.wenjiaquan.cms.controller.admin 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2019年12月16日 下午2:28:02 
* @version V1.0   
*/
@Controller
@RequestMapping("/admin/user/")
public class AdminUserController {
	@Autowired
	private UserService userService;
	/**
	 * @Title: login   
	 * @Description: 后台登录接口   
	 * @param: @param user
	 * @param: @return      
	 * @return: Object      
	 * @throws
	 */
	@RequestMapping("login")
	@ResponseBody
	public Object login(User user,HttpSession session) {
		//判断用户名和密码
		if(StringUtil.isBlank(user.getUsername()) || StringUtil.isBlank(user.getPassword())) {
			return JsonResult.fail(1000, "用户名和密码不能为空");
		}
		//查询用户
		User userInfo = userService.getByUsername(user.getUsername());
		//用户为空
		if(userInfo==null) {
			return JsonResult.fail(1000, "用户名或密码错误");
		}
		//是否管理员
		if(userInfo.isAdmin()) {
			return JsonResult.fail(1000, "权限不够");
		}
		//判断密码
		String string2md5 = CmsMd5Util.string2MD5(user.getPassword());
		System.out.println(string2md5);
		if(string2md5.equals(userInfo.getPassword())) {
			session.setAttribute(CmsConstant.UserAdminSessionKey, userInfo);
			session.setAttribute("username",userInfo.getUsername());
			return JsonResult.sucess();
		}
		return JsonResult.fail(500, "未知错误");
	}

	@RequestMapping("logout")
	public Object logout(HttpServletResponse response,HttpSession session) {
		session.invalidate();
		session.removeAttribute(CmsConstant.UserAdminSessionKey);
		return "redirect:/admin/";
	}
}
