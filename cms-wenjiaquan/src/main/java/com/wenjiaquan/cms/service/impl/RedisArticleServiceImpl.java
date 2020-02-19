package com.wenjiaquan.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.wenjiaquan.cms.pojo.Article;
import com.wenjiaquan.cms.service.RedisArticleService;

/**   
* @Title: RedisArticleServiceImpl.java 
* @Package com.wenjiaquan.cms.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2020年2月17日 下午1:46:33 
* @version V1.0   
*/
@Service
public class RedisArticleServiceImpl implements RedisArticleService {
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	@Override
	public void save(Article a) {
		// TODO Auto-generated method stub
		ListOperations opsForList = redisTemplate.opsForList();
		opsForList.leftPush("addArticle", a);
		kafkaTemplate.sendDefault("redis_key", "addArticle");
	}

}
