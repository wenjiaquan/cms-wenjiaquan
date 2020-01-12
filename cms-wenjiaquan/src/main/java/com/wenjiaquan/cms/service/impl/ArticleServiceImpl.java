package com.wenjiaquan.cms.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wenjiaquan.cms.dao.ArticleDao;
import com.wenjiaquan.cms.dao.CategoryDao;
import com.wenjiaquan.cms.dao.ChannelDao;
import com.wenjiaquan.cms.pojo.Article;
import com.wenjiaquan.cms.pojo.Category;
import com.wenjiaquan.cms.pojo.Channel;
import com.wenjiaquan.cms.pojo.Comment;
import com.wenjiaquan.cms.service.ArticleService;

/**   
* @Title: ArticleServiceImpl.java 
* @Package com.wenjiaquan.cms.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2019年12月16日 下午7:40:48 
* @version V1.0   
*/
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private ChannelDao channelDao;
	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private RedisTemplate<String, Article> redisTemplate;
	@Override
	public PageInfo<Article> getPageInfo(Article article, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Article> articleList = articleDao.select(article);
		return new PageInfo<>(articleList);
	}

	/*
	 * @Override public boolean updateStatus(Integer id, int status) { return
	 * articleDao.updateStatus(id, status)>0; }
	 */
	
	//使用redis后的文章状态设置
		@Override
		public boolean updateStatus(Integer id, int status) {
			
			boolean flag = articleDao.updateStatus(id, status)>0;
			
			//加判断：一、状态必须是审核通过；二、必须是操作成功的
			if(status == 1 && flag) {
				//清空redis中最新文章的key值
				
				redisTemplate.delete("article_new");
			}
			
			return flag ;
		}
	
	@Override
	public boolean addHot(Integer id) {
		return articleDao.addHot(id)>0;
	}

	@Override
	public List<Channel> getChannelList() {
		return channelDao.select(null);
	}
	
	@Override
	public Article getById(Integer id) {
		return articleDao.selectById(id);
	}

	@Override
	public boolean save(Article article) {
		if(article.getId()==null) {
			article.setDeleted(0);
			article.setCreated(new Date());
			article.setUpdated(new Date());
			article.setCommentcnt(0);
			article.setHits(0);
			article.setHot(0);
			articleDao.insert(article);
		}else {
			article.setUpdated(new Date());
			articleDao.update(article);
		}
		return true;
	}

	@Override
	public List<Category> getCateListByChannelId(Integer channelId) {
		return categoryDao.selectListByChannelId(channelId);
	}
	
	@Override
	public boolean delByIds(String ids) {
		return articleDao.updateDeletedByIds(ids)>0;
	}

	@Override
	public boolean isAllCheck(String ids) {
		List<Article> articleList = articleDao.selectByIds(ids);
		for (Article article:articleList) {
			if(article.getStatus()==1) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public List<Article> getListByChannelId(Integer channelId, Integer aritcleId, int num) {
		return articleDao.selectListByChannelId(channelId,aritcleId,num);
	}

	/*
	 * @Override public PageInfo<Article> getHotList(int pageNum) {
	 * PageHelper.startPage(pageNum, 6); List<Article> articleList =
	 * articleDao.selectByHot();
	 * 
	 * return new PageInfo<>(articleList); }
	 */
	
	//使用redis缓存热点文章
		@Override
		public PageInfo<Article> getHotList(int pageNum) {
			//设置每页显示的条数
			int pageSize = 6;
			
			PageInfo<Article> pageInfo = null;
			
			//redis模板对象，list操作对象
			ListOperations<String, Article> opsForList = redisTemplate.opsForList();
			
			//第一次访问时，从mysql中查询数据
			//如何判断第一次，判断redis中有没有对应的key值，没有，则为第一次
			if(!redisTemplate.hasKey("article_hot")) {
				
				//从mysql中获取数据，查询全部数据，所有热点文章
				List<Article> articleList = articleDao.selectByHot();
				
				//存入redis中
				opsForList.rightPushAll("article_hot", articleList);
				
				//查询分页数据
				PageHelper.startPage(pageNum, pageSize);
				
				List<Article> list = articleDao.selectByHot();
				
				//创建pageInfo对象
				pageInfo = new PageInfo<Article>(list);
				
			}else {
				//将数据存入redis中
				//非第一次时，直接从redis中获取数据，（如果新增了文章，则清空redis，在审核通过处写）
				
				//(pageNum - 1) * pageSize    , pageNum * pageSize - 1
				List<Article> list = opsForList.range("article_hot", (pageNum - 1) * pageSize, pageNum * pageSize - 1);
				
				//将list数据封装到Page对象中
				Page<Article> page_list = new Page<>(pageNum, pageSize);
				
				page_list.addAll(list);
				
				//获取数据总条数
				Long total = opsForList.size("article_hot");
				page_list.setTotal(total);
				
				//创建pageInfo对象
				pageInfo = new PageInfo<Article>(page_list);
			}
			
			return pageInfo;
		}

		@Override
		public PageInfo<Article> getListByChannelIdAndCateId(Integer channelId, Integer cateId, Integer pageNum) {
			PageHelper.startPage(pageNum, 6);
			List<Article> articleList = articleDao.selectListByChannelIdAndCateId(channelId,cateId);
			 return new PageInfo<>(articleList);
		}
	
	
	/*
	 * @Override public List<Article> getNewList(int num) { return
	 * articleDao.selectNewList(num); }
	 */
	
	@Override
	public List<Article> getNewList(int num) {
		//redis模板对象
		//list类型数据结构
		ListOperations<String, Article> opsForList = redisTemplate.opsForList();
		
		List<Article> list = null;
		//怎么存入redis中？
		
		//第一次访问时，从mysql中查询数据
		//如何判断第一次，判断redis中有没有对应的key值，没有，则为第一次
		if(!redisTemplate.hasKey("article_new")) {
			//从mysql中获取数据
			list = articleDao.selectNewList(num);
			
			//存入redis中
			opsForList.rightPushAll("article_new", list);
			
		}else {
			//将数据存入redis中
			//非第一次时，直接从redis中获取数据，（如果新增了文章，则清空redis，在审核通过处写）
			list = opsForList.range("article_new", 0, -1);
		}
		
		return list;
	}
	
	@Override
	public List<Comment> comment(int id) {
		// TODO Auto-generated method stub
		return articleDao.comment(id);
	}

	@Override
	public int deleteComment(String ids) {
		// TODO Auto-generated method stub
		return articleDao.deleteComment(ids);
	}

	@Override
	public void addTousu(Integer id) {
		// TODO Auto-generated method stub
		articleDao.addTousu(id);
	}

	@Override
	public int kafkaSave(Article article) {
		// TODO Auto-generated method stub
		return articleDao.insert(article);
	}
}
