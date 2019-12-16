package com.wenjiaquan.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wenjiaquan.cms.common.CmsMd5Util;

/**   
* @Title: CmsMd5Test.java 
* @Package com.wenjiaquan.test 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2019年12月16日 下午7:01:13 
* @version V1.0   
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring-beans.xml")
public class CmsMd5Test {
	@Test
	public void test() {
		String string2md5 = CmsMd5Util.string2MD5("admin");
		System.out.println(string2md5);
	}

}
