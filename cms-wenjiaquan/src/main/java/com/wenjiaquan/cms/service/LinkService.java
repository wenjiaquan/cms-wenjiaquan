package com.wenjiaquan.cms.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wenjiaquan.cms.dao.LinkDao;
import com.wenjiaquan.cms.pojo.Link;

/**   
* @Title: LinkService.java 
* @Package com.wenjiaquan.cms.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2019年12月21日 上午11:20:10 
* @version V1.0   
*/
@Service
@Transactional
public class LinkService {
	@Autowired
	private LinkDao linkDao;
	/**
	 * @Title: getPageInfo   
	 * @Description: 列表   
	 * @param: @param link
	 * @param: @param pageNum
	 * @param: @param pageSize
	 * @param: @return      
	 * @return: PageInfo<Link>      
	 * @throws
	 */
	public PageInfo<Link> getPageInfo(Link link, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Link> userList = linkDao.select(link);
		return new PageInfo<>(userList);
	}
	/**
	 * @Title: save   
	 * @Description: 保存   
	 * @param: @param link
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	public boolean save(Link link) {
		link.setCreated(new Date());
		if(link.getId()==null) {
			return linkDao.insert(link)>0;
		}
		return linkDao.update(link)>0;
	}
	/**
	 * @Title: getById   
	 * @Description: 根据Id查询友情链接   
	 * @param: @param id
	 * @param: @return      
	 * @return: Link      
	 * @throws
	 */
	public Link getById(Integer id) {
		return linkDao.selectById(id);
	}
	/**
	 * @Title: delByIds   
	 * @Description: 批量删除   
	 * @param: @param ids      
	 * @return: void      
	 * @throws
	 */
	public boolean delByIds(String ids) {
		return linkDao.deleteByIds(ids)>0;
	}
}
