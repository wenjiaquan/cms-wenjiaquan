package com.wenjiaquan.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wenjiaquan.cms.pojo.Comment;

/**   
* @Title: CommentDao.java 
* @Package com.wenjiaquan.cms.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2019年12月12日 下午3:28:32 
* @version V1.0   
*/
public interface CommentDao {
	/**
	 * @Title: selectById   
	 * @Description: 根据Id，查询对象   
	 * @param: @param id
	 * @param: @return      
	 * @return: Comment      
	 * @throws
	 */
	Comment selectById(@Param("id") Integer id);
	/**
	 * @Title: select   
	 * @Description: 根据Comment查询列表  
	 * @param: @param comment
	 * @param: @return      
	 * @return: List<Comment>      
	 * @throws
	 */
	List<Comment> select(@Param("comment") Comment comment);
	/**
	 * @Title: count   
	 * @Description: 查询数据条数   
	 * @param: @param comment
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	int count(@Param("comment") Comment comment);
	/**
	 * @Title: insert   
	 * @Description: 插入一条记录   
	 * @param: @param comment
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	int insert(@Param("comment") Comment comment);
	/**
	 * @Title: update   
	 * @Description: 根据Id更新记录 
	 * @param: @param comment
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	int update(@Param("comment") Comment comment);
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
}
