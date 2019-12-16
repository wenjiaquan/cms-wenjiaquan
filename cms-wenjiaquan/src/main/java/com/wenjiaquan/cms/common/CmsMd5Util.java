package com.wenjiaquan.cms.common;

import com.wenjiaquan.utils.Md5Util;

/**   
* @Title: CmsMd5Util.java 
* @Package com.wenjiaquan.cms.common 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2019年12月16日 下午2:19:32 
* @version V1.0   
*/
public class CmsMd5Util {
	/**
	 * @Title: stringToMd5   
	 * @Description: TODO(描述这个方法的作用)   
	 * @param: @param str
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public static String string2MD5(String str) {
		return Md5Util.string2MD5(str+"_cmsAdmin");
	}
}
