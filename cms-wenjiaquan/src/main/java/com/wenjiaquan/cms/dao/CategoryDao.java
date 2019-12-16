package com.wenjiaquan.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wenjiaquan.cms.pojo.Category;

/**   
* @Title: CategoryDao.java 
* @Package com.wenjiaquan.cms.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2019年12月12日 下午3:27:12 
* @version V1.0   
*/
public interface CategoryDao {
	/**
	 * @Title: selectById   
	 * @Description: 根据Id，查询对象   
	 * @param: @param id
	 * @param: @return      
	 * @return: Category      
	 * @throws
	 */
	Category selectById(@Param("id") Integer id);
	/**
	 * @Title: select   
	 * @Description: 根据Category查询列表  
	 * @param: @param category
	 * @param: @return      
	 * @return: List<Category>      
	 * @throws
	 */
	List<Category> select(@Param("category") Category category);
	/**
	 * @Title: count   
	 * @Description: 查询数据条数   
	 * @param: @param category
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	int count(@Param("category") Category category);
	/**
	 * @Title: insert   
	 * @Description: 插入一条记录   
	 * @param: @param category
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	int insert(@Param("category") Category category);
	/**
	 * @Title: update   
	 * @Description: 根据Id更新记录 
	 * @param: @param category
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	int update(@Param("category") Category category);
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
	 * @Title: selectListByChannelId   
	 * @Description: 根据频道Id，查询列表
	 * @param: @param channelId
	 * @param: @return      
	 * @return: List<Category>      
	 * @throws
	 */
	List<Category> selectListByChannelId(Integer channelId);
}
