package com.wenjiaquan.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wenjiaquan.cms.dao.SettingsDao;
import com.wenjiaquan.cms.pojo.Settings;

/**   
* @Title: SettingService.java 
* @Package com.wenjiaquan.cms.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2019年12月16日 下午2:33:29 
* @version V1.0   
*/
@Service
public class SettingsService {
	@Autowired
	private SettingsDao settingsDao;
	/**
	 * @Title: get   
	 * @Description: 查询系统设置   
	 * @param: @return      
	 * @return: Settings      
	 * @throws
	 */
	public Settings get() {
		List<Settings> setList = settingsDao.select(null);
		if(setList==null || setList.size()==0) {
			Settings settings = new Settings();
			settingsDao.insert(settings);
			return settings;
		}
		return setList.get(0);
	}
	/**
	 * @Title: save   
	 * @Description: 保存系统设置   
	 * @param: @param settings
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	public boolean save(Settings settings) {
		Settings set = settingsDao.selectById(settings.getId());
		set.setSiteDomain(settings.getSiteDomain());
		set.setSiteName(settings.getSiteName());
		System.out.println("settings:"+set);
		return settingsDao.update(set)>0;
	}
}
