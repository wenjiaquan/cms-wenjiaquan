package com.wenjiaquan.cms.service;

import com.github.pagehelper.PageInfo;
import com.wenjiaquan.cms.pojo.User;

/**   
* @Title: UserService.java 
* @Package com.wenjiaquan.cms.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2019年12月10日 下午6:22:19 
* @version V1.0   
*/
public interface UserService {

	int logins(User user);
	
	/**
	 * @Title: register   
	 * @Description: 注册新增用户   
	 * @param: @param user
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	boolean register(User user);
	/**
	 * @Title: getByUsername   
	 * @Description: 根据登录名称，查询用户   
	 * @param: @param username
	 * @param: @return      
	 * @return: User      
	 * @throws
	 */
	User getByUsername(String username);
	/**
	 * @Title: locked   
	 * @Description: 锁用户   
	 * @param: @param userId
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	boolean locked(Integer userId);
	/**
	 * @Title: unLocked   
	 * @Description: 解锁用户   
	 * @param: @param userId
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	boolean unLocked(Integer userId);
	/**
	 * @Title: addScore   
	 * @Description: 给用户添加积分，返回用户总积分。 
	 * @param: @param userId
	 * @param: @param score
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	int addScore(Integer userId,int score);
	/**
	 * @Title: getPageInfo   
	 * @Description: 查询用户列表（PageInfo）   
	 * @param: @param user
	 * @param: @param pageNum
	 * @param: @param pageSize
	 * @param: @return      
	 * @return: PageInfo<User>      
	 * @throws
	 */
	PageInfo<User> getPageInfo(User user, int pageNum,
			int pageSize);
}
