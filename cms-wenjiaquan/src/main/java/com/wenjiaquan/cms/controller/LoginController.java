package com.wenjiaquan.cms.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.wenjiaquan.cms.pojo.Article;
import com.wenjiaquan.cms.pojo.Channel;
import com.wenjiaquan.cms.pojo.User;
import com.wenjiaquan.cms.service.ArticleService;
import com.wenjiaquan.cms.service.UserService;

/**   
* @Title: LoginController.java 
* @Package com.wenjiaquan.cms.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2019年12月10日 下午6:24:11 
* @version V1.0   
*/
@Controller
public class LoginController {
	@Autowired
	private UserService userservice;
	@RequestMapping("/logins")
	public String logins(Model m,HttpSession session,User user) {
		int rs=userservice.logins(user);
		if(rs>0) {
			session.setAttribute("name", user.getUsername());
			return "admin/home";
		}else {
			m.addAttribute("error","登录失败");
			return "admin/login";
		}
	}
}
