package com.wenjiaquan.cms.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wenjiaquan.cms.common.CmsConstant;
import com.wenjiaquan.cms.common.JsonResult;
import com.wenjiaquan.cms.pojo.Tousu;
import com.wenjiaquan.cms.pojo.User;
import com.wenjiaquan.cms.service.TousuService;

/**   
* @Title: TousuController.java 
* @Package com.wenjiaquan.cms.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2019年12月23日 下午2:34:08 
* @version V1.0   
*/
@Controller
@RequestMapping("/tousu/")
public class TousuController {
	@Autowired
	private TousuService toususervice;
	/**
	 * @Title: add   
	 * @Description: 添加投诉 
	 * @param: @param comment
	 * @param: @return      
	 * @return: JsonResult      
	 * @throws
	 */
	@RequestMapping(value="add",method=RequestMethod.POST)
	public @ResponseBody JsonResult add(Tousu tousu,HttpSession session) {
		User userInfo = (User)session.getAttribute(CmsConstant.UserSessionKey);
		if(userInfo==null) {
			return JsonResult.fail(CmsConstant.unLoginErrorCode, "用户未登录");
		}
		tousu.setUserId(userInfo.getId());
		boolean result = toususervice.add(tousu);
		if(result) {
			return JsonResult.sucess();
		}
		return JsonResult.fail(10000, "未知错误");
	}
}
