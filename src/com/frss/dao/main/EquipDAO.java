package com.frss.dao.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.frss.dao.util.BaseDAO;
import com.frss.model.main.BackupApplication;
import com.frss.model.main.EquipInfo;
import com.frss.model.main.FaultRepairReport;
import com.frss.model.main.UserInfo;
import com.frss.model.mapping.FrssApprovalInfo;
import com.frss.model.mapping.FrssBackupApp;
import com.frss.model.mapping.FrssEquipmentInfo;
import com.frss.model.mapping.FrssFactoryInfo;
import com.frss.model.mapping.FrssFaultInfo;
import com.frss.util.DateUtil;
import com.frss.util.FrssException;

public class EquipDAO {
	
	public EquipDAO() {
		
	}

	// 将装备信息插入到装备信息表中
	public long insertEquipInfo(FrssEquipmentInfo equipInfo) throws FrssException {
		long equipId = 0;
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = BaseDAO.getSession();
			transaction = session.beginTransaction();
			session.save(equipInfo);
			transaction.commit();
			session.close();
			
			equipId = equipInfo.getId();
		} catch(Exception e) {
			transaction.rollback();			
//			log.error("插入装备信息出现异常");
			throw new FrssException(e);			
		}
		
		return equipId;
	}
	
	// 添加装备信息记录，然后返回equipID, 每个表单插入都需要输入当前登录用户信息
	public long insertEquip(FaultRepairReport faultReport) throws FrssException {
		long equipId = 0;

		/// log
		FrssEquipmentInfo insertObj = new FrssEquipmentInfo();
		insertObj.setEquipType(faultReport.getEquipType());
		insertObj.setEquipNumber(faultReport.getEquipNumber());
		insertObj.setEquipName(faultReport.getEquipName());
		insertObj.setDepartment(faultReport.getEquipDepartment());
		insertObj.setOperater(faultReport.getEquipOperater());
//		insertObj.setFactoryId((long)0);			/// 不知道的信息在后面再更新		
		
		equipId = insertEquipInfo(insertObj);
		if(equipId<=0) {
			/// log 错误
			return 0;
		}
		
		return equipId;
	}
	
	/**
	 * @函数名称: insertBackup
	 * @函数描述: 将备件信息记录插入备件信息表
	 * @输入参数: @param backInfo
	 * @输入参数: @return
	 * @输入参数: @throws FrssException
	 * @返回类型: long
	 * @throws
	 */
	public long insertBackup(FrssBackupApp backInfo) throws FrssException {
		long backId = 0;
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = BaseDAO.getSession();
			transaction = session.beginTransaction();
			session.save(backInfo);
			transaction.commit();
			session.close();
			
			backId = backInfo.getId();
		} catch(Exception e) {
			transaction.rollback();			
//			log.error("插入备件信息出现异常");
			throw new FrssException(e);			
		}
		
		return backId;
	}
	
	/**
	 * @函数名称: insertBackup
	 * @函数描述: 将备件申请单数据输入到数据库中
	 * @输入参数: @param backApp
	 * @输入参数: @param userInfo
	 * @输入参数: @return
	 * @输入参数: @throws FrssException
	 * @返回类型: long
	 * @throws
	 */
	public long insertBackup(BackupApplication backApp, UserInfo userInfo) throws FrssException {
		long backId = 0;
		
		FrssApprovalInfo appInfo = null;
		ApprovalDAO appDAO = null;
		// 备件型号和编号等信息可以通过维修申请单号外联装备表查询，备件使用的装备名称和型号可以通过equipID外键查询
		try {
			FrssBackupApp insertBack = new FrssBackupApp();
			insertBack.setId(backApp.getBackID());
			insertBack.setEquiPlace(backApp.getUseEquipPlace());
			insertBack.setBackCount(backApp.getBackAmount());
			insertBack.setReporter(backApp.getReporter());
			insertBack.setContactWay(backApp.getReporterContact());
			insertBack.setReportTime(backApp.getReportTime());
			insertBack.setFaultId(backApp.getFaultID());
			
			FrssEquipmentInfo equipInfo = getEquipForTypeAndNumber(backApp.getBackType(), backApp.getBackNumber());
			if(equipInfo!=null) {
				insertBack.setEquipId(equipInfo.getId());
			}
			
			// 入库
			backId = insertBackup(insertBack);
			if(backId<=0) {
				/// log
				return 0;
			}
			
			// 将审核信息插入到审核表中
			appInfo = new FrssApprovalInfo();
			appDAO = new ApprovalDAO();
			if(appInfo==null || appDAO==null) {
				/// log 
				return backId;
			}
			
			appInfo.setType(ApprovalDAO.backL1);			// 备件单L1级审核
			appInfo.setStatus(ApprovalDAO.confirm);		// 提交确认状态
			appInfo.setKeyId(backId);
			appInfo.setUserId(userInfo.getUserID());		// 设置关联用户
			// 添加提交用户和提交时间
			appInfo.setChecker(userInfo.getUserName());
//			DateUtil dateUtil = new DateUtil();
			appInfo.setCheckTime(DateUtil.getCurTime());
			
			long appId = appDAO.insertApproval(appInfo);
			if(appId<=0) {
				/// log 插入验证信息失败
			}			
		} catch (Exception e) {
			throw new FrssException(e);
		}
		
		return backId;
	}	
	
	// 根据ID获取装备信息
	public EquipInfo getEquipForID(Long idEquip) {
		EquipInfo equipInfo = null;
		
		return equipInfo;		
	}
	
	/**
	 * @函数名称: queryBackupByTime
	 * @函数描述: 根据时间范围查找备件申报单信息
	 * @输入参数: @param starTime
	 * @输入参数: @param endTime
	 * @输入参数: @param status，1表示审核通过，2表示审核没通过
	 * @输入参数: @param type，3表示备件1级审核，4表示备件2级审核
	 * @输入参数: @return
	 * @返回类型: ArrayList<BackupApplication>
	 * @throws
	 */
	public ArrayList<BackupApplication> queryBackupByTime(Date starTime, Date endTime, int type, int status) {
		ArrayList<BackupApplication> arrBack = null;
		Session session = null;
		
		SimpleDateFormat simFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Date date = null;

		// 安全检查
		if(starTime==null&&endTime==null) {
			// log
			return null;
		}
		if(type<3 || type>4) {
			// log
			return null;
		}
		if(status<0 || status>2) {
			// log
			return null;
		}
		
		StringBuffer hql = new StringBuffer();
		// 组织hql语句
		hql.append("select bak.id, bak.backCount, eq.equipName, eq.equipType, bak.equiPlace, bak.faultId, ");
		hql.append("eq.department, eq.operater, bak.reporter, bak.contactWay, bak.reportTime ");
		if(status>0)	// 有审核信息
			hql.append("app.checker, app.checkTime, app.opinion ");
		hql.append("from ");
		hql.append(FrssBackupApp.class.getName()).append(" bak ");
		hql.append("inner join ").append(FrssEquipmentInfo.class.getName()).append(" eq on bak.equipId=eq.id ");
		hql.append("innor join ").append(FrssApprovalInfo.class.getName()).append(" app on bak.id=app.keyId ");
		hql.append("where ");
		if(starTime!=null)	// 表示有起始时间
			hql.append("app.checkTime after ").append(starTime).append(" and ");
		if(endTime!=null)	// 表示有截至时间
			hql.append("app.checkTime before ").append(endTime).append(" and ");
		hql.append("and app.type=").append(type);
		hql.append("and app.status=").append(status);
		hql.append("order by bak.id ");		

		try{
			session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql.toString());
			List lstResult = objQuery.list();
			session.close();
			
			FaultDAO faultDAO = new FaultDAO();
			ApprovalDAO appDAO = new ApprovalDAO();
			
			if (lstResult != null && lstResult.size() > 0) {
				for(int num=0; num<lstResult.size(); num++) {
					Map map = (Map)lstResult.get(num);
					BackupApplication bakApp = new BackupApplication();
					bakApp.setBackAmount(Integer.parseInt(map.get("bak.backCount").toString()));
					bakApp.setUseEquipName(map.get("eq.equipName").toString());
					bakApp.setUseEquipType(map.get("eq.equipType").toString());
					bakApp.setUseEquipPlace(map.get("bak.equiPlace").toString());
					bakApp.setFaultID(Long.parseLong(map.get("bak.faultId").toString()));
					bakApp.setEquipDepartment(map.get("eq.department").toString());
					bakApp.setOperater(map.get("eq.operater").toString());
					bakApp.setReporter(map.get("bak.reporter").toString());
					bakApp.setReporterContact(map.get("bak.contactWay").toString());
					date = simFormat.parse(map.get("bak.reportTime").toString());
					bakApp.setReportTime(date);
					
					// 根据故障编号查找备件信息
					FrssEquipmentInfo equipInfo =  faultDAO.queryEquipbyFaultId(bakApp.getFaultID());
					if(equipInfo==null)
						continue;
					
					bakApp.setBackName(equipInfo.getEquipName());
					bakApp.setBackNumber(equipInfo.getEquipNumber());
					bakApp.setBackType(equipInfo.getEquipType());
					
					// 如果是查询正在提交的
					if(status==0) {
						bakApp.setL1Checker(null);
						bakApp.setL1CheckTime(null);
						bakApp.setL1CheckOpinion(null);
						bakApp.setL2Checker(null);
						bakApp.setL2CheckOpinion(null);
						bakApp.setL2CheckTime(null);
					} else if (status>0 && type==1){					
						if(type==1) {	// 设置l1级审核信息并查找l2级审核信息
							bakApp.setL1Checker(map.get("app.checker").toString());
							bakApp.setL1CheckOpinion(map.get("app.opinion").toString());
							date = simFormat.parse(map.get("app.checkTime").toString());
							bakApp.setL1CheckTime(date);
							
							// 根据故障单号查找l2级审核信息
							FrssApprovalInfo appInfo = appDAO.getApprovalInfo(2, bakApp.getFaultID()); 
							if(appInfo==null)
								continue;
							bakApp.setL2Checker(appInfo.getChecker());
							bakApp.setL2CheckTime(appInfo.getCheckTime());
							bakApp.setL2CheckOpinion(appInfo.getOpinion());
						} else if(type==2) {	// 设置l2级审核信息并查找l1级信息
							bakApp.setL2Checker(map.get("app.checker").toString());
							bakApp.setL2CheckOpinion(map.get("app.opinion").toString());
							date = simFormat.parse(map.get("app.checkTime").toString());
							bakApp.setL2CheckTime(date);
							
							// 根据故障单号查找l1级审核信息
							FrssApprovalInfo appInfo = appDAO.getApprovalInfo(1, bakApp.getFaultID()); 
							if(appInfo==null)
								continue;
							bakApp.setL1Checker(appInfo.getChecker());
							bakApp.setL1CheckTime(appInfo.getCheckTime());
							bakApp.setL1CheckOpinion(appInfo.getOpinion());							
						}
					} 
					
					arrBack.add(bakApp);
				}
			}
		} catch (Exception e) {
			/// log
			return null;
		}
		
		return arrBack;
	}
	
	/**
	 * @函数名称: delBackById
	 * @函数描述: 根据备件id从备件信息表中删除备件信息
	 * @输入参数: @param backId
	 * @输入参数: @return
	 * @输入参数: @throws FrssException
	 * @返回类型: boolean
	 * @throws
	 */
	public boolean delBackById(long backId) throws FrssException {
		Session session = null;		
		Transaction transaction = null;
		
		StringBuffer hql = new StringBuffer();
		hql.append("delete from "); 
		hql.append(FrssBackupApp.class.getName()).append(" bak ");
		hql.append("where bak.id=").append(backId);
		
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
	 * @函数名称: queryBack
	 * @函数描述: 通过故障单编号查询备件信息
	 * @输入参数: @param faultId
	 * @输入参数: @return
	 * @返回类型: BackupApplication
	 * @throws
	 */
	public BackupApplication queryBack(long faultId) {
		BackupApplication backApp = null;
		Session session = null;
		SimpleDateFormat simFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		
		// 组织hql语句
		StringBuffer hql = new StringBuffer();
		hql.append("select bak.id, bak.backCount, eq.equipName, eq.equipType, bak.equiPlace, bak.faultId, ");
		hql.append("eq.department, eq.operater, bak.reporter, bak.contactWay, bak.reportTime ");
		hql.append("app.type, app.status, app.checker, app.checkTime, app.opinion ");
		hql.append("from ");
		hql.append(FrssBackupApp.class.getName()).append(" bak ");
		hql.append("inner join ").append(FrssEquipmentInfo.class.getName()).append(" eq on bak.equipId=eq.id ");
		hql.append("innor join ").append(FrssApprovalInfo.class.getName()).append(" app on bak.id=app.keyId ");		
		hql.append("wherre bak.faultId=").append(faultId);
		hql.append("and app.type=3 or app.type=4");		

		try{
			// 查询
			session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql.toString());
			List lstResult = objQuery.list();
			session.close();
			
			// 获取信息
			backApp = new BackupApplication();			
			if (lstResult != null && lstResult.size() > 0) {
				// 先获取L1级审核信息
				for(int num=0; num<lstResult.size(); num++) {
					Map map = (Map)lstResult.get(num);
					// 如果是查询正在提交的
					int type = Integer.parseInt(map.get("app.type").toString());
					int status = Integer.parseInt(map.get("app.status").toString());					
					if(type==3) {		// 提交或者L1审核
						backApp.setBackID(Long.parseLong(map.get("bak.id").toString()));
						backApp.setBackAmount(Integer.parseInt(map.get("bak.backCount").toString()));
						backApp.setUseEquipName(map.get("eq.equipName").toString());
						backApp.setUseEquipType(map.get("eq.equipType").toString());
						backApp.setUseEquipPlace(map.get("bak.equiPlace").toString());
						backApp.setFaultID(faultId);
						backApp.setEquipDepartment(map.get("eq.department").toString());
						backApp.setOperater(map.get("eq.operater").toString());
						backApp.setReporter(map.get("bak.reporter").toString());
						backApp.setReporterContact(map.get("bak.contactWay").toString());
						date = simFormat.parse(map.get("bak.reportTime").toString());
						backApp.setReportTime(date);
						
						// 根据故障编号查找备件信息
						FaultDAO faultDAO = new FaultDAO();
						FrssEquipmentInfo equipInfo =  faultDAO.queryEquipbyFaultId(faultId);
						if(equipInfo==null)
							continue;
						
						backApp.setBackName(equipInfo.getEquipName());
						backApp.setBackNumber(equipInfo.getEquipNumber());
						backApp.setBackType(equipInfo.getEquipType());
						
						// 加上提交/审核信息
						if(status==0) {	// 提交状态
							backApp.setL1Checker(null);
							backApp.setL1CheckTime(null);
							backApp.setL1CheckOpinion(null);
							backApp.setL2Checker(null);
							backApp.setL2CheckOpinion(null);
							backApp.setL2CheckTime(null);
						} else {		// L1级审核状态
							backApp.setL1Checker(map.get("app.checker").toString());
							date = simFormat.parse(map.get("app.checkTime").toString());
							backApp.setL1CheckTime(date);
							backApp.setL1CheckOpinion(map.get("app.opinion").toString());							
						}
						backApp.setStatus(status);
					} else if (type==2){		// L2审核状态		
						backApp.setL2Checker(map.get("app.checker").toString());
						backApp.setL2CheckOpinion(map.get("app.opinion").toString());
						date = simFormat.parse(map.get("app.checkTime").toString());
						backApp.setL2CheckTime(date);
						backApp.setStatus(Integer.parseInt(map.get("app.status").toString()));
					}												
				}	
			}			
		} catch (Exception e) {
			/// log
			return null;
		}
		
		return backApp;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 根据装备类型和名字返回装备列表
	public ArrayList<FrssEquipmentInfo> getEquipList(String equipType, String equipName) {
		StringBuffer sql = null;
		ArrayList<FrssEquipmentInfo> lstEquip = new ArrayList<FrssEquipmentInfo>();
		if(lstEquip==null){
			/// log
			return null;
		}
		
		String hql = null;
		hql = "from " + FrssEquipmentInfo.class.getName() + " eq ";
		hql += "where eq.equipType='" + equipType + "' ";
		hql += "and eq.equipName='" + equipName + "'" +
				"order by eq.id";
		
		try {
			Session session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql);
			List lstResult = objQuery.list();
			session.close();			
			if (lstResult != null && lstResult.size() > 0) {
				for(int num=0;num<lstResult.size();num++) {
					FrssEquipmentInfo equipObj = (FrssEquipmentInfo)lstResult.get(num);
					lstEquip.add(equipObj);
				}
			}
		} catch (Exception e) {
			/// log
			return null;
		}
		
		return lstEquip;
	}
	
	// 根据装备类型和型号查询装备信息
	public FrssEquipmentInfo getEquipForTypeAndNumber(String equipType, String equipNumber) throws FrssException {
		FrssEquipmentInfo equipInfo = null;
		Session session = null;
		
		StringBuffer sql = new StringBuffer();
//		sql.append("select eq.id as id, eq.equipType as equipType, eq.equipNumber as equipNumber,");
//		sql.append("eq.equipNAME as equipName, eq.createTime as createTime, eq.description as descripion,");
//		sql.append("eq.department as department, eq.operater as operater, eq.keyFactoryInfo as idFrssFactoryInfo");
//		sql.append("fa.name");
		sql.append("from ");
		sql.append(FrssEquipmentInfo.class.getName()).append(" eq ");
//		sql.append("left join ").append(FrssFactoryInfo.class.getName()).append(" fa on fa.id=eq.keyFactoryInfo");
		sql.append("where eq.equipType='").append(equipType).append("' ");
		sql.append("and eq.equipNumber='").append(equipNumber).append("'");
		
		try {
			session = BaseDAO.getSession();
			Query objQuery = session.createQuery(sql.toString());
			List lstResult = objQuery.list();
			session.close();			
			if (lstResult != null && lstResult.size() > 0) {
				equipInfo = (FrssEquipmentInfo)lstResult.get(0);

				if(equipInfo==null || equipInfo.getId()<=0) {		// 安全检查
					return null;
				}		
			}		
		} catch (Exception e) {
			/// log
			throw new FrssException(e);
		}
		
		return equipInfo;
	}
		
}
