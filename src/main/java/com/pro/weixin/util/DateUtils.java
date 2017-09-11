package com.pro.weixin.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * 处理日期的工具类
 */
public class DateUtils {

	//获取当天的 月/日
	public static  String getTodayMonthAndDay(){
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("MM/dd");
		
		return sdf.format(date);
	}
	//获取昨天的 月/日
	public static String getYeTodayMonthAndDay(){
		Date date=new Date(System.currentTimeMillis()-1000*60*60*24);
		SimpleDateFormat sdf=new SimpleDateFormat("MM/dd");
		
		return sdf.format(date);
	}
	//获取昨天的 年-月-日 
		public static String getYeTodayYearsAndMonthAndDay(){
			Date date=new Date(System.currentTimeMillis()-1000*60*60*24);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");		
			return sdf.format(date);
		}
		
	//获取今的 年-月-日 
			public static String getTodayYearsAndMonthAndDay(){
				Date date=new Date();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");		
				return sdf.format(date);
			}	
}
