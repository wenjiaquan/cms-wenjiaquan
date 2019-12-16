package com.wenjiaquan.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wenjiaquan.cms.dao.UserDao;
import com.wenjiaquan.cms.pojo.User;
import com.wenjiaquan.cms.service.UserService;

/**   
* @Title: UserServiceImpl.java 
* @Package com.wenjiaquan.cms.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2019年12月10日 下午6:22:40 
* @version V1.0   
*/
@Transactional
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userdao;
	
	public List<User> selectUser(){
		List<User> list=userdao.selectUser();
		return list;
	}

	@Override
	public int logins(User user) {
		// TODO Auto-generated method stub
		return userdao.logins(user);
	}
	@Override
	public boolean register(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User getByUsername(String username) {
		// TODO Auto-generated method stub
		return userdao.selectByUsername(username);
	}

	@Override
	public boolean locked(Integer userId) {
		return userdao.updateLocked(userId,1)>0;
	}

	@Override
	public boolean unLocked(Integer userId) {
		return userdao.updateLocked(userId,0)>0;
	}

	@Override
	public int addScore(Integer userId, int score) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PageInfo<User> getPageInfo(User user, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<User> userList = userdao.select(user);
		return new PageInfo<>(userList);
	}
}
