package com.wenjiaquan.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wenjiaquan.cms.dao.ArticleDao;
import com.wenjiaquan.cms.pojo.Article;
import com.wenjiaquan.utils.Md5Util;

/**   
* @Title: ArticleController.java 
* @Package com.wenjiaquan.cms.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2019年12月12日 下午3:34:18 
* @version V1.0   
*/
@Controller
@RequestMapping("/article/")
public class ArticleController {
	@Autowired
	private ArticleDao articleDao;
	/**
	 * @Title: add   
	 * @Description: 发布文章   
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping("add")
	public String add(Model model) {
		Article article = articleDao.selectById(1);
		Md5Util.string2MD5("abc");
		model.addAttribute("article", null);
		return "article/add";
	}
	/**
	 * @Title: update   
	 * @Description: 修改文章   
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping("update")
	public String update() {
		return "update";
	}
}
