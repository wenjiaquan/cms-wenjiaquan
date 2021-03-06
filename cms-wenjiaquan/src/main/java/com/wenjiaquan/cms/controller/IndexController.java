package com.wenjiaquan.cms.controller;

import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;
import com.wenjiaquan.cms.pojo.Article;
import com.wenjiaquan.cms.pojo.Category;
import com.wenjiaquan.cms.pojo.Channel;
import com.wenjiaquan.cms.pojo.Slide;
import com.wenjiaquan.cms.pojo.User;
import com.wenjiaquan.cms.service.ArticleService;
import com.wenjiaquan.cms.service.SlideService;
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
public class IndexController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private UserService userService;
	@Autowired
	private SlideService slideService;
	@Autowired
	private KafkaTemplate<String,String> kafkaTemplate;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private ThreadPoolTaskExecutor executor;
	
	@RequestMapping(value="/")
	public String index(Model model) {
		return index(1, model);
	}

	@RequestMapping(value="/hot/{pageNum}.html")
	public String index(@PathVariable Integer pageNum, Model model) {
		/** 频道 */
		List<Channel> channelList = articleService.getChannelList();
		model.addAttribute("channelList", channelList);
		/** 轮播图 */
		List<Slide> slideList = slideService.getAll();
		model.addAttribute("slideList", slideList);
		/** 最新文章 **/
		List<Article> newArticleList = articleService.getNewList(6);
		model.addAttribute("newArticleList", newArticleList);
		/** 热点文章 **/
		if(pageNum==null) {
			pageNum=1;
		}
		PageInfo<Article> pageInfo =  articleService.getHotList(pageNum);
		model.addAttribute("pageInfo", pageInfo);
		return "index";
	}
	@RequestMapping("/{channelId}/{cateId}/{pageNo}.html")
	public String channel(@PathVariable Integer channelId, Model model,
			@PathVariable Integer cateId,@PathVariable Integer pageNo) {
		/** 频道 */
		List<Channel> channelList = articleService.getChannelList();
		model.addAttribute("channelList", channelList);
		/** 最新文章 **/
		List<Article> newArticleList = articleService.getNewList(6);
		model.addAttribute("newArticleList", newArticleList);
		/** 分类 */
		List<Category> cateList = articleService.getCateListByChannelId(channelId);
		model.addAttribute("cateList", cateList);
		PageInfo<Article> pageInfo =  articleService.getListByChannelIdAndCateId(channelId,cateId,pageNo);
		model.addAttribute("pageInfo", pageInfo);
		return "index";
	}
	@RequestMapping("article/{id}.html")
	public String articleDetail(@PathVariable Integer id,Model model,HttpServletRequest request) throws UnknownHostException {
		/** 查询文章 **/
		Article article = articleService.getById(id);
		model.addAttribute("article", article);
		if(article.getStatus()==3) {
			return "article/error";
		}
		/** 查询用户 **/
		User user = userService.getById(article.getUserId());
		model.addAttribute("user", user);
		/** 查询相关文章 **/
		//String hostAddress = InetAddress.getLocalHost().getHostAddress();
		String remoteAddr = request.getRemoteAddr();
		ValueOperations opsForValue = redisTemplate.opsForValue();
		boolean key=redisTemplate.hasKey("hits_"+id+"_"+remoteAddr);
		if(!key) {
			executor.execute(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					articleService.hits(id.toString());
					opsForValue.set("hits_"+id+"_"+remoteAddr, null,10,TimeUnit.SECONDS);
				}
			});
		}
		List<Article> articleList = articleService.getListByChannelId(article.getChannelId(),id,10);
		model.addAttribute("articleList", articleList);
		for (Article article2 : articleList) {
			model.addAttribute("hits",article.getHits()+1);
		}
		return "article/detail";
	}
}
