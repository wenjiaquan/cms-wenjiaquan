package com.wenjiaquan.cms.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wenjiaquan.cms.dao.TousuDao;
import com.wenjiaquan.cms.pojo.Tousu;

/**   
* @Title: TousuService.java 
* @Package com.wenjiaquan.cms.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2019年12月23日 下午1:48:25 
* @version V1.0   
*/
@Service
@Transactional
public class TousuService {
	@Autowired
	private TousuDao tousuDao;
	@Autowired
	private ArticleService articleService;
	public boolean add(Tousu tousu) {
		// TODO Auto-generated method stub
		String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		tousu.setCreated(format);
		tousuDao.insert(tousu);
		articleService.addTousu(tousu.getArticleId());
		return true;
	}
}
