package com.frss.dao.main;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.frss.dao.util.BaseDAO;
import com.frss.model.main.FaultRepairReport;
import com.frss.model.main.RemoteSupportForm;
import com.frss.model.main.RepairDispatchForm;
import com.frss.model.main.RepairFeedbackForm;
import com.frss.model.main.UserInfo;
import com.frss.model.mapping.FrssApprovalInfo;
import com.frss.model.mapping.FrssClientReview;
import com.frss.model.mapping.FrssEquipCreating;
import com.frss.model.mapping.FrssEquipmentInfo;
import com.frss.model.mapping.FrssExpertInfo;
import com.frss.model.mapping.FrssFactoryInfo;
import com.frss.model.mapping.FrssFaultInfo;
import com.frss.model.mapping.FrssRemoteSupport;
import com.frss.model.mapping.FrssRepairDispatch;
import com.frss.model.mapping.FrssRepairFeedback;
import com.frss.util.DateUtil;
import com.frss.util.FrssException;

public class RepairDAO {
	
	// 将远程支援记录插入远程支援表中
	public long insertRemoteSupport(FrssRemoteSupport remoteObj) throws FrssException {
		long remoteId = 0;
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = BaseDAO.getSession();
			transaction = session.beginTransaction();
			session.save(remoteObj);
			transaction.commit();
			session.close();
			
			remoteId = remoteObj.getId();
		} catch (Exception e) {
			transaction.rollback();			
//			log.error("插入远程支援信息出现异常");
			throw new FrssException(e);			
		}
		
		return remoteId;
	}
	
	// 将维修派遣信息插入派遣信息表中
	public long insertRepairDispatch(FrssRepairDispatch dispatchObj) throws FrssException {
		long dispatchId = 0;
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = BaseDAO.getSession();
			transaction = session.beginTransaction();
			session.save(dispatchObj);
			transaction.commit();
			session.close();
			
			dispatchId = dispatchObj.getId();
		} catch (Exception e) {
			transaction.rollback();			
//			log.error("插入维修派遣信息出现异常");
			throw new FrssException(e);			
		}		
		
		return dispatchId;
	}
	
	// 将故障维修反馈信息插入反馈信息表中
	public long insertRepairFeedback(FrssRepairFeedback feedbackObj) throws FrssException {
		long feedbackId = 0;
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = BaseDAO.getSession();
			transaction = session.beginTransaction();
			session.save(feedbackObj);
			transaction.commit();
			session.close();
			
			feedbackId = feedbackObj.getId();
		} catch (Exception e) {
			transaction.rollback();			
//			log.error("插入故障维修反馈信息出现异常");
			throw new FrssException(e);			
		}		
		
		return feedbackId;
	}
	
	/**
	 * @函数名称: insertClientReview
	 * @函数描述: 导入客户回访单 [zuow, 2012/05/08]
	 * @输入参数: @param review
	 * @输入参数: @return
	 * @输入参数: @throws FrssException
	 * @返回类型: long
	 * @throws
	 */
	public long insertClientReview(FrssClientReview review) throws FrssException {
		long reviewId = 0;
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = BaseDAO.getSession();
			transaction = session.beginTransaction();
			session.save(review);
			transaction.commit();
			session.close();
			
			reviewId = review.getFaultId();
		} catch (Exception e) {
			transaction.rollback();			
//			log.error("插入故障维修反馈信息出现异常");
			throw new FrssException(e);			
		}		
		
		return reviewId;		
	}
	
	// 将远程支援信息表单导入到数据库中，远程支援信息表是维修中心下发给专家的
	public long insertRemoteSupportForm(RemoteSupportForm supportForm, UserInfo userInfo) throws FrssException {
		int ret = 0;
		long supportId = 0;		// 远程支援信息表Id
		
		if(userInfo.getUserType()!=UserDAO.Factory) {
			/// log 非法用户
			return -1;
		}
		
		// 插入远程支援信息
		FrssRemoteSupport insertObj = new FrssRemoteSupport();
		if(insertObj==null) {
			/// log
			return -1;
		}
		
		insertObj.setSupportType(supportForm.getSupportType());
		insertObj.setChannel(supportForm.getSupportChannel());
		insertObj.setDepartment(supportForm.getDepartment());
		insertObj.setFaultId(supportForm.getFaultId());			// 加入故障单和反馈表单id [zuow, 2012/04/11]
		insertObj.setFeedbackId(supportForm.getFeedbackId());
		// 根据专家姓名获取其id
		long expertId = 0;
		String expertName = supportForm.getExpert();
		if(expertName!=null&&!(expertName.equals(""))) {
			ExpertDAO expertDAO = new ExpertDAO();
			FrssExpertInfo expertInfo = expertDAO.queryExpert(expertName);
			if(expertInfo!=null)
				expertId = expertInfo.getId();
		}
		insertObj.setExpertId(expertId);
		
		// 插入反馈信息表
		supportId = insertRemoteSupport(insertObj);		
		if(supportId<=0){
			/// log
			return 0;
		}
		
		/// 将验证确认状态插入到验证信息表中----远程支援信息表的状态信息如何设置和更新？？？ [zuow, 2012/04/03]
		FrssApprovalInfo appInfo = null;
		ApprovalDAO appDAO = new ApprovalDAO();	
		if(userInfo.getUserType()==4) {		// 维修中心，则为表单提交状态
			appInfo = new FrssApprovalInfo();
			appInfo.setType(4);
			appInfo.setStatus(supportForm.getStatus());
			appInfo.setKeyId(supportId);
			appInfo.setUserId(userInfo.getUserID());
			
			long appId = appDAO.insertApproval(appInfo);
			if(appId<=0) {
				/// log 插入提交信息失败
				return 0;
			}
		} else if(userInfo.getUserType()==3) {	// 专家，确认状态
			appInfo = appDAO.getApprovalInfo(4, supportId);
			// 更新记录
			StringBuffer hql = new StringBuffer();
			hql.append("update ").append(FrssApprovalInfo.class.getName()).append(" app ");
			hql.append("set app.checker='").append(userInfo.getUserName()).append("' ");
			hql.append("app.checkTime='").append(supportForm.getCheckTime()).append("' ");
			hql.append("app.status=").append(supportForm.getStatus()).append(" ");
			hql.append("app.userId=").append(userInfo.getUserID()).append(" ");
			hql.append("where app.id=").append(appInfo.getId());
			
			// 然后更新数据库中的记录
			ret = appDAO.updateApprovalInfo(hql.toString());
			/// log			
		}
		
		return supportId;
	}
		
	/**
	 * @函数名称: insertRepairDispatchForm
	 * @函数描述: 维修中心根据故障单进行分发派遣单给工业部门，在分发的时候根据故障单好从装备信息表中获取工业部门Id然后实现自动分发，分发后派遣单信息入库、处于提交状态，
	 * 		同时生成派遣单的审核信息，工业部门在获取派遣单后，插入派遣单的确认信息，并生成反馈表的审核待确认信息
	 * @输入参数: @param dispatchForm
	 * @输入参数: @param userInfo
	 * @输入参数: @return
	 * @输入参数: @throws FrssException
	 * @返回类型: long
	 * @throws
	 */
	public long insertRepairDispatchForm(RepairDispatchForm dispatchForm, UserInfo userInfo) throws FrssException {
		long dispatchId = 0;		// 派遣表编号
		FrssApprovalInfo appInfo = null;
		ApprovalDAO appDAO = null;
		FrssRepairDispatch insertObj = null;
		
		if(userInfo.getUserType()==UserDAO.Factory) {		// 工业部门，派遣单确认
			// 首先将派遣信息单数据进行更新
			appDAO = new ApprovalDAO();
			int type = ApprovalDAO.phoneBack;
			int status = ApprovalDAO.confirm;
			long keyId = dispatchForm.getFaultId();
			// 工业部门进行确认后，不更新反馈单状态；反馈单状态必须是在反馈表分发后进行更改，现在只生成一个电话确认单的待确认记录
			appInfo = appDAO.queryApproval(type, keyId, status);
			if(appInfo==null) {		// 如果没有就插入
				appInfo = new FrssApprovalInfo();
				appInfo.setType(type);
				appInfo.setStatus(status);
				appInfo.setKeyId(keyId);
				appInfo.setUserId(userInfo.getUserID());
				appInfo.setChecker(userInfo.getUserName());
				appInfo.setCheckTime(DateUtil.getCurTime());					
				long appId = appDAO.insertApproval(appInfo);
				if(appId<=0) {
					/// log 插入提交信息失败
					return 0;				
				}				
			}			
		} else if(userInfo.getUserType()==UserDAO.Distributor) {		// 维修中心，派遣单分发/提交
			// 将派遣单插入派遣单信息表中
			insertObj = new FrssRepairDispatch();
			long faultId = dispatchForm.getFaultId();
			// 从界面点击中获取故障维修单号和工业部门信息号，其它信息从其它表中可以获取，不需要添加[MC, 2012/03/15]
			insertObj.setFaultId(faultId);
//			insertObj.setFactoryId(dispatchForm.getFactoryId());
//			insertObj.setDispatchTime(DateUtil.getCurTime());
			
			dispatchId = insertRepairDispatch(insertObj);
			if(dispatchId<=0){
				/// log
				return 0;
			}
						
			// 更新/插入派遣单信息表状态
			appDAO = new ApprovalDAO();	
			int type = ApprovalDAO.dispatch;		
			int status = ApprovalDAO.confirm;
			appInfo = appDAO.queryApproval(type, faultId, status);
			if(appInfo!=null) {		// 更新状态
				appInfo.setStatus(ApprovalDAO.pass);
				appInfo.setChecker(userInfo.getUserName());
				appInfo.setUserId(userInfo.getUserID());
				appInfo.setCheckTime(DateUtil.getCurTime());
				
				boolean bFlag = appDAO.updateApprovalInfo(appInfo);
				if(!bFlag) {
					/// log 更新失败
					
					// 删除派遣记录
					delDispatch(dispatchId);
					
					return 0;
				}
				
				/// 确认分发信息
//				appInfo = new FrssApprovalInfo();
//				appInfo.setType(ApprovalDAO.dispatch);
//				appInfo.setStatus(ApprovalDAO.pass);
//				appInfo.setKeyId(faultId);
//				appInfo.setUserId(userInfo.getUserID());
//				appInfo.setChecker(userInfo.getUserName());
//				appInfo.setCheckTime(DateUtil.getCurTime());		
//				
//				long appId = appDAO.insertApproval(appInfo);
//				if(appId<=0) {
//					/// log 插入提交信息失败
//					
//					// 删除派遣记录
//					delDispatch(dispatchId);
//				}
				
			} else {	// 插入
				appInfo = new FrssApprovalInfo();
				appInfo.setType(ApprovalDAO.dispatch);
				appInfo.setStatus(ApprovalDAO.pass);
				appInfo.setKeyId(faultId);
				appInfo.setUserId(userInfo.getUserID());
				appInfo.setChecker(userInfo.getUserName());
				appInfo.setCheckTime(DateUtil.getCurTime());		
				
				long appId = appDAO.insertApproval(appInfo);
				if(appId<=0) {
					/// log 插入提交信息失败
					
					// 删除派遣记录
					delDispatch(dispatchId);
					return 0;
				}				
			}
			
			// 生成一个反馈待确认记录
			appInfo.setType(ApprovalDAO.feedback);
			appInfo.setStatus(ApprovalDAO.confirm);
			appInfo.setKeyId(faultId);
			appInfo.setUserId(userInfo.getUserID());
			appInfo.setChecker(userInfo.getUserName());
			appInfo.setCheckTime(DateUtil.getCurTime());		
			
			long newAppId = appDAO.insertApproval(appInfo);	
			if(newAppId<=0) {
				/// log
				return 0;
			}			
		} 
		
		return dispatchId;
	}

	/**
	 * @函数名称: insertFeedbackForm
	 * @函数描述: 将故障反馈信息表插入到数据库中，并更新其确认信息记录
	 * @输入参数: @param feedbackForm
	 * @输入参数: @param userInfo
	 * @输入参数: @return
	 * @输入参数: @throws FrssException
	 * @返回类型: long
	 * @throws
	 */
	public long insertFeedbackForm(RepairFeedbackForm feedbackForm, UserInfo userInfo) throws FrssException {
		long feedbackId = 0;
		FrssRepairFeedback insertObj = null;
		FrssApprovalInfo appInfo = null;
		ApprovalDAO appDAO = null;	
		
		if(userInfo.getUserType()==UserDAO.Factory) {		// 工业部门，表单提交
			// 首先插入反馈表到数据库中
			insertObj = new FrssRepairFeedback();
			long faultId = feedbackForm.getFaultId();
			if(faultId<=0) {
				/// log
				return 0;
			}
			insertObj.setFaultId(faultId);
			insertObj.setQuality(feedbackForm.getRepairQuality());
			insertObj.setRepairDisp(feedbackForm.getRepairDisp());
			insertObj.setRepairTime(feedbackForm.getRepairTime());
			insertObj.setRepairWay(feedbackForm.getRepairWay());
			insertObj.setResults(feedbackForm.getRepairResults());
			insertObj.setFaultDispatch(feedbackForm.getFaultDispatch());
			insertObj.setBackSource(feedbackForm.getBackSource());
			insertObj.setBackName(feedbackForm.getBackName());
			insertObj.setBackType(feedbackForm.getBackType());
			
			feedbackId = insertRepairFeedback(insertObj);
			if(feedbackId<=0) {
				/// log
				return 0;
			}		
			
			// 查询并更新反馈表确认信息
			appDAO = new ApprovalDAO();
			if(appDAO==null) {
				/// log
				return feedbackId;
			}
			
			int type = ApprovalDAO.feedback;
			int status = ApprovalDAO.confirm;
			appInfo = appDAO.queryApproval(type, faultId, status);
			if(appInfo!=null) {		// 更新反馈表确认信息
				appInfo.setChecker(userInfo.getUserName());
				appInfo.setCheckTime(DateUtil.getCurTime());
				appInfo.setStatus(ApprovalDAO.pass);
				appInfo.setUserId(userInfo.getUserID());
				boolean bFlag = appDAO.updateApprovalInfo(appInfo);
				if(!bFlag) {
					/// log
					return feedbackId;
				}
				
			} else {	// 插入反馈表确认信息
				appInfo = new FrssApprovalInfo();
				appInfo.setChecker(userInfo.getUserName());
				appInfo.setCheckTime(DateUtil.getCurTime());
				appInfo.setKeyId(faultId);
				appInfo.setStatus(ApprovalDAO.pass);
				appInfo.setType(ApprovalDAO.feedback);
				appInfo.setUserId(userInfo.getUserID());	
				long appId = appDAO.insertApproval(appInfo);
				if(appId<=0) {
					/// log
					return feedbackId;
				}
			}
			
			// 将故障原因更新到故障表中 [zuow, 2012/04/22]
			String cause = feedbackForm.getFaultCause();
			if(cause!=null && !(cause.equals(""))) {
				FaultDAO faultDAO = new FaultDAO();
				FrssFaultInfo faultInfo = faultDAO.queryFaultInfo(faultId);
				if(faultInfo!=null) {
					faultInfo.setCause(cause);
					
					// 进行更新
					boolean flag = faultDAO.updateFault(faultInfo);
					if(!flag) {
						/// log 更新故障原因失败
					}
				}
			}
			
			// 插入电话回访待确认信息
			appInfo.setCheckTime(DateUtil.getCurTime());
			appInfo.setType(ApprovalDAO.phoneBack);
			appInfo.setStatus(ApprovalDAO.confirm);
			long appId = appDAO.insertApproval(appInfo);
			if(appId<=0) {
				/// log
				return feedbackId;
			}
			
		} else if (userInfo.getUserType()==UserDAO.Formater)  {		// 维修中心，反馈单状态确认
			// 更新反馈表确认信息
			appDAO = new ApprovalDAO();
			if(appDAO==null) {
				/// log
				return 0;
			}
			
			int type = ApprovalDAO.feedback;
			int status = ApprovalDAO.pass;
			feedbackId = feedbackForm.getFeedbackId();
			long keyId = feedbackId;
			appInfo = appDAO.queryApproval(type, keyId, status);
			if(appInfo==null) {		// 没有待确认信息，就插入
				appInfo = new FrssApprovalInfo();
				appInfo.setChecker(userInfo.getUserName());
				appInfo.setCheckTime(feedbackForm.getCheckTime());
				appInfo.setKeyId(keyId);
				appInfo.setStatus(status);
				appInfo.setType(type);
				appInfo.setUserId(userInfo.getUserID());
				
				long appId = appDAO.insertApproval(appInfo);
				if(appId<=0) {
					/// log
					return keyId;
				}
				
			} else if(appInfo.getStatus()==ApprovalDAO.confirm) {	//就更新确认信息记录
				appInfo.setChecker(userInfo.getUserName());
				appInfo.setCheckTime(feedbackForm.getCheckTime());
				appInfo.setStatus(status);
				appInfo.setUserId(userInfo.getUserID());
				
				boolean bFlag = appDAO.updateApprovalInfo(appInfo);
				if(!bFlag) {	// 更新失败
					/// log
					return keyId;
				}
				
				// 生成电话回访确认单
				appInfo.setKeyId(keyId);
				appInfo.setStatus(ApprovalDAO.confirm);
				appInfo.setType(ApprovalDAO.phoneBack);
				long appId = appDAO.insertApproval(appInfo);
				if(appId<=0) {
					/// log
					return keyId;
				}
				
			} else if(appInfo.getStatus()==ApprovalDAO.pass && feedbackForm.getPhoneBack()==ApprovalDAO.pass) {		// 更新电话回访确认信息状态
				// 更新电话回访单，首先查询确认信息记录
				appInfo = appDAO.queryApproval(ApprovalDAO.phoneBack, keyId, ApprovalDAO.confirm);
				if(appInfo==null) {		// 前面没有插入待确认信息，现在进行插入
					appInfo = new FrssApprovalInfo();
					appInfo.setChecker(userInfo.getUserName());
					appInfo.setCheckTime(feedbackForm.getCheckTime());
					appInfo.setKeyId(keyId);
					appInfo.setStatus(ApprovalDAO.confirm);
					appInfo.setType(ApprovalDAO.phoneBack);
					appInfo.setUserId(userInfo.getUserID());
					
					long appId = appDAO.insertApproval(appInfo);
					if(appId<=0) {
						/// log
						return keyId;
					}
				} else {	// 前面已经插入，现在进行更新
					appInfo.setChecker(userInfo.getUserName());
					appInfo.setCheckTime(feedbackForm.getCheckTime());
					appInfo.setStatus(ApprovalDAO.pass);
					appInfo.setUserId(userInfo.getUserID());
					boolean bFlag = appDAO.updateApprovalInfo(appInfo);
					if(!bFlag) {
						/// log
						return keyId;
					}
				}			
			}			
		}
		
		return feedbackId;
	}
	
	/**
	 * @函数名称: autoDispatch
	 * @函数描述: 通过故障单编号查询派遣单信息
	 * @输入参数: @param faultId
	 * @输入参数: @return
	 * @返回类型: RepairDispatchForm
	 * @throws
	 */
	public RepairDispatchForm autoDispatch(long faultId) {
		RepairDispatchForm dispatchForm = null;
		
		String hql = null;
		hql = "select new map(eq.department as department, fc.guarantee as guarantee, eq.equipType as equipType, eq.equipName as equipName," + 
				"fc.guaranteeAddress as guaranteeAddress, fc.contact as contact, fa.faultDesp as faultDesp) ";
		hql += "from " + FrssFaultInfo.class.getName() + " fa," + FrssEquipmentInfo.class.getName() + " eq," + FrssFactoryInfo.class.getName() + " fc ";
		hql += "where fa.id=" + faultId + "and fa.equipId=eq.id and eq.factoryId=fc.id";
		
		try {
			Session session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql);
			List<HashMap> lstResult =  objQuery.list();
			session.close();
			
			if(lstResult!=null && lstResult.size()>0) {
				HashMap map = lstResult.get(0);
				dispatchForm = new RepairDispatchForm();
				
//				if(map.get("factoryId")!=null)
//					dispatchForm.setFactoryId(Long.parseLong(map.get("factoryId").toString()));
				if(map.get("department")!=null)
					dispatchForm.setDepartment(map.get("department").toString());
				if(map.get("guarantee")!=null)
					dispatchForm.setGuarantee(map.get("guarantee").toString());
				if(map.get("equipType")!=null)
					dispatchForm.setEquipType(map.get("equipType").toString());
				if(map.get("equipName")!=null)
					dispatchForm.setEquipName(map.get("equipName").toString());
				if(map.get("guaranteeAddress")!=null)
					dispatchForm.setGuaranteeAddress(map.get("guaranteeAddress").toString());
				if(map.get("contact")!=null)
					dispatchForm.setContact(map.get("contact").toString());
				if(map.get("faultDesp")!=null)
					dispatchForm.setFaultDisp(map.get("faultDesp").toString());
			}
			
		} catch (Exception e) {
			/// log
			return null;
		}
		
		return dispatchForm;
	}
	
	/**
	 * @函数名称: delDispatch
	 * @函数描述: 根据派遣单id删除派遣单记录
	 * @输入参数: @param dispatchId
	 * @输入参数: @return
	 * @返回类型: boolean
	 * @throws
	 */
	public boolean delDispatch(long dispatchId) {
		Session session = null;		
		Transaction transaction = null;
		
		String hql = "delete from " + FrssRepairDispatch.class.getName() + " dis ";
		hql += "where dis.id=" + dispatchId;
		
		try {
			session = BaseDAO.getSession();
			transaction = session.beginTransaction();
			session.createQuery(hql);
			transaction.commit();
			session.close();	
		} catch(Exception e) {
			transaction.rollback();	
			return false;
		}
		
		return true;
	}
	
	/**
	 * @函数名称: updateRepairDispatch
	 * @函数描述: 将故障维修派遣表单记录进行更新
	 * @输入参数: @param updateObj
	 * @输入参数: @return
	 * @返回类型: boolean
	 * @throws
	 */
	public boolean updateRepairDispatch(FrssRepairDispatch updateObj) {
		if(updateObj.getId()<=0) {
			/// log 非法记录
			return false;
		}
		
		Transaction trans = null;
		Session session = null;
		
		try{
			// 首先删除派遣记录
			
			// 然后插入
			
		} catch(Exception e) {
			/// log
			return false;
		}
		
		return true;
	}

	/**
	 * @函数名称: queryRepairDispatch
	 * @函数描述: 根据故障单号查询维修派遣表记录
	 * @输入参数: @param faultId
	 * @输入参数: @return
	 * @返回类型: FrssRepairDispatch
	 * @throws
	 */
	public RepairDispatchForm queryRepairDispatch(long faultId) {
		RepairDispatchForm dispatchForm = null;
		Session session = null;
		
		// 首先查询审核表，查看是否有派遣信息单
		ApprovalDAO appDAO = new ApprovalDAO();
		FrssApprovalInfo appInfo = appDAO.queryApproval(ApprovalDAO.dispatch, faultId, ApprovalDAO.pass);
		if(appInfo==null) {
			// log
			return null;
		}
		
		// 组织hql语句
		String hql = "select dis.id, fa.id, fa.faultDesp, eq.department, eq.equipType, eq.equipName ";
		hql += "from " + FrssRepairDispatch.class.getName() + " dis, " + FrssFaultInfo.class.getName() + " fa, " + 
				FrssEquipmentInfo.class.getName() + " eq ";
		hql += "where fa.id=" + faultId + " and dis.faultId=fa.id  and fa.equipId=eq.id ";
		hql += "order by dis.id";
		
		try {
			// 进行数据库连接
			session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql);
			List<Object[]> lstResult = objQuery.list();
			session.close();
			if (lstResult != null && lstResult.size() > 0) {
				dispatchForm = new RepairDispatchForm();
				
				for( Object[] object : lstResult) {
					if(object[0]!=null)
						dispatchForm.setDispatchId(Long.parseLong(object[0].toString()));
					if(object[1]!=null)
						dispatchForm.setFaultId(Long.parseLong(object[1].toString()));
					if(object[2]!=null)
						dispatchForm.setFaultDisp(object[2].toString());
					if(object[3]!=null)
						dispatchForm.setDepartment(object[3].toString());
					if(object[4]!=null)
						dispatchForm.setEquipType(object[4].toString());
					if(object[5]!=null)
						dispatchForm.setEquipName(object[5].toString());
//					if(object[6]!=null)
//						dispatchForm.setGuarantee(object[6].toString());
//					if(object[7]!=null)
//						dispatchForm.setGuaranteeAddress(object[8].toString());
//					if(object[8]!=null)
//						dispatchForm.setContact(object[8].toString());
				}
				
				dispatchForm.setDispatchTime(appInfo.getCheckTime());
			}
		} catch (Exception e) {
			/// log
			return null;
		}
		
		// 获取工业部门信息 [zuow,2012/05/06]
		try {
			FactoryDAO facDAO = new FactoryDAO();
			if(facDAO!=null&&dispatchForm!=null) {
				String equipType = dispatchForm.getEquipType();
				String equipName = dispatchForm.getEquipName();
				if(equipType==null || equipType.equals("") || equipName==null || equipName.equals("")) {
					/// log
					return dispatchForm;
				}
				
				ArrayList<FrssEquipCreating> arrCreating = facDAO.queryEquipCreating(equipType, equipName);
				
				long factoryId = 0;
				for (FrssEquipCreating equipCreating : arrCreating ) {
					factoryId = equipCreating.getFactoryId();
					if(factoryId>0)
						break;
				}
				
				FrssFactoryInfo facInfo = facDAO.queryFactory(factoryId);
				if(facInfo!=null) {
					dispatchForm.setGuarantee(facInfo.getGuarantee());
					dispatchForm.setGuaranteeAddress(facInfo.getGuardAddress());
					dispatchForm.setContact(facInfo.getContact());
				}				
			}
		} catch (Exception e) {
			return null;
		}
		
		return dispatchForm;
	}

	/**
	 * @函数名称: queryRepairFeedback
	 * @函数描述: 根据故障单号查询反馈信息表
	 * @输入参数: @param faultId
	 * @输入参数: @return
	 * @返回类型: FrssRepairFeedback
	 * @throws
	 */
	public FrssRepairFeedback queryRepairFeedback(long faultId) {
		FrssRepairFeedback repairFeedback = null;
		Session session = null;
		
		String hql = "from " + FrssRepairFeedback.class.getName() + " dis ";
		hql += "where dis.faultId=" + faultId;		

		try {
			// 进行数据库连接
			session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql);
			List lstResult = objQuery.list();
			session.close();
			
			if (lstResult!=null && lstResult.size()>0) {
				repairFeedback = (FrssRepairFeedback) lstResult.get(0);
				
			}
		} catch (Exception e) {
			/// log
			return null;
		}
		
		return repairFeedback;
	}
	
	/**
	 * @函数名称: queryRemoteSupport
	 * @函数描述: 根据故障单编号和反馈表单编号查询远程协助信息表单
	 * @输入参数: @param faultId
	 * @输入参数: @param feedbackId
	 * @输入参数: @return
	 * @返回类型: RemoteSupportForm
	 * @throws
	 */
	public RemoteSupportForm queryRemoteSupport(long faultId, long feedbackId) {
		Session session = null;
		RemoteSupportForm remoteForm = null;
		
		String hql = null;
//		hql = "select re.id, re.channel, re.supportType, re.department, ex.name, ex.contact, ex.contactWay ";
//		hql += "from " + FrssRemoteSupport.class.getName() + " re," + FrssExpertInfo.class.getName() + " ex ";
//		hql += "where re.faultId=" + faultId + " and re.feedbackId=" + feedbackId + " and re.expertId=ex.id ";
//		hql += "order by re.id";
		
		hql = "select re.id, re.channel, re.supportType, re.department ";
		hql += "from " + FrssRemoteSupport.class.getName() + " re ";
		hql += "where re.faultId=" + faultId + " and re.feedbackId=" + feedbackId + " ";
		hql += "order by re.id";		
		
		try {
			session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql);
			List<Object[]> lstResult = objQuery.list();
			session.close();
			
			if(lstResult!=null && lstResult.size()>0) {
				remoteForm = new RemoteSupportForm();
				
				for(Object[] object : lstResult) {
					if(object[0]!=null)
						remoteForm.setRemoteId(Long.parseLong(object[0].toString()));
					if(object[1]!=null)
						remoteForm.setSupportChannel(object[1].toString());
					if(object[2]!=null)
						remoteForm.setSupportType(object[2].toString());
					if(object[3]!=null)
						remoteForm.setDepartment(object[3].toString());
//					if(object[4]!=null)
//						remoteForm.setExpert(object[4].toString());
//					if(object[5]!=null)
//						remoteForm.setContact(object[5].toString());
//					if(object[6]!=null)
//						remoteForm.setContactWay(object[6].toString());
				}
			}
			
		} catch (Exception e) {
			/// log
			return null;
		}
		
		return remoteForm;
	}
	
	/**
	 * @函数名称: queryClientReview
	 * @函数描述: 根据故障单编号查找客户回访单 [zuow,2012/05/08]
	 * @输入参数: @param faultId
	 * @输入参数: @return
	 * @返回类型: FrssClientReview
	 * @throws
	 */
	public FrssClientReview queryClientReview(long faultId) {
		FrssClientReview review = null;
		
		String hql = "from " + FrssClientReview.class.getName() + " re ";
		hql += "where re.faultId=" + faultId;
		
		try {
			Session session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql);
			List lstResult = objQuery.list();
			session.close();
			if (lstResult != null && lstResult.size() > 0) {
				review = (FrssClientReview) lstResult.get(0);
			}
		} catch(Exception e) {
			System.out.print("查询客户回访单失败!");
			return null;
		}
		
		return review;
	}
	
	/////////////////////////////////////////////////////////////////////////	
	// 根据故障单号更新维修派遣表单记录中的反馈单号
	public int updateRepairDispatch(long faultId, long feedbackId) {
		int ret = 0;
		try {
			 StringBuffer hql=new StringBuffer();	
			 Session session = BaseDAO.getSession();
			 hql.append("update ").append(FrssRepairDispatch.class.getName()).append(" t ");
			 hql.append("set t.feedbackId='").append(feedbackId).append("' where t.faultId=").append(faultId);
			 BaseDAO.beginTransaction();
			 Query query = session.createQuery(hql.toString());
			 query.executeUpdate();
			 BaseDAO.commitTransaction();
		} catch (Exception e) {
			ret = 1;
			BaseDAO.rollbackTransaction();
		} finally{			
			BaseDAO.closeSession();
		}		
		
		return ret;
	}

	// 根据故障单号更新远程支援表单记录中的反馈单号
	public int updateRemoteSupport(long faultId, long feedbackId) {
		int ret = 0;
		try {
			 StringBuffer hql=new StringBuffer();	
			 Session session = BaseDAO.getSession();
			 hql.append("update ").append(FrssRemoteSupport.class.getName()).append(" t ");
			 hql.append("set t.feedbackId='").append(feedbackId).append("' where t.faultId=").append(faultId);
			 BaseDAO.beginTransaction();
			 Query query = session.createQuery(hql.toString());
			 query.executeUpdate();
			 BaseDAO.commitTransaction();
		} catch (Exception e) {
			ret = 1;
			BaseDAO.rollbackTransaction();
		} finally{			
			BaseDAO.closeSession();
		}		
		
		return ret;
	}

}
