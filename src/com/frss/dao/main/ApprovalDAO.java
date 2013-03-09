package com.frss.dao.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.frss.dao.util.BaseDAO;
import com.frss.model.main.BackupApplication;
import com.frss.model.main.FaultRepairReport;
import com.frss.model.mapping.FrssApprovalInfo;
import com.frss.model.mapping.FrssEquipmentInfo;
import com.frss.model.mapping.FrssFaultInfo;
import com.frss.model.mapping.FrssUserInfo;
import com.frss.util.DateUtil;
import com.frss.util.FrssException;

public class ApprovalDAO {
	// 流程阶段
	public final static int faultL1 = 1;		// 故障L1级审核
	public final static int faultL2 = 2;		// 故障L2级审核
	public final static int backL1 = 3;			// 备件L1级审核
	public final static int backL2 = 4;			// 备件L2级审核
	public final static int formatCheck = 5;	// 表单格式化确认
	public final static int dispatch = 6;		// 故障单分发
	public final static int feedback = 7;		// 反馈
	public final static int phoneBack = 8;		// 电话确认
	
	// 审核状态
	public final static int confirm = 0;		// 待确认
	public final static int pass = 1;			// 审核通过/已确认
	public final static int nopass = 2;			// 审核没通过
	
	// 表单状态
	public final static int submit = 0;			// 表单提交状态
	public final static int l1check = 1;		// L1级审核通过
	public final static int l2check = 2;		// L2级审核通过
	public final static int formated = 3;		// 格式化确认通过
	public final static int dispatched = 4;		// 表单已经分发
	public final static int feedbacked = 5;		// 已经维修并反馈
	public final static int phonebacked = 6;	// 已经电话确认
	// 将审核信息插入到审核信息表中
	/**
	 * @函数名称: insertApproval
	 * @函数描述: 将审核信息表插入审核信息表中
	 * @输入参数: @param appInfo
	 * @输入参数: @return
	 * @输入参数: @throws FrssException
	 * @返回类型: long
	 * @throws
	 */
	public long insertApproval(FrssApprovalInfo appInfo) throws FrssException {
		long appId = 0;
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = BaseDAO.getSession();
			transaction = session.beginTransaction();
			session.save(appInfo);
			transaction.commit();
			session.close();
			
			appId = appInfo.getId();
		} catch (Exception e) {
			transaction.rollback();			
			///log	
			throw new FrssException(e);
		}
			
		return appId;
	}
	
	/**
	 * @函数名称: insertApproval
	 * @函数描述: 插入故障审核信息
	 * @输入参数: @param faultReport
	 * @输入参数: @param userId
	 * @输入参数: @return
	 * @输入参数: @throws FrssException
	 * @返回类型: long
	 * @throws
	 */
	public long insertApproval(FaultRepairReport faultReport, long userId) throws FrssException {
		long appId = 0;

		long faultId = faultReport.getFaultID();
		if(faultId<=0) {
			/// log 非法故障单
			return 0;
		}
		
		FrssApprovalInfo appInfo = null;
		if(faultReport.getL1Checker()==null && faultReport.getL2Checker()==null) {	// 故障上报 
			appInfo = new FrssApprovalInfo();
			appInfo.setType(1);
			appInfo.setStatus(0);
			appInfo.setKeyId(faultId);
			appInfo.setUserId(userId);		// 建立与当前提交/处理用户的联系
			
			appId = insertApproval(appInfo);
			
		} else if(faultReport.getL1Checker()!=null && faultReport.getL2Checker()==null) { // L1级审核，需要更新记录
			// 首先根据故障上报单号从审核表中查找上报记录
			appInfo = getApprovalInfo(1, faultId);
			
			// 更新记录
			StringBuffer hql = new StringBuffer();
			hql.append("update ").append(FrssApprovalInfo.class.getName()).append(" app ");
			hql.append("set app.checker='").append(faultReport.getL1Checker()).append("' ");
			hql.append("app.opinion='").append(faultReport.getL1CheckOpinion()).append("' ");
			hql.append("app.checkTime='").append(faultReport.getL1CheckTime()).append("' ");
			hql.append("app.status=").append(faultReport.getStatus());
			hql.append("app.userId=").append(userId);				// 更新审核用户信息
			hql.append(" where app.id=").append(appInfo.getId());
			
			// 然后更新数据库中的记录
			int ret = updateApprovalInfo(hql.toString());
			/// log
			
			appId = appInfo.getId();
			
		} else if(faultReport.getL1Checker()!=null && faultReport.getL2Checker()!=null) { // L2级审核，更新记录
			// 将L2级审核信息插入到审核表中(只有故障上报审核信息是对应两个的，其它皆为1对1)
			appInfo = new FrssApprovalInfo();
			appInfo.setChecker(faultReport.getL2Checker());
			appInfo.setOpinion(faultReport.getL2CheckOpinion());
			appInfo.setCheckTime(faultReport.getL2CheckTime());
			appInfo.setType(2);
			appInfo.setStatus(faultReport.getStatus());
			appInfo.setKeyId(faultId);
			appInfo.setUserId(userId);		// 更新审核用户信息
			
			// 插入到数据库中
			appId = insertApproval(appInfo);
		}				
		
		return appId;
	}

	/**
	 * @函数名称: queryApproval
	 * @函数描述: 根据表单类型、表单主键和状态查询审核信息
	 * @输入参数: @param type
	 * @输入参数: @param keyId
	 * @输入参数: @param status
	 * @输入参数: @return
	 * @返回类型: long
	 * @throws
	 */
	public FrssApprovalInfo queryApproval(int type, long keyId, int status) {
		long appId = 0;
		FrssApprovalInfo appInfo = null;
		StringBuffer hql = new StringBuffer();
		
		hql.append("from ").append(FrssApprovalInfo.class.getName()).append(" app ");
		hql.append("where app.type=").append(type);
		hql.append(" and app.keyId=").append(keyId);
		hql.append(" and app.status=").append(status);

		try {
			Session session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql.toString());
			List lstResult = objQuery.list();
			session.close();
			
			if (lstResult != null && lstResult.size() > 0) {
				appInfo = (FrssApprovalInfo)lstResult.get(0);
				if(appInfo!=null) {
					return appInfo;
				}			
			}
		} catch (Exception e) {
			/// log
		}
		
		return null;
	}
	
	/**
	 * @函数名称: updateApprovalInfo
	 * @函数描述: 更新审核信息表记录
	 * @输入参数: @param appInfo
	 * @输入参数: @return
	 * @返回类型: boolean
	 * @throws
	 */
	public boolean updateApprovalInfo(FrssApprovalInfo appInfo) {
		if(appInfo.getId()<=0) {
			/// log 非法记录
			System.out.println("确认信息记录非法!");
			return false;
		}
		
		Transaction trans = null;
		Session session = null;
		DateUtil dateUtil = new DateUtil();
		
		String hql = null;
		hql = "update " + FrssApprovalInfo.class.getName() + " app ";
		hql += "set app.checker='" + appInfo.getChecker() + "',";
		hql += "app.opinion='" + appInfo.getOpinion() + "',";
		hql += "app.checkTime=:date,";
		hql += "app.status=" + appInfo.getStatus() + ",";
		hql += "app.keyId=" + appInfo.getKeyId() + ",";
		hql += "app.userId=" + appInfo.getUserId() + " ";
		hql += "where app.id=" + appInfo.getId();
		
		try {	// 执行更新操作
			session = BaseDAO.getSession();
			trans = session.beginTransaction();
			Query update = session.createQuery(hql);
			update.setParameter("date", appInfo.getCheckTime());
			int ret = update.executeUpdate();
			trans.commit();
		} catch(Exception e) {
			trans.rollback();
			System.out.println("更新确认信息记录出现异常!");
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	/**
	 * @函数名称: queryApproval
	 * @函数描述: 根据faultId查询所有相关审核/确认信息--用来判断故障单进度
	 * @输入参数: @param faultId
	 * @输入参数: @return
	 * @返回类型: ArrayList<FrssApprovalInfo>
	 * @throws
	 */
	public ArrayList<FrssApprovalInfo> queryApproval(long faultId) {
		ArrayList arrApproval = null;
		Session session = null;
		
		String hql = "from " + FrssApprovalInfo.class.getName() + " app ";
		hql += "where app.keyId="+faultId;
		hql += " order by app.keyId";
		
		try{
			// 执行查询
			session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql);
			List lstResult =  objQuery.list();
			session.close();
			
			if (lstResult != null && lstResult.size() > 0) {
				arrApproval = new ArrayList<FrssApprovalInfo>();
				
				for(int num=0; num<lstResult.size();num++) {
					FrssApprovalInfo appInfo = (FrssApprovalInfo)lstResult.get(num);
					if(appInfo!=null) {
						arrApproval.add(appInfo);
					}
				}
			}
		} catch (Exception e) {
			/// log
			return null;
		}
		
		return arrApproval;
	}
	
	/**
	 * @函数名称: queryTheApproval
	 * @函数描述: 根据故障单编号查找最终审核状态 [zuow, 2012/05/04]
	 * @输入参数: @param faultId
	 * @输入参数: @return
	 * @返回类型: FrssApprovalInfo
	 * @throws
	 */
	public FrssApprovalInfo queryTheApproval(long faultId) {
		FrssApprovalInfo appInfo = null;
		Session session = null;
		
		// 首先查询最终的审核类型
		String hql1 = "select max(app.type) from " + FrssApprovalInfo.class.getName() + " app ";
		hql1 += "where app.keyId=" + faultId;
			
		int type = 0;
		
		try{
			// 执行查询
			session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql1);
			List<Object> lstResult =  objQuery.list();
			session.close();
			
			
			if (lstResult != null && lstResult.size() > 0) {
				type = Integer.parseInt(lstResult.get(0).toString());
			}	
			
		} catch (Exception e) {
			/// log
			return null;
		}	
		
		// 然后根据故障单编号和审核类型查找审核记录
		String hql2 = null;
		if(type>0) {
			hql2 = "from " + FrssApprovalInfo.class.getName() + " app ";
			hql2 += "where app.keyId=" + faultId;
			hql2 += " and app.type=" + type;
		} else {
			/// log
			return null;
		}
		
		try {
			session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql2);
			List lstResult =  objQuery.list();
			session.close();
			
			if(lstResult!=null && lstResult.size()>0) {
				appInfo = (FrssApprovalInfo)lstResult.get(0);
			}
		} catch (Exception e) {
			return null;
		}
			
		return appInfo;
	}
	
	/**
	 * @函数名称: queryApproval
	 * @函数描述: 查询故障审核信息
	 * @输入参数: @param startTime
	 * @输入参数: @param endTime
	 * @输入参数: @param userType
	 * @输入参数: @param reportType
	 * @输入参数: @param status
	 * @输入参数: @return
	 * @返回类型: ArrayList<FrssApprovalInfo>
	 * @throws
	 */
	public ArrayList<FrssApprovalInfo> queryApproval(Date startTime, Date endTime, int userType, int reportType, int status) {
		ArrayList arrApproval = null;
		Session session = null;
		
		String hql = null;
		hql = "select new map(app.id as id, app.checker as checker, app.opinion as opinion, app.checkTime as checkTime," +
				"app.type as type, app.status as status, app.keyId as keyId, app.userId as userId) ";
		hql += "from " + FrssApprovalInfo.class.getName() + " app, " + FrssUserInfo.class.getName() + " us ";
		hql += "where app.userId=us.id ";
		if(startTime!=null)
			hql += "and app.checkTime>:start ";
		if(endTime!=null)
			hql += "and app.checkTime<:end ";
		hql += " and app.type=" + reportType;
		hql += " and app.status=" + status;
		hql += " and us.userType=" + userType;
		hql += " order by app.id";
		
		try {
			session = BaseDAO.getSession();			
			Query objQuery = session.createQuery(hql);
			objQuery.setParameter("start", startTime);
			objQuery.setParameter("end", endTime);			
			List<HashMap> lstResult =  objQuery.list();
			session.close();	
			
			if (lstResult != null && lstResult.size() > 0) {
				arrApproval = new ArrayList<FrssApprovalInfo>();
				
				for(HashMap map : lstResult) {
					FrssApprovalInfo appInfo = new FrssApprovalInfo();
					if(map.get("id")!=null)
						appInfo.setId(Long.parseLong(map.get("id").toString()));
					if(map.get("checker")!=null)
						appInfo.setChecker(map.get("checker").toString());
					if(map.get("opinion")!=null)
						appInfo.setOpinion(map.get("opinion").toString());
					if(map.get("checkTime")!=null){						
						Date date = DateUtil.timeFormat.parse(map.get("checkTime").toString());
						appInfo.setCheckTime(date);
					}
					if(map.get("keyId")!=null) {
						appInfo.setKeyId(Long.parseLong(map.get("keyId").toString()));
					}
					if(map.get("userId")!=null)
						appInfo.setUserId(Long.parseLong(map.get("userId").toString()));
					
					appInfo.setType(reportType);
					appInfo.setStatus(status);
					
					arrApproval.add(appInfo);
				}
			}			
 		} catch (Exception e) {
 			/// log 
 			return null;
 		}
		
		return arrApproval;
	}
	
	/**
	 * @函数名称: queryApproval
	 * @函数描述: 查询某用户在某时间段内审核/提交的某级故障单 [zuow, 2012/05/06]
	 * @输入参数: @param startTime
	 * @输入参数: @param endTime
	 * @输入参数: @param flag		flag=1-->当前用户；		flag=2-->其直属下级
	 * @输入参数: @param userName
	 * @输入参数: @param reportType
	 * @输入参数: @param status
	 * @输入参数: @return
	 * @返回类型: ArrayList<FrssApprovalInfo>
	 * @throws
	 */
	public ArrayList<FrssApprovalInfo> queryApproval(Date startTime, Date endTime, int reportType, int status, int flag, String userName) {
		ArrayList arrApproval = null;
		Session session = null;
		
		String hql = null;
		hql = "select new map(app.id as id, app.checker as checker, app.opinion as opinion, app.checkTime as checkTime," +
				"app.type as type, app.status as status, app.keyId as keyId, app.userId as userId) ";
		hql += "from " + FrssApprovalInfo.class.getName() + " app, " + FrssUserInfo.class.getName() + " us ";
		hql += "where app.userId=us.id ";
		if(startTime!=null)
			hql += "and app.checkTime>:start ";
		if(endTime!=null)
			hql += "and app.checkTime<:end ";
		hql += " and app.type=" + reportType;
		hql += " and app.status=" + status;
		if(flag==1)	// 用户userName待审核/已审核记录
			hql += " and us.userName='" + userName + "' ";
		else if(flag==2)	// 用户userName直属下级待审核/已审核记录
			hql += " and us.createMan='" + userName + "' ";
		hql += "order by app.id";
		
		try {
			session = BaseDAO.getSession();			
			Query objQuery = session.createQuery(hql);
			objQuery.setParameter("start", startTime);
			objQuery.setParameter("end", endTime);			
			List<HashMap> lstResult =  objQuery.list();
			session.close();	
			
			if (lstResult != null && lstResult.size() > 0) {
				arrApproval = new ArrayList<FrssApprovalInfo>();
				
				for(HashMap map : lstResult) {
					FrssApprovalInfo appInfo = new FrssApprovalInfo();
					if(map.get("id")!=null)
						appInfo.setId(Long.parseLong(map.get("id").toString()));
					if(map.get("checker")!=null)
						appInfo.setChecker(map.get("checker").toString());
					if(map.get("opinion")!=null)
						appInfo.setOpinion(map.get("opinion").toString());
					if(map.get("checkTime")!=null){						
						Date date = DateUtil.timeFormat.parse(map.get("checkTime").toString());
						appInfo.setCheckTime(date);
					}
					if(map.get("keyId")!=null) {
						appInfo.setKeyId(Long.parseLong(map.get("keyId").toString()));
					}
					if(map.get("userId")!=null)
						appInfo.setUserId(Long.parseLong(map.get("userId").toString()));
					
					appInfo.setType(reportType);
					appInfo.setStatus(status);
					
					arrApproval.add(appInfo);
				}
			}			
 		} catch (Exception e) {
 			/// log 
 			return null;
 		}
		
		return arrApproval;		
	}
 
	/**
	 * @函数名称: queryApproval
	 * @函数描述: 查找团级用户userName上报的待处理/已处理表单
	 * @输入参数: @param startTime
	 * @输入参数: @param endTime
	 * @输入参数: @param userName
	 * @输入参数: @param reportType
	 * @输入参数: @param status
	 * @输入参数: @return
	 * @返回类型: ArrayList<FrssApprovalInfo>
	 * @throws
	 */
	public ArrayList<FrssApprovalInfo> queryApproval(Date startTime, Date endTime, String userName, int reportType, int status) {
		ArrayList arrApproval = null;
		Session session = null;
		
		String hql = null;
		hql = "select new map(app.id as id, app.checker as checker, app.opinion as opinion, app.checkTime as checkTime," +
				"app.type as type, app.status as status, app.keyId as keyId, app.userId as userId) ";
		hql += "from " + FrssApprovalInfo.class.getName() + " app, " + FrssFaultInfo.class.getName() + " fa ";
		hql += "where app.keyId=fa.id ";
		if(startTime!=null)
			hql += "and app.checkTime>:start ";
		if(endTime!=null)
			hql += "and app.checkTime<:end ";
		hql += " and app.type=" + reportType;
		hql += " and app.status=" + status;
		hql += " and fa.reporter='" + userName + "' ";
		hql += "order by app.id";
		
		try {
			session = BaseDAO.getSession();			
			Query objQuery = session.createQuery(hql);
			objQuery.setParameter("start", startTime);
			objQuery.setParameter("end", endTime);			
			List<HashMap> lstResult =  objQuery.list();
			session.close();	
			
			if (lstResult != null && lstResult.size() > 0) {
				arrApproval = new ArrayList<FrssApprovalInfo>();
				
				for(HashMap map : lstResult) {
					FrssApprovalInfo appInfo = new FrssApprovalInfo();
					if(map.get("id")!=null)
						appInfo.setId(Long.parseLong(map.get("id").toString()));
					if(map.get("checker")!=null)
						appInfo.setChecker(map.get("checker").toString());
					if(map.get("opinion")!=null)
						appInfo.setOpinion(map.get("opinion").toString());
					if(map.get("checkTime")!=null){						
						Date date = DateUtil.timeFormat.parse(map.get("checkTime").toString());
						appInfo.setCheckTime(date);
					}
					if(map.get("keyId")!=null) {
						appInfo.setKeyId(Long.parseLong(map.get("keyId").toString()));
					}
					if(map.get("userId")!=null)
						appInfo.setUserId(Long.parseLong(map.get("userId").toString()));
					
					appInfo.setType(reportType);
					appInfo.setStatus(status);
					
					arrApproval.add(appInfo);
				}
			}			
 		} catch (Exception e) {
 			/// log 
 			return null;
 		}
		
		return arrApproval;				
	}
	
	/**
	 * @函数名称: queryYear
	 * @函数描述: 根据用户类型查询故障单上报时间跨越年份或者是故障审核跨越的时间年份
	 * @输入参数: @param userType
	 * @输入参数: @return
	 * @返回类型: ArrayList<Integer>
	 * @throws
	 */
	public ArrayList<Integer> queryYear(int userType) {
		ArrayList<Integer> arrYear = null;
		String hql = null;
		
		int type = 0;	// 审核类型
		if(userType==UserDAO.Military)
			type = ApprovalDAO.faultL2;
		else if(userType==UserDAO.GroupArmy)
			type = ApprovalDAO.faultL1;
		
		if(userType==UserDAO.Regiment) {		// 如果是团级用户，就统计故障上报的年份
			hql = "select min(to_char(fa.reportTime, 'yyyy')), max(to_char(fa.reportTime, 'yyyy')) ";
			hql += "from " + FrssFaultInfo.class.getName() + " fa ";
		} else {		// 如果是其他级别用户，就统计故障审核的年份
			hql = "select min(app.checkTime), max(app.checkTime) ";
			hql += "from " + FrssApprovalInfo.class.getName() + " app ";
			hql += "where app.type=" + type;
		}
		
		try {
			Session session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql);
			List<Object[]> lstResult =  objQuery.list();
			session.close();
			
			if (lstResult != null && lstResult.size() > 0) {
				arrYear = new ArrayList<Integer>();
				
				int minYear = 0;
				int maxYear = 0;
				Object[] arrDate = lstResult.get(0);
				if(arrDate!=null) {
					minYear = Integer.parseInt(arrDate[0].toString());
					maxYear = Integer.parseInt(arrDate[1].toString());
				}
				
				for(int num=minYear;num<=maxYear;num++) {
					arrYear.add(num);
				}
			}
			
		} catch (Exception e) {
			/// log
			return null;
		}
		
		return arrYear;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////
	// 插入审核信息，审核者信息入库时需与用户信息表建立连接，用户信息从当前登录用户获取，所以审核者和用户是一一对应的
	public long insertApproval(BackupApplication backApp, int level) throws FrssException {
		long approvalID = 0;
		FrssApprovalInfo insertApproval = new FrssApprovalInfo();
		
		if(level==1) {		// L1级审核信息
			insertApproval.setChecker(backApp.getL1Checker());
			insertApproval.setCheckTime(backApp.getL1CheckTime());
			insertApproval.setOpinion(backApp.getL1CheckOpinion());
			int checked = backApp.getL1CheckOpinion()==null?1:2;
			insertApproval.setStatus(5);
			insertApproval.setKeyId(backApp.getBackID());
			/// 暂将userid设置为0，需要从用户表中查找
			insertApproval.setUserId((long)0);
		} else if(level==2) {		// L2级审核信息
			insertApproval.setChecker(backApp.getL2Checker());
			insertApproval.setCheckTime(backApp.getL2CheckTime());
			insertApproval.setOpinion(backApp.getL2CheckOpinion());
			int checked = backApp.getL2CheckOpinion()==null?1:2;
			insertApproval.setStatus(5);
			insertApproval.setKeyId(backApp.getBackID());
			/// 先将userid设置为0
			insertApproval.setUserId((long)0);			
		}
		
		try {
			// 入库
			Session session = BaseDAO.getSession();
			Transaction transaction = session.beginTransaction();
			session.save(insertApproval);
			transaction.commit();
			session.close();
			
			approvalID = insertApproval.getId();			
		} catch (Exception e) {
			throw new FrssException(e);			
		}
		
		return approvalID;
	}
	
	// 通过userId获取审核者Id(注意approvalInfo和userInfo表记录是一一对应的)
	public long getApprovalId(long userId) {
		long approvalId = 0;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ap.id from "); 
		sql.append(FrssApprovalInfo.class.getName()).append(" ap ");
		sql.append("where ap.userId=").append(userId);
		
		try {
			Session session = BaseDAO.getSession();
			Query objQuery = session.createQuery(sql.toString());
			List lstResult = objQuery.list();
			session.close();
			
			if (lstResult != null && lstResult.size() > 0) {
				FrssApprovalInfo queryObj = (FrssApprovalInfo)lstResult.get(0);
				if(queryObj!=null) {
					approvalId = queryObj.getId();
				}			
			}
		} catch (Exception e) {
			/// log
		}
		
		return approvalId;
	}

	// 根据type和keyId来查询FrssApprovalInfo记录
	public FrssApprovalInfo getApprovalInfo(int type, long keyId) {
		FrssApprovalInfo appInfo = null;
		Session session = null;
		Transaction transaction = null;
		StringBuffer hql = new StringBuffer();
		
		hql.append("from "); 
		hql.append(FrssApprovalInfo.class.getName()).append(" ap ");
		hql.append("where ap.type='").append(type).append("' ");
		hql.append("and ap.keyId='").append(keyId).append("'");
		
		try {
			session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql.toString());
			List lstResult = objQuery.list();
			session.close();
			
			if(lstResult!=null && lstResult.size()>0) {
				appInfo = (FrssApprovalInfo)lstResult.get(0);
			} else {
				/// log 数据有误
			}
			
		} catch (Exception e) {
			/// log  查询出现异常
		}
		
		return appInfo;
	}
	
	// 更新FrssApprovalInfo记录
	public int updateApprovalInfo(String hql) throws FrssException {
		int ret = 0;		// 0表示更新成功，!0表示失败
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = BaseDAO.getSession();
			transaction = session.beginTransaction();
			Query queryUpdate = session.createQuery(hql);
			ret = queryUpdate.executeUpdate();
			transaction.commit();			
		} catch (Exception e) {
			/// log
			throw new FrssException(e);
		}
		
		return ret;
	}

	/**
	 * @函数名称: getStep
	 * @函数描述: 根据当前的表单类型和状态，判断当前步骤
	 * @输入参数: @param type
	 * @输入参数: @param status
	 * @输入参数: @return
	 * @返回类型: int
	 * @throws
	 */
	public static int getStep(int type, int status) {
		int step = 0;
		
		switch(type) {
		case ApprovalDAO.faultL1:
			if(status==ApprovalDAO.confirm)
				step = ApprovalDAO.submit;
			else if(status==ApprovalDAO.pass || status==ApprovalDAO.nopass)
				step = ApprovalDAO.l1check;
			break;
		case ApprovalDAO.faultL2:
			if(status==ApprovalDAO.confirm) 
				step = ApprovalDAO.l1check;
			else if(status==ApprovalDAO.pass || status==ApprovalDAO.nopass) 
				step = ApprovalDAO.l2check;
			break;
		case ApprovalDAO.backL1:
			step = ApprovalDAO.l1check;
			break;
		case ApprovalDAO.backL2:
			if(status==ApprovalDAO.confirm)
				step = ApprovalDAO.l1check;
			else if(status==ApprovalDAO.pass || status==ApprovalDAO.nopass)
				step = ApprovalDAO.l2check;
			break;
		case ApprovalDAO.formatCheck:
			if(status==ApprovalDAO.confirm)
				step = ApprovalDAO.l2check;
			else if(status==ApprovalDAO.pass || status==ApprovalDAO.nopass)
				step = ApprovalDAO.formated;
		case ApprovalDAO.dispatch:
			if(status==ApprovalDAO.confirm)
				step = ApprovalDAO.formated;
			else if(status==ApprovalDAO.pass || status==ApprovalDAO.nopass)
				step = ApprovalDAO.dispatched;
			break;
		case ApprovalDAO.feedback:
			if(status==ApprovalDAO.confirm) 
				step = ApprovalDAO.dispatched;
			else if(status==ApprovalDAO.pass || status==ApprovalDAO.nopass)
				step = ApprovalDAO.feedbacked;
			break;
		case ApprovalDAO.phoneBack:
			if(status==ApprovalDAO.confirm)
				step = ApprovalDAO.feedbacked;
			else if(status==ApprovalDAO.pass || status==ApprovalDAO.nopass)
				step = ApprovalDAO.phonebacked;
		}
		
		return step;
	}
}
