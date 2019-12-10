package com.wenjiaquan.cms.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wenjiaquan.cms.service.UserService;

/**   
* @Title: UserController.java 
* @Package com.wenjiaquan.cms.controller.User 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2019年12月10日 下午6:23:11 
* @version V1.0   
*/
@Controller
public class UserController {
	@Autowired
	private UserService userservice;
	@RequestMapping("")
	public String login() {
		return "index";
	}
}
