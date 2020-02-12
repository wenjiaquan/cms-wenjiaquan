package com.wenjiaquan.cms.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.wenjiaquan.cms.pojo.Article;

/**   
* @Title: ArticleRepository.java 
* @Package com.wenjiaquan.cms.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2020年2月12日 下午12:00:27 
* @version V1.0   
*/
public interface ArticleRepository extends ElasticsearchRepository<Article, Integer>{
	
}
