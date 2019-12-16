package com.wenjiaquan.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.wenjiaquan.cms.pojo.User;

/**   
* @Title: UserDao.java 
* @Package com.wenjiaquan.cms.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2019年12月10日 下午6:21:58 
* @version V1.0   
*/
public interface UserDao {
	@Select("select * from cms_user")
	List<User> selectUser();
	@Select("select count(*) from cms_user where username=#{username} and password=#{password}")
	int logins(User user);
	/**
	 * @Title: selectById   
	 * @Description: 根据Id，查询对象   
	 * @param: @param id
	 * @param: @return      
	 * @return: User      
	 * @throws
	 */
	User selectById(@Param("id") Integer id);
	/**
	 * @Title: select   
	 * @Description: 根据User查询列表  
	 * @param: @param user
	 * @param: @return      
	 * @return: List<User>      
	 * @throws
	 */
	List<User> select(@Param("user") User user);
	/**
	 * @Title: count   
	 * @Description: 查询数据条数   
	 * @param: @param user
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	int count(@Param("user") User user);
	/**
	 * @Title: insert   
	 * @Description: 插入一条记录   
	 * @param: @param user
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	int insert(@Param("user") User user);
	/**
	 * @Title: update   
	 * @Description: 根据Id更新记录 
	 * @param: @param user
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	int update(@Param("user") User user);
	/**
	 * @Title: deleteById   
	 * @Description: 根据Id删除记录   
	 * @param: @param id
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	int deleteById(@Param("id") Integer id);
	/**
	 * @Title: deleteByIds   
	 * @Description: 根据Ids批量删除记录   
	 * @param: @param ids "1,2,3"
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	int deleteByIds(@Param("ids") String ids);
	/**
	 * @Title: updateLocked   
	 * @Description: 修改locked字段值   
	 * @param: @param userId
	 * @param: @param locked
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	
	int updateLocked(@Param("userId") Integer userId, @Param("locked")  int locked);

	/**
	 * @Title: selectByUsername   
	 * @Description: 根据登录名，查询用户  
	 * @param: @param username
	 * @param: @return      
	 * @return: User      
	 * @throws
	 */
	User selectByUsername(String username);
}
