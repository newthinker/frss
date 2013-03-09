package com.frss.util;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * @类型名称: SequenceFactory
 * @类型描述: 订单序列号生成工厂类，序列号定义为long类型(8字节)、18位：YYYYMMDD(8)+HHMMSS(6)+T(1)+ID(3)，日期段+时间段+订单类型+当前登录用户ID
 * @作           者: Michael.Cho, zuow11@gmail.com
 * @创建时间: 2012-4-9 下午3:37:25
 *
 */
public class SequenceFactory {
	// 表单类型(用以生成故障单和备件单序列号) [zuow, 2012/04/10]
	public final static int faultReport = 1;
	public final static int backReport = 2;
	
	private long id = 0L;		// 订单序列号
	private String idString = null;		// 订单序列号的字符串格式
	
	private int orderType = 0;		// 订单类型，1表示故障单，2表示备件单
	private long userId = 0;		// 当前登录用户的id号
	
	/**
	 * <p> 构造函数: 构造函数，根据当前登录用户id号和当前时间生成订单序列号</p>
	 * <p> 函数描述: </p>
	 * @param userId2
	 */
	public SequenceFactory(int orderType, long userId2) {
		this.orderType = orderType;
		this.userId = userId2;
		
		// 进行安全检查
		if(orderType<0 || orderType>9) {
			/// log
			return;
		}
		if(userId2<=0 || userId2>999) {
			/// log
			return;
		}
		
		// 生成序列号
		GenerateSequence();
	}
	
	/**
	 * @函数名称: getSequenceString
	 * @函数描述: 接口函数，返回系统生成的订单序列号的字符串
	 * @输入参数: @return
	 * @返回类型: String
	 * @throws
	 */
	public String getSequenceString() {
		return this.idString;
	}
	
	/**
	 * @函数名称: getSequenceLong
	 * @函数描述: 接口函数，返回系统生成的订单号
	 * @输入参数: @return
	 * @返回类型: long
	 * @throws
	 */
	public long getSequenceLong() {
		return this.id;
	}	
	////////////////////////////////////////////////////////////////////////////////
	/**
	 * @函数名称: GenerateSequence
	 * @函数描述: 根据当前时间和当前登录用户id号生成订单序列号
	 * @输入参数: 
	 * @返回类型: void
	 * @throws
	 */
	private void GenerateSequence() {
		// 首先获取当前时间字符串
		DateUtil dateUtil = new DateUtil();
		Date curDate = DateUtil.getCurTime();
		String timeString = dateUtil.getTimeString(curDate);
		if(timeString.indexOf("-")>=0)
			timeString = timeString.replaceAll("-", "");
		if(timeString.indexOf(":")>=0)
			timeString = timeString.replaceAll(":", "");
		if(timeString.indexOf(" ")>=0)
			timeString = timeString.replaceAll(" ", "");
		
		// 设置用户id的格式
		DecimalFormat nf = new DecimalFormat("000"); 
		String userIdString = nf.format(userId);
		
		// 根据当前时间字符串等生成序列号
		idString = Integer.toString(orderType) + userIdString + timeString;
		
		id = Long.parseLong(idString);
	}
	
}
