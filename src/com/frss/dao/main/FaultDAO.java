package com.frss.dao.main;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.frss.dao.util.BaseDAO;
import com.frss.model.main.FaultRepairReport;
import com.frss.model.main.UserInfo;
import com.frss.model.mapping.FrssApprovalInfo;
import com.frss.model.mapping.FrssAudioInfo;
import com.frss.model.mapping.FrssEquipmentInfo;
import com.frss.model.mapping.FrssFaultInfo;
import com.frss.model.mapping.FrssUserInfo;
import com.frss.util.DateUtil;
import com.frss.util.FrssException;

public class FaultDAO {
	private Log log = LogFactory.getLog(SecurityDAO.class);

	public final static long threshold = 200000000000000000L; 			// 故障和备件记录的分隔点
	
	/**
	 * @函数名称: insertFault
	 * @函数描述: 将故障信息数据插入数据库中
	 * @输入参数: @param faultInfo
	 * @输入参数: @return
	 * @输入参数: @throws FrssException
	 * @返回类型: long
	 * @throws
	 */
	private long insertFault(FrssFaultInfo faultInfo) throws FrssException {
		long faultId = 0;
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = BaseDAO.getSession();
			transaction = session.beginTransaction();
			session.save(faultInfo);
			transaction.commit();
			session.close();		
		} catch (Exception e) {
			transaction.rollback();			
//			log.error("插入装备维修上报表出现异常");
			throw new FrssException(e);
		}

		faultId = faultInfo.getId();
		
		return faultId;
	}
	
	/**
	 * @函数名称: insertFault
	 * @函数描述: 插入故障维修上报信息：首先插入故障信息表；然后查找装备表，如果没有，就插入
	 * @输入参数: @param faultReport
	 * @输入参数: @param userId
	 * @输入参数: @return
	 * @输入参数: @throws FrssException
	 * @返回类型: long
	 * @throws
	 */
	public long insertFault(FaultRepairReport faultReport, long userId) throws FrssException {
		long faultId = 0;
		try {
//			log.debug("增加装备信息:");
			long equipId = 0;
			
			// 先在准备表中查找是否有这个记录--根据装备型号和编号进行查询，如果没有就进行插入并生成编号
			EquipDAO equipDAO = new EquipDAO();
			FrssEquipmentInfo equipInfo = equipDAO.getEquipForTypeAndNumber(faultReport.getEquipType(), faultReport.getEquipNumber());
			if(equipInfo==null) {	// 将新纪录插入到装备表中
				equipId = equipDAO.insertEquip(faultReport);
			} else {
				equipId = equipInfo.getId();
			}
			if(equipId<=0) {
				/// 故障单格式错误
				return 0;
			}

//			log.debug("增加故障信息:");
			FrssFaultInfo objFaultInfo = new FrssFaultInfo();
			objFaultInfo.setId(faultReport.getFaultID());			// id手动进行添加 [zuow,2012/04/10]
			objFaultInfo.setAmount(faultReport.getFaultAmount());
			objFaultInfo.setFaultDesp(faultReport.getFaultDesp());
			objFaultInfo.setFaultTime(faultReport.getFaultTime());
			objFaultInfo.setPreProcess(faultReport.getPreprocess());
			objFaultInfo.setFrequency(faultReport.getFaultFrequency());
			objFaultInfo.setFaultPlace(faultReport.getFaultPlace());
			objFaultInfo.setReporter(faultReport.getReporter());
			objFaultInfo.setContactWay(faultReport.getReporterContact());
			objFaultInfo.setReportTime(faultReport.getReportTime());
			objFaultInfo.setPhotos(faultReport.getFaultPhotos());
			objFaultInfo.setPhotoName(faultReport.getPhotoName());		// 增加一个图片名字段
			/// 故障原因在反馈信息表录入的时候进行更新
			objFaultInfo.setEquipId(equipId);	
			
			/// 将故障信息插入表中
			faultId = insertFault(objFaultInfo);
			if(faultId<=0) {
//				log.info("插入故障信息失败");
				return 0;
			}
			
//			log.info("插入故障信息成功");						
		} catch (Exception e) {	
//			log.error("插入装备维修上报表失败");
			throw new FrssException(e);
		}
		
		// 插入提交审核信息
		ApprovalDAO appDAO = new ApprovalDAO();
		FrssApprovalInfo appInfo = new FrssApprovalInfo();
		if(appDAO==null || appInfo==null) {
			/// log 警告
			return faultId;
		}
		appInfo.setKeyId(faultId);
		appInfo.setType(ApprovalDAO.faultL1);
		appInfo.setUserId(userId);
		appInfo.setStatus(ApprovalDAO.confirm);
		/// 提交时也需要填入提交者和提交时间 [zuow, 2012/03/31]
		appInfo.setChecker(faultReport.getReporter());		// 故障提交者
//		DateUtil dateUtil = new DateUtil();
		appInfo.setCheckTime(DateUtil.getCurTime());
		
		long appId = appDAO.insertApproval(appInfo);
		if(appId<=0) {
			/// log 插入提交审核信息失败			
		}
		
		return faultId;
	}
	
	/**
	 * @函数名称: updateFault
	 * @函数描述: 更新故障单
	 * @输入参数: @param faultReport
	 * @输入参数: @param userId
	 * @输入参数: @return
	 * @输入参数: @throws FrssException
	 * @返回类型: boolean
	 * @throws
	 */
	public boolean updateFault(FaultRepairReport faultReport, long userId) throws FrssException {
		boolean bFlag = false;
		Session session = null;
		Transaction transaction = null;	
		
		try {
			log.debug("插入或者更新L1级审核信息:");
			// 如果在L1级审核者姓名不为空时，就先查询审核表，如果有记录就进行更新，如果没有就插入
			StringBuffer sql = new StringBuffer();
			if ((faultReport.getL1Checker())!=null) {
				sql.append("select app.id, app.checker, app.checked, app.opinion," +
						"app.checktime, app.id_frss_fault_info");
				sql.append("  from");
				sql.append(FrssApprovalInfo.class.getName()).append("  app ");
				sql.append("where app.id_frss_fault_info = '").append(
						faultReport.getFaultID()).append("'");
				sql.append(" and app.checker = '").append(
						faultReport.getL1Checker()).append("'");
				
				session = BaseDAO.getSession();
				Query objQuery = session.createQuery(sql.toString());
				List lstResult = objQuery.list();
				session.close();
				if (lstResult == null || lstResult.size() == 0) {	// 插入审核信息表
					FrssApprovalInfo objApproval = new FrssApprovalInfo();
					objApproval.setChecker(faultReport.getL1Checker());
					objApproval.setCheckTime(faultReport.getL1CheckTime());
					objApproval.setOpinion(faultReport.getL1CheckOpinion());
					objApproval.setStatus(2);	// 故障L1级上报阶段
					objApproval.setKeyId(faultReport.getFaultID());
					/// 如何确认审核是否通过？
					
					// 插入L1级审核信息
					session = BaseDAO.getSession();
					transaction = session.beginTransaction();
					session.save(objApproval);
					transaction.commit();
					session.close();	
					
					bFlag = true;
					log.info("插入L1级审核信息成功");					
				}				
			}
			
			log.debug("插入或更新L2级审核信息:");
			sql.setLength(0);		// 清空sql
			if ((faultReport.getL2Checker())!=null) {
				sql.append("select app.id, app.checker, app.checked, app.opinion," +
						"app.checktime, app.id_frss_fault_info");
				sql.append("  from");
				sql.append(FrssApprovalInfo.class.getName()).append("  app ");
				sql.append("where app.id_frss_fault_info = '").append(
						faultReport.getFaultID()).append("'");
				sql.append(" and app.checker = '").append(
						faultReport.getL2Checker()).append("'");
				
				session = BaseDAO.getSession();
				Query objQuery = session.createQuery(sql.toString());
				List lstResult = objQuery.list();
				session.close();
				if (lstResult != null && lstResult.size() > 0) {	// 插入审核信息表
					FrssApprovalInfo objApproval = new FrssApprovalInfo();
					objApproval.setChecker(faultReport.getL2Checker());
					objApproval.setCheckTime(faultReport.getL2CheckTime());
					objApproval.setOpinion(faultReport.getL2CheckOpinion());
					objApproval.setStatus(3);	// 故障L2级上报阶段
					objApproval.setKeyId(faultReport.getFaultID());
					/// 如何确认审核是否通过？
					
					// 插入L2级审核信息
					session = BaseDAO.getSession();
					transaction = session.beginTransaction();
					session.save(objApproval);
					transaction.commit();
					session.close();	
					
					bFlag = true;
					log.info("插入L2级审核信息成功");	
				}					
			}
			
		} catch (Exception e) {
			log.error("插入审核信息失败");
			bFlag = false;
			transaction.rollback();			
			throw new FrssException(e);
		}
		
		return bFlag;
	}
	
	/**
	 * @函数名称: updateFault
	 * @函数描述: 更新故障表记录
	 * @输入参数: @param faultInfo
	 * @输入参数: @return
	 * @返回类型: boolean
	 * @throws
	 */
	public boolean updateFault(FrssFaultInfo faultInfo) {
		if(faultInfo.getId()<=0) {
			return false;
		}
		
		String hql = null;
		hql = "update " +FrssFaultInfo.class.getName() + " fa ";
		hql += "set fa.amount=" + faultInfo.getAmount() + ",";
		hql += "fa.faultDesp='" + faultInfo.getFaultDesp() + "',";
		hql += "fa.faultTime=:faulttime,";
		hql += "fa.preProcess='" + faultInfo.getPreProcess() + "',";
		hql += "fa.frequency=" + faultInfo.getFrequency() + ",";
		hql += "fa.faultPlace='" + faultInfo.getFaultPlace() + "',";
		hql += "fa.reporter='" + faultInfo.getReporter() + "',";
		hql += "fa.contactWay='" + faultInfo.getContactWay() + "',";
		hql += "fa.reportTime=:reporttime,";
		hql += "fa.photoName='" + faultInfo.getPhotoName() + "',";
		hql += "fa.cause='" + faultInfo.getCause() + "', ";
		hql += "fa.equipId=" + faultInfo.getEquipId() + " ";
		hql += "where fa.id=" + faultInfo.getId();
		
		Session session = null;
		Transaction trans = null;
		try {	// 执行更新操作
			session = BaseDAO.getSession();
			trans = session.beginTransaction();
			Query update = session.createQuery(hql);
			update.setParameter("faulttime", faultInfo.getFaultTime());
			update.setParameter("reporttime", faultInfo.getReportTime());
			int ret = update.executeUpdate();
			trans.commit();
			
		} catch(Exception e) {
			trans.rollback();
			return false;
		}
		
		return true;
	}
	
	/**
	 * @throws FrssException 
	 * @函数名称: delFaultById
	 * @函数描述: 根据故障单号删除故障表中的故障单，只可能是在插入审核信息失败才会导致删除故障信息，所以不需要更新审核信息表
	 * @输入参数: @param faultId
	 * @输入参数: @return
	 * @返回类型: boolean
	 * @throws
	 */
	public boolean delFaultById(long faultId) throws FrssException {
		Session session = null;		
		Transaction transaction = null;
		
		StringBuffer hql = new StringBuffer();
		hql.append("delete from "); 
		hql.append(FrssFaultInfo.class.getName()).append(" fa ");
		hql.append("where fa.id=").append(faultId);
		
		try {
			session = BaseDAO.getSession();
			transaction = session.beginTransaction();
			session.createQuery(hql.toString());
			transaction.commit();
			session.close();	
		} catch(Exception e) {
			transaction.rollback();	
			throw new FrssException(e);
		}
		
		return true;
	}
	
	/**
	 * @函数名称: queryFault
	 * @函数描述: 根据开始/截止时间、处理用户的类型、审核级别和审核状态查询故障信息
	 * @输入参数: @param startTime
	 * @输入参数: @param endTime
	 * @输入参数: @param userType，用户类型
	 * @输入参数: @param type，1表示1级审核，2表示2级审核
	 * @输入参数: @param status，0表示所有故障单，1表示审核通过故障单，2表示审核未通过故障单
	 * @输入参数: @return
	 * @返回类型: ArrayList<FaultRepairReport>
	 * @throws
	 */
	public ArrayList<FaultRepairReport> queryFault(Date startTime, Date endTime, int userType, int reportType, int status) {
		ArrayList<FaultRepairReport> arrFault = null;
		Session session = null;
		Date date = null;
		// 安全检查
		if(reportType<ApprovalDAO.faultL1 || reportType>ApprovalDAO.phoneBack) {
			// log
			return null;
		}
		if(status<ApprovalDAO.confirm || status>ApprovalDAO.nopass) {
			// log
			return null;
		}
						
		// 组织hql语句		
		String hql = null;
//		hql = "select fa.id, eq.equipType, eq.equipName, eq.equipNumber, fa.amount, fa.faultDesp,fa.faultTime, fa.preProcess, fa.frequency, fa.faultPlace," +
//		"eq.department, eq.operater, fa.reporter, fa.contactWay, fa.reportTime, fa.photos, app.checker, app.checkTime, app.opinion, app.status ";
//		hql = "select new map(fa.id as faultId, eq.equipType as equipType, eq.equipName as equipName, eq.equipNumber as equipNumber, fa.amount as amount," +  
//			 "eq.operater as operater, fa.reporter as reporter,fa.reportTime as reportTime," +
////			 "fa.faultDesp as faultDesp, fa.faultTime as faultTime, fa.preProcess as preProcess, fa.frequency as frequency, fa.faultPlace as faultPlace," +
////			 "eq.department as department, fa.contactWay as contactWay, fa.photos as photos," + 
////			 " app.checker as checker, app.checkTime as checkTime, app.opinion as opinion, app.status as status" +
//			")";
//		hql += "from FrssFaultInfo fa,  FrssApprovalInfo app, FrssUserInfo us, FrssEquipmentInfo eq "; 
//		hql += "where fa.id=app.keyId ";
//		if(startTime!=null)
//			hql += "and app.checkTime>:start ";
////			hql += "and app.checkTime>to_date('2012/03/01 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
//		if(endTime!=null)
//			hql += "and app.checkTime<:end ";
////			hql += "and app.checkTime<to_date('2012/04/01 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
//		if(reportType==ApprovalDAO.faultL1 || reportType==ApprovalDAO.faultL2)
//			hql += "and app.type=" + reportType + " ";
//		else 
//			hql += "and app.type<" + ApprovalDAO.backL1 + " ";
//		if(status==ApprovalDAO.confirm || status==ApprovalDAO.pass || status==ApprovalDAO.nopass)
//			hql += "and app.status=" + status;
//		hql += " and app.id<" + threshold + " ";
//		if(userType>UserDAO.Super&&userType<=UserDAO.Formater)
//			hql += "and app.userId=us.id and us.userType=" + userType + " ";
//		hql += "and fa.equipId=eq.id ";
//		hql += "order by fa.id";
		
		try{
			// 首先查询审核信息
			ApprovalDAO appDAO = new ApprovalDAO();	
			ArrayList<FrssApprovalInfo> arrApproval = appDAO.queryApproval(startTime, endTime, userType, reportType, status);
			if(arrApproval==null) {
				// log
				return null;
			}
			
			arrFault = new ArrayList<FaultRepairReport>();
			// 根据审核信息查询故障相关信息
			for (FrssApprovalInfo appInfo : arrApproval) {
				long faultId = appInfo.getKeyId();
				
				hql = "select new map(fa.id as faultId, eq.equipType as equipType, eq.equipName as equipName, eq.equipNumber as equipNumber, fa.amount as amount," +  
				 "eq.operater as operater, fa.reporter as reporter,fa.reportTime as reportTime) ";
				hql += "from FrssFaultInfo fa,  FrssEquipmentInfo eq "; 
				hql += "where fa.equipId=eq.id ";
				hql += "and fa.id=" + faultId;
				hql += " order by fa.id";
				
				// 执行查询
				session = BaseDAO.getSession();
				Query objQuery = session.createQuery(hql);
				List<HashMap> lstResult =  objQuery.list();
				session.close();
				
				// 获取信息				
				if(lstResult!=null && lstResult.size()>0) {
					HashMap map = lstResult.get(0);
					FaultRepairReport faultReport = new FaultRepairReport();
					faultReport.setFaultID(faultId);
					if(map.get("equipType")!=null)
						faultReport.setEquipType(map.get("equipType").toString());
					if(map.get("equipName")!=null)
						faultReport.setEquipName(map.get("equipName").toString());
					if(map.get("equipNumber")!=null)
						faultReport.setEquipNumber(map.get("equipNumber").toString());
					if(map.get("amount")!=null)
						faultReport.setfaultAmount(Integer.parseInt(map.get("amount").toString()));
					if(map.get("operater")!=null)
						faultReport.setEquipOperater(map.get("operater").toString());
					if(map.get("reporter")!=null)
						faultReport.setReporter(map.get("reporter").toString());
					if(map.get("reportTime")!=null) {
						date = DateUtil.timeFormat.parse(map.get("reportTime").toString());
						faultReport.setReportTime(date);
					}
					
					faultReport.setStatus(status);
					// 根据故障单编号查找最终审核状态 [zuow, 2012/05/07]
					FrssApprovalInfo newApp = appDAO.queryTheApproval(faultId);
					int type = 0;
					status = 0;
					if(newApp!=null) {
						type = newApp.getType();
						status = newApp.getStatus();
					} else {
						type = appInfo.getType();
						status = appInfo.getStatus();
					}
					
//					int step = ApprovalDAO.getStep(type, status);
					faultReport.setStep(type);	
					faultReport.setReportType(type);
					
					arrFault.add(faultReport);
				}
			}
		} catch(Exception e) {
			/// log 查询出现异常
			return null;
		}
		
		return arrFault;
	}	
	
	/**
	 * @函数名称: queryFault
	 * @函数描述: 根据开始/截止时间、用户名、审核级别和审核状态查询故障信息
	 * @输入参数: @param startTime
	 * @输入参数: @param endTime
	 * @输入参数: @param flag		-- flag=0, reporter; flag=1, userName; flag=2, createMan
	 * @输入参数: @param userInfo
	 * @输入参数: @param reportType
	 * @输入参数: @param status
	 * @输入参数: @return
	 * @返回类型: ArrayList<FaultRepairReport>
	 * @throws
	 */
	public ArrayList<FaultRepairReport> queryFault(Date startTime, Date endTime, int reportType, int status, int flag, String userName) {
		ArrayList<FaultRepairReport> arrFault = null;
		Session session = null;
		Date date = null;
		// 安全检查
		if(reportType<ApprovalDAO.faultL1 || reportType>ApprovalDAO.phoneBack) {
			// log
			return null;
		}
		if(status<ApprovalDAO.confirm || status>ApprovalDAO.nopass) {
			// log
			return null;
		}
						
		// 组织hql语句		
		String hql = null;
		
		try{
			// 首先查询审核信息
			ApprovalDAO appDAO = new ApprovalDAO();	
			ArrayList<FrssApprovalInfo> arrApproval = null;
			if(flag==0) { 
				arrApproval = appDAO.queryApproval(startTime, endTime, userName, reportType, status);
			} else if(flag==1||flag==2)	{
				arrApproval = appDAO.queryApproval(startTime, endTime, reportType, status, flag, userName);
			}
			if(arrApproval==null) {
				// log
				return null;
			}
			
			arrFault = new ArrayList<FaultRepairReport>();
			// 根据审核信息查询故障相关信息
			for (FrssApprovalInfo appInfo : arrApproval) {
				long faultId = appInfo.getKeyId();
				
				hql = "select new map(fa.id as faultId, eq.equipType as equipType, eq.equipName as equipName, eq.equipNumber as equipNumber, fa.amount as amount," +  
				 "eq.operater as operater, fa.reporter as reporter,fa.reportTime as reportTime) ";
				hql += "from FrssFaultInfo fa,  FrssEquipmentInfo eq "; 
				hql += "where fa.equipId=eq.id ";
				hql += "and fa.id=" + faultId;
				hql += " order by fa.id";
				
				// 执行查询
				session = BaseDAO.getSession();
				Query objQuery = session.createQuery(hql);
				List<HashMap> lstResult =  objQuery.list();
				session.close();
				
				// 获取信息				
				if(lstResult!=null && lstResult.size()>0) {
					HashMap map = lstResult.get(0);
					FaultRepairReport faultReport = new FaultRepairReport();
					faultReport.setFaultID(faultId);
					if(map.get("equipType")!=null)
						faultReport.setEquipType(map.get("equipType").toString());
					if(map.get("equipName")!=null)
						faultReport.setEquipName(map.get("equipName").toString());
					if(map.get("equipNumber")!=null)
						faultReport.setEquipNumber(map.get("equipNumber").toString());
					if(map.get("amount")!=null)
						faultReport.setfaultAmount(Integer.parseInt(map.get("amount").toString()));
					if(map.get("operater")!=null)
						faultReport.setEquipOperater(map.get("operater").toString());
					if(map.get("reporter")!=null)
						faultReport.setReporter(map.get("reporter").toString());
					if(map.get("reportTime")!=null) {
						date = DateUtil.timeFormat.parse(map.get("reportTime").toString());
						faultReport.setReportTime(date);
					}
					
					// 根据故障单编号查找最终审核状态 [zuow, 2012/05/07]
					FrssApprovalInfo newApp = appDAO.queryTheApproval(faultId);
					int type = 0;
					int state = 0;
					if(newApp!=null) {
						type = newApp.getType();
						state = newApp.getStatus();
					} else {
						type = appInfo.getType();
						state = appInfo.getStatus();
					}
					
					faultReport.setStep(type);	
					if(state==ApprovalDAO.nopass)		// 如果最终状态是不通过则将所有状态设置为不通过 [zuow, 2012/05/20]
						faultReport.setStatus(state);
					else
						faultReport.setStatus(status);
					faultReport.setReportType(type);
					
					arrFault.add(faultReport);
				}
			}
		} catch(Exception e) {
			/// log 查询出现异常
			return null;
		}
		
		return arrFault;		
	}
	
	/**
	 * @函数名称: queryFault
	 * @函数描述: 查询故障
	 * @输入参数: @param startTime
	 * @输入参数: @param endTime
	 * @输入参数: @param reportType
	 * @输入参数: @param status
	 * @输入参数: @param reporter
	 * @输入参数: @return
	 * @返回类型: ArrayList<FaultRepairReport>
	 * @throws
	 */
	public ArrayList<FaultRepairReport> queryFault(Date startTime, Date endTime, int reportType, int status, String reporter) {
		ArrayList<FaultRepairReport> arrFault = null;
		
		String hql = "select new map(fa.id as faultId, eq.equipType as equipType, eq.equipName as equipName, eq.equipNumber as equipNumber, fa.amount as amount," +  
				 "eq.operater as operater, fa.reporter as reporter,fa.reportTime as reportTime, app.type as type, app.status as status ) ";
		hql += "from " + FrssFaultInfo.class.getName() + " fa," + FrssEquipmentInfo.class.getName() + " eq," + FrssApprovalInfo.class.getName() + " app ";
		hql += "where fa.equipId=eq.id and fa.id=app.keyId ";
		if(startTime!=null)
			hql += " and fa.reportTime>:start ";
		if(endTime!=null)
			hql += " and fa.reportTime<:end ";
		if(reporter!=null&&!(reporter.equals("")))
			hql += " and fa.reporter='" + reporter + "' ";
		hql += "and app.type=" + reportType + " ";
		hql += "and app.status=" + status + " ";
		hql += "order by fa.id";
		
		try{
			Session session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql);
			if(startTime!=null)
				objQuery.setParameter("start", startTime);
			if(endTime!=null)
				objQuery.setParameter("end", endTime);
			List<HashMap> lstResult = objQuery.list();
			session.close();
			
			long faultId = 0;
			if (lstResult != null && lstResult.size() > 0) {
				arrFault = new ArrayList<FaultRepairReport>();
				
				ApprovalDAO appDAO = new ApprovalDAO();
				
				for (HashMap map : lstResult) {
					FaultRepairReport faultReport = new FaultRepairReport();
					if(map.get("faultId")!=null) {
						faultId = Long.parseLong(map.get("faultId").toString());
						if(faultId<=0)	// 对故障单序列号进行检查
							continue;
						
						faultReport.setFaultID(faultId);
					}
					if(map.get("equipType")!=null)
						faultReport.setEquipType(map.get("equipType").toString());
					if(map.get("equipName")!=null)
						faultReport.setEquipName(map.get("equipName").toString());
					if(map.get("equipNumber")!=null)
						faultReport.setEquipNumber(map.get("equipNumber").toString());
					if(map.get("amount")!=null)
						faultReport.setfaultAmount(Integer.parseInt(map.get("amount").toString()));
					if(map.get("operater")!=null)
						faultReport.setEquipOperater(map.get("operater").toString());
					if(map.get("reporter")!=null)
						faultReport.setReporter(map.get("reporter").toString());
					if(map.get("reportTime")!=null) {
						DateUtil dateUtil = new DateUtil();
						faultReport.setReportTime(dateUtil.getTheTime(map.get("reportTime").toString()));							
					}
					if(map.get("type")!=null) { 
						faultReport.setReportType(Integer.parseInt(map.get("type").toString()));
						faultReport.setStep(Integer.parseInt(map.get("type").toString()));
					}
					if(map.get("status")!=null)
						faultReport.setStatus(Integer.parseInt(map.get("status").toString()));
					
					// 根据故障单编号查找最终审核状态 [zuow, 2012/05/07]
					FrssApprovalInfo newApp = appDAO.queryTheApproval(faultId);
					int type = 0;
					int state = 0;
					if(newApp!=null) {
						type = newApp.getType();
						state = newApp.getStatus();
					}
					
					faultReport.setStep(type);	
					faultReport.setReportType(type);
					faultReport.setStatus(status);					
					
					arrFault.add(faultReport);
				}
			}
		} catch(Exception e) {
			/// log 查询出现异常
			return null;
		}		
		
		return arrFault;		
	}
	
	/**
	 * @函数名称: queryFault
	 * @函数描述: 根据起止时间、装备类型/名称/编号查询故障相关信息
	 * @输入参数: @param startTime，可为空
	 * @输入参数: @param endTime，可为空
	 * @输入参数: @param equipType，可为空
	 * @输入参数: @param equipName，可为空
	 * @输入参数: @param equipNumber，可为空，但这三个装备信息至少有一个存在
	 * @输入参数: @return
	 * @返回类型: ArrayList<FaultRepairReport>
	 * @throws
	 */
	public ArrayList<FaultRepairReport> queryFault(Date startTime, Date endTime, String equipType, String equipName, String equipNumber,
			String reporter, String department, int userType) {
		ArrayList<FaultRepairReport> arrFault = null;
		Session session = null;
		Date date = null;
		// 安全检查
		if(startTime==null&&endTime==null&&equipType==null&&equipName==null&&equipNumber==null&&reporter==null&&department==null){
			return null;
		}
		
		// 首先通过故障上报时间和装备信息查询所有的故障
		String hql = "select new map(fa.id as faultId, eq.equipType as equipType, eq.equipName as equipName, eq.equipNumber as equipNumber, fa.amount as amount," +  
				 "eq.operater as operater, fa.reporter as reporter,fa.reportTime as reportTime) ";
		hql += "from " + FrssFaultInfo.class.getName() + " fa," + FrssEquipmentInfo.class.getName() + " eq ";
		if(userType!=UserDAO.Regiment)		// 查询时区分团级用户和其它用户
			hql += ", " + FrssApprovalInfo.class.getName() + " app ";
		hql += "where fa.equipId=eq.id";
		if(userType!=UserDAO.Regiment)
			hql += " and fa.id=app.keyId ";
		if(startTime!=null) {
			if(userType!=UserDAO.Regiment)
				hql += " and app.checkTime>:start ";
			else
				hql += " and fa.reportTime>:start ";
		}
		if(endTime!=null) {
			if(userType!=UserDAO.Regiment)
				hql += " and app.checkTime<:end ";
			else
				hql += " and fa.reportTime<:end ";
		}
		if(equipType!=null&&!(equipType.equals("")))
			hql += " and eq.equipType='" + equipType + "' ";
		if(equipName!=null&&!(equipName.equals("")))
			hql += " and eq.equipName='" + equipName + "'";
		if(equipNumber!=null&&!(equipNumber.equals("")))
			hql += " and eq.equipNumber='" + equipNumber + "'";
		if(department!=null&&!(department.equals("")))
			hql += " and eq.department='" + department + "'";
		if(reporter!=null&&!(reporter.equals("")))
			hql += " and fa.reporter='" + reporter + "' ";
		hql += "order by fa.id";
		
		try{
			session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql);
			if(startTime!=null)
				objQuery.setParameter("start", startTime);
			if(endTime!=null)
				objQuery.setParameter("end", endTime);
			List<HashMap> lstResult = objQuery.list();
			session.close();
			
			long faultId = 0;
			if (lstResult != null && lstResult.size() > 0) {
				arrFault = new ArrayList<FaultRepairReport>();
				
				for (HashMap map : lstResult) {
					FaultRepairReport faultReport = new FaultRepairReport();
					if(map.get("faultId")!=null) {
						faultId = Long.parseLong(map.get("faultId").toString());
						if(faultId<=0)	// 对故障单序列号进行检查
							continue;
						
						faultReport.setFaultID(faultId);
					}
					
					// 去掉重复值
					boolean next = false;
					for (FaultRepairReport report : arrFault) {
						long faultid = report.getFaultID();
						if(faultId==faultid) {
							next = true;
							break;
						}
					}
					
					if(next)	// 已经有财富值，跳过 [zuow, 2012/05/08]
						continue;
					
					if(map.get("equipType")!=null)
						faultReport.setEquipType(map.get("equipType").toString());
					if(map.get("equipName")!=null)
						faultReport.setEquipName(map.get("equipName").toString());
					if(map.get("equipNumber")!=null)
						faultReport.setEquipNumber(map.get("equipNumber").toString());
					if(map.get("amount")!=null)
						faultReport.setfaultAmount(Integer.parseInt(map.get("amount").toString()));
					if(map.get("operater")!=null)
						faultReport.setEquipOperater(map.get("operater").toString());
					if(map.get("reporter")!=null)
						faultReport.setReporter(map.get("reporter").toString());
					if(map.get("reportTime")!=null) {
						DateUtil dateUtil = new DateUtil();
						faultReport.setReportTime(dateUtil.getTheTime(map.get("reportTime").toString()));							
					}
					
					// 查询审核信息 [zuow, 2012/04/11]
					ApprovalDAO appDAO = new ApprovalDAO();
					ArrayList<FrssApprovalInfo> arrApproval = appDAO.queryApproval(faultId);
					if(arrApproval==null || arrApproval.size()<=0) {
						/// 查询的审核信息有误
						continue;
					}
					
					int type = 0;
					int status = 0;
					for(FrssApprovalInfo appInfo : arrApproval) {
						// 查找到最终获取审核/确认的阶段
						if(type<appInfo.getType()) {
							type = appInfo.getType();
							status = appInfo.getStatus();
						}
					}
					
					int step = ApprovalDAO.getStep(type, status);
					faultReport.setStep(step);
					faultReport.setStatus(status);
					faultReport.setReportType(type);
					
					arrFault.add(faultReport);		// 保存障表单
				}
			}
		} catch(Exception e) {
			/// log 查询出现异常
			return null;
		}
				
		return arrFault;
	}
	
	/**
	 * @函数名称: queryFault
	 * @函数描述: 根据起止时间及上报人进行查询故障信息
	 * @输入参数: @param startTime，可为空
	 * @输入参数: @param endTime，可为空
	 * @输入参数: @param reporter，不能为空
	 * @输入参数: @return
	 * @返回类型: ArrayList<FaultRepairReport>
	 * @throws
	 */
	public ArrayList<FaultRepairReport> queryFault(Date startTime, Date endTime, String reporter) {
		ArrayList<FaultRepairReport> arrFault = null;
		Session session = null;
		Date date = null;
		
		// 安全检查
		if(reporter==null){
			/// log 输入条件非法
			return null;
		}
		
		// 组织hql语句
		String hql = "select fa.id, eq.equipType, eq.equipName, eq.equipNumber, fa.amount, fa.faultDesp,fa.faultTime, fa.preProcess, fa.frequency, fa.faultPlace," +
				"eq.department, eq.operater, fa.reporter, fa.contactWay, fa.reportTime, fa.photos, fa.cause, app.checker, app.checkTime, app.opinion, app.type, app.status "; 
		hql += "from (FrssFaultInfo fa" + 
				"inner join FrssApprovalInfo app on fa.id=app.keyId ";
		if(startTime!=null)
			hql += "and app.checkTime>:start ";
		if(endTime!=null)
			hql += "and app.checkTime<:end ";
		hql += "and fa.reporter=" + reporter + " ";
		hql += "and app.type<" + ApprovalDAO.backL1;
		hql += "and app.id<" + threshold + ") ";
		hql += "inner join FrssEquipmentInfo eq on fa.equipId=eq.id ";
		hql += "order by fa.id";	

		try{
			session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql.toString());
			objQuery.setParameter("start", startTime);
			objQuery.setParameter("end", endTime);
			List lstResult = objQuery.list();
			session.close();
			
			if (lstResult != null && lstResult.size() > 0) {
				int num = 0;	int step = 1;
				while(num<lstResult.size()) {
					Map map = (Map)lstResult.get(num);
					FaultRepairReport faultReport = null;	//new FaultRepairReport();
					long faultId = Long.parseLong((map.get("fa.id").toString()));
					faultReport.setFaultID(faultId);
					faultReport.setEquipType(map.get("eq.equipType").toString());
					faultReport.setEquipName(map.get("eq.equipName").toString());
					faultReport.setEquipNumber(map.get("eq.equipNumber").toString());
					faultReport.setfaultAmount(Integer.parseInt(map.get("fa.amount").toString()));
					faultReport.setFaultDesp(map.get("fa.faultDesp").toString());
					date = DateUtil.timeFormat.parse(map.get("fa.faultTime").toString());
					faultReport.setFaultTime(date);
					faultReport.setPreprocess(map.get("fa.preProcess").toString());
					faultReport.setFaultFrequency(Integer.parseInt(map.get("fa.frequency").toString()));
					faultReport.setFaultPlace(map.get("fa.faultPlace").toString());
					faultReport.setEquipDepartment(map.get("eq.department").toString());
					faultReport.setEquipOperater(map.get("eq.operater").toString());
					faultReport.setReporter(map.get("fa.reporter").toString());
					faultReport.setReporterContact(map.get("fa.contactWay").toString());
					date = DateUtil.timeFormat.parse(map.get("fa.reportTime").toString());
					faultReport.setReportTime(date);
//					faultReport.setFaultPhotos(map.get("fa.photos"));
					faultReport.setFaultCause(map.get("fa.cause").toString());
					// 审核信息部分
					int type = Integer.parseInt(map.get("app.type").toString());
					int status = Integer.parseInt(map.get("app.status").toString());
					faultReport.setStatus(status);
					if(type==ApprovalDAO.faultL1){		// L1级审核
						String value = map.get("app.checker").toString();
						if(value!=null)
							faultReport.setL1Checker(value);
						value = map.get("app.opinion").toString();
						if(value!=null)
							faultReport.setL1CheckOpinion(value);
						value = map.get("app.checkTime").toString();
						if(value!=null) {
							date = DateUtil.timeFormat.parse(value);
							faultReport.setL1CheckTime(date);
						}
						
						// 如果L1级审核通过，继续解析L2级审核信息
						if(status==ApprovalDAO.pass) {	// L1审核通过
							// 获取下一个记录
							map = (Map)lstResult.get(num+step);
							long newFaultId = Long.parseLong((map.get("fa.id").toString()));
							type = Integer.parseInt(map.get("app.type").toString());
							if(faultId==newFaultId && type==ApprovalDAO.faultL2) {
								value = map.get("app.checker").toString();
								if(value!=null)
									faultReport.setL2Checker(value);
								value = map.get("app.opinion").toString();
								if(value!=null)
									faultReport.setL2CheckOpinion(value);
								value = map.get("app.checkTime").toString();
								if(value!=null) {
									date = DateUtil.timeFormat.parse(value);
									faultReport.setL2CheckTime(date);
								}
								value = map.get("app.status").toString();
								if(value!=null) {
									faultReport.setStatus(Integer.parseInt(value));
								}
								
								num = num + step + step;	// 跳转到下条
							} else {	// 异常，缺少L2级审核记录
								/// log
								num += step;
							}							
						}
					}
				
					arrFault.add(faultReport);		// 保存障表单
				}				
			}
		} catch(Exception e) {
			/// log 查询出现异常
			return null;
		}		
		return arrFault;
	}
	
	/**
	 * @函数名称: queryFault
	 * @函数描述: 根据故障现象关键字查询故障单 [zuow,2012/05/04]
	 * @输入参数: @param keyWord
	 * @输入参数: @return
	 * @返回类型: ArrayList<FaultRepairReport>
	 * @throws
	 */
	public ArrayList<FaultRepairReport> queryFault(String keyWord) {
		ArrayList<FaultRepairReport> arrFault = null;
		Session session = null;
		ApprovalDAO appDAO = null;
		
		String hql = "from " + FrssFaultInfo.class.getName() + " fa ";
		hql += "where fa.faultDesp like :key ";
		hql += "order by fa.id";
		
		try {
			session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql);
			objQuery.setString("key", "%"+keyWord+"%");
			List lstResult =  objQuery.list();
			session.close();
			
			// 获取信息				
			if(lstResult!=null && lstResult.size()>0) {
				arrFault = new ArrayList<FaultRepairReport>();
				
				appDAO = new ApprovalDAO();
				for(int num=0; num<lstResult.size();num++) {
					FrssFaultInfo faultInfo = (FrssFaultInfo)lstResult.get(num);
					if(faultInfo!=null) {
						long faultId = faultInfo.getId();
						if(faultId<=0)
							continue;
					
						FaultRepairReport faultReport = new FaultRepairReport();						
						// 根据故障单编号查询最终审核状态
						FrssEquipmentInfo equipInfo = queryEquipbyFaultId(faultId);
						FrssApprovalInfo appInfo = appDAO.queryTheApproval(faultId);
						if(appInfo==null || equipInfo==null)		// 安全检查
							continue;
						
						int step = ApprovalDAO.getStep(appInfo.getType(), appInfo.getStatus());
						faultReport.setStep(step);
						faultReport.setStatus(appInfo.getStatus());
						
						faultReport.setEquipType(equipInfo.getEquipType());
						faultReport.setEquipName(equipInfo.getEquipName());
						faultReport.setEquipNumber(equipInfo.getEquipNumber());
						faultReport.setEquipOperater(equipInfo.getOperater());
						faultReport.setfaultAmount(faultInfo.getAmount());
						faultReport.setReporter(faultInfo.getReporter());
						faultReport.setReportTime(faultInfo.getReportTime());
						faultReport.setFaultID(faultId);

						arrFault.add(faultReport);
					}
				}
			}
		} catch (Exception e) {
			return null;
		}
		
		return arrFault;
	}
	
	/**
	 * @函数名称: queryFault
	 * @函数描述: 根据故障单类型查询故障详细信息
	 * @输入参数: @param type
	 * @输入参数: @param faultId
	 * @输入参数: @param status
	 * @输入参数: @return
	 * @返回类型: FaultRepairReport
	 * @throws
	 */
	public FaultRepairReport queryFault(long faultId) {
		FaultRepairReport repairReport = null;
		Session session = null;
		
		// 组织hql语句
		String hql = "select fa.id, eq.equipType, eq.equipName, eq.equipNumber, fa.amount, fa.faultDesp,fa.faultTime, fa.preProcess, fa.frequency, fa.faultPlace," +
				"eq.department, eq.operater, fa.reporter, fa.contactWay, fa.reportTime, fa.photoName, fa.cause "; 
		hql += "from " + FrssFaultInfo.class.getName() + " fa," + FrssEquipmentInfo.class.getName() + " eq ";
		hql += "where fa.equipId=eq.id and fa.id=" + faultId;
		hql += " order by fa.id";
		
		try{
			session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql.toString());
			List<Object[]> lstResult = objQuery.list();
			session.close();
			
			if (lstResult != null && lstResult.size() > 0) {
				repairReport = new FaultRepairReport();
				DateUtil dateUtil = new DateUtil();
				
				ApprovalDAO appDAO = new ApprovalDAO();
				
				for(Object[] object : lstResult) {
					if(object[0]!=null)
						repairReport.setFaultID(Long.parseLong(object[0].toString()));
					if(object[1]!=null)
						repairReport.setEquipType(object[1].toString());
					if(object[2]!=null)
						repairReport.setEquipName(object[2].toString());
					if(object[3]!=null)
						repairReport.setEquipNumber(object[3].toString());
					if(object[4]!=null)
						repairReport.setfaultAmount(Integer.parseInt(object[4].toString()));
					if(object[5]!=null)
						repairReport.setFaultDesp(object[5].toString());
					if(object[6]!=null)
						repairReport.setFaultTime(dateUtil.getTheTime(object[6].toString()));
					if(object[7]!=null)
						repairReport.setPreprocess(object[7].toString());
					if(object[8]!=null)
						repairReport.setFaultFrequency(Integer.parseInt(object[8].toString()));
					if(object[9]!=null)
						repairReport.setFaultPlace(object[9].toString());
					if(object[10]!=null)
						repairReport.setEquipDepartment(object[10].toString());
					if(object[11]!=null)
						repairReport.setEquipOperater(object[11].toString());
					if(object[12]!=null)
						repairReport.setReporter(object[12].toString());
					if(object[13]!=null)
						repairReport.setReporterContact(object[13].toString());
					if(object[14]!=null)
						repairReport.setReportTime(dateUtil.getTheTime(object[14].toString()));
					if(object[15]!=null)
						repairReport.setPhotoName(object[15].toString());	
					if(object[16]!=null)
						repairReport.setFaultCause(object[16].toString());
					
					// 根据故障单编号查找最终审核状态 [zuow, 2012/05/07]
					FrssApprovalInfo appInfo = appDAO.queryTheApproval(faultId);
					int type = 0;
					int state = 0;
					if(appInfo!=null) {
						type = appInfo.getType();
						state = appInfo.getStatus();
					}
					
					repairReport.setStep(type);	
					repairReport.setReportType(type);
					repairReport.setStatus(state);					
					
				}
			}
		} catch (Exception e) {
			/// log
			return null;
		}
				
		return repairReport;
	}
	
	/**
	 * @函数名称: queryFaultInfo
	 * @函数描述: 根据故障单号查找故障表记录
	 * @输入参数: @param faultId
	 * @输入参数: @return
	 * @返回类型: FrssFaultInfo
	 * @throws
	 */
	public FrssFaultInfo queryFaultInfo(long faultId) {
		FrssFaultInfo faultInfo = null;
		
		String hql = "from " + FrssFaultInfo.class.getName() + " fa ";
		hql += "where fa.id=" + faultId;
		
		try{
			Session session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql.toString());
			List lstResult = objQuery.list();
			session.close();
			
			if (lstResult != null && lstResult.size() > 0) {
				faultInfo = (FrssFaultInfo) lstResult.get(0);
			}
		} catch (Exception e) {
			/// log
			return null;
		}
		
		return faultInfo;
	}
	
	/**
	 * @函数名称: queryFault
	 * @函数描述: 根据输入信息来统计故障单个数
	 * @输入参数: @param startTime，开始时间
	 * @输入参数: @param endTime，结束时间
	 * @输入参数: @param department，用户所属单位
	 * @输入参数: @param type，装备信息类型，暂定1表示装备类型，2表示装备名称，3表示装备编号
	 * @输入参数: @param equipInfo，和上个参数对应，分别表示装备类型名，装备名称，装备编号名
	 * @输入参数: @return
	 * @返回类型: int
	 * @throws
	 */
//	public int queryFaults(Date startTime, Date endTime, String department, int type, String equipInfo) {
//		Session session = null;
//		int count = 0;
//				
//		String hql = null;
//		hql = "select count(*) ";
//		hql += "from " + FrssFaultInfo.class.getName() + " fa," + FrssApprovalInfo.class.getName() + " app,";
//		hql += FrssUserInfo.class.getName() + " user, " + FrssEquipmentInfo.class.getName() + " eq ";
//		hql += "where fa.equipId=eq.id and fa.id=app.keyId and app.userId=user.id ";
//		hql += "and app.checkTime>:start and app.checkTime<:end ";
//		if(department!=null&&!(department.equals(""))) {	/// 根据department字符串中解析出用户级别 [zuow, 2012/05/02]
//			String str = department;
//			department = str.substring(str.indexOf("-")+1);
//			int level = Integer.parseInt(str.substring(0, str.indexOf("-")));
//			if(level==1)
//				level = UserDAO.Regiment;
//			else if(level==2)
//				level = UserDAO.GroupArmy;
//			else if(level==3)
//				level = UserDAO.Military;
//			else if(level==4)
//				level = UserDAO.Formater;
//			
//			hql += "and user.department='" + department + "' ";
//			hql += "and user.userType=" + level + " ";
//		}
//		if(type==1) 
//			hql += "and eq.equipType='";
//		else if(type==2)
//			hql += "and eq.equipName='";
//		else if(type==3)
//			hql += "and eq.equipNumber='";
//		if(type==1 || type==2 || type==3)
//			hql += equipInfo + "' ";
//		hql += "group by fa.id";		// 审核表中有重复的记录，需要根据keyId进行分组
//		
//		try{
//			session = BaseDAO.getSession();
//			Query objQuery = session.createQuery(hql);
//			if(startTime!=null)
//				objQuery.setParameter("start", startTime);
//			if(endTime!=null)
//				objQuery.setParameter("end", endTime);
//			List lstResult = objQuery.list();
//			session.close();
//			
//			count = lstResult.size();
//		} catch (Exception e) {
//			/// log 统计过程中出现异常
//			return 0;
//		}
//		
//		return count;
//	}
	
	/**
	 * @函数名称: queryFault
	 * @函数描述: 根据上报时间、上报人以及输入的装备信息统计故障单个数 [zuow, 2012/05/06]
	 * @输入参数: @param startTime
	 * @输入参数: @param endTime
	 * @输入参数: @param userName
	 * @输入参数: @param type
	 * @输入参数: @param equipInfo
	 * @输入参数: @return
	 * @返回类型: int
	 * @throws
	 */
	public long queryFault(Date startTime, Date endTime, String userName, int type, String equipInfo) {
		long amount = 0;
		
		String hql = "select count(*) from " + FrssFaultInfo.class.getName() + " fa, " + FrssEquipmentInfo.class.getName() + " eq ";
		hql += "where fa.equipId=eq.id and fa.reportTime>:start and fa.reportTime<:end ";
		hql += "and fa.reporter='" + userName + "' ";
		if(type==1) 
			hql += "and eq.equipType='";
		else if(type==2)
			hql += "and eq.equipName='";
		else if(type==3)
			hql += "and eq.equipNumber='";
		if(type==1 || type==2 || type==3)
			hql += equipInfo + "' ";
//		hql += "group by fa.id";		// 审核表中有重复的记录，需要根据keyId进行分组
		
		try{
			Session session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql);
			if(startTime!=null)
				objQuery.setParameter("start", startTime);
			if(endTime!=null)
				objQuery.setParameter("end", endTime);
			List lstResult = objQuery.list();
			session.close();
			
			amount = (Long)lstResult.get(0);
		} catch (Exception e) {
			/// log 统计过程中出现异常
			return 0;
		}		
		
		return amount;
	}
	
	/**
	 * @函数名称: queryFault
	 * @函数描述: 统计故障单个数
	 * @输入参数: @param startTime	=》审核起始时间
	 * @输入参数: @param endTime	=》审核结束时间
	 * @输入参数: @param userName	=》审核者
	 * @输入参数: @param type		=》装备信息标识
	 * @输入参数: @param equipInfo	=》装备信息
	 * @输入参数: @param reportType	=》审核类型
	 * @输入参数: @param status	=》审核状态
	 * @输入参数: @return
	 * @返回类型: int
	 * @throws
	 */
	public long queryFault(Date startTime, Date endTime, String userName, int type, String equipInfo, int reportType, int status) {
		long amount = 0;
		
		String hql = "select count(*) ";
		hql += "from " + FrssFaultInfo.class.getName() + " fa," + FrssEquipmentInfo.class.getName() + " eq," + 
				FrssApprovalInfo.class.getName() + " app," + FrssUserInfo.class.getName() + " us ";
		hql += "where fa.equipId=eq.id and fa.id=app.keyId and app.userId=us.id ";
		if(status!=ApprovalDAO.confirm)
			hql += "and app.checker='" + userName + "' ";			// 通过审核者进行直接查询 [zuow, 2012/05/16]
		else 
			hql += "and us.createMan='" + userName + "' ";			// 未处理的不能使用审核者条件过滤，而是用户的创建者条件 [zuow, 2012/5/20]
		hql += "and app.checkTime>:start and app.checkTime<:end ";
		hql += "and app.type=" + reportType + " and app.status=" + status + " ";
		if(type==1) 
			hql += "and eq.equipType='";
		else if(type==2)
			hql += "and eq.equipName='";
		else if(type==3)
			hql += "and eq.equipNumber='";
		if(type==1 || type==2 || type==3)
			hql += equipInfo + "' ";

		try{
			Session session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql);
			if(startTime!=null)
				objQuery.setParameter("start", startTime);
			if(endTime!=null)
				objQuery.setParameter("end", endTime);
			List lstResult = objQuery.list();
			session.close();
			
			amount = (Long)lstResult.get(0);
		} catch (Exception e) {
			System.out.print("统计故障单个数出现异常!");
			return 0;
		}
		
		return amount;
	}
	
	/**
	 * @函数名称: queryFault
	 * @函数描述: 根据故障上报起止时间、当前登录用户类型以及输入的装备信息进行查询符合要求的故障单个数
	 * @输入参数: @param startTime
	 * @输入参数: @param endTime
	 * @输入参数: @param userType
	 * @输入参数: @param type
	 * @输入参数: @param equipInfo
	 * @输入参数: @return
	 * @返回类型: int
	 * @throws
	 */
//	public int queryFault(Date startTime, Date endTime, int userType, int type, String equipInfo) {
//		int count = 0;
//		
//		String hql = null;
//		hql = "select count(*) ";
//		hql += "from " + FrssFaultInfo.class.getName() + " fa," + FrssApprovalInfo.class.getName() + " app,";
//		hql += FrssUserInfo.class.getName() + " user, " + FrssEquipmentInfo.class.getName() + " eq ";
//		hql += "where fa.equipId=eq.id and fa.id=app.keyId and app.userId=user.id ";
//		if(userType==UserDAO.Regiment)		// 如果是团级用户，就以故障上报时间进行查询
//			hql += "and fa.reportTime>:start and fa.reportTime<:end ";
//		else 	// 如果是其它用户就以故障审核时间进行查询
//			hql += "and app.checkTime>:start and app.checkTime<:end ";
//		if(userType!=UserDAO.Regiment)
//			hql += "and user.userType=" + userType + " ";
//		if(type==1) 
//			hql += "and eq.equipType='" + equipInfo + "' ";
//		else if(type==2)
//			hql += "and eq.equipName='" + equipInfo + "' ";
//		else if(type==3)
//			hql += "and eq.equipNumber='" + equipInfo + "' ";
////		hql += equipInfo + "' ";
//		hql += "group by fa.id";		// 审核表中有重复的记录，需要根据keyId进行分组
//		
//		try {
//			Session session = BaseDAO.getSession();
//			Query objQuery = session.createQuery(hql);
//			if(startTime!=null)
//				objQuery.setParameter("start", startTime);
//			if(endTime!=null)
//				objQuery.setParameter("end", endTime);
//			List lstResult = objQuery.list();
//			session.close();
//			
//			count = lstResult.size();	
//		} catch (Exception e) {
//			/// log
//			return 0;
//		}
//		
//		return count;
//	}
	/////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * @函数名称: queryEquipbyFaultId
	 * @函数描述: 根据故障单编号查询装备信息
	 * @输入参数: @param faultId
	 * @输入参数: @return
	 * @返回类型: FrssEquipmentInfo
	 * @throws
	 */
	public FrssEquipmentInfo queryEquipbyFaultId(long faultId) {
		FrssEquipmentInfo equipInfo = null;
		DateUtil dateUtil = null;
		
		StringBuffer hql = new StringBuffer();
		hql.append("select eq.id, eq.equipType, eq.equipNumber, eq.equipName, eq.createTime, ");
		hql.append("eq.description, eq.department, eq.operater ");
		hql.append("from ");
		hql.append(FrssFaultInfo.class.getName()).append(" fa, ");
		hql.append(FrssEquipmentInfo.class.getName()).append(" eq ");
		hql.append("where fa.equipId=eq.id and fa.id=").append(faultId);
		
		try {
			Session session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql.toString());
			List<Object[]> lstResult = objQuery.list();
			session.close();
			if (lstResult!=null && lstResult.size()>0) {
				equipInfo = new FrssEquipmentInfo();
				dateUtil = new DateUtil();
				
				for(Object[] object : lstResult) {
					if(object[0]!=null)
						equipInfo.setId(Long.parseLong(object[0].toString()));
					if(object[1]!=null)
						equipInfo.setEquipType(object[1].toString());
					if(object[2]!=null)
						equipInfo.setEquipName(object[2].toString());
					if(object[3]!=null)
						equipInfo.setEquipNumber(object[3].toString());
					if(object[4]!=null)
						equipInfo.setCreateTime(dateUtil.getTheTime(object[4].toString()));
					if(object[5]!=null)
						equipInfo.setDescription(object[5].toString());
					if(object[6]!=null)
						equipInfo.setDepartment(object[6].toString());
					if(object[7]!=null)
						equipInfo.setOperater(object[7].toString());
				}
			}
		} catch (Exception e) {
			// log
			return null;
		}
		
		return equipInfo;
	}
}

