package com.frss.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

public class DateUtil {
	
	Calendar cal = null;		
	
	public final static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * @函数名称: getDateSerial
	 * @函数描述: 将时间编号输出，20120325161847
	 * @输入参数: @param type，0表示获取开始时间，1表示获取当前时间序列，2表示获取结束时间
	 * @输入参数: @return
	 * @返回类型: String
	 * @throws
	 */
	public String getDateSerial(int type) {
		String dateId = "";
		if(cal==null)
			return null;
		dateId += cal.get(Calendar.YEAR); 
		int month = cal.get(Calendar.MONTH)+1;
		if(month<10)
			dateId += "0"+month;
		else 
			dateId += month;
		
		int day = cal.get(Calendar.DATE);
		if(day<10)
			dateId += "0"+day;
		else
			dateId += day;
		
		if(type==0) {	// 起始时间
			dateId += "000000";
		} else if(type==1) {	// 当前时间
			cal.setTime(new Date(System.currentTimeMillis()));
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			if(hour<10)
				dateId += "0"+hour;
			else
				dateId += hour;
			
			int minute = cal.get(Calendar.MINUTE);
			if(minute<10)
				dateId += "0"+minute;
			else
				dateId += minute;
			
			int second = cal.get(Calendar.SECOND);
			if(minute<10)
				dateId += "0"+second;
			else 
				dateId += second;
		} else if(type==2) {	// 结束时间
			dateId += "235959";
		}
		
		return dateId;
	}
	
	/**
	 * @函数名称: getDateFromSerial
	 * @函数描述: 根据时间序列编号返回date
	 * @输入参数: @param serial
	 * @输入参数: @return
	 * @返回类型: Date
	 * @throws
	 */
	public Date getDateFromSerial(String serial) {
		Date date = null;
		cal=Calendar.getInstance();
		int value = Integer.parseInt(serial.substring(0, 4));
		if(value<=0)
			return null;
		cal.set(Calendar.YEAR, value);
		
		value = Integer.parseInt(serial.substring(4,6));
		if(value<=0 || value>12)
			return null;
		cal.set(Calendar.MONTH, value-1);
		
		value = Integer.parseInt(serial.substring(6,8));
		if(value<=0 || value>31)
			return null;
		cal.set(Calendar.DATE, value);
		
		value = Integer.parseInt(serial.substring(8,10));
		if(value<0 || value>=24)
			return null;
		cal.set(Calendar.HOUR_OF_DAY, value);
		
		value = Integer.parseInt(serial.substring(10,12));
		if(value<0 || value>60) 
			return null;
		cal.set(Calendar.MINUTE, value);
		
		value = Integer.parseInt(serial.substring(12));
		if(value<0 || value>60)
			return null;
		cal.set(Calendar.SECOND, value);
		
		date = cal.getTime();
		
		return date;
	}
	
	/**
	 * @函数名称: getDateString
	 * @函数描述: 将日期按照规定格式输出
	 * @输入参数: @param date
	 * @输入参数: @return
	 * @返回类型: String
	 * @throws
	 */
	public String getDateString(Date date) {
		return dateFormat.format(date);
	}
	
	/**
	 * @函数名称: getTimeString
	 * @函数描述: 将时间按照规定格式输出
	 * @输入参数: @param date
	 * @输入参数: @return
	 * @返回类型: String
	 * @throws
	 */
	public String getTimeString(Date date) {
		return timeFormat.format(date);
	}
	
	/**
	 * @函数名称: getStartDate
	 * @函数描述: 获取起始时间
	 * @输入参数: @param date
	 * @输入参数: @return
	 * @返回类型: String
	 * @throws
	 */
	public String getStartDate(Date date) {
		String dateStart = dateFormat.format(date+" 00:00:00");
		return dateStart;
	}
	
	/**
	 * @函数名称: getEndDate
	 * @函数描述: 获取结束时间
	 * @输入参数: @param date
	 * @输入参数: @return
	 * @返回类型: String
	 * @throws
	 */
	public String getEndDate(Date date) {
		String dateEnd = dateFormat.format(date+" 23:59:59");
		return dateEnd;
	}
	
	/**
	 * @函数名称: getTheTime
	 * @函数描述: 将给定的时间字符串解析成指定的格式
	 * @输入参数: @param time
	 * @输入参数: @return
	 * @输入参数: @throws ParseException
	 * @返回类型: Date
	 * @throws
	 */
	public Date getTheTime(String time) throws ParseException {
		Date date = timeFormat.parse(time);
		return date;
	}
	
	/**
	 * @函数名称: getCurTime
	 * @函数描述: 获取当前时间
	 * @输入参数: @return
	 * @返回类型: Date
	 * @throws
	 */
	public static Date getCurTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(System.currentTimeMillis()));
	    Date curTime =calendar.getTime();
	    
		return curTime;
	}
	
	/**
	 * @函数名称: getThisYear
	 * @函数描述: 获取今年年数
	 * @输入参数: @return
	 * @返回类型: int
	 * @throws
	 */
	public int getThisYear() {
		cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);	
		
		return year;
	}	
	/**
	 * @函数名称: getThisMonth
	 * @函数描述: 获取当前月
	 * @输入参数: @return
	 * @返回类型: int
	 * @throws
	 */
	public int getThisMonth() {
		cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		return month;
	}
	/**
	 * @函数名称: getThisDay
	 * @函数描述: 获取当前天
	 * @输入参数: @return
	 * @返回类型: int
	 * @throws
	 */
	public int getThisDay() {
		cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		return day;
	}
	/**
	 * @函数名称: getThisDayofWeek
	 * @函数描述: 获取星期
	 * @输入参数: @return
	 * @返回类型: int
	 * @throws
	 */
	public int getThisDayofWeek() {
		cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_WEEK)-1;
		if(day==0)
			day=7;
		return day;
	}
	
	/**
	 * @函数名称: getStartTime
	 * @函数描述: 将给定日期设置为当天的起始时间
	 * @输入参数: @param date
	 * @输入参数: @return
	 * @返回类型: Date
	 * @throws
	 */
	public Date getStartTime(Date date) {
		cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		date = cal.getTime();
		return date;
	}
	
	/**
	 * @函数名称: getEndTime
	 * @函数描述: 将指定日期设置为当天的截止时间
	 * @输入参数: @param date
	 * @输入参数: @return
	 * @返回类型: Date
	 * @throws
	 */
	public Date getEndTime(Date date) {
		cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		date = cal.getTime();
		return date;
	}
	
	/**
	 * @函数名称: getThisDayStart
	 * @函数描述: 获取当天起始时间
	 * @输入参数: 
	 * @返回类型: Date
	 * @throws
	 */
	public Date getThisDayStart() {
		// 获取当天开始时间
		cal=Calendar.getInstance();
	    cal.setTime(new Date(System.currentTimeMillis()));

	    Date date = cal.getTime();	    
	    return date;	    
	}    

	/**
	 * @函数名称: getThisDayEnd
	 * @函数描述: 获取当天截止时间
	 * @输入参数: 
	 * @返回类型: Date
	 * @throws
	 */
	public Date getThisDayEnd() {
		// 获取当天开始时间
	    cal = Calendar.getInstance();
	    cal.setTime(new Date(System.currentTimeMillis()));
	    Date date = cal.getTime();
	    
	    return date;
	}
	
	/**
	 * @函数名称: getLastDayStart
	 * @函数描述: 获取昨天的起始时间
	 * @输入参数: @return
	 * @返回类型: Date
	 * @throws
	 */
	public Date getLastDayStart() {
		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);

		Date date = cal.getTime();
		return date;
	}
	
	/**
	 * @函数名称: getLastDayEnd
	 * @函数描述: 获取昨天的截止时间
	 * @输入参数: @return
	 * @返回类型: Date
	 * @throws
	 */
	public Date getLastDayEnd() {
		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);

		Date date = cal.getTime();
		return date;		
	}
	
	/**
	 * @函数名称: getThisWeekStart
	 * @函数描述: 获取当前周的起始时间
	 * @输入参数: @return
	 * @返回类型: Date
	 * @throws
	 */
	public Date getThisWeekStart() {
		cal = Calendar.getInstance();
		// 获取今天与本周一的时间间隔
	    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1; 
	   
	    if (dayOfWeek == 0) {	// 如果为周日，那么应该算上周
	    	dayOfWeek = -6;
	    } else {
	    	dayOfWeek = -(dayOfWeek-1);
	    }
	    
	    cal.add(Calendar.DAY_OF_WEEK, dayOfWeek);
	    cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		Date date = cal.getTime();
		return date;
	}
	
	/**
	 * @函数名称: getThisWeekEnd
	 * @函数描述: 获取当前周的截止时间，也就是当前时间
	 * @输入参数: @return
	 * @返回类型: Date
	 * @throws
	 */
	public Date getThisWeekEnd() {
		cal = Calendar.getInstance();
	    cal.setTime(new Date(System.currentTimeMillis()));
	    Date date = cal.getTime();
	    return date;	
	}
	
	/**
	 * @函数名称: getLastWeekStart
	 * @函数描述: 获取上周的开始时间
	 * @输入参数: @return
	 * @返回类型: Date
	 * @throws
	 */
	public Date getLastWeekStart() {
		cal = Calendar.getInstance();
		
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) -1;
		if(dayOfWeek==0) {	// 如果是周日	[zuow, 2012/04/01]
			cal.add(Calendar.WEEK_OF_YEAR, -2);
		} else {
			cal.add(Calendar.WEEK_OF_YEAR, -1);
		}
			
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		Date date = cal.getTime();
		return date;
	}
	
	/**
	 * @函数名称: getLastWeekEnd
	 * @函数描述: 获取上周的截止时间
	 * @输入参数: @return
	 * @返回类型: Date
	 * @throws
	 */
	public Date getLastWeekEnd() {
		cal = Calendar.getInstance();
		
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)-1;
		if(dayOfWeek==0) {
			cal.add(Calendar.WEEK_OF_YEAR, -1);
		} else {
			cal.add(Calendar.WEEK_OF_YEAR, 0);
		}
		
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
	
		Date date = cal.getTime();
		return date;
	}

	/**
	 * @函数名称: getThisMonthStart
	 * @函数描述: 获取这个月的起始时间
	 * @输入参数: @return
	 * @返回类型: Date
	 * @throws
	 */
	public Date getThisMonthStart() {
		cal = Calendar.getInstance();
		cal.set(Calendar.DATE,1);//设为当前月的1号		
		Date date = cal.getTime();
		
		return date;
	}
	
	/**
	 * @函数名称: getThisMonthEnd
	 * @函数描述: 获取这个月的截止时间(当前时间)
	 * @输入参数: @return
	 * @返回类型: Date
	 * @throws
	 */
	public Date getThisMonthEnd() {
		cal = Calendar.getInstance();
		cal.setTime(new Date(System.currentTimeMillis()));
		Date date = cal.getTime();
		return date;
	}
	
	/**
	 * @函数名称: getLastMonthStart
	 * @函数描述: 获取上月的起始时间
	 * @输入参数: @return
	 * @返回类型: Date
	 * @throws
	 */
	public Date getLastMonthStart() {
		cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);	//设为当前月的1号
		cal.add(Calendar.MONTH, -1);	//减一个月，变为上月的1号
		Date date = cal.getTime();
		   
		return date;
	}
	
	/**
	 * @函数名称: getLastMonthEnd
	 * @函数描述: 获取上月的截止日期
	 * @输入参数: @return
	 * @返回类型: Date
	 * @throws
	 */
	public Date getLastMonthEnd() {
	 	cal = Calendar.getInstance();
	  	cal.add(Calendar.MONTH,-1);			//减一个月
	  	cal.set(Calendar.DATE, 1);			//把日期设置为当月第一天 
	  	cal.roll(Calendar.DATE, -1);		//日期回滚一天，也就是本月最后一天 		
		
		Date date = cal.getTime();
		return date;		
	}
	
	/**
	 * @函数名称: getTheMonthStart
	 * @函数描述: 获取某年某月的起始日期
	 * @输入参数: @param year
	 * @输入参数: @param month
	 * @返回类型: void
	 * @throws
	 */
	public Date getTheMonthStart(int year, int month) {
		// 获得某年某月的第一天
		cal = Calendar.getInstance();  
		cal.set(Calendar.YEAR, year);  
		cal.set(Calendar.MONTH, month-1);  
		cal.set(Calendar.DATE, 1);

		Date date = cal.getTime();
		return date;
	}
	
	/**
	 * @函数名称: getTheMonthEnd
	 * @函数描述: 获得某年某也的截止日期
	 * @输入参数: @param year
	 * @输入参数: @param month
	 * @输入参数: @return
	 * @返回类型: Date
	 * @throws
	 */
	public Date getTheMonthEnd(int year, int month) {
		// 获得某年某月的最后一天
		cal.set(Calendar.YEAR, year);  		  
		cal.set(Calendar.MONTH, month-1);  
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));  
		Date date = cal.getTime();
		return date;
	}

	/**
	 * @函数名称: getTheQuarterStart
	 * @函数描述: 获得某季度的开始日期
	 * @输入参数: @param year
	 * @输入参数: @param seasonth
	 * @返回类型: void
	 * @throws
	 */
	public Date getTheQuarterStart(int year, int season) {
		cal = Calendar.getInstance();  
		cal.set(Calendar.YEAR, year);  
		
		// 设置每个季度的月和天
		switch (season) {
		case 1:
			cal.set(Calendar.MONTH, 0);			
			cal.set(Calendar.DAY_OF_MONTH, 1);
			break;
		case 2:
			cal.set(Calendar.MONTH, 3);
			cal.set(Calendar.DATE, 1);
			break;
		case 3:
			cal.set(Calendar.MONTH, 6);
			cal.set(Calendar.DAY_OF_MONTH, 1);			
			break;
		case 4:
			cal.set(Calendar.MONTH, 9);
			cal.set(Calendar.DAY_OF_MONTH, 1);	
			break;
		}
		
		Date date = cal.getTime();
		return date;		
	}
	
	/**
	 * @函数名称: getTheQuarterEnd
	 * @函数描述: 获取指定年中某季度的截止日期
	 * @输入参数: @param year
	 * @输入参数: @param season
	 * @输入参数: @return
	 * @返回类型: Date
	 * @throws
	 */
	public Date getTheQuarterEnd(int year, int season) {
		cal = Calendar.getInstance();  
		cal.set(Calendar.YEAR, year);  
		
		// 设置每个季度的月和天
		switch (season) {
		case 1:
			cal.set(Calendar.MONTH, 2);			
			cal.set(Calendar.DAY_OF_MONTH, 31);

			break;
		case 2:
			cal.set(Calendar.MONTH, 5);
			cal.set(Calendar.DAY_OF_MONTH, 30);

			break;
		case 3:
			cal.set(Calendar.MONTH, 8);
			cal.set(Calendar.DAY_OF_MONTH, 30);
			
			break;
		case 4:
			cal.set(Calendar.MONTH, 11);
			cal.set(Calendar.DAY_OF_MONTH, 31);
	
			break;
		}
		
		Date date = cal.getTime();
		return date;		
	}	

	/**
	 * @函数名称: getTheYearStart
	 * @函数描述: 获取某年的开始日期
	 * @输入参数: @param year
	 * @输入参数: @return
	 * @返回类型: Date
	 * @throws
	 */
	public Date getTheYearStart(int year) {
		cal = Calendar.getInstance();  
		cal.set(Calendar.YEAR, year);  
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DATE, 1);
		Date date = cal.getTime();
		return date;
	}
	
	/**
	 * @函数名称: getTheYearEnd
	 * @函数描述: 获取某年的截止日期
	 * @输入参数: @param year
	 * @输入参数: @return
	 * @返回类型: Date
	 * @throws
	 */
	public Date getTheYearEnd(int year) {
		cal = Calendar.getInstance();  
		cal.set(Calendar.YEAR, year);  
		cal.set(Calendar.MONTH, 11);
		cal.set(Calendar.DATE, 31);
		Date date = cal.getTime();
		return date;		
	}

	/**
	 * @函数名称: getDatePeried
	 * @函数描述: 根据传入的时间标识和年份获取时间段起止时间
	 * @输入参数: @param flag
	 * @输入参数: @param year
	 * @输入参数: @return
	 * @返回类型: ArrayList<Date>
	 * @throws
	 */
	public LinkedHashMap<String, ArrayList<Date>> getDatePeried(int flag, int year) {
//		ArrayList<Date> arrDate = new ArrayList<Date>();
		LinkedHashMap<String, ArrayList<Date>> mapDate = new LinkedHashMap<String, ArrayList<Date>>();
		
		String name = null;
		int baseYear = 2012;
		// 首先获取当前年/月/日
		int curYear = this.getThisYear();
		int curMonth = this.getThisMonth();
		
		if(flag==1) {	// 年度
			for(int i=baseYear;i<=curYear;i++) {
				Date dateStart = this.getTheYearStart(i);
				dateStart = this.getStartTime(dateStart);
				Date dateEnd = this.getTheYearEnd(i);
				dateEnd = this.getEndTime(dateEnd);
				
				name = i + "年度";
				ArrayList<Date> arrDate1 = new ArrayList<Date>();
				arrDate1.add(dateStart);
				arrDate1.add(dateEnd);
				mapDate.put(name, arrDate1);
			}
			
		} else if (flag==2) {	// 半年度
			for(int i=baseYear;i<curYear;i++) {
				Date firstStart = this.getTheYearStart(i);
				firstStart = this.getStartTime(firstStart);
				Date firstEnd = this.getTheMonthEnd(i, 6);
				firstEnd = this.getEndTime(firstEnd);
				
				ArrayList<Date> arrDate1 = new ArrayList<Date>();
				arrDate1.add(firstStart);
				arrDate1.add(firstEnd);
				name = i + "上半年";
				mapDate.put(name, arrDate1);
				
				Date secondStart = this.getTheMonthStart(i, 7);
				secondStart = this.getStartTime(secondStart);								
				Date secondEnd = this.getTheYearEnd(i);
				secondEnd = this.getEndTime(secondEnd);
				
				ArrayList<Date> arrDate2 = new ArrayList<Date>();
				arrDate2.add(secondStart);
				arrDate2.add(secondEnd);
				name = i + "下半年";
				mapDate.put(name, arrDate2);
			}

			if(curMonth>=1&&curMonth<7) {
				Date start = this.getTheYearStart(curYear);
				start = this.getStartTime(start);
				Date end = DateUtil.getCurTime();
				
				ArrayList<Date> arrDate1 = new ArrayList<Date>();
				arrDate1.add(start);
				arrDate1.add(end);
				name = curYear + "上半年";
				mapDate.put(name, arrDate1);
			} else if(curMonth>6&&curMonth<=12) {
				Date firstStart = this.getTheYearStart(curYear);
				firstStart = this.getStartTime(firstStart);								
				Date firstEnd = this.getTheMonthEnd(curYear, 6);
				firstEnd = this.getEndTime(firstEnd);
				
				ArrayList<Date> arrDate1 = new ArrayList<Date>();
				arrDate1.add(firstStart);
				arrDate1.add(firstEnd);
				name = curYear + "上半年";
				mapDate.put(name, arrDate1);
				
				Date secondStart = this.getTheMonthStart(curYear, 7);
				secondStart = this.getStartTime(secondStart);
				Date SecondEnd = DateUtil.getCurTime();		
				
				ArrayList<Date> arrDate2 = new ArrayList<Date>();
				arrDate2.add(secondStart);
				arrDate2.add(SecondEnd);
				name = curYear + "下半年";
				mapDate.put(name, arrDate2);
			} 			
		} else if (flag==3) {	// 季度
			if(year<curYear) {
				for(int i=1;i<4;i++) {
					Date qStart = this.getTheQuarterStart(year, i);
					qStart = this.getStartTime(qStart);
					Date qEnd = this.getTheQuarterEnd(year, i);
					qEnd = this.getEndTime(qEnd);	
				
					ArrayList<Date> arrDate1 = new ArrayList<Date>();
					arrDate1.add(qStart);
					arrDate1.add(qEnd);
					name = "第" + i + "季度";
					mapDate.put(name, arrDate1);
				}
			} else if(year==curYear) {
				Date q1Start = null, q1End = null, q2Start = null, q2End = null, q3Start = null, q3End = null, q4Start = null;
				q1Start = this.getTheQuarterStart(year, 1);
				q1Start = this.getStartTime(q1Start);
				
				int num = 0;
				if(curMonth>=3) {
					q1End = this.getTheQuarterEnd(year, 1);
					q1End = this.getEndTime(q1End);
					ArrayList<Date> arrDate1 = new ArrayList<Date>();					
					name = "第1季度";
					arrDate1.add(q1Start);
					arrDate1.add(q1End);
					mapDate.put(name, arrDate1);
					
					q2Start = this.getTheQuarterStart(year, 2);
					q2Start = this.getStartTime(q2Start);
					num++;
				} 
				if(curMonth>=6) {
					q2End = this.getTheQuarterEnd(year, 2);
					q2End = this.getEndTime(q2End);
					name = "第2季度";
					ArrayList<Date> arrDate1 = new ArrayList<Date>();
					arrDate1.add(q2Start);
					arrDate1.add(q2End);
					mapDate.put(name, arrDate1);
					
					q3Start = this.getTheQuarterStart(year, 3);
					q3Start = this.getStartTime(q3Start);
					num++;
				} 
				if(curMonth>=9) {
					q3End = this.getTheQuarterStart(year, 3);
					q3End = this.getEndTime(q3End);
					ArrayList<Date> arrDate1 = new ArrayList<Date>();
					arrDate1.add(q3Start);
					arrDate1.add(q3End);
					name = "第3季度";
					mapDate.put(name, arrDate1);
					
					q4Start = this.getTheQuarterStart(year, 4);
					q4Start = this.getStartTime(q4Start);
					name = "第4季度";
					num++;
				} 
				
				Date date = DateUtil.getCurTime();
				ArrayList<Date> arrDate2 = new ArrayList<Date>();
				if(num==0)
					arrDate2.add(q1Start);
				else if(num==1)
					arrDate2.add(q2Start);
				else if(num==2)
					arrDate2.add(q3Start);
				else if(num==3)
					arrDate2.add(q4Start);
				arrDate2.add(date);
				
				name = "第" + (num+1) + "季度";			
				mapDate.put(name, arrDate2);
			}
			
		} else if (flag==4) {	// 月
			if(year<curYear) {
				for(int i=1;i<12;i++) {
					Date start = this.getTheMonthStart(year, i);
					start = this.getStartTime(start);					
					Date end = this.getTheMonthEnd(year, i);
					end = this.getEndTime(end);
					
					ArrayList<Date> arrDate1 = new ArrayList<Date>();
					arrDate1.add(start);
					arrDate1.add(end);
					name = i + "月份";
					mapDate.put(name, arrDate1);
				}
			} else {
				for(int i=1;i<curMonth;i++) {
					Date start = this.getTheMonthStart(curYear, i);
					start = this.getStartTime(start);
					Date end = this.getTheMonthEnd(curYear, i);
					end = this.getEndTime(end);
					
					ArrayList<Date> arrDate1 = new ArrayList<Date>();
					arrDate1.add(start);
					arrDate1.add(end);
					name = i + "月份";
					mapDate.put(name, arrDate1);
				}
				
				Date curStart = this.getTheMonthStart(curYear, curMonth);
				curStart = this.getStartTime(curStart);
				Date curEnd = DateUtil.getCurTime();
				
				ArrayList<Date>arrDate2 = new ArrayList<Date>();
				arrDate2.add(curStart);
				arrDate2.add(curEnd);
				name = curMonth + "月份";
				mapDate.put(name, arrDate2);
			}
		}
		
		return mapDate;
	}
}
