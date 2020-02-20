package com.wenjiaquan.cms.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**   
* @Title: StringToDateConverter.java 
* @Package com.wenjiaquan.cms.common 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2019年12月17日 下午7:39:26 
* @version V1.0   
*/
public class StringToDateConverter implements Converter<String,Date>{
	@Override
	public Date convert(String source) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = simpleDateFormat.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
		
	}
}
