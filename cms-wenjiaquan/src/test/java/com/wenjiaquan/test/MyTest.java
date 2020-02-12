package com.wenjiaquan.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wenjiaquan.cms.dao.ArticleDao;
import com.wenjiaquan.cms.dao.ArticleRepository;
import com.wenjiaquan.cms.dao.UserDao;
import com.wenjiaquan.cms.pojo.Article;
import com.wenjiaquan.cms.pojo.User;

/**   
* @Title: MyTest.java 
* @Package com.wenjiaquan.test 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2019年12月10日 下午6:41:29 
* @version V1.0   
*/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring-beans.xml")
public class MyTest {
	@Autowired
	private UserDao userdao;
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private ArticleRepository articleRepository;
	@Test
	public void list() {
		List<User> selectUser = userdao.selectUser();
		System.out.println(selectUser);
	}
	
	@Test
	public void addArticleRepository() {
		Article article = new Article();
		List<Article> list = articleDao.select(article);
		articleRepository.saveAll(list);
	}
}
