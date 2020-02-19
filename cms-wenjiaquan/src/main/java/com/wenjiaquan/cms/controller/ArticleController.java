package com.wenjiaquan.cms.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
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
import com.wenjiaquan.cms.util.HLUtils;
import com.wenjiaquan.utils.DateUtil;
import com.wenjiaquan.utils.ESUtils;
import com.wjq.util.StringUtil;

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
	 *         @@ -22,13 +33,42 @@ @return: String @throws
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
	 *         channelId @param: @param model @param: @param
	 *         session @param: @return @return: JsonResult @throws
	 */
	@RequestMapping(value = "getCateList", method = RequestMethod.GET)
	@ResponseBody
	public JsonResult getCateList(Integer channelId, Model model, HttpSession session) {
		return JsonResult.sucess(articleService.getCateListByChannelId(channelId));
	}

	/**
	 * @Title: delByIds @Description: 批量删除 @param: @param
	 *         ids @param: @return @return: JsonResult @throws
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
	//收藏添加
	@RequestMapping("collection")
	public String collection(Integer id) {
		Article a = articleService.getById(id);
		String url = "http://127.0.0.1/article/" + id + ".html";
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		System.err.println(time);
		if (StringUtil.isHttpUrl(url)) {
			int rs = articleService.addcollection(a, url, time, a.getId());
		}
		return "/user/center";
	}

	/*
	 * @RequestMapping("search") public String searchRepository(Model m,String
	 * key,Integer pageNum) {
	 *//** 频道 */
	/*
	 * List<Channel> channelList = articleService.getChannelList();
	 * m.addAttribute("channelList", channelList);
	 *//** 最新文章 **/
	/*
	 * List<Article> newArticleList = articleService.getNewList(6);
	 * m.addAttribute("newArticleList", newArticleList);
	 *//** 轮播图 *//*
					 * List<Slide> slideList = slideService.getAll(); m.addAttribute("slideList",
					 * slideList); if(pageNum==null) { pageNum=1; } long start =
					 * System.currentTimeMillis(); PageInfo<Article> pageInfo = (PageInfo<Article>)
					 * HLUtils.findByHighLight(elasticsearchTemplate, Article.class, pageNum, 5, new
					 * String[] {"title"}, "id", key); //AggregatedPage<Article> selectObjects =
					 * ESUtils.selectObjects(elasticsearchTemplate, Article.class, null, pageNum-1,
					 * 4, "id", new String[] {"title"}, key); long end = System.currentTimeMillis();
					 * System.err.println("查询所耗时长"+(end-start)+"毫秒"); //PageInfo pageInfo=new
					 * PageInfo(selectObjects.getContent()); pageInfo.setPrePage(pageNum-1);
					 * pageInfo.setNextPage(pageNum+1); m.addAttribute("pageInfo",pageInfo);
					 * 
					 * m.addAttribute("key",key); return "index"; }
					 */
	//高亮查询
	@RequestMapping("search")
	public String search(Model m,Integer pageNum,String key){
		/** 频道 */
		List<Channel> channelList = articleService.getChannelList();
		m.addAttribute("channelList", channelList);
		/** 轮播图 */
		List<Slide> slideList = slideService.getAll();
		m.addAttribute("slideList", slideList);
		/** 最新文章 **/
		if(pageNum==null) {
			pageNum=1;
		}
		List<Article> newArticleList = articleService.getNewList(6);
		m.addAttribute("newArticleList", newArticleList);
		PageInfo<Article> pageInfo = (PageInfo<Article>) HLUtils.findByHighLight(elasticsearchTemplate, Article.class, pageNum, 3, new String[] {"title"}, "id", key);
		pageInfo.setPrePage(pageNum-1);
		pageInfo.setNextPage(pageNum+1);
		m.addAttribute("pageInfo",pageInfo);
		m.addAttribute("key",key);
		return "index";
	}

}
