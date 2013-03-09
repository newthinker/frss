package com.frss.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.frss.dao.main.ApprovalDAO;
import com.frss.dao.main.EquipDAO;
import com.frss.dao.main.FactoryDAO;
import com.frss.dao.main.FaultDAO;
import com.frss.dao.main.RepairDAO;
import com.frss.dao.main.UserDAO;
import com.frss.model.main.BackupApplication;
import com.frss.model.main.FaultRepairReport;
import com.frss.model.main.RemoteSupportForm;
import com.frss.model.main.RepairDispatchForm;
import com.frss.model.mapping.FrssApprovalInfo;
import com.frss.model.mapping.FrssClientReview;
import com.frss.model.mapping.FrssFactoryInfo;
import com.frss.model.mapping.FrssRepairDispatch;
import com.frss.model.mapping.FrssRepairFeedback;
import com.frss.util.DateUtil;

/**
 * @类型名称: StatManagerBean
 * @类型描述: 实施各种统计操作
 * @作           者: Michael.Cho, zuow11@gmail.com
 * @创建时间: 2012-3-23 下午4:12:19
 *
 */
public class StatManagerBean {
	
	private long numRecord = 0;						// 记录个数
	Set<Long> idRecord = null;						// 记录编号
	private ArrayList<FaultRepairReport> arrFault = null;	// 故障上报表列表
	private ArrayList<BackupApplication> arrBack = null;	// 备件申请表列表
	
	// 当前登录用户信息
	private long userId = 0;
	private int userType = 0;	
	private String userName = null;
	
	// 开始和结束时间
	private Date startTime = null;
	private Date endTime = null;
	
	private int state = 0;			// 查询的故障状态，0表示终止，1表示真正进行(区别于审核表中的状态)	
	private int step = 0;			// 当前运行的步骤
	
	private Date feedbackTime = null;			/// 临时加上，以后可能有改变 [zuow, 2012/04/10]
	
	/////////////////////////////////////////////////////////////////////////
	public ArrayList<ArrayList<String>> retResult = null;	// 返回给前端的结果，根据前端servlet中数组要求进行整理
	public HashMap<String, String> mapDetail = null;		// 细节显示的查询结果
	
	private FaultDAO faultDAO = null;
	private DateUtil dateUtil = null;
	
	public StatManagerBean() {
		dateUtil = new DateUtil();
		
		faultDAO = new FaultDAO();
	}
	
	/*
	 * 获取查询的记录个数
	 */
	public long getRecordNum() {
		return this.numRecord;
	}
	
	/**
	 * @函数名称: queryFault
	 * @函数描述: 进行故障单统计查询
	 * @输入参数: @param startime
	 * @输入参数: @param endtime
	 * @输入参数: @param usertype
	 * @输入参数: @param state
	 * @输入参数: @return
	 * @返回类型: boolean
	 * @throws
	 */
	public boolean queryFault(Date startime, Date endtime, int usertype, String userName, int state) {
		
		if(startime==null || endtime==null) {
			// log 
			return false;
		}
		
		this.startTime = startime;
		this.endTime = endtime;
		this.userType = usertype;
		this.state = state;
		this.userName = userName;
		if(userName==null || userName.equals("")) {		// 安全检查 [zuow, 2012/05/06]
			return false;
		}
		
		// 根据用户类型进行分类处理
		if(userType==UserDAO.Regiment) {	// 团级用户
			queryFaultOfRegiment();
		} else if(userType==UserDAO.GroupArmy) {	// 集团军用户
			queryFaultOfGroupArmy();
		} else if(userType==UserDAO.Military) {	// 军区用户
			queryFaultOfMilitary();
		} else if(userType==UserDAO.Formater) {	// 维修中心格式审核人员
			queryFaultOfFormater();
		} else if(userType==UserDAO.Distributor){	// 维修中心故障分发人员
			queryFaultOfDistributor();		
		} else if(userType==UserDAO.Factory) {	// 工业部门
			queryFaultOfFactory();
		}
		
		// 整理输出信息
		if(arrFault!=null) {
			formatFormResults(1);
		}
		
		return true;
	}
	
	/**
	 * @函数名称: getFormatRestult
	 * @函数描述: 根据查询结果进行格式化
	 * @输入参数: @return
	 * @返回类型: ArrayList<ArrayList<String>>
	 * @throws
	 */
	public ArrayList<ArrayList<String>> getFormatRestult() {
		return this.retResult;
	}
	
	/**
	 * @函数名称: queryDetail
	 * @函数描述: 根据故障单编号查询表单详情和进度
	 * @输入参数: @param faultId
	 * @返回类型: HashMap<String, String>
	 * @throws
	 */
	public HashMap<String, String> queryDetail(long faultId) {
		
		if(faultId<=0)
			return null;
		
		// 首先查询此故障单所有的审核信息
		ApprovalDAO appDAO = new ApprovalDAO();
		ArrayList<FrssApprovalInfo> arrApproval = appDAO.queryApproval(faultId);
		if(arrApproval==null || arrApproval.size()<=0)
			return null;
		
		int type = 0;
		int status = 0;
		for(FrssApprovalInfo appInfo : arrApproval) {
			// 查找到最终获取审核/确认的阶段
			if(type<appInfo.getType()) {
				type = appInfo.getType();
				status = appInfo.getStatus();
			}
		}
		
//		if(status==ApprovalDAO.confirm || status==ApprovalDAO.pass)
//			state = 1;
		state = status;			/// 前后台状态统一 [zuow, 2012/04/10]
		
		if(type<=0) {
			return null;
		}
		// 进行故障单详情查询
		queryFaultDetail(type, faultId, status);

		return mapDetail;
	}
	
	/**
	 * @函数名称: searchFault
	 * @函数描述: 根据输入条件查询故障信息
	 * @输入参数: @param startime
	 * @输入参数: @param endtime
	 * @输入参数: @param equipType
	 * @输入参数: @param equipName
	 * @输入参数: @param equipId
	 * @输入参数: @param reporter
	 * @输入参数: @param department
	 * @返回类型: void
	 * @throws
	 */
	public void searchFault(Date startTime, Date endTime, String equipType, String equipName, String equipId, String reporter, String department, int userType) {
		// 首先搜索结果
		FaultDAO faultDAO = new FaultDAO();
		arrFault = faultDAO.queryFault(startTime, endTime, equipType, equipName, equipId, reporter, department, userType);
		
		// 然后将搜索结果按照规定格式输出
		if(arrFault!=null) {
			formatFormResults(1);
		}
		
		return;			
	}
	
	/**
	 * @函数名称: searchFault
	 * @函数描述: 根据故障单编号查找故障信息
	 * @输入参数: @param faultId
	 * @返回类型: void
	 * @throws
	 */
	public void searchFault(long faultId) {
		FaultDAO faultDAO = new FaultDAO();
		FaultRepairReport faultReport = faultDAO.queryFault(faultId);
		
		if(faultReport!=null) {
			arrFault = new ArrayList<FaultRepairReport>();
			arrFault.add(faultReport);
			
			formatFormResults(1);
		}	
	}
	
	/**
	 * @函数名称: searchFault
	 * @函数描述: 故障现象关键字查询
	 * @输入参数: @param keyWord
	 * @返回类型: void
	 * @throws
	 */
	public void searchFault(String keyWord) {
		// 首先搜索结果
		FaultDAO faultDAO = new FaultDAO();
		arrFault = faultDAO.queryFault(keyWord);
		
		// 然后将搜索结果按照规定格式输出
		if(arrFault!=null) {
			formatFormResults(1);
		}
		
		return;		
	}
	
	/**
	 * @函数名称: statFault
	 * @函数描述: 统计图表的数据查询接口
	 * @输入参数: @param dateFlag
	 * @输入参数: @param year
	 * @输入参数: @param departName
	 * @输入参数: @param equipTag
	 * @输入参数: @param equipInfo
	 * @输入参数: @param loginUserType
	 * @输入参数: @return
	 * @返回类型: LinkedHashMap<String, Integer>
	 * @throws
	 */
	public LinkedHashMap<String, Integer> statFault(int dateFlag, int year, String departName, int equipTag, String equipInfo, int loginUserType) {
		// 返回结果，就是根据时间段划分出的一串统计值
		LinkedHashMap<String, Integer> mapStatResult = null;
		
		// 首先根据日期标识和年份获取各个时间段
		DateUtil dateUtil = new DateUtil();
		LinkedHashMap<String, ArrayList<Date>> mapDate = dateUtil.getDatePeried(dateFlag, year);
		if(mapDate==null) {
			/// log
			return null;
		}
		
		int amount = 0;
		FaultDAO faultDAO = new FaultDAO();
		// 查询各个时间段的故障单个数并返回
		int count = mapDate.size();
		mapStatResult = new LinkedHashMap<String, Integer>();
		
		String department = null;
		int level = 0;
		if(loginUserType==UserDAO.Regiment) {	// 团级用户
			level = 1;
			department = departName;
		} else if(departName!=null&&!(departName.equals(""))) {	/// 根据department字符串中解析出用户级别 [zuow, 2012/05/02]
			String str = departName;
			department = str.substring(str.indexOf("-")+1);
			level = Integer.parseInt(str.substring(0, str.indexOf("-")));
		} else {
			System.out.print("输入参数非法，请重新输入!");
			return null;
		}
		
		Iterator itKey = mapDate.entrySet().iterator();
		while(itKey.hasNext()) {
			Map.Entry<String, ArrayList> entryMap = (Map.Entry<String, ArrayList>)itKey.next();
			String key = entryMap.getKey();
			ArrayList<Date> arrDate = entryMap.getValue();
			
			Date start = arrDate.get(0);
			Date end = arrDate.get(1);
			
			amount = queryFaultAmount(start, end, level, department, equipTag, equipInfo);
			
			mapStatResult.put(key, amount);
		}
		
		return mapStatResult;
	}
	
	/**
	 * @函数名称: statFault
	 * @函数描述: 统计图表数据查询接口
	 * @输入参数: @param dateFlag
	 * @输入参数: @param year
	 * @输入参数: @param departName
	 * @输入参数: @param equipTag
	 * @输入参数: @param equipInfo
	 * @输入参数: @param loginUserType
	 * @输入参数: @param state
	 * @输入参数: @return
	 * @返回类型: LinkedHashMap<String,ArrayList<Integer>>
	 * @throws
	 */
	public LinkedHashMap<String, LinkedHashMap<String, Integer>> statFault(int dateFlag, int year, String departName, int equipTag, String equipInfo, int loginUserType, int state) {
		LinkedHashMap<String, LinkedHashMap<String, Integer>> mapStatResult = null;		// 保存统计结果
		LinkedHashMap<String, ArrayList<Date>> mapDate = null;				// 保存解析出来的时间段
		HashMap<String, Integer> mapDepartment = null;						// 需要查询的部门级别及部门名
		int status = 0;														// 故障单状态
		
		// 首先根据日期标识和年份获取各个时间段
		DateUtil dateUtil = new DateUtil();
		mapDate = dateUtil.getDatePeried(dateFlag, year);
		if(mapDate==null) {
			System.out.print("输入的时间参数有误，请检查!");
			return null;
		}
		
		// 获取部门信息列表 [zuow, 2012/05/13]
		mapDepartment = getDepartment(departName);
		if(mapDepartment==null || mapDepartment.size()<=0) {
			System.out.print("输入的部门信息有误，请检查!");
			return null;
		}
				
		mapStatResult = new LinkedHashMap<String, LinkedHashMap<String, Integer>>();
		/// 进行查询统计
		Iterator itKey = mapDate.entrySet().iterator();
		while(itKey.hasNext()) {
			int amount = 0;
			Map.Entry<String, ArrayList> entryMap = (Map.Entry<String, ArrayList>)itKey.next();
			String key = entryMap.getKey();
			ArrayList<Date> arrDate = entryMap.getValue();
			
			Date start = arrDate.get(0);
			Date end = arrDate.get(1);
			
			// 分两种情况进行处理：单个部门和多个部门 [zuow,2012/05/13]
			int level = 0;
			String department = null;
			if(mapDepartment.size()==1) {	// 单个部门分别统计未处理和处理的故障单个数
				Iterator itDepart = mapDepartment.entrySet().iterator();
				Map.Entry<String, Integer> entryMap2 = (Map.Entry<String, Integer>)itDepart.next();
				department = entryMap2.getKey();
				level = entryMap2.getValue();
				
				LinkedHashMap<String, Integer> arrFaultAmount = new LinkedHashMap<String, Integer>();
				
				// 首先查询未处理故障单
				status = 0;
				int amount1 = 0;
				if(level==1) {
					amount1 = queryFaultAmount(start, end, level, department, equipTag, equipInfo);
				} else {
					amount1 = queryFaultAmount(start, end, level, department, equipTag, equipInfo, status);
				}
								
				// 查询已经处理故障单
				status = 1;
				int amount2 = 0;				
				if(level>1){
					amount2 = queryFaultAmount(start, end, level, department, equipTag, equipInfo, status);	
				}
				
				// 查询退回的故障单
				status = 2;
				int amount3 = 0;
				if(level>1) {
					amount3 = queryFaultAmount(start, end, level, department, equipTag, equipInfo, status);
				}
				
				if(state==0) {	// 已处理+未处理
					arrFaultAmount.put(department, amount1 + amount2 + amount3);
				} else if(state==1) {	// 未处理，对于团级用户来说是故障上报个数
					arrFaultAmount.put(department, amount1);
				} else if(state==10) {	// 已处理
					arrFaultAmount.put(department, amount2 + amount3);
				} else if(state==11) { // 未处理，已处理
					arrFaultAmount.put("未处理", amount1);
					arrFaultAmount.put("已处理", amount2+amount3);
				}
				
				mapStatResult.put(key, arrFaultAmount);
				
			} else if (mapDepartment.size()>1) {	// 多个用户只统计处理或者未处理一种状态
				LinkedHashMap<String, Integer> arrFaultAmount = new LinkedHashMap<String, Integer>();
				Iterator itDepart = mapDepartment.entrySet().iterator();
				while(itDepart.hasNext()) {
					Map.Entry<String, Integer> entryDepart = (Map.Entry<String, Integer>)itDepart.next();
					department = entryDepart.getKey();
					level = entryDepart.getValue();
					
					// 首先查询未处理故障单
					int amount1 = 0;
					status = 0;
					if(level==1) {	// 团级用户统计故障单上报个数
						amount1 = queryFaultAmount(start, end, level, department, equipTag, equipInfo);
					} else {		// 其它用户统计故障审核处理个数
						amount1 = queryFaultAmount(start, end, level, department, equipTag, equipInfo, status);
					}
					
					// 已经处理故障单
					status = 1;
					int amount2 = 0;
					if(level>1) {
						amount2 = queryFaultAmount(start, end, level, department, equipTag, equipInfo, status);
					}
					
					// 被打回的故障单
					status = 2;
					int amount3 = 0;
					if(level>1) {
						amount3 = queryFaultAmount(start, end, level, department, equipTag, equipInfo, status);
					}
					
					if(state==0) {	// 已处理+未处理
						arrFaultAmount.put(department, amount1 + amount2 + amount3);
					} else if(state==1) {	// 未处理
						arrFaultAmount.put(department, amount1);
					} else if(state==10) {	// 已处理
						arrFaultAmount.put(department, amount2 + amount3);
					} else if(state==11) { // 未处理，已处理
						arrFaultAmount.put(department, amount1);
						arrFaultAmount.put(department, amount2+amount3);
					}
				}
				
				mapStatResult.put(key, arrFaultAmount);
			}	
		}
				
		return mapStatResult;
	}
	
	/**
	 * @函数名称: queryFaultAmount
	 * @函数描述: 查找当前部门下所有团级用户处理的故障单个数
	 * @输入参数: @param start
	 * @输入参数: @param end
	 * @输入参数: @param level
	 * @输入参数: @param department
	 * @输入参数: @param equipTag
	 * @输入参数: @param equipInfo
	 * @输入参数: @return
	 * @返回类型: int
	 * @throws
	 */
	public int queryFaultAmount(Date start, Date end, int level, String department, int equipTag, String equipInfo) {
		int amount = 0;
		
		HashSet<String> setUser = null;
		if(department!=null&&!(department.equals(""))) {	/// 根据department字符串中解析出用户级别 [zuow, 2012/05/02]
			UserManagerBean userManager = new UserManagerBean();
//			setUser = userManager.getRegimentUser(level, department);	
			setUser = userManager.getSubUser(level, department);
		}
		
		FaultDAO faultDAO = new FaultDAO();
		if(faultDAO!=null && setUser!=null && setUser.size()>0) {
			
			for(String username : setUser) {
				long count = faultDAO.queryFault(start, end, username, equipTag, equipInfo);
			
				amount += count;
			}
		}
		
		return amount;
	}
	
	/**
	 * @函数名称: queryFaultAmount
	 * @函数描述: 查询当前部门下所某状态下的故障单个数 [zuow, 2012/05/12]
	 * @输入参数: @param start
	 * @输入参数: @param end
	 * @输入参数: @param level
	 * @输入参数: @param department
	 * @输入参数: @param equipTag
	 * @输入参数: @param equipInfo
	 * @输入参数: @param processTag
	 * @输入参数: @return
	 * @返回类型: int
	 * @throws
	 */
	public int queryFaultAmount(Date start, Date end, int level, String department, int equipTag, String equipInfo, int status) {
		int amount = 0;
		
		HashSet<String> setUser = null;
		if(department!=null&&!(department.equals(""))) {	/// 根据department字符串中解析出用户级别 [zuow, 2012/05/02]
			UserManagerBean userManager = new UserManagerBean();
//			setUser = userManager.getRegimentUser(level, department);
			setUser = userManager.getSubUser(level, department);		// 查找其下直接下属用户，不通过团级用户上报进行查询 [zuow,2012/05/16]
		}
		
		// 根据部门级别换算审核类型 [zuow, 2012/05/12]
		int reportType = 0;
		switch(level) {
		case 1:
		case 2:
			reportType = ApprovalDAO.faultL1;
			break;
		case 3:
			reportType = ApprovalDAO.faultL2;
			break;
		case 4:
			reportType = ApprovalDAO.formatCheck;
			break;
		case 5:
			reportType = ApprovalDAO.dispatch;
			break;
		case 6:
			reportType = ApprovalDAO.feedback;
			break;
		case 7:
			reportType = ApprovalDAO.phoneBack;
			break;
		}
		
		FaultDAO faultDAO = new FaultDAO();
		if(faultDAO!=null && setUser!=null && setUser.size()>0) {
			
			for(String username : setUser) {
				long count = faultDAO.queryFault(start, end, username, equipTag, equipInfo, reportType, status);
				
				amount += count;
			}
		}
		
		return amount;
	}
	////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * @函数名称: queryFaultOfRegiment
	 * @函数描述: 团级用户故障查询
	 * @输入参数: @param status
	 * @返回类型: void
	 * @throws
	 */
	private void queryFaultOfRegiment() {
		
		if(faultDAO==null || startTime==null || endTime==null)
			return;
		
		int type = ApprovalDAO.faultL1;			// L1级审核
		int status = 0;			// 订单状态
		int flag = 0;			// 输入的用户信息标识
		if(state==0) {			// 未处理的，即团级上报的未经过L1审核的故障单--集团军未确认的故障单	
			status = ApprovalDAO.confirm;	// 确认状态
			arrFault = faultDAO.queryFault(startTime, endTime, type, status, flag, userName);
		} else if(state==1) {	// 已处理的，包括集团军通过/未通过L1审核的故障单
			// 审核通过的故障单
			status = ApprovalDAO.pass;		
			flag = 0;
			// 团级用户采用上报时间进行查询 [zuow,2012/05/07]
			arrFault = faultDAO.queryFault(startTime, endTime, type, status, flag, userName);
			
			// 审核不通过的故障单
			status = ApprovalDAO.nopass;		
			ArrayList<FaultRepairReport> arrTemp = faultDAO.queryFault(startTime, endTime, type, status, flag, userName);
			
			if(arrTemp!=null) {
				if(arrFault==null) {
					arrFault = arrTemp;
				} else {
					for (FaultRepairReport fault : arrTemp)
						arrFault.add(fault);
				}
			}			
		}
		
	}
	
	/**
	 * @函数名称: queryFaultOfGroupArmy
	 * @函数描述: 集团军故障查询显示
	 * @输入参数: @param status
	 * @返回类型: void
	 * @throws
	 */
	private void queryFaultOfGroupArmy() {
		if(faultDAO==null || startTime==null || endTime==null)
			return;
		
		int type = ApprovalDAO.faultL1;			// L1级审核
		int status = 0;			// 订单状态
		int usertype = 0;		// 处理订单的用户类型
		int flag = 0;
		
		if(state==0) {			// 未处理的，即团级上报的未经过L1审核的故障单	是否还包括L2级审核未通过的？？？	
			status = ApprovalDAO.confirm;
			flag = 2;
			arrFault = faultDAO.queryFault(startTime, endTime, type, status, flag, userName);
		} else if(state==1) {	// 已处理的，包括集团军通过/未通过L1审核的故障单
			status = ApprovalDAO.pass;		// 审核通过
			flag = 1;
			arrFault = faultDAO.queryFault(startTime, endTime, type, status, flag ,userName);
			
			status = ApprovalDAO.nopass;		// 审核不通过
			ArrayList<FaultRepairReport> arrTemp = faultDAO.queryFault(startTime, endTime, type, status, flag, userName);
			
			if(arrTemp!=null) {
				if(arrFault==null)
					arrFault = arrTemp;
				else {
					for(FaultRepairReport fault : arrTemp) {
						arrFault.add(fault);
					}
				}
			}
		}
	}
	
	/**
	 * @函数名称: queryFaultOfMilitary
	 * @函数描述: 军区故障查询显示
	 * @输入参数: @param status
	 * @返回类型: void
	 * @throws
	 */
	private void queryFaultOfMilitary() {
		if(faultDAO==null || startTime==null || endTime==null)
			return;
		
		int type = 0;			// 审核级别
		int status = 0;			// 订单状态
		int usertype = 0;		// 处理订单的用户类型
		int flag = 0;
		
		if(state==0) {			// 未处理的，即军区L2级的所有需确认的故障单		
			usertype = UserDAO.GroupArmy;
			type = ApprovalDAO.faultL2;	
			status = ApprovalDAO.confirm;
			flag = 2;
			arrFault = faultDAO.queryFault(startTime, endTime, type, status, flag, userName);		
			
		} else if(state==1) {	// 已处理的，包括集军区通过/未通过L2审核的故障单
			usertype = UserDAO.Military;
			type = ApprovalDAO.faultL2;
			status = ApprovalDAO.pass;		// 审核通过
			flag = 1;
			arrFault = faultDAO.queryFault(startTime, endTime, type, status, flag, userName);
			
			status = ApprovalDAO.nopass;		// 审核不通过
			ArrayList<FaultRepairReport> arrTemp = faultDAO.queryFault(startTime, endTime, type, status, flag, userName);
			if(arrTemp!=null) {
				if(arrFault==null)
					arrFault = arrTemp;
				else {
					for(FaultRepairReport fault : arrTemp) {
						arrFault.add(fault);
					}
				}
			}
		}		
	}
	

	/**
	 * @函数名称: queryFaultOfFormater
	 * @函数描述: 维修中心故障查询显示，包括两部分：数据格式审核人员、分发人员
	 * @输入参数: @param status
	 * @返回类型: void
	 * @throws
	 */
	private void queryFaultOfFormater() {
		// 未处理故障单：统计通过L2级审核但没有数据格式检查的故障单， 未进行电话回访的反馈单
		int usertype = 0;
		int type = 0;
		int status = 0;
		if(state==0) {		// 未处理表单
			// 首先查询通过L2级审核但没有数据格式确认的故障单
			usertype = UserDAO.Military;
			type = ApprovalDAO.formatCheck;
			status = ApprovalDAO.confirm;
			arrFault = faultDAO.queryFault(startTime, endTime, usertype, type, status);	// 包括故障和备件未经格式确认的所有记录，在查询的时候对单号进行处理
			
			/// 查询未进行电话回访的反馈表
			usertype = UserDAO.Factory;
			type = ApprovalDAO.phoneBack;
			status = ApprovalDAO.confirm;
			ArrayList<FaultRepairReport> arrTemp = faultDAO.queryFault(startTime, endTime, usertype, type, status);
			if(arrTemp!=null) {
				if(arrFault==null)
					arrFault = arrTemp;
				else {
					for(FaultRepairReport fault : arrTemp) {
						arrFault.add(fault);
					}
				}
			}
			
		} else if(state==1) {		
			/// 查询经过电话回访确认的故障单
			usertype = UserDAO.Formater;
			type = ApprovalDAO.phoneBack;
			status = ApprovalDAO.pass;
			int iFlag = 1;
			arrFault = faultDAO.queryFault(startTime, endTime, type, status, iFlag, userName);

			
			usertype = UserDAO.Formater;
			type = ApprovalDAO.formatCheck;
			status = ApprovalDAO.pass;		// 格式审查通过			
			ArrayList<FaultRepairReport> arrTemp = faultDAO.queryFault(startTime, endTime, type, status, iFlag, userName);
			if(arrTemp!=null) {
				if(arrFault==null)
					arrFault = arrTemp;
				else {
					for(FaultRepairReport fault1 : arrTemp) {
						boolean flag = false;
						for (FaultRepairReport fault2 : arrFault) {		// 去掉两次查询重复值 [zuow, 2012/04/21]
							if(fault1.getFaultID()==fault2.getFaultID())
								flag = true;
						}
						
						if(flag)
							continue;
						
						arrFault.add(fault1);
					}
				}
			}
			
			status = ApprovalDAO.nopass;		// 格式审查通过			
			arrTemp = faultDAO.queryFault(startTime, endTime, type, status, iFlag, userName);
			if(arrTemp!=null) {
				if(arrFault==null)
					arrFault = arrTemp;
				else {
					for(FaultRepairReport fault1 : arrTemp) {
						boolean flag = false;
						for (FaultRepairReport fault2 : arrFault) {		// 去掉两次查询重复值 [zuow, 2012/04/21]
							if(fault1.getFaultID()==fault2.getFaultID())
								flag = true;
						}
						
						if(flag)
							continue;
						
						arrFault.add(fault1);
					}
				}
			}			
			
		}
		
	}
	
	
	/**
	 * @函数名称: queryFaultOfDistributor
	 * @函数描述: 维修中心故障显示--故障分发人员
	 * @输入参数: 
	 * @返回类型: void
	 * @throws
	 */
	private void queryFaultOfDistributor() {
		int usertype = 0;
		int type = 0;
		int status = 0;
		if(state==0) {		// 未处理表单，所有待分发故障单
			usertype = UserDAO.Formater;			/// 暂时不进行格式确认
			type = ApprovalDAO.dispatch;		
			status = ApprovalDAO.confirm;
			arrFault = faultDAO.queryFault(startTime, endTime, usertype, type, status);
			
		} else if(state==1) {		// 已处理表单，所有已经分发的故障单
			usertype = UserDAO.Distributor;
			type = ApprovalDAO.dispatch;
			status = ApprovalDAO.pass;
			int flag = 1;
			arrFault = faultDAO.queryFault(startTime, endTime, type, status, flag, userName);
		}
	}
	
	
	/**
	 * @函数名称: queryFaultOfFactory
	 * @函数描述: 工业部门故障单显示
	 * @输入参数: 
	 * @返回类型: void
	 * @throws
	 */
	private void queryFaultOfFactory(){
		// 显示已经分发但还没有生成现场维修/远程支援表单确认信息的记录
		int usertype = 0;
		int type = 0;
		int status = 0;
		if(state==0) {	// 未处理表单
			// 查询待确认的反馈单
			usertype = UserDAO.Distributor;
			type = ApprovalDAO.feedback;
			status = ApprovalDAO.confirm;
			arrFault = faultDAO.queryFault(startTime, endTime, usertype, type, status);			
			
		} else if(state==1) {	// 已处理表单
			usertype = UserDAO.Factory;
			type = ApprovalDAO.feedback;
			status = ApprovalDAO.pass;
			int flag = 1;
			arrFault = faultDAO.queryFault(startTime, endTime, type, status, flag, userName);
		}		
	}
	
	
	/**
	 * @函数名称: queryFaultDetail
	 * @函数描述: 根据表单类型、故障id和状态查询故障单详细信息
	 * @输入参数: @param type
	 * @输入参数: @param faultId
	 * @输入参数: @param status
	 * @返回类型: void
	 * @throws
	 */
	private void queryFaultDetail(int type, long faultId, int state) {
		// 基本信息和审核信息分开查询
		FaultDAO faultDAO = new FaultDAO();
		FaultRepairReport faultReport = faultDAO.queryFault(faultId);
		if(faultReport==null) {
			/// log
			return;
		}
		// 备件信息查询
		EquipDAO equipDAO = new EquipDAO();
		BackupApplication bakApp = equipDAO.queryBack(faultId);
		
		RepairDispatchForm repairDispatch = null;
		FrssRepairFeedback repairFeedback = null;
		RemoteSupportForm remoteForm = null;
		FrssClientReview review = null;
		
		int status = ApprovalDAO.pass;		// 查询各个表单已经审核的信息
		// 查询各级审核信息		
		FrssApprovalInfo appInfo = null;
		ApprovalDAO appDAO = new ApprovalDAO();
		
		// 故障一级审核
		appInfo = appDAO.queryApproval(ApprovalDAO.faultL1, faultId, status);
		if(appInfo!=null) {
			faultReport.setL1Checker(appInfo.getChecker());
			faultReport.setL1CheckOpinion(appInfo.getOpinion());
			faultReport.setL1CheckTime(appInfo.getCheckTime());
					
			step = ApprovalDAO.l1check;
		} else {
			appInfo = appDAO.queryApproval(ApprovalDAO.faultL1, faultId, ApprovalDAO.nopass);
			if(appInfo!=null) {
				faultReport.setL1Checker(appInfo.getChecker());
				faultReport.setL1CheckOpinion(appInfo.getOpinion());
				faultReport.setL1CheckTime(appInfo.getCheckTime());
						
				step = ApprovalDAO.l1check;
			} else {
				step = ApprovalDAO.submit;
			}
		}
		
		// 故障二级审核
		appInfo = appDAO.queryApproval(ApprovalDAO.faultL2, faultId, status);
		if(appInfo!=null) {
			faultReport.setL2Checker(appInfo.getChecker());
			faultReport.setL2CheckOpinion(appInfo.getOpinion());
			faultReport.setL2CheckTime(appInfo.getCheckTime());
			
			step = ApprovalDAO.l2check;
		} else {
			appInfo = appDAO.queryApproval(ApprovalDAO.faultL2, faultId, ApprovalDAO.nopass);
			if(appInfo!=null) {
				faultReport.setL2Checker(appInfo.getChecker());
				faultReport.setL2CheckOpinion(appInfo.getOpinion());
				faultReport.setL2CheckTime(appInfo.getCheckTime());
						
				step = ApprovalDAO.l2check;
			}
		}
		
		if(bakApp!=null) {		// 如果有备件信息
			long  bakId = bakApp.getBackID();
			// 备件一级审核
			appInfo = appDAO.queryApproval(ApprovalDAO.backL1, bakId, status);
			if(appInfo!=null && bakApp!=null) {
				bakApp.setL1Checker(appInfo.getChecker());
				bakApp.setL1CheckOpinion(appInfo.getOpinion());
				bakApp.setL1CheckTime(appInfo.getCheckTime());
				
				step = ApprovalDAO.l1check;
			}	
			
			// 备件二级审核信息
			appInfo = appDAO.queryApproval(ApprovalDAO.backL2, bakId, status);
			if(appInfo!=null && bakApp!=null) {
				bakApp.setL2Checker(appInfo.getChecker());
				bakApp.setL2CheckOpinion(appInfo.getOpinion());
				bakApp.setL2CheckTime(appInfo.getCheckTime());
				
				step = ApprovalDAO.l2check;
			}	
		}
		
		// 格式确认信息
		appInfo = appDAO.queryApproval(ApprovalDAO.formatCheck, faultId, status);
		if(appInfo!=null){			
			step = ApprovalDAO.formated;
		} else {	// 审核不通过 [zuow, 2012/05/08]
			appInfo = appDAO.queryApproval(ApprovalDAO.formatCheck, faultId, ApprovalDAO.nopass);
			if(appInfo!=null) {						
				step = ApprovalDAO.formated;
			}
		}
		
		// 分发信息
		appInfo = appDAO.queryApproval(ApprovalDAO.dispatch, faultId, status);
		if(appInfo!=null) {
			step = ApprovalDAO.dispatched;
			RepairDAO repairDAO = new RepairDAO();				
			repairDispatch = repairDAO.queryRepairDispatch(faultId);	
		}	
		
		// 反馈信息
		appInfo = appDAO.queryApproval(ApprovalDAO.feedback, faultId, status);
		if(appInfo!=null) {
			step = ApprovalDAO.feedbacked;
			RepairDAO repairDAO = new RepairDAO();
			repairFeedback = repairDAO.queryRepairFeedback(faultId);
			// 如果采用远程支援方式，则一定有远程支援信息单
			if(repairFeedback.getRepairWay()==2 || repairFeedback.getRepairWay()==3) {
				long feedbackId = repairFeedback.getId();
				remoteForm = repairDAO.queryRemoteSupport(faultId, feedbackId);
			}	
			
			feedbackTime = appInfo.getCheckTime();		// 获取审核时间
		}	
		
		// 电话确认
		appInfo = appDAO.queryApproval(ApprovalDAO.phoneBack, faultId, status);
		if(appInfo!=null) {
			step = ApprovalDAO.phonebacked;	// 完成
			
			RepairDAO repairDAO = new RepairDAO();
			review = repairDAO.queryClientReview(faultId);			
		}			
		
		// 整理输出数据
		formatDetaiResults(faultReport, bakApp, repairDispatch, repairFeedback, remoteForm, review);
	}
	
	
	/**
	 * @函数名称: formatResults
	 * @函数描述: 整理主页表格输出结果
	 * @输入参数: @param flag，1表示故障信息，2表示备件信息
	 * @返回类型: void
	 * @throws
	 */
	private void formatFormResults(int flag) {

		
		if(flag==1&&arrFault!=null)	{	// 故障信息	
			
			retResult = new ArrayList<ArrayList<String>>();
			
			for(FaultRepairReport fault : arrFault) {				
				ArrayList arrRet = new ArrayList<String>();	
				
				arrRet.add(Long.toString(fault.getFaultID()));
				/// 重新划分阶段step
				step = fault.getStep();			//.getReportType();
				int status = fault.getStatus();
				if(step==ApprovalDAO.backL1 || step==ApprovalDAO.faultL1) {
					if(status==ApprovalDAO.confirm)
						step = ApprovalDAO.submit;
					else if (status==ApprovalDAO.pass || status==ApprovalDAO.nopass)
						step = ApprovalDAO.l1check;
				} else if(step==ApprovalDAO.backL2 || step==ApprovalDAO.faultL2) {
					if(status==ApprovalDAO.confirm)
						step = ApprovalDAO.l1check;
					else if(status==ApprovalDAO.pass || status==ApprovalDAO.nopass)
						step = ApprovalDAO.l2check;
				} else if(step==ApprovalDAO.formatCheck) {
					if(status==ApprovalDAO.confirm)
						step = ApprovalDAO.l2check;
					else if(status==ApprovalDAO.pass)
						step = ApprovalDAO.formated;
				} else if(step==ApprovalDAO.dispatch) {
					if(status==ApprovalDAO.confirm)
						step = ApprovalDAO.formated;
					else if(status==ApprovalDAO.pass)
						step = ApprovalDAO.dispatched;
				} else if(step==ApprovalDAO.feedback) {
					if(status==ApprovalDAO.confirm)
						step = ApprovalDAO.dispatched;
					else if(status==ApprovalDAO.pass)
						step = ApprovalDAO.feedbacked;
				} else if(step==ApprovalDAO.phoneBack) {
					if(status==ApprovalDAO.confirm)
						step = ApprovalDAO.feedbacked;
					else if(status==ApprovalDAO.pass)
						step = ApprovalDAO.phonebacked;					
				} else 
					step = ApprovalDAO.submit;
				arrRet.add(Integer.toString(step));
				
				status = fault.getStatus();
				arrRet.add(Integer.toString(status));			// 前后台返回状态保持一致 [zuow, 2012/04/10]
//				if(status==2) {	// 终止
//					arrRet.add(Integer.toString(0));
//				} else {	// 进行中
//					arrRet.add(Integer.toString(1));
//				}
				
				arrRet.add(fault.getEquipType());
				arrRet.add(fault.getEquipName());
				arrRet.add(Integer.toString(fault.getFaultAmount()));
				arrRet.add(fault.getEquipOperater());
				arrRet.add(fault.getReporter());
				arrRet.add(dateUtil.getTimeString(fault.getReportTime()));
				
				retResult.add(arrRet);
			}
		} 
	}
	
	/**
	 * @函数名称: formatDetaiResults
	 * @函数描述: 根据查询结果整理故障单详细信息输出结果
	 * @输入参数: @param faultReport
	 * @输入参数: @param bakApp
	 * @输入参数: @param factoryInfo
	 * @输入参数: @param repairDispatch
	 * @输入参数: @param repairFeedback
	 * @输入参数: @param remoteForm
	 * @返回类型: void
	 * @throws
	 */
	private void formatDetaiResults(FaultRepairReport faultReport, BackupApplication bakApp, 
			RepairDispatchForm repairDispatch, FrssRepairFeedback repairFeedback, 
			RemoteSupportForm remoteForm, FrssClientReview review) {
		mapDetail = new HashMap<String, String>();
		DateUtil dateUtil = new DateUtil();
		
		// 整理故障表单
		if(faultReport!=null) {
			// 整体信息
			mapDetail.put("orderid", Long.toString(faultReport.getFaultID()));
			mapDetail.put("step", Integer.toString(step));
			mapDetail.put("state", Integer.toString(state));
			
			mapDetail.put("commiter", faultReport.getReporter());
			mapDetail.put("committime", dateUtil.getTimeString(faultReport.getReportTime()));
			mapDetail.put("contact", faultReport.getReporterContact());
			mapDetail.put("zbuser", faultReport.getEquipOperater());
			mapDetail.put("zbsydw", faultReport.getEquipDepartment());
			mapDetail.put("faultid", Long.toString(faultReport.getFaultID()));
			mapDetail.put("zbtype", faultReport.getEquipType());
			mapDetail.put("zbname", faultReport.getEquipName());
			mapDetail.put("zbserial", faultReport.getEquipNumber());
			mapDetail.put("zbnum", Integer.toString(faultReport.getFaultAmount()));
			mapDetail.put("zbgzxx", faultReport.getFaultDesp());
			mapDetail.put("zbgzsj", dateUtil.getTimeString(faultReport.getFaultTime()));
			mapDetail.put("zbxqcl", faultReport.getPreprocess());
			mapDetail.put("zbgzpc", Integer.toString(faultReport.getFaultFrequency()));
			mapDetail.put("zbgzbw", faultReport.getFaultPlace());
			// 派遣单位
			if(repairDispatch!=null)
				mapDetail.put("dpunit", faultReport.getEquipDepartment());
			// 远程支援单位
			if(remoteForm!=null)
				mapDetail.put("rsunit", faultReport.getEquipDepartment());
			
			// 故障照片
			mapDetail.put("zbqzzp", faultReport.getPhotoName());
			// 故障原因
			mapDetail.put("fbgzreason", faultReport.getFaultCause());
		}
		
		if(bakApp!=null) {
			mapDetail.put("backupid", Long.toString(bakApp.getBackID()));
			mapDetail.put("bjtype", bakApp.getBackType());
			mapDetail.put("bjname", bakApp.getBackName());
			mapDetail.put("bjserial", bakApp.getBackNumber());
			mapDetail.put("bjnum", Integer.toString(bakApp.getBackAmount()));
			mapDetail.put("bjpart", bakApp.getUseEquipPlace());
			mapDetail.put("bjfaultid", Long.toString(faultReport.getFaultID()));		
//			mapDetail.put("fbrpbjname", bakApp.getUseEquipName());
//			mapDetail.put("fbrpbjtype", bakApp.getUseEquipType());
		}
		
		// 审核信息是故障审核还是备件审核？？？
		if(faultReport!=null && step>0) {
			mapDetail.put("l1checker", faultReport.getL1Checker());
			mapDetail.put("l1time", dateUtil.getTimeString(faultReport.getL1CheckTime()));
			mapDetail.put("l1opinion", faultReport.getL1CheckOpinion());
		}
		if(faultReport!=null && step>1) {
			mapDetail.put("l2checker", faultReport.getL2Checker());
			mapDetail.put("l2time", dateUtil.getTimeString(faultReport.getL2CheckTime()));
			mapDetail.put("l2opinion", faultReport.getL2CheckOpinion());
		}		
		
		if(repairDispatch!=null) {
			mapDetail.put("dpid", Long.toString(repairDispatch.getDispatchId()));			
			mapDetail.put("dptime", dateUtil.getTimeString(repairDispatch.getDispatchTime()));
			mapDetail.put("dpsafeunit", repairDispatch.getGuarantee());
			mapDetail.put("dpaddr", repairDispatch.getGuaranteeAddress());
			mapDetail.put("dpcontact", repairDispatch.getContact());
		}
		
		if(repairFeedback!=null) {
			mapDetail.put("fbid", Long.toString(repairFeedback.getId()));
			mapDetail.put("fbdispatch", repairFeedback.getFaultDispatch());
			mapDetail.put("fbrpcontent", repairFeedback.getRepairDisp());
			mapDetail.put("fbrpway", Integer.toString(repairFeedback.getRepairWay()));
			mapDetail.put("fbrpresult", repairFeedback.getResults());
			mapDetail.put("fbrpquality", Integer.toString(repairFeedback.getQuality()));
			mapDetail.put("fbrptime", dateUtil.getTimeString(repairFeedback.getRepairTime()));		
			mapDetail.put("fbrpbjfrom", repairFeedback.getBackSource());
			mapDetail.put("fbtime", dateUtil.getTimeString(feedbackTime));		/// 临时加上，以后再改 [zuow, 2012/04/10]
			
//			if(bakApp!=null) {		// 添加备件使用信息[zuow, 2012/04/22]
//				mapDetail.put("fbrpbjname", bakApp.getBackName());
//				mapDetail.put("fbrpbjtype", bakApp.getBackType());
//			}
			
			// 暂时使用反馈表中的 [zuow, 2012/05/06]
			mapDetail.put("fbrpbjname", repairFeedback.getBackName());
			mapDetail.put("fbrpbjtype", repairFeedback.getBackType());
		}
		
		if(remoteForm!=null) {
			mapDetail.put("rsid", Long.toString(remoteForm.getRemoteId()));
			mapDetail.put("rschannel", remoteForm.getSupportChannel());
			mapDetail.put("rstype",  remoteForm.getSupportType());
			mapDetail.put("rsexpert", remoteForm.getExpert());
			mapDetail.put("recontacter", remoteForm.getContact());
			mapDetail.put("rscontactway", remoteForm.getContactWay());			
		}
		
		// 客户回访单 [zuow,2012/05/08]
		if(review!=null) {
			mapDetail.put("re_client", review.getClient());
			mapDetail.put("re_quality", Integer.toString(review.getQuality()));
			
			int way = review.getReviewWay();
			mapDetail.put("re_reviewway", Integer.toString(way));
			mapDetail.put("re_contact", review.getContact());
			mapDetail.put("re_disc", review.getDiscription());		// 维修质量描述 [zuow,2012/05/15]
			
			// 查询回访确认记录
			ApprovalDAO appDAO = new ApprovalDAO();
			FrssApprovalInfo appInfo = appDAO.queryApproval(ApprovalDAO.phoneBack, review.getFaultId(), ApprovalDAO.pass);
			if(appInfo!=null) {
				mapDetail.put("re_reporter", appInfo.getChecker());
				mapDetail.put("re_reporttime", dateUtil.getTimeString(appInfo.getCheckTime()));
			}
		}
	}
	
	/**
	 * @函数名称: getDepartment
	 * @函数描述: 根据输入的字符串解析出部门列表；   format: level-department level-department;level-department;...
	 * @输入参数: @param departName
	 * @输入参数: @return
	 * @返回类型: HashMap<Integer,String>
	 * @throws
	 */
	private HashMap<String, Integer> getDepartment(String departName) {
		HashMap<String, Integer> mapDepartment = new HashMap<String, Integer>();
		
		String department = departName;
		if(department!=null) {
			int loc1 = department.indexOf(";");
			while(loc1 >= 0) {
				if (loc1 > 0) {
					// 解析出每对部门键值对
					String values = department.substring(0, loc1);
					int loc2 = values.indexOf("-");
					int level = Integer.parseInt(values.substring(0, loc2));
					String depart = values.substring(loc2+1);
					mapDepartment.put(depart, level);
				}
				department = department.substring(loc1+1);
				loc1 = department.indexOf(";");
			}
			if (department.length() > 0) {
				// 解析出每对部门键值对
				int loc2 = department.indexOf("-");
				int level = Integer.parseInt(department.substring(0, loc2));
				String depart = department.substring(loc2+1);
				mapDepartment.put(depart, level);
			}
		}
		
		return mapDepartment;
	}
	//////////////////////////////////////////////////////////////////////////////////////
}
