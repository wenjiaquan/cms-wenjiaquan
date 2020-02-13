package com.wenjiaquan.cms.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.wenjiaquan.cms.common.CmsConstant;
import com.wenjiaquan.cms.common.JsonResult;
import com.wenjiaquan.cms.pojo.Article;
import com.wenjiaquan.cms.pojo.Category;
import com.wenjiaquan.cms.pojo.Channel;
import com.wenjiaquan.cms.pojo.Slide;
import com.wenjiaquan.cms.pojo.User;
import com.wenjiaquan.cms.service.ArticleService;
import com.wenjiaquan.cms.service.SlideService;
import com.wenjiaquan.utils.ESUtils;

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
	private ArticleService articleService;
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	@Autowired
	private SlideService slideService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * @Title: add
	 * 
	 * @@ -22,13 +33,42 @@ @return: String @throws
	 */
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String add(Integer id, Model model) {
		logger.info("articleId:{}", id);
		if (id != null) {
			Article article = articleService.getById(id);
			logger.info(article.toString());
			model.addAttribute("article", article);
			List<Category> cateList = articleService.getCateListByChannelId(article.getChannelId());
			model.addAttribute("cateList", cateList);
		}
		List<Channel> channelList = articleService.getChannelList();
		model.addAttribute("channelList", channelList);
		return "article/add";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult add(Article article, Model model, HttpSession session) {
		System.out.println(article);
		User userInfo = (User) session.getAttribute(CmsConstant.UserSessionKey);
		if (userInfo == null) {
			return JsonResult.fail(CmsConstant.unLoginErrorCode, "未登录");
		}
		article.setUserId(userInfo.getId());
		boolean result = articleService.save(article);
		return JsonResult.sucess(result);
	}

	/**
	 * @Title: getCateList @Description: 根据频道Id查询分类列表 @param: @param
	 * channelId @param: @param model @param: @param
	 * session @param: @return @return: JsonResult @throws
	 */
	@RequestMapping(value = "getCateList", method = RequestMethod.GET)
	@ResponseBody
	public JsonResult getCateList(Integer channelId, Model model, HttpSession session) {
		return JsonResult.sucess(articleService.getCateListByChannelId(channelId));
	}

	/**
	 * @Title: delByIds @Description: 批量删除 @param: @param
	 * ids @param: @return @return: JsonResult @throws
	 */
	@RequestMapping("delByIds")
	public @ResponseBody JsonResult delByIds(String ids) {
		if (ids == null) {
			return JsonResult.fail(10001, "请选择删除的文章");
		}
		// 已审核判断
		boolean isCheck = articleService.isAllCheck(ids);
		if (!isCheck) {
			return JsonResult.fail(10001, "请选择未审核的文章删除");
		}
		// 删除
		boolean result = articleService.delByIds(ids);
		if (result) {
			return JsonResult.sucess();
		}
		return JsonResult.fail(500, "未知错误");
	}

	/*
	 * @RequestMapping("search") public String searchRepository(Model m,String key){
	 * AggregatedPage<Article> selectObjects =
	 * ESUtils.selectObjects(elasticsearchTemplate, Article.class, null, 0, 10,
	 * "id", new String[] {"title"}, key); List<Article> content =
	 * selectObjects.getContent(); PageInfo pageInfo=new PageInfo(content);
	 * m.addAttribute("pageInfo",pageInfo); List<Article> newArticleList =
	 * articleService.getNewList(6); m.addAttribute("newArticleList",
	 * newArticleList);
	 *//** 频道 */
	/*
	 * List<Channel> channelList = articleService.getChannelList();
	 * m.addAttribute("channelList", channelList);
	 *//** 轮播图 *//*
					 * List<Slide> slideList = slideService.getAll(); m.addAttribute("slideList",
					 * slideList); return "index"; }
					 */
	@RequestMapping("search1")
	public String searchRepository(Model m,String key){
		AggregatedPage<Article> selectObjects = ESUtils.selectObjects(elasticsearchTemplate, Article.class, null, 0, 6, "id", new String[] {"title"}, key);
		PageInfo pageInfo=new PageInfo(selectObjects.getContent());
		m.addAttribute("pageInfo",pageInfo);
		/** 频道 */
		List<Channel> channelList = articleService.getChannelList();
		m.addAttribute("channelList", channelList);
		/** 轮播图 */
		List<Slide> slideList = slideService.getAll();
		m.addAttribute("slideList", slideList);
		/** 最新文章 **/
		List<Article> newArticleList = articleService.getNewList(6);
		m.addAttribute("newArticleList", newArticleList);
		m.addAttribute("key",key);
		return "index";
	}
	
}
