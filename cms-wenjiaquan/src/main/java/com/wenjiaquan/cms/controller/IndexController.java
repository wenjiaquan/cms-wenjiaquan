package com.wenjiaquan.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**   
* @Title: UserController.java 
* @Package com.wenjiaquan.cms.controller.User 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2019年12月10日 下午6:23:11 
* @version V1.0   
*/
@Controller
public class IndexController {
	@RequestMapping("")
	public String index() {
		return "index";
	}
	@RequestMapping("/{channelId}/{cateId}/{pageNo}.html")
	public String channel(@PathVariable Integer channelId,
			@PathVariable Integer cateId,@PathVariable Integer pageNo) {
		return "index";
	}
	@RequestMapping("article/{id}.html")
	public String articleDetail(@PathVariable Integer id) {
		return "article/detail";
	}
}
