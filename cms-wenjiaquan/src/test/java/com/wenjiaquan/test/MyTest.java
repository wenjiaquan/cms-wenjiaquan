package com.wenjiaquan.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.wenjiaquan.cms.dao.ArticleDao;
import com.wenjiaquan.cms.dao.ArticleRepository;
import com.wenjiaquan.cms.dao.UserDao;
import com.wenjiaquan.cms.pojo.Article;
import com.wenjiaquan.cms.pojo.Collections;
import com.wenjiaquan.cms.pojo.User;
import com.wenjiaquan.cms.service.ArticleService;
import com.wenjiaquan.cms.service.RedisArticleService;
import com.wenjiaquan.utils.DateUtil;
import com.wenjiaquan.utils.FileUtil;
import com.wenjiaquan.utils.JSoup;
import com.wenjiaquan.utils.RandomUtil;
import com.wjq.util.StringUtil;

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
	@Resource
	private KafkaTemplate<String, String> kafkaTemplate;
	@Autowired
	private RedisArticleService redisArticleService;
	@Autowired
	private ArticleService articleService;
	@Test
	public void list() {
		/*
		 * List<User> selectUser = userdao.selectUser(); System.out.println(selectUser);
		 */
		int random = RandomUtil.random(1,7);
		System.out.println(random);
	}
	
	/*
	 * @Test public void addArticleRepository() { Article article = new Article();
	 * List<Article> list = articleDao.select(article);
	 * articleRepository.saveAll(list); }
	 */
	
	/*
	 * @Test public void add() throws FileNotFoundException { File file = new
	 * File("D:\\1709DJsoup"); File[] listFiles = file.listFiles(); for (File file2
	 * : listFiles) { Article article = new Article(); String readTextFileByLine =
	 * FileUtil.readTextFileByLine(file2); if(readTextFileByLine.length()>140) {
	 * String substring = readTextFileByLine.substring(0,140);
	 * article.setContent(substring); } String replace =
	 * file2.getName().replace(".txt",""); article.setTitle(replace);
	 * article.setPicture("/pic/image/20200212/20200212135247_180.jpg"); int random
	 * = RandomUtil.random(1,9); article.setChannelId(random); int random1 =
	 * RandomUtil.random(1,32); article.setCategoryId(random1);
	 * article.setUserId(194); article.setHits(0); Date randomDate =
	 * DateUtil.randomDate("2019-01-01", "2020-02-13");
	 * article.setCreated(randomDate); article.setUpdated(randomDate);
	 * article.setCommentcnt(0); System.out.println(article); Gson g=new Gson();
	 * kafkaTemplate.sendDefault("articleAdd", g.toJson(article)); }
	 * 
	 * }
	 */
	
	@Test public void jsoup() throws IOException { 
		JSoup.jsoup1(); 

		//String  hostAddress = InetAddress.getLocalHost().getHostAddress();
		  //System.out.println(hostAddress); 
	}
	 
	
	
	@Test
	public void addArticle() throws FileNotFoundException {
		File file = new File("D:\\1709DJsoup");
		File[] listFiles = file.listFiles();
		for (File file2 : listFiles) {
			Article a = new Article();
			String replace = file2.getName().replace(".txt", "");
			a.setTitle(replace);
			a.setPicture("/pic/image/20191221/20191221081510_635.jpg");
			String readTextFileByLine = FileUtil.readTextFileByLine(file2);
			if (readTextFileByLine.length() >= 140) {
				String substring = readTextFileByLine.substring(0, 140);
				a.setContent(substring);
			}
			int random = RandomUtil.random(1, 9);
			a.setChannelId(random);
			int random1 = RandomUtil.random(1, 32);
			a.setCategoryId(random1);
			a.setUserId(194);
			a.setHits(0);
			a.setHot(0);
			a.setStatus(0);
			a.setDeleted(0);
			Date randomDate = DateUtil.randomDate("2019-01-01", "2020-02-14");
			a.setCreated(randomDate);
			a.setUpdated(randomDate);
			a.setCommentcnt(0);
			redisArticleService.save(a);
		}
	}
	 

	/*
	 * @Test public void addes() { //添加到es Article article = new Article();
	 * List<Article> select = articleDao.select(article);
	 * articleRepository.saveAll(select); System.out.println("保存成功"); }
	 */
	//测试路径是否已http://开头
	@Test
	public void util() {
		System.out.println(StringUtil.isHttpUrl("http://127.0.0.1"));
		System.out.println(StringUtil.isHttpUrl("http//127.0.0.1"));
	}
	 //网es存储数据
	@Test
	public void addRepository(){
		Article article = new Article();
		List<Article> select = articleDao.select(article);
		articleRepository.saveAll(select);
		System.out.println("存储成功");
	}
	//测试收藏查询方法
	@Test
	public void selectcollect() {
		List<Collections> selectcollection = articleService.selectcollection(194);
		for (Collections collections : selectcollection) {
			System.out.println(collections);
		}
	}
	
	@Test
	public void addcollect() {
		Article a = new Article();
		a.setTitle("afgsdfgdfg");
		a.setUserId(155);
		articleService.addcollection(a, "http://127.0.0.1", "2020-02-18 12:05:12", 22);
		System.out.println("存储成功");
	}
	@Test
	public void delcollect() {
		articleService.delcollect(17);
		System.out.println("删除成功");
	}
}
